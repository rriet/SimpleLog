/*
 * Copyright (C) 2019 Ricardo Riet Correa - rietcorrea.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.rietcorrea.simplelog;

import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.csv.ImporterAirport;
import com.rietcorrea.simplelog.csv.ImporterCsvOpener;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author riet
 */
public class ImportAirportController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ComboBox<String> cmbIcao;
    @FXML
    private ComboBox<String> cmbIata;
    @FXML
    private ComboBox<String> cmbName;
    @FXML
    private ComboBox<String> cmbCity;
    @FXML
    private ComboBox<String> cmbCountry;
    @FXML
    private CheckBox chkLatLonError;
    @FXML
    private CheckBox chkIataError;
    @FXML
    private CheckBox chkNameError;
    @FXML
    private CheckBox chkCityError;
    @FXML
    private CheckBox chkCountryError;
    @FXML
    private ComboBox<String> cmbLat;
    @FXML
    private ComboBox<String> cmbLon;
    @FXML
    private CheckBox chkOverride;

    Task<Void> importTask;
    String file;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	// not used
    }

    @FXML
    private void btnImportAction(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ImportAction.fxml"));
            
            // Set locale for fxml translation
        	loader.setResources(MyTranslate.getResourceBundle());

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene((Pane) loader.load()));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            ImportActionController controller = loader.<ImportActionController>getController();

            stage.show();

            controller.progressBar.setProgress(0);
            
            
//            ImporterAirport importerAirport = new ImporterAirport()
//            		.setIcao(cmbIcao.getSelectionModel().getSelectedIndex())
//            		.setIata(cmbIata.getSelectionModel().getSelectedIndex())
//            		.setIgnoreIataError(chkIataError.isSelected())
//            		.setName(cmbName.getSelectionModel().getSelectedIndex())
//            		.setIgnoreNameError(chkNameError.isSelected())
//            		.setCity(cmbCity.getSelectionModel().getSelectedIndex())
//            		.setIgnoreCityError(chkCityError.isSelected())
//            		.setCountry(cmbCountry.getSelectionModel().getSelectedIndex())
//            		.setIgnoreCountryError(chkCountryError.isSelected())
//            		.setLat(cmbLat.getSelectionModel().getSelectedIndex())
//            		.setLon(cmbLon.getSelectionModel().getSelectedIndex())
//            		.setIgnoreLatLonError(chkLatLonError.isSelected())
//            		.setOverride(chkOverride.isSelected());
 
            
            
//            importTask = importerAirport.checkFile(file);
//
//            bindController(controller);
//
//            importTask.setOnSucceeded(e -> {
//            	importTask = importerAirport.importFile(file);
//
//            	bindController(controller);
//
//                importTask.setOnSucceeded(f -> close());
//                new Thread(importTask).start();
//            });
            
            new Thread(importTask).start();
        } catch (IOException ex) {
            Logger.getLogger(AirportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void bindController(ImportActionController controller) {
    	controller.progressBar.progressProperty().unbind();
        controller.progressBar.progressProperty().bind(importTask.progressProperty());

        importTask.messageProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> 
            controller.addText(newValue));
	}

    @FXML
    private void btnCancelAction(ActionEvent event) {
        close();
    }

    public void close() {
        // Close window
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    public void readFile(String selectedFile) {
        this.file = selectedFile;
        
        List<String[]> lineStrings = ImporterCsvOpener.open(file);
        
        ObservableList<String> options = FXCollections.observableArrayList(MyTranslate.text("DontImport"));
        options.addAll(lineStrings.get(0));
        
        cmbIcao.setItems(options);
        cmbIcao.setValue(MyTranslate.text("DontImport"));
        cmbIata.setItems(options);
        cmbIata.setValue(MyTranslate.text("DontImport"));
        cmbName.setItems(options);
        cmbName.setValue(MyTranslate.text("DontImport"));
        cmbCity.setItems(options);
        cmbCity.setValue(MyTranslate.text("DontImport"));
        cmbCountry.setItems(options);
        cmbCountry.setValue(MyTranslate.text("DontImport"));
        cmbLat.setItems(options);
        cmbLat.setValue(MyTranslate.text("DontImport"));
        cmbLon.setItems(options);
        cmbLon.setValue(MyTranslate.text("DontImport"));
    }
}