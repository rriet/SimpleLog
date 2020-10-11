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
import com.rietcorrea.simplelog.csv.ImporterCrew;
import com.rietcorrea.simplelog.csv.ImporterCsvOpener;
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
public class ImportCrewController implements Initializable {

    @FXML
    private ComboBox<String> cmbName;
    @FXML
    private ComboBox<String> cmbEmail;
    @FXML
    private ComboBox<String> cmbPhone;
    @FXML
    private ComboBox<String> cmbComments;
    @FXML
    private CheckBox chkIgnoreName;
    @FXML
    private CheckBox chkIgnoreEmail;
    @FXML
    private CheckBox chkIgnorePhone;
    @FXML
    private CheckBox chkIgnoreComments;
    @FXML
    private CheckBox chkOverride;
    @FXML
    private AnchorPane anchorPane;

    Task<Void> importTask;
    String file;
    @FXML
    private ComboBox<String> cmbNameFormat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
// Initialize values on EngineType ComboBox
        ObservableList<String> options = FXCollections.observableArrayList(
                MyTranslate.text("PilotNameUnchanged"),
                MyTranslate.text("PilotNameUpperFirst"),
                MyTranslate.text("PilotNameUppercase"),
                MyTranslate.text("PilotNameLowercase")
        );
        
        cmbNameFormat.setItems(options);
        cmbNameFormat.setValue(MyTranslate.text("PilotNameUnchanged"));
    }

    public void readFile(String selectedFile) {
        this.file = selectedFile;
        List<String[]> lineStrings = ImporterCsvOpener.open(file);
        
        ObservableList<String> options = FXCollections.observableArrayList(MyTranslate.text("DontImport"));
        options.addAll(lineStrings.get(0));
    
        cmbName.setItems(options);
        cmbName.setValue(MyTranslate.text("DontImport"));
        cmbEmail.setItems(options);
        cmbEmail.setValue(MyTranslate.text("DontImport"));
        cmbPhone.setItems(options);
        cmbPhone.setValue(MyTranslate.text("DontImport"));
        cmbComments.setItems(options);
        cmbComments.setValue(MyTranslate.text("DontImport"));
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

            ImporterCrew importerCrew = new ImporterCrew()
    				.setName(cmbName.getSelectionModel().getSelectedIndex())
    				.setIgnoreNameError(chkIgnoreName.isSelected())
    				.setNameFormat(cmbNameFormat.getValue())
    				.setEmail(cmbEmail.getSelectionModel().getSelectedIndex())
    				.setIgnoreEmailError(chkIgnoreEmail.isSelected())
    				.setPhone(cmbPhone.getSelectionModel().getSelectedIndex())
    				.setIgnorePhoneError(chkIgnorePhone.isSelected())
    				.setComments(cmbComments.getSelectionModel().getSelectedIndex())
    				.setIgnoreCommentsError(chkIgnoreComments.isSelected())
    				.setOverride(chkOverride.isSelected());
            
            importTask = importerCrew.checkFile(file);

            bindController(controller);

            importTask.setOnSucceeded(e -> {
            	importTask = importerCrew.importFile(file);

            	bindController(controller);

                importTask.setOnSucceeded(f -> close());
                new Thread(importTask).start();
            });

            new Thread(importTask).start();
        } catch (Exception ex) {
            Logger.getLogger(AirportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void bindController(ImportActionController controller) {
    	controller.progressBar.progressProperty().unbind();
        controller.progressBar.progressProperty().bind(importTask.progressProperty());

        importTask.messageProperty().addListener(
        		(ObservableValue<? extends String> observable, String oldValue, String newValue) -> controller.addText(newValue));
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
}
