/*
 * Copyright (C) 2018 Ricardo Riet Correa - rietcorrea.com
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

import com.rietcorrea.constants.StrLen;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.converters.IntegerConverter;
import com.rietcorrea.simplelog.database.AircraftDAO;
import com.rietcorrea.simplelog.database.AircraftDaoImpl;
import com.rietcorrea.simplelog.database.ModelDAO;
import com.rietcorrea.simplelog.database.ModelDaoImpl;
import com.rietcorrea.simplelog.objects.Aircraft;
import com.rietcorrea.simplelog.objects.Model;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.util.logging.resources.logging;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 * FXML Controller class
 *
 * @author riet
 */
public class AircraftEditController implements Initializable {

    private Aircraft aircraft;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField txtRegistration;
    @FXML
    private CheckBox chkSimulator;
    @FXML
    private ComboBox<Model> cmbModel;
    @FXML
    private TextField txtMtow;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCmbModel();
        
        txtRegistration.textProperty().addListener((ov, oldValue, newValue) -> {
            // Capitalize all the registration
        	txtRegistration.setText(newValue.toUpperCase());
            // Maximum 4 characters
            if (txtRegistration.getText().length() > StrLen.AIRCRAFT_REGISTRATION_MAX) {
            	txtRegistration.setText(txtRegistration.getText().substring(0, StrLen.AIRCRAFT_REGISTRATION_MAX));
            }
        });

        // Set validator decorations
        ValidationSupport vs = new ValidationSupport();
        vs.registerValidator(txtRegistration, Validator.createRegexValidator(
        		MyTranslate.formated("ErrorRegistrationLenght", 
        				new Object[] {StrLen.AIRCRAFT_REGISTRATION_MIN,StrLen.AIRCRAFT_REGISTRATION_MAX}),
                "^[^\\s]{"+StrLen.AIRCRAFT_REGISTRATION_MIN+","+StrLen.AIRCRAFT_REGISTRATION_MAX+"}$", Severity.ERROR));
        
        vs.registerValidator(cmbModel, Validator.createEmptyValidator( "Model Selection required"));
        
        // initialize validator
        vs.initInitialDecoration();
    }

    void newAircraft() {
        this.aircraft = new Aircraft();
        bindFields();
    }

    void editAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
        bindFields();
    }

    private void bindFields() {
        Bindings.bindBidirectional(txtRegistration.textProperty(), aircraft.registrationProperty());
        Bindings.bindBidirectional(chkSimulator.selectedProperty(), aircraft.simulatorProperty());
        cmbModel.setValue(aircraft.getAircraftModel());
        Bindings.bindBidirectional(txtMtow.textProperty(), aircraft.aircraftMtowProperty(), new IntegerConverter());
    }

    @FXML
    private void brnSaveAction(ActionEvent event) {
        if (validAircraft()) {
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            AircraftDAO aircraftDao = new AircraftDaoImpl();

            if (cmbModel.getValue().getModelId() != 0){
                // Determines is is a new Aircraft or editing a existing one and if it's duplicated
            	boolean aircraftExist = aircraftDao.aircraftExist(aircraft);
                if (aircraft.getAircraftId() == 0 && !aircraftExist) {
	                    aircraftDao.insertAircraft(aircraft);
	                    stage.close();
                } else if (!aircraftExist || aircraft.getAircraftId().equals(aircraftDao.getAircraft(aircraft.getRegistration()).getAircraftId())) {
                    aircraftDao.updateAircraft(aircraft);
                    stage.close();
                } else {
            		errorAlert(MyTranslate.text("AircraftExistTitle"), MyTranslate.text("AircraftExistMessage"));
            	}
            } else {
            	errorAlert(MyTranslate.text("MissingModelTitle"), MyTranslate.text("MissingModelMessage"));
            }
            
        }
    }
    
    private void errorAlert(String titleString, String messageString) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(titleString);
		alert.setHeaderText(messageString);
		alert.showAndWait();
	}

    @FXML
    private void btnCancelAction(ActionEvent event) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    private void initializeCmbModel() {
        // Initialize values on ModelGroup ComboBox from database
        ModelDAO modelDao = new ModelDaoImpl();
        ObservableList<Model> modelList = FXCollections.observableList(modelDao.getAllModels());
        cmbModel.setItems(modelList);
    }

    private boolean validAircraft() {
    	return aircraft.getRegistration().matches("^[^\\s]{"+StrLen.AIRCRAFT_REGISTRATION_MIN+","+StrLen.AIRCRAFT_REGISTRATION_MAX+"}$");
    }

    @FXML
    private void cmbModelAction(ActionEvent event) {

        // If the MTOW is not set, set it to the Model MTOW.
        // If it's already set, ask if the user wants to update
        if (txtMtow.getText().equals("0")) {
            txtMtow.setText(cmbModel.getValue().getMtow().toString());
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(MyTranslate.text("ModifiedMtow"));
            
            alert.setHeaderText(MyTranslate.formated("TheSelectedMtow", 
            		new Object[] {txtMtow.getText(), cmbModel.getValue().getMtow().toString()}));
            alert.setContentText(MyTranslate.text("WouldYouLikeUpdate"));

            Stage stage = (Stage) anchorPane.getScene().getWindow();
            alert.initOwner(stage);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
            	txtMtow.setText(cmbModel.getValue().getMtow().toString());
			}
        }

        //set the model to the new selected model
        aircraft.setAircraftModel(cmbModel.getValue());
    }

}
