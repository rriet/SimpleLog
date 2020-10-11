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
import com.rietcorrea.controls.LatitudeField;
import com.rietcorrea.controls.LongitudeField;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.converters.LatitudeStringConverter;
import com.rietcorrea.simplelog.converters.LongitudeStringConverter;
import com.rietcorrea.simplelog.database.AirportDAO;
import com.rietcorrea.simplelog.database.AirportDaoImpl;
import com.rietcorrea.simplelog.objects.Airport;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.text.WordUtils;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 * FXML Controller class
 *
 * @author riet
 */
public class AirportEditController implements Initializable {

    private Airport airport;

    @FXML
    private TextField txtIcao;
    @FXML
    private TextField txtIata;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtCountry;
    @FXML
    private LatitudeField txtLatitude;
    @FXML
    private LongitudeField txtLongitude;
    @FXML
    private AnchorPane anchorPanel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configTextFields();
    }

    void editAirport(Airport selectedAirport) {
        this.airport = selectedAirport;
        bindFields();
    }

    void newAirport() {
        airport = new Airport();
        bindFields();
    }

    private void bindFields() {
        Bindings.bindBidirectional(txtIcao.textProperty(), airport.icaoProperty());
        Bindings.bindBidirectional(txtIata.textProperty(), airport.iataProperty());
        Bindings.bindBidirectional(txtName.textProperty(), airport.airportNameProperty());
        Bindings.bindBidirectional(txtCity.textProperty(), airport.airportCityProperty());
        Bindings.bindBidirectional(txtCountry.textProperty(), airport.airportCountryProperty());
        Bindings.bindBidirectional(txtLatitude.textProperty(), airport.latitudeProperty(), new LatitudeStringConverter());
        Bindings.bindBidirectional(txtLongitude.textProperty(), airport.longitudeProperty(), new LongitudeStringConverter());
    }

    // Set maximum chars and capitalization rules for text fields
    // Sets validation to correspondent field
    private void configTextFields() {
        txtIcao.textProperty().addListener((ov, oldValue, newValue) -> {
            // Capitalize all the ICAO code
            txtIcao.setText(newValue.toUpperCase());
            // Maximum 4 characters
            if (txtIcao.getText().length() > StrLen.AIRPORT_ICAO) {
                txtIcao.setText(txtIcao.getText().substring(0, StrLen.AIRPORT_ICAO));
            }
        });

        txtIata.textProperty().addListener((ov, oldValue, newValue) -> {
            // Capitalize all the IATA code
            txtIata.setText(newValue.toUpperCase());
            // Maximum 3 characters
            if (txtIata.getText().length() > StrLen.AIRPORT_IATA) {
                txtIata.setText(txtIata.getText().substring(0, StrLen.AIRPORT_IATA));
            }
        });

        txtName.textProperty().addListener((ov, oldValue, newValue) -> {
            //Captalize the first letter of each word
            txtName.setText(WordUtils.capitalize(txtName.getText()));
            // Maximum 50 characters
            if (txtName.getText().length() > StrLen.AIRPORT_NAME) {
                txtName.setText(txtName.getText().substring(0, StrLen.AIRPORT_NAME));
            }
        });

        txtCity.textProperty().addListener((ov, oldValue, newValue) -> {
            //Captalize the first letter of each word
            txtCity.setText(WordUtils.capitalize(txtCity.getText()));
            // Maximum 50 characters
            if (txtCity.getText().length() > StrLen.AIRPORT_CITY) {
                txtCity.setText(txtCity.getText().substring(0, StrLen.AIRPORT_CITY));
            }
        });

        txtCountry.textProperty().addListener((ov, oldValue, newValue) -> {
            //Captalize the first letter of each word
            txtCountry.setText(WordUtils.capitalize(txtCountry.getText()));
            // Maximum 50 characters
            if (txtCountry.getText().length() > StrLen.AIRPORT_COUNTRY) {
                txtCountry.setText(txtCountry.getText().substring(0, StrLen.AIRPORT_COUNTRY));
            }
        });

        // Set validator decorations
        ValidationSupport vs = new ValidationSupport();
        vs.registerValidator(txtIcao, Validator.createRegexValidator(
        		MyTranslate.formated("IcaoMustContain", new Object[] {StrLen.AIRPORT_ICAO}),
        		"[A-Z0-9]{"+StrLen.AIRPORT_ICAO+"}$", Severity.ERROR));
        
        // initialize validator
        vs.initInitialDecoration();
    }

    @FXML
    private void btnSaveAction(ActionEvent event) {
        if (validAirport()) {
            Stage stage = (Stage) anchorPanel.getScene().getWindow();
            AirportDAO airportDao = new AirportDaoImpl();
            
            // Determines is is a new airport or editing a existing one
            boolean airportExist = airportDao.airportExist(airport.getIcao());
            if (airport.getAirportId() == 0 && !airportExist) {
                airportDao.insertAirport(airport);
                stage.close();
            } else if (!airportExist || airport.getAirportId().equals(airportDao.getIcao(airport.getIcao()).getAirportId())) {       
                airportDao.updateAirport(airport);
                stage.close();
            } else {
            	Alert alert = new Alert(Alert.AlertType.ERROR);
        		alert.setTitle(MyTranslate.text("AirportExistTitle"));
        		alert.setHeaderText(MyTranslate.text("AirportExistMessage"));
        		alert.showAndWait();
			}
        }
    }

    @FXML
    private void btnCancelAction(ActionEvent event) {
        Stage stage = (Stage) anchorPanel.getScene().getWindow();
        stage.close();
    }

    private boolean validAirport() {
    	return txtIcao.getText().matches("[A-Z0-9]{"+ StrLen.AIRPORT_ICAO+ "}$");
    }

}
