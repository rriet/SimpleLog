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

import com.rietcorrea.controls.AdvancedDatePicker;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.converters.TimeIntegerConverter;
import com.rietcorrea.simplelog.database.FlightDAO;
import com.rietcorrea.simplelog.database.FlightDaoImpl;
import com.rietcorrea.simplelog.objects.Flight;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Ricardo Riet Correa
 */
public class FlightController implements Initializable { // NO_UCD (unused code)

    ObservableList<Flight> flightList = FXCollections.observableArrayList();

    @FXML
    private AdvancedDatePicker dateStart;
    @FXML
    private AdvancedDatePicker dateEnd;
    @FXML
    private TableView<Flight> tableFlights;
    @FXML
    private TableColumn<Flight, String> colDate;
    @FXML
    private TableColumn<Flight, String> colRegistration;
    @FXML
    private TableColumn<Flight, String> colModel;
    @FXML
    private TableColumn<Flight, String> colFrom;
    @FXML
    private TableColumn<Flight, String> colTo;
    @FXML
    private TableColumn<Flight, String> colTime;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtSearchString;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dateStart.setValue(LocalDate.of(1990, 01, 01));

        initializeTableFlight();
        refreshTableFlight();

        dateStart.valueProperty().addListener(e -> refreshTableFlight());
        dateEnd.valueProperty().addListener(e -> refreshTableFlight());
        txtSearchString.textProperty().addListener(e -> refreshTableFlight());
    }

    @FXML
    private void btnNewAction(ActionEvent event) {
        new FlightEditDialog();
        
        // Get selected line number to Reselect after refresh
        int selectedindex = tableFlights.getSelectionModel().getSelectedIndex();

        refreshTableFlight();
        tableFlights.getSelectionModel().clearSelection();
        tableFlights.getSelectionModel().select(selectedindex);
    }

    @FXML
    private void btnEditAction(ActionEvent event) {
        editRow();
    }

    private void initializeTableFlight() {
        // Link the table columns to the property of the object
        colDate.setCellValueFactory(data -> {
            long epoch = data.getValue().getDepartureDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            String date = dateFormat.format(new java.util.Date(epoch * 1000));
            return new SimpleStringProperty(date);
        });

        colRegistration.setCellValueFactory(data
                -> new SimpleStringProperty(data.getValue().getAircraft().getRegistration()));

        colModel.setCellValueFactory(data
                -> new SimpleStringProperty(data.getValue().getAircraft().getAircraftModel().getModelName()));

        colFrom.setCellValueFactory(data
                -> new SimpleStringProperty(data.getValue().getDepartureAirport().getIcao()));

        colTo.setCellValueFactory(data
                -> new SimpleStringProperty(data.getValue().getArrivalAirport().getIcao()));

        colTime.setCellValueFactory(data -> {
            int minutes = data.getValue().getTotalTime();
            StringConverter<Number> converter = new TimeIntegerConverter();
            return new SimpleStringProperty(converter.toString(minutes));
        });
        tableFlights.setItems(flightList);

        // Set double click ation to edit selected row
        tableFlights.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                editRow();
            }
            activateButtons();
        });
        
        tableFlights.getSelectionModel().setSelectionMode(
        		SelectionMode.MULTIPLE
        );

		// Disable edit and delete buttons if no line is selected
		tableFlights.getSelectionModel().selectedItemProperty().addListener((obs, previousAirport, selectedAirport) -> activateButtons());
    }
    
    private void activateButtons() {
    	ObservableList<Flight> selectedItems = tableFlights.getSelectionModel().getSelectedItems();
		if (selectedItems.isEmpty()) {
			btnEdit.setDisable(true);
			btnDelete.setDisable(true);
		} else if (selectedItems.size() == 1) {
			btnEdit.setDisable(false);
			btnDelete.setDisable(false);
		} else {
			btnEdit.setDisable(true);
			btnDelete.setDisable(false);
		}
	}

    private void editRow() {
        // check the table's selected item and open edit window
        if (tableFlights.getSelectionModel().getSelectedItem() != null) {
            Flight selectedFlight = tableFlights.getSelectionModel().getSelectedItem();

            new FlightEditDialog(selectedFlight);
            refreshTableFlight();            
        }
    }
    
    @FXML
    private void btnDeleteAction(ActionEvent event) {
        // check the table's selected item and open edit window
        if (tableFlights.getSelectionModel().getSelectedItem() != null) {
        	ObservableList<Flight> selectedFlights = tableFlights.getSelectionModel().getSelectedItems();
        	
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(MyTranslate.text("DeleteFlight"));
            alert.setHeaderText(MyTranslate.formated("YouAreAboutToDelete", new Object[] {selectedFlights.size()}));
            alert.setContentText(MyTranslate.text("AreYouSure"));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
            	FlightDAO flightDao = new FlightDaoImpl();
            	for (Flight flight : selectedFlights) {
	                flightDao.deleteFlight(flight);
            	}
                refreshTableFlight();
            }
        }
    }

    private void refreshTableFlight() {
        FlightDAO flightDao = new FlightDaoImpl();
        List<Flight> flights;

        ZoneId zoneId = ZoneId.of("UTC");
        long dateStartLong = dateStart.getValue().atStartOfDay(zoneId).toEpochSecond();
        long dateEndLong = dateEnd.getValue().atStartOfDay(zoneId).toEpochSecond();

        // Bugfix
        // Make it midnight of the END day, so all flight at that day will show...
        dateEndLong += 86399;

        if (txtSearchString.getText().equals("")) {

            flights = flightDao.getFlightsByDate(dateStartLong, dateEndLong);
        } else {
            flights = flightDao.searchFlights(dateStartLong, dateEndLong, txtSearchString.getText());
        }
        
        // Get selected line number to Reselect after refresh
        int selectedindex = tableFlights.getSelectionModel().getSelectedIndex();
        
        flightList.clear();
        flightList.addAll(FXCollections.observableArrayList(flights));

        if (!flightList.isEmpty()) {
			if (selectedindex > 0) {
				tableFlights.getSelectionModel().select(selectedindex);
			} else {
				tableFlights.getSelectionModel().select(0);
			}
		}
    }
}
