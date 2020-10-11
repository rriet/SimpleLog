package com.rietcorrea.simplelog;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

import com.rietcorrea.simplelog.converters.EpochMilliToDateString;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManageBackupController implements Initializable { // NO_UCD (unused code)

	@FXML
    private VBox vbox;
	@FXML
    private Label lblBackupLocation;
	@FXML
    private ListView<FileInfo> lvFiles;
	@FXML
    private CheckBox chkBackupCurrent;
	@FXML
    private Button btnRestore;
    @FXML
    private Button btnDelete;
    
    private String backupPathString;
    private String databasepathString;
    
    @Override
	public void initialize(URL url, ResourceBundle rb) {
    	
    	// get database Path and backup path
    	UserPreferences pref = new UserPreferences();
        databasepathString = pref.getDbPath();
        backupPathString = System.getProperty("user.home") + File.separatorChar + "SimpleLogBackup";
        
        lblBackupLocation.setText(backupPathString + File.separatorChar);
        
        lvFiles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        // Disable edit and delete buttons if no line is selected
        lvFiles.getSelectionModel().selectedItemProperty().addListener((obs, previousAirport, selectedAirport) -> activateButtons());
        
        chkBackupCurrent.setSelected(pref.getBackupOnRestore());
        
        populateList();
	}
    
    private void populateList() {
    	try {
	        File file = new File(backupPathString);
	        // make new directory if it doesn't exist...
	        if (!file.exists()) { 
	        	file.mkdir();
	        } else {
	        	String[] fileLiStrings = file.list();
	        	Arrays.sort(fileLiStrings);
	        	lvFiles.getItems().clear();
	        	for (String fileName : fileLiStrings) {
	        		FileInfo fileInfo = new FileInfo();
	        		fileInfo.setFileName(fileName, backupPathString);
	        		lvFiles.getItems().addAll(fileInfo);
				}
			}
	        if(file.list().length == 0){
	        	btnDelete.setDisable(true);
	        	btnRestore.setDisable(true);
    		}
        } catch (Exception e) {
			// ignore write exception
		}
	}
    
    private void activateButtons() {
    	ObservableList<FileInfo> selectedItems = lvFiles.getSelectionModel().getSelectedItems();
		if (selectedItems.isEmpty()) {
			btnRestore.setDisable(true);
			btnDelete.setDisable(true);
		} else if (selectedItems.size() == 1) {
			btnRestore.setDisable(false);
			btnDelete.setDisable(false);
		} else {
			btnRestore.setDisable(true);
			btnDelete.setDisable(false);
		}
	}

    @FXML
    void btnCloseAction(ActionEvent event) {
    	Stage stage = (Stage) vbox.getScene().getWindow();
		stage.close();
    }

    @FXML
    void btnDeleteAction(ActionEvent event) {
    	ObservableList<FileInfo> selectedFiles = lvFiles.getSelectionModel().getSelectedItems();
		if (!selectedFiles.isEmpty()) {
			for (FileInfo selectedFile : selectedFiles) {
				File file = new File(backupPathString + File.separatorChar + selectedFile.getFileName());
				file.delete();
			}
			
			populateList();
    	}
    }

    @FXML
    void btnRestoreAction(ActionEvent event) {
    	ObservableList<FileInfo> selectedFiles = lvFiles.getSelectionModel().getSelectedItems();
    	if (selectedFiles.size() == 1) {
	        try {
	        	if (chkBackupCurrent.isSelected()) {
		    		Path srcPath = Paths.get(databasepathString);
		            Path destPath = Paths.get(backupPathString + File.separatorChar + Instant.now().toEpochMilli());
		        		
					FileUtils.copyFile(srcPath.toFile(), destPath.toFile());
	        	}
	        	
	        	Path srcPath = Paths.get(backupPathString + File.separatorChar + selectedFiles.get(0).getFileName());
	        	Path destPath = Paths.get(databasepathString);
	            
	        	FileUtils.copyFile(srcPath.toFile(), destPath.toFile());
	        	
	        	
	        	Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Backup Restored");
				alert.setHeaderText("Database backup was restored successfully.");
				alert.initModality(Modality.WINDOW_MODAL);
				alert.show();
				
				Stage stage = (Stage) vbox.getScene().getWindow();
				stage.close();
	
				
			} catch (IOException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Backup error!");
				alert.setHeaderText("Error during backup operation.");
				alert.setContentText(e.toString());
				alert.initModality(Modality.WINDOW_MODAL);
				alert.showAndWait();
			}
    	}
    }
    
    @FXML
    void chkBackupCurrentAction(ActionEvent event) {
    	UserPreferences pref = new UserPreferences();
    	pref.setBackupOnRestore(chkBackupCurrent.isSelected());
    }
}

class FileInfo {
	private String fileNameString = "";
	private String fileSizeString = "";
	
	public void setFileName(String fileNameString, String backupPathString) {
		this.fileNameString = fileNameString;
		
		File file =new File(backupPathString + File.separatorChar + fileNameString);
		
		if(file.exists()){
			this.fileSizeString = Integer.toString( (int) file.length() / 1024);
		}
	}
	
	public String getFileName() {
		return fileNameString;
	}
	
	public String toString() {
		EpochMilliToDateString pDateString = new EpochMilliToDateString();
		return "Backup " + pDateString.toString(Long.valueOf(fileNameString)) + " UTC, File size: " + fileSizeString + " kb";
	}
}


