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

import com.rietcorrea.constants.StrLen;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.converters.IntegerConverter;
import com.rietcorrea.simplelog.database.ModelDAO;
import com.rietcorrea.simplelog.database.ModelDaoImpl;
import com.rietcorrea.simplelog.objects.Model;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 * FXML Controller class
 *
 * @author riet
 */
public class ModelEditController implements Initializable {

    Model model;
    
    @FXML
    private AnchorPane anchorPanel;
    @FXML
    private TextField txtModelName;
    @FXML
    private ComboBox<String> cmbModelGroup;
    @FXML
    private TextField txtMtow;
    @FXML
    private ComboBox<String> cmbEngineType;
    @FXML
    private CheckBox chkMultiPilot;
    @FXML
    private CheckBox chkMultiEngine;
    @FXML
    private CheckBox chkEfis;
    @FXML
    private CheckBox chkSeaplane;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configTextFields();
        initializeCmbModelGroup();
        initializeCmbEngineType();
    }

    void editModel(Model selectedModel) {
        this.model = selectedModel;
        bindFields();
    }

    void newModel() {
        this.model = new Model();
        bindFields();
    }

    private void configTextFields() {
        txtModelName.textProperty().addListener((ov, oldValue, newValue) -> {
            // Capitalize all the ICAO code
            txtModelName.setText(newValue.toUpperCase());
            // Maximum 4 characters
            if (txtModelName.getText().length() > StrLen.MODEL_NAME_MAX) {
                txtModelName.setText(txtModelName.getText().substring(0, StrLen.MODEL_NAME_MAX));
            }
        });

        cmbModelGroup.getEditor().textProperty().addListener((ov, oldValue, newValue) -> {
            // Capitalize all the ICAO code
            cmbModelGroup.setValue(newValue.toUpperCase());
            // Maximum 4 characters
            if (cmbModelGroup.getValue().length() > StrLen.MODEL_GROUP) {
                cmbModelGroup.setValue(cmbModelGroup.getValue().substring(0, StrLen.MODEL_GROUP));
            }
        });

        // Set validator decorations
        ValidationSupport vs = new ValidationSupport();
        vs.registerValidator(txtModelName, Validator.createRegexValidator(
                "Model must contain between 2 and "+StrLen.MODEL_NAME_MAX+" characters", 
                "^[^\\s]{2,"+StrLen.MODEL_NAME_MAX+"}$", Severity.ERROR));
        
        vs.registerValidator(cmbModelGroup, Validator.createRegexValidator(
                "Model Group must contain between 2 and "+StrLen.MODEL_GROUP+" characters", 
                "^[^\\s]{2,"+StrLen.MODEL_GROUP+"}$", Severity.ERROR));

        // initialize validator
        vs.initInitialDecoration();
    }

    private void initializeCmbModelGroup() {
        // Initialize values on ModelGroup ComboBox from database
        ModelDAO modelDao = new ModelDaoImpl();
        ObservableList<String> modelList = FXCollections.observableList(modelDao.getAllModelGroups());
        cmbModelGroup.setItems(modelList);
    }

    private void initializeCmbEngineType() {
        // Initialize values on EngineType ComboBox
        ObservableList<String> engineTypeList = FXCollections.observableArrayList(
                "Turbofan", "Turboprop", "Piston", "Glider"
        );
        cmbEngineType.setItems(engineTypeList);
        cmbEngineType.setValue("Turbofan");
    }

    private void bindFields() {
        Bindings.bindBidirectional(txtModelName.textProperty(), model.modelNameProperty());
        Bindings.bindBidirectional(txtMtow.textProperty(), model.mtowProperty(), new IntegerConverter());
        Bindings.bindBidirectional(cmbModelGroup.valueProperty(), model.modelGroupProperty());
        Bindings.bindBidirectional(cmbEngineType.valueProperty(), model.engineTypeProperty());
        Bindings.bindBidirectional(chkMultiPilot.selectedProperty(), model.multiPilotProperty());
        Bindings.bindBidirectional(chkMultiEngine.selectedProperty(), model.multiEngineProperty());
        Bindings.bindBidirectional(chkEfis.selectedProperty(), model.efisProperty());
        Bindings.bindBidirectional(chkSeaplane.selectedProperty(), model.seaplaneProperty());
    }

    @FXML
    private void btnSaveAction(ActionEvent event) {
        if (validModel()) {
            Stage stage = (Stage) anchorPanel.getScene().getWindow();
            ModelDAO modelDao = new ModelDaoImpl();

            // Determines is is a new Model or editing a existing one and if it's duplicated
            boolean modelExist = modelDao.modelExist(model);
            if (model.getModelId() == 0 && !modelExist) {
            		modelDao.insertModel(model);
            		stage.close();
            } else if (!modelExist || model.getModelId().equals(modelDao.getModel(model.getModelName()).getModelId())) {
            	modelDao.updateModel(model);
                stage.close();
            }else {
        		Alert alert = new Alert(Alert.AlertType.ERROR);
        		alert.setTitle(MyTranslate.text("ModelExistTitle"));
        		alert.setHeaderText(MyTranslate.text("ModelExistMessage"));
        		alert.showAndWait();
			}
        }
    }

    @FXML
    private void btnCancelAction(ActionEvent event) {
        Stage stage = (Stage) anchorPanel.getScene().getWindow();
        stage.close();
    }

    private boolean validModel() {
        return (cmbModelGroup.getValue().length() >= 2 && txtModelName.getText().length() >= 2);
    }

}
