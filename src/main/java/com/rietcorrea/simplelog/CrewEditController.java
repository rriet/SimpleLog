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
import com.rietcorrea.simplelog.database.CrewDAO;
import com.rietcorrea.simplelog.database.CrewDaoImpl;
import com.rietcorrea.simplelog.objects.Crew;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
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
public class CrewEditController implements Initializable {

    Crew crew;
    
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextArea txtComments;
    @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Set validator decorations
        ValidationSupport vs = new ValidationSupport();
        vs.registerValidator(txtName, Validator.createRegexValidator(
        		MyTranslate.formated("CrewNameMustContain", new Object[] {StrLen.CREW_NAME_MIN, StrLen.CREW_NAME_MAX}),
                "[a-zA-Z0-9_\\-\\s\\(\\)\\[\\]]{"+ StrLen.CREW_NAME_MIN + ","+StrLen.CREW_NAME_MAX+"}$", Severity.ERROR));
        
        // initialize validator
        vs.initInitialDecoration();
        
        // Limit size of text feilds
        txtName.textProperty().addListener((ov, oldValue, newValue) -> {
            // Maximum characters
            if (txtName.getText().length() > StrLen.CREW_NAME_MAX) {
            	txtName.setText(txtName.getText().substring(0, StrLen.CREW_NAME_MAX));
            }
        });
        
        txtEmail.textProperty().addListener((ov, oldValue, newValue) -> {
            // Maximum characters
            if (txtEmail.getText().length() > StrLen.CREW_EMAIL) {
            	txtEmail.setText(txtEmail.getText().substring(0, StrLen.CREW_EMAIL));
            }
        });
        
        txtPhone.textProperty().addListener((ov, oldValue, newValue) -> {
            // Maximum characters
            if (txtPhone.getText().length() > StrLen.CREW_PHONE) {
            	txtPhone.setText(txtPhone.getText().substring(0, StrLen.CREW_PHONE));
            }
        });
        
        txtComments.textProperty().addListener((ov, oldValue, newValue) -> {
            // Maximum characters
            if (txtComments.getText().length() > StrLen.CREW_COMMENTS) {
            	txtComments.setText(txtComments.getText().substring(0, StrLen.CREW_COMMENTS));
            }
        });
    }    

    void newAirport() {
        this.crew = new Crew();
        bindFields();
        
    }

    void editAirport(Crew selectedCrew) {
        this.crew = selectedCrew;
        bindFields();
    }

    @FXML
    private void btnSaveAction(ActionEvent event) {
        if (validCrew()) {
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            CrewDAO crewDao = new CrewDaoImpl();

            boolean crewExists = crewDao.crewExist(crew.getCrewName());
            // Determines is is a new Crew or editing a existing one
            if (crew.getCrewId()== 0 && !crewExists) {
                crewDao.insertCrew(crew);
                stage.close();
            } else if (!crewExists || crew.getCrewId().equals(crewDao.getCrew(crew.getCrewName()).getCrewId())) {
                crewDao.updateCrew(crew);
                stage.close();
            } else {
            	Alert alert = new Alert(Alert.AlertType.ERROR);
        		alert.setTitle(MyTranslate.text("CrewExistTitle"));
        		alert.setHeaderText(MyTranslate.text("CrewExistMessage"));
        		alert.showAndWait();
            }
        }
    }

    @FXML
    private void btnCancelAction(ActionEvent event) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }
    
    private void bindFields() {
        Bindings.bindBidirectional(txtName.textProperty(), crew.crewNameProperty());
        Bindings.bindBidirectional(txtEmail.textProperty(), crew.emailProperty());
        Bindings.bindBidirectional(txtPhone.textProperty(), crew.phoneProperty());
        Bindings.bindBidirectional(txtComments.textProperty(), crew.commentsProperty());
    }
    
    private boolean validCrew() {
    	return crew.getCrewName().matches("[a-zA-Z0-9_\\-\\s\\(\\)\\[\\]]{"+StrLen.CREW_NAME_MIN+","+StrLen.CREW_NAME_MAX+"}$"); 
    }
    
}
