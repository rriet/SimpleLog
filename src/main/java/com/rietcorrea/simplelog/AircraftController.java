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
import com.rietcorrea.simplelog.database.AircraftDAO;
import com.rietcorrea.simplelog.database.AircraftDaoImpl;
import com.rietcorrea.simplelog.database.FlightDAO;
import com.rietcorrea.simplelog.database.FlightDaoImpl;
import com.rietcorrea.simplelog.database.ModelDAO;
import com.rietcorrea.simplelog.database.ModelDaoImpl;
import com.rietcorrea.simplelog.objects.Aircraft;
import com.rietcorrea.simplelog.objects.Model;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author riet
 */
public class AircraftController implements Initializable { // NO_UCD (unused code)

	ObservableList<Model> modelList = FXCollections.observableArrayList();
	ObservableList<Aircraft> aircraftList = FXCollections.observableArrayList();

	@FXML
	private AnchorPane leftPane;
	@FXML
	private SplitPane splitPane;
	@FXML
	private TableColumn<Model, String> colModel;
	@FXML
	private TableColumn<Model, String> colGroup;
	@FXML
	private TableColumn<Model, String> colModelEngine;
	@FXML
	private TableColumn<Model, Integer> colMtow;
	@FXML
	private Button btnEditModel;
	@FXML
	private Button btnDeleteModel;
	@FXML
	private TableColumn<Aircraft, String> colRegistration;
	@FXML
	private TableColumn<Aircraft, String> colAircraftModel;
	@FXML
	private TableColumn<Aircraft, Integer> colAircraftMtow;
	@FXML
	private TableView<Aircraft> tableAircraft;
	@FXML
	private Button btnEditAircraft;
	@FXML
	private Button btnDeleteAircraft;
	@FXML
	private TableView<Model> tableModel;
	@FXML
	private TextField txtSearchAircraft;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		// Fix the divider in 50% of the pane width
		leftPane.maxWidthProperty().bind(splitPane.widthProperty().multiply(0.5));
		leftPane.minWidthProperty().bind(splitPane.widthProperty().multiply(0.5));

		initializeModelTable();
		initializeAircraftTable();

		// Get all airports
		refreshModelTable();
		refreshAircraftTable();

		txtSearchAircraft.textProperty().addListener(e -> refreshAircraftTable());
	}

	private void initializeModelTable() {

		// Link the table columns to the property of the object
		colModel.setCellValueFactory(new PropertyValueFactory<Model, String>("modelName"));
		colGroup.setCellValueFactory(new PropertyValueFactory<Model, String>("modelGroup"));
		colModelEngine.setCellValueFactory(new PropertyValueFactory<Model, String>("engineType"));
		colMtow.setCellValueFactory(new PropertyValueFactory<Model, Integer>("mtow"));

		tableModel.setItems(modelList);

		// Set double click action to edit selected row
		tableModel.setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() > 1) {
				editModelRow();
			}
			activateModelButtons();
		});

		tableModel.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Disable edit and delete buttons if no line is selected
		tableModel.getSelectionModel().selectedItemProperty()
				.addListener((obs, previousAirport, selectedAirport) -> activateModelButtons());
	}

	private void activateModelButtons() {
		ObservableList<Model> selectedModels = tableModel.getSelectionModel().getSelectedItems();
		if (selectedModels.isEmpty()) {
			btnEditModel.setDisable(true);
			btnDeleteModel.setDisable(true);
		} else if (selectedModels.size() == 1) {
			btnEditModel.setDisable(false);
			btnDeleteModel.setDisable(false);
		} else {
			btnEditModel.setDisable(true);
			btnDeleteModel.setDisable(false);
		}
	}

	private void initializeAircraftTable() {
		// Link the table columns to the property of the object
		colRegistration.setCellValueFactory(new PropertyValueFactory<Aircraft, String>("registration"));
		colAircraftModel.setCellValueFactory(
				(CellDataFeatures<Aircraft, String> data) -> data.getValue().getAircraftModel().modelNameProperty());
		colAircraftMtow.setCellValueFactory(new PropertyValueFactory<Aircraft, Integer>("aircraftMtow"));

		tableAircraft.setItems(aircraftList);

		// Set double click ation to edit selected row
		tableAircraft.setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() > 1) {
				editAircraftRow();
			}
			activateAircraftButtons();
		});

		tableAircraft.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Disable edit and delete buttons if no line is selected
		tableAircraft.getSelectionModel().selectedItemProperty()
				.addListener((obs, previousAirport, selectedAirport) -> activateAircraftButtons());
	}

	private void activateAircraftButtons() {
		ObservableList<Aircraft> selectedAircrafts = tableAircraft.getSelectionModel().getSelectedItems();
		if (selectedAircrafts.isEmpty()) {
			btnEditAircraft.setDisable(true);
			btnDeleteAircraft.setDisable(true);
		} else if (selectedAircrafts.size() == 1) {
			btnEditAircraft.setDisable(false);
			btnDeleteAircraft.setDisable(false);
		} else {
			btnEditAircraft.setDisable(true);
			btnDeleteAircraft.setDisable(false);
		}
	}

	@FXML
	private void btnNewModelAction(ActionEvent event) {
		new ModelEditDialog();
		refreshModelTable();
	}

	@FXML
	private void btnEditModelAction(ActionEvent event) {
		editModel();
	}

	@FXML
	private void btnDeleteModelAction(ActionEvent event) {
		// check the table's selected item and open edit window
		if (tableModel.getSelectionModel().getSelectedItem() != null) {
			
			AircraftDAO aircraftDao = new AircraftDaoImpl();
			ObservableList<Model> selectedModels = tableModel.getSelectionModel().getSelectedItems();
			
			// Check if any flight is using this Model
			if (aircraftDao.getModelInUse(selectedModels)) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
					
				alert.setTitle(MyTranslate.text("DeleteModel"));
				alert.setHeaderText(MyTranslate.formated("ErrorModelInUse", new Object[] {selectedModels.size()}));
				alert.setContentText(MyTranslate.text("ErrorMustDeleteAircraftBeforeModel"));
				alert.showAndWait();
			} else {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle(MyTranslate.text("DeleteModel"));
				alert.setHeaderText(MyTranslate.formated("DeleteModelIrreversible", new Object[] {selectedModels.size()}));
				alert.setContentText(MyTranslate.text("AreYouSure"));

				Optional<ButtonType> result = alert.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					ModelDAO modelDao = new ModelDaoImpl();
					modelDao.deleteModelArray(selectedModels);
					refreshModelTable();
				}
			}
		}
	}

	@FXML
	private void btnNewAircraftAction(ActionEvent event) {
		new AircraftEditDialog();
		refreshAircraftTable();
	}

	@FXML
	private void btnEditAircraftAction(ActionEvent event) {
		editAircraftRow();
	}

	@FXML
	private void btnDeleteAircraftAction(ActionEvent event) {
		// check the table's selected item and open edit window
		if (tableAircraft.getSelectionModel().getSelectedItem() != null) {
			
			FlightDAO flightDao = new FlightDaoImpl();
			ObservableList<Aircraft> selectedAircrafts = tableAircraft.getSelectionModel().getSelectedItems();

			// Check if any flight is using this Aircraft
			if (flightDao.getAircraftInUse(selectedAircrafts)) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				
				
				alert.setTitle(MyTranslate.text("DeleteAircraft"));
				alert.setHeaderText(MyTranslate.formated("ErrorAircraftInUse", new Object[] {selectedAircrafts.size()}));
				alert.setContentText(MyTranslate.text("ErrorMustDeleteFlightBeforeAircraft"));
				alert.showAndWait();
			} else {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle(MyTranslate.text("DeleteAircraft"));
				alert.setHeaderText(MyTranslate.formated("DeleteAircraftIrreversible", new Object[] {selectedAircrafts.size()}));
				alert.setContentText(MyTranslate.text("AreYouSure"));

				Optional<ButtonType> result = alert.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					AircraftDAO aircraftDao = new AircraftDaoImpl();
					for (Aircraft aircraft : selectedAircrafts) {
						aircraftDao.deleteAircraft(aircraft);
					}
					refreshAircraftTable();
				}
			}
		}
	}

	private void editModelRow() {
		editModel();
	}

	private void refreshModelTable() {
		ModelDAO modelDao = new ModelDaoImpl();
		List<Model> models = modelDao.getAllModels();
		
		int selectedindex = tableModel.getSelectionModel().getSelectedIndex();

		modelList.clear();
		modelList.addAll(FXCollections.observableArrayList(models));

		if (!modelList.isEmpty()) {
			if (selectedindex > 0) {
				tableModel.getSelectionModel().select(selectedindex);
			} else {
				tableModel.getSelectionModel().select(0);
			}
		}
	}

	private void editModel() {
		// check the table's selected item and open edit window
		if (tableModel.getSelectionModel().getSelectedItem() != null) {
			Model selectedModel = tableModel.getSelectionModel().getSelectedItem();
			new ModelEditDialog(selectedModel);

			refreshModelTable();
			refreshAircraftTable();
		}
	}

	private void editAircraftRow() {
		// check the table's selected item and open edit window
		if (tableAircraft.getSelectionModel().getSelectedItem() != null) {
			Aircraft selectedAircraft = tableAircraft.getSelectionModel().getSelectedItem();
			new AircraftEditDialog(selectedAircraft);

			refreshAircraftTable();
		}
	}

	private void refreshAircraftTable() {
		AircraftDAO modelDao = new AircraftDaoImpl();
		List<Aircraft> aircrafts;

		if (txtSearchAircraft.getText().equals("")) {
			aircrafts = modelDao.getAllAircrafts();
		} else {
			aircrafts = modelDao.searchAircrafts(txtSearchAircraft.getText());
		}

		int selectedindex = tableAircraft.getSelectionModel().getSelectedIndex();
		
		aircraftList.clear();
		aircraftList.addAll(FXCollections.observableArrayList(aircrafts));

		if (!aircraftList.isEmpty()) {
			if (selectedindex > 0) {
				tableAircraft.getSelectionModel().select(selectedindex);
			} else {
				tableAircraft.getSelectionModel().select(0);
			}
		}
	}

}
