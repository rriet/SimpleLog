/*
*    Simple Log - Pilot logbook software
*    Copyright (C) 2018  Ricardo Brito Riet Correa
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.rietcorrea.simplelog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import com.rietcorrea.constants.StrEng;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author riet
 */
public class ImportExportController implements Initializable { // NO_UCD (unused code)

    @FXML
    private TextField txtDatabaseLocation;
    @FXML
    private Button btnSelectDatabase;
    @FXML
    private Button btnRestoreBackup;
    @FXML
    private Button btnBackup;
    @FXML
    private ComboBox<String> cmbImportCsv;
    @FXML
    private Button btnImportCsv;
    @FXML
    private CheckBox chkBackupDatabase;
    @FXML
    private Label lblImportDescription;
    @FXML
    private ComboBox<String> cmbExportCsv;
    @FXML
    private Button btnExportCsv;
    @FXML
    private Label blbDefaultDatabaseLocation;

    UserPreferences pref;
    private String backupPathString;
    private String databasepathString;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCmbOptions();

        pref = new UserPreferences();
        
        // check the backup checkbox
        chkBackupDatabase.setSelected(pref.getBackupOnImport());
        
        blbDefaultDatabaseLocation.setText("Default database location: '"
                + System.getProperty("user.home") + File.separatorChar +"SimpleLog.db'");
        
        databasepathString = pref.getDbPath();
        txtDatabaseLocation.setText(databasepathString);
        
        backupPathString = System.getProperty("user.home") + File.separatorChar + "SimpleLogBackup";
        
        resetBackupButtons();
    }
    
    private void resetBackupButtons() {
    	try {
	        File file = new File(backupPathString);
	        // make new directory if it doesn't exist...
	        if (!file.exists()) { 
	        	file.mkdir();
	        } else {
	        	// Enable backup restore if directory is not empty
	        	btnRestoreBackup.setDisable(file.list().length == 0);
			}
        } catch (Exception e) {
			// ignore write exception, just disable backup button
        	btnBackup.setDisable(true);
		}
	}

    private void initializeCmbOptions() {
        // Initialize values on Export ComboBox
        ObservableList<String> options = FXCollections.observableArrayList(
                StrEng.SIMPLELOG,
                StrEng.FLIGHTS,
                StrEng.AIRCRAFTS,
                StrEng.MODELS,
                StrEng.AIRPORTS,
                StrEng.CREW
        );
        cmbExportCsv.setItems(options);
        cmbExportCsv.setValue("SimpleLog");
        
        // Initialize values on Import ComboBox
        options = FXCollections.observableArrayList(
        		StrEng.SIMPLELOG,
                StrEng.FLIGHTS,
                StrEng.AIRPORTS_WEB,
                StrEng.QATAR_AIRWAYS_XLSX,
                StrEng.MCC_PILOT_LOG
        );
        cmbImportCsv.setItems(options);
        cmbImportCsv.setValue(StrEng.FLIGHTS);
    }

    @FXML
    private void btnSelectDatabaseClicked(ActionEvent event) {
        selectDatabaseLocation();
    }

    @FXML
    private void txtDatabaseLocationClicked(MouseEvent event) {
        selectDatabaseLocation();
    }
    
    @FXML
    void createBackupClicked(ActionEvent event) {
    	createBackup();
    	
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Backup Created");
		alert.setHeaderText("Database backup created successfully.");
		alert.showAndWait();
    }
    
    private void createBackup() {
    	Path srcPath = Paths.get(databasepathString);
        Path destPath = Paths.get(backupPathString + File.separatorChar + Instant.now().toEpochMilli());
        try {
			FileUtils.copyFile(srcPath.toFile(), destPath.toFile());
			btnRestoreBackup.setDisable(false);
			
			resetBackupButtons();
			
		} catch (IOException e) {
			// log error
			Logger.getLogger(AirportController.class.getName()).log(Level.SEVERE, null, e);
		}
	}

    @FXML
    void restoreBackupClicked(ActionEvent event) {
    	try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ManageBackup.fxml"));
			
            // Set locale for fxml translation
        	loader.setResources(MyTranslate.getResourceBundle());

			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene((Pane) loader.load()));
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);

			stage.setTitle(MyTranslate.text("ManageBackup"));
			stage.showAndWait();
			
			resetBackupButtons();
			
		} catch (IOException ex) {
			Logger.getLogger(AirportController.class.getName()).log(Level.SEVERE, null, ex);
		}
    }

    @FXML
    private void btnExportCsvClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        
        //Set extension filter for text files
        fileChooser.getExtensionFilters().add(
        		new ExtensionFilter("CSV files (*.csv)", "*.csv"));
        
        fileChooser.setInitialFileName(cmbExportCsv.getValue().concat(".csv"));
        
        Stage stage = (Stage) btnExportCsv.getScene().getWindow();
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            Export.export(cmbExportCsv.getValue(), file.getPath());
        }
    }

    @FXML
    private void btnImportCsvClick(ActionEvent event) {
    	if(cmbImportCsv.getValue().equals(StrEng.AIRPORTS_WEB)) {
    		downloadAirports();
    	} else {
    		importFile();
    	}
    }
    
    /**
     *  Display airport download dialog
     */
    private void downloadAirports() {
    	if (chkBackupDatabase.isSelected()) {
    		createBackup();
		}
    	
    	try {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AirportsDownload.fxml"));
	        
	        // Set locale for fxml translation
	    	loader.setResources(MyTranslate.getResourceBundle());
	
	        Stage stage = new Stage(StageStyle.DECORATED);
	        stage.setScene(new Scene((Pane) loader.load()));
	        stage.setResizable(false);
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.showAndWait();
	        
	    } catch (IOException ex) {
	        Logger.getLogger(AirportController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    
    /**
     * Prompt for file to import and if a file selected, display import Dialog
     */
    private void importFile() {
    	FileChooser fileChooser = new FileChooser();
    	
    	if (cmbImportCsv.getValue().equals(StrEng.QATAR_AIRWAYS_XLSX)) {
    		//Set extension filter for text files
    		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"));
    	} else {
    		//Set extension filter for text files
            fileChooser.getExtensionFilters().addAll(
        			new ExtensionFilter("CSV files (*.csv)", "*.csv"),
        			new ExtensionFilter("Text files (*.txt)", "*.txt"));
    	}
    	
        Stage stage = (Stage) btnImportCsv.getScene().getWindow();
        //Show save file dialog
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
        	if (chkBackupDatabase.isSelected()) {
        		createBackup();
    		}
            new ImportDialog(file.getPath(), cmbImportCsv.getValue());
        }
	}
    
    @FXML
    void cmbImportCsvAction(ActionEvent event) {
    	switch (cmbImportCsv.getValue()) {
    		case StrEng.SIMPLELOG:
				lblImportDescription.setText(MyTranslate.text("ImportSimpleLogDesc"));
				break;
    		case StrEng.QATAR_AIRWAYS_XLSX:
				lblImportDescription.setText(MyTranslate.text("ImportQrDesc"));
				break;
    		case StrEng.MCC_PILOT_LOG:
				lblImportDescription.setText(MyTranslate.text("ImportMccDesc"));
				break;
    		default:
				lblImportDescription.setText(MyTranslate.text("ImportFlightDesc"));
				break;
    	}
    }
    
    @FXML
    void chkBackupDatabaseAction(ActionEvent event) {
    	pref.setBackupOnImport(chkBackupDatabase.isSelected());
    }

    private void selectDatabaseLocation() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        fileChooser.getExtensionFilters().add(
        		new ExtensionFilter("SQL files (*.db)", "*.db"));
        
        fileChooser.setInitialFileName(StrEng.DB_FILE_NAME);
        
        Stage stage = (Stage) btnSelectDatabase.getScene().getWindow();

        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            pref.setDbPath(file.getPath());
            txtDatabaseLocation.setText(file.getPath());
        }
    }
}
