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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import com.rietcorrea.constants.AppSettings;
import com.rietcorrea.constants.AppStyle;
import com.rietcorrea.constants.Version;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainSceneController implements Initializable { // NO_UCD (unused code)
    
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button btnFlights;
    @FXML
    private Button btnAircrafts;
    @FXML
    private Button btnAirports;
    @FXML
    private Button btnCrew;
    @FXML
    private Button btnTotals;
    @FXML
    private Button btnReports;
    @FXML
    private Button btnImportExport;
    @FXML
    private Button btnPreferences;
    @FXML
    private AnchorPane paneMain;
    @FXML
    private Label lblVersion;
    @FXML
    private Label lblLatestVersionTitle;
    @FXML
    private Label lblLatestVersion;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnFlights.setStyle(AppStyle.SELECTED_BUTTON);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Flight.fxml"));
        loadView(loader);
        
        lblVersion.setText(Version.VERSION_NUMBER);
        
        lblLatestVersionTitle.setVisible(false);
        lblLatestVersion.setVisible(false);
        
        
        Task<String> checker = checkOnlineVersion();
        
        checker.setOnSucceeded(e -> {
        		if (checker.getValue() != null) {
        			lblLatestVersionTitle.setVisible(true);
        	        lblLatestVersion.setVisible(true);
        	        lblLatestVersion.setText(checker.getValue());
        		}
        	});
        
        
        
        // check the version of the last software on background not to slowdown execution
        new Thread(checker).start();
    }    

    public Task<String> checkOnlineVersion() {
		return new Task<String>() {
			@Override
			protected String call() {
		    	try (BufferedReader reader = new BufferedReader(
		    			new InputStreamReader(
		    					new URL(AppSettings.VERSION_URL).openStream()))){
		    		
		            String version = reader.readLine();
		
		            if (!version.equals(Version.VERSION_NUMBER)) {
		            	return version;
		            }
		        } catch (Exception e) {
		        	LogException.getMessage(e);
		        	return null;
		        }
		    	return null;
			}
		};
	}
    
    @FXML
    private void btnConfigureAction(ActionEvent event) {
        // make all buttons look unselected
        clearButtons();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Preferences.fxml"));
        loadView(loader);
    }

    @FXML
    private void menuClick(ActionEvent event) {
        // make all buttons look unselected
        clearButtons();
        
        
        if (event.getSource()==btnFlights){
            btnFlights.setStyle(AppStyle.SELECTED_BUTTON);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Flight.fxml"));
            loadView(loader);
        }
        if (event.getSource()==btnAircrafts){
            btnAircrafts.setStyle(AppStyle.SELECTED_BUTTON);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Aircraft.fxml"));
            loadView(loader);
        }
        if (event.getSource()==btnAirports){
            btnAirports.setStyle(AppStyle.SELECTED_BUTTON);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Airport.fxml"));
            loadView(loader);
        }
        if (event.getSource()==btnCrew){
            btnCrew.setStyle(AppStyle.SELECTED_BUTTON);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Crew.fxml"));
            loadView(loader);
        }
        if (event.getSource()==btnTotals){
            btnTotals.setStyle(AppStyle.SELECTED_BUTTON);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Totals.fxml"));
            loadView(loader);
        }
        if (event.getSource()==btnReports){
            btnReports.setStyle(AppStyle.SELECTED_BUTTON);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Reports.fxml"));
            loadView(loader);
        }
        if (event.getSource()==btnImportExport){
            btnImportExport.setStyle(AppStyle.SELECTED_BUTTON);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ImportExport.fxml"));
            loadView(loader);
        }
    }
    
    private void loadView(FXMLLoader loader){
        try {
        	// Set locale for fxml translation
        	loader.setResources(MyTranslate.getResourceBundle());
            AnchorPane pane = loader.load();
            pane.getStylesheets().add(getClass().getResource("/styles/Styles.css").toExternalForm());
            borderPane.setCenter(pane);
            
        } catch (IOException ex) {
            LogException.getMessage(ex);
        }
    }
    
    private void clearButtons(){
        // Set all background to notmal
        btnFlights.setStyle(AppStyle.INACTIVE_BUTTON);
        btnAircrafts.setStyle(AppStyle.INACTIVE_BUTTON);
        btnAirports.setStyle(AppStyle.INACTIVE_BUTTON);
        btnCrew.setStyle(AppStyle.INACTIVE_BUTTON);
        btnTotals.setStyle(AppStyle.INACTIVE_BUTTON);
        btnReports.setStyle(AppStyle.INACTIVE_BUTTON);
        btnImportExport.setStyle(AppStyle.INACTIVE_BUTTON);
    }
}
