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

import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.database.AirportDAO;
import com.rietcorrea.simplelog.database.AirportDaoImpl;
import com.rietcorrea.simplelog.database.FlightDAO;
import com.rietcorrea.simplelog.database.FlightDaoImpl;
import com.rietcorrea.simplelog.objects.Airport;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author riet
 */
public class AirportController implements Initializable {

	ObservableList<Airport> list = FXCollections.observableArrayList();

	@FXML
	private TextField txtSearchString;
	@FXML
	private TableView<Airport> tableirports;
	@FXML
	private TableColumn<Airport, String> colIcao;
	@FXML
	private TableColumn<Airport, String> colIata;
	@FXML
	private TableColumn<Airport, String> colName;
	@FXML
	private TableColumn<Airport, String> colCity;
	@FXML
	private TableColumn<Airport, String> colCountry;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnDelete;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		// Set size of table and link columns to field
		initializeTable();

		// Get all airports
		refreshTableAirports();

		txtSearchString.textProperty().addListener(e -> refreshTableAirports());

	}

	@FXML
	private void btnAddAction(ActionEvent event) {
		new AirportEditDialog();
		refreshTableAirports();
	}

	@FXML
	private void btnEditAction(ActionEvent event) {
		editRow();
	}

	@FXML
	private void btnDeleteAction(ActionEvent event) {
		// check the table's selected item and open edit window
		if (tableirports.getSelectionModel().getSelectedItem() != null) {
			FlightDAO flightDao = new FlightDaoImpl();
			
			ObservableList<Airport> selectedAirports = tableirports.getSelectionModel().getSelectedItems();
			
			// Check if any flight is using this airport
			if (flightDao.getAirportInUse(selectedAirports)) {
				Alert alert = new Alert(AlertType.ERROR);
				
				alert.setTitle(MyTranslate.text("DeleteAirport"));
				alert.setHeaderText(MyTranslate.formated("ErrorAirportInUse", new Object[] {selectedAirports.size()}));
				alert.setContentText(MyTranslate.text("ErrorMustDeleteFlightBeforeAirport"));
				
				alert.showAndWait();
			} else {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle(MyTranslate.text("DeleteAirport"));
				alert.setHeaderText(MyTranslate.formated("DeleteAirportIrreversible", new Object[] {selectedAirports.size()}));
				alert.setContentText(MyTranslate.text("AreYouSure"));

				Optional<ButtonType> result = alert.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					AirportDAO airportDao = new AirportDaoImpl();
					for (Airport airport : selectedAirports) {
						airportDao.deleteAirport(airport);
					}
					refreshTableAirports();
				}
			}

		}
	}

	private void editRow() {
		// check the table's selected item and open edit window
		if (tableirports.getSelectionModel().getSelectedItem() != null) {
			Airport selectedAirport = tableirports.getSelectionModel().getSelectedItem();
			int selectedindex = tableirports.getSelectionModel().getSelectedIndex();
			new AirportEditDialog(selectedAirport);
			refreshTableAirports();

			tableirports.getSelectionModel().select(selectedindex);
		}
	}

	private void refreshTableAirports() {
		AirportDAO airportDao = new AirportDaoImpl();
		List<Airport> airports;
		if (txtSearchString.getText().equals("")) {
			airports = airportDao.getAllAirports();
		} else {
			airports = airportDao.searchAll(txtSearchString.getText());
		}
		
		int selectedindex = tableirports.getSelectionModel().getSelectedIndex();
		
		list.clear();
		list.addAll(FXCollections.observableArrayList(airports));

		if (!list.isEmpty()) {
			if (selectedindex > 0) {
				tableirports.getSelectionModel().select(selectedindex);
			} else {
				tableirports.getSelectionModel().select(0);
			}
		}
	}

	private void initializeTable() {
		// Set width for the columns of the table
		tableirports.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		colIcao.setMaxWidth(1f * Integer.MAX_VALUE * 10); // 10% width
		colIata.setMaxWidth(1f * Integer.MAX_VALUE * 10); // 10% width
		colName.setMaxWidth(1f * Integer.MAX_VALUE * 40); // 40% width
		colCity.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width
		colCountry.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width

		// Link the table columns to the property of the object
		colIcao.setCellValueFactory(new PropertyValueFactory<Airport, String>("icao"));
		colIata.setCellValueFactory(new PropertyValueFactory<Airport, String>("iata"));
		colName.setCellValueFactory(new PropertyValueFactory<Airport, String>("airportName"));
		colCity.setCellValueFactory(new PropertyValueFactory<Airport, String>("airportCity"));
		colCountry.setCellValueFactory(new PropertyValueFactory<Airport, String>("airportCountry"));

		tableirports.setItems(list);

		// Set double click action to edit selected row
		tableirports.setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() > 1) {
				editRow();
			}
			activateButtons();
		});

		tableirports.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Disable edit and delete buttons if no line is selected
		tableirports.getSelectionModel().selectedItemProperty()
			.addListener((obs, previousAirport, selectedAirport) -> activateButtons());
	}
	
	private void activateButtons() {
		ObservableList<Airport> selectedAirports = tableirports.getSelectionModel().getSelectedItems();
		if (selectedAirports.isEmpty()) {
			btnEdit.setDisable(true);
			btnDelete.setDisable(true);
		} else if (selectedAirports.size() == 1) {
			btnEdit.setDisable(false);
			btnDelete.setDisable(false);
		} else {
			btnEdit.setDisable(true);
			btnDelete.setDisable(false);
		}
	}
	
	@FXML
    void btnDownloadAction(ActionEvent event) {
		try {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AirportsDownload.fxml"));
	        
	        // Set locale for fxml translation
	    	loader.setResources(MyTranslate.getResourceBundle());
	
	        Stage stage = new Stage(StageStyle.DECORATED);
	        stage.setScene(new Scene((Pane) loader.load()));
	        stage.setResizable(false);
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.showAndWait();
	        
	        refreshTableAirports();
	        
	    } catch (IOException ex) {
	        Logger.getLogger(AirportController.class.getName()).log(Level.SEVERE, null, ex);
	    }
    }
}
