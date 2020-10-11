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
import com.rietcorrea.simplelog.database.CrewDAO;
import com.rietcorrea.simplelog.database.CrewDaoImpl;
import com.rietcorrea.simplelog.database.FlightDAO;
import com.rietcorrea.simplelog.database.FlightDaoImpl;
import com.rietcorrea.simplelog.objects.Crew;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author riet
 */
public class CrewController implements Initializable { // NO_UCD (unused code)
    
    ObservableList<Crew> list = FXCollections.observableArrayList();

    @FXML
    private TextField txtSearchCrew;
    @FXML
    private TableView<Crew> tableCrew;
    @FXML
    private TableColumn<Crew, String> colName;
    @FXML
    private TableColumn<Crew, String> colEmail;
    @FXML
    private TableColumn<Crew, String> colPhone;
    @FXML
    private TableColumn<Crew, String> colFlights;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTable();
        refreshTable();
        
        txtSearchCrew.textProperty().addListener(e -> refreshTable());
    }    

    @FXML
    private void btnNewAction(ActionEvent event) {
        new CrewEditDialog();
        refreshTable();
    }

    @FXML
    private void btnEditAction(ActionEvent event) {
        editRow();
    }

    @FXML
    private void btnDeleteAction(ActionEvent event) {
        // check the table's selected item and open edit window
        if (tableCrew.getSelectionModel().getSelectedItem() != null) {
        	FlightDAO flightDao = new FlightDaoImpl();
        	
        	ObservableList<Crew> selectedCrewList = tableCrew.getSelectionModel().getSelectedItems();
        	
            //Check if any flight is using this crew
            if (flightDao.getCrewInUse(selectedCrewList)){
                Alert alert = new Alert(AlertType.ERROR);
                
                alert.setTitle(MyTranslate.text("DeleteCrew"));
				alert.setHeaderText(MyTranslate.formated("ErrorCrewInUse", new Object[] {selectedCrewList.size()}));
				alert.setContentText(MyTranslate.text("ErrorMustDeleteFlightBeforeCrew"));
				
				alert.showAndWait();
			} else {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle(MyTranslate.text("DeleteCrew"));
				alert.setHeaderText(MyTranslate.formated("DeleteCrewIrreversible", new Object[] {selectedCrewList.size()}));
				alert.setContentText(MyTranslate.text("AreYouSure"));
                
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    CrewDAO crewDao = new CrewDaoImpl();
                    for (Crew crew : selectedCrewList) {
                    	crewDao.deleteCrew(crew);
					}
                    refreshTable();
                }
            }

        }
    }
    
    
    private void initializeTable() {
        // Set width for the columns of the table
        tableCrew.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colName.setMaxWidth(1f * Integer.MAX_VALUE * 40); // 40% width
        colEmail.setMaxWidth(1f * Integer.MAX_VALUE * 40); // 40% width
        colPhone.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width
        
        // Link the table columns to the property of the object
        colName.setCellValueFactory(new PropertyValueFactory<Crew, String> ("crewName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Crew, String> ("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<Crew, String> ("phone"));
        
        tableCrew.setItems(list);

        // Set double click ation to edit selected row
        tableCrew.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                editRow();
            }
            activateButtons();
        });

        tableCrew.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        // Disable edit and delete buttons if no line is selected
        tableCrew.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, previousAirport, selectedAirport) -> activateButtons());
    }
    
    private void activateButtons() {
    	ObservableList<Crew> selectedCrews = tableCrew.getSelectionModel().getSelectedItems();
		if (selectedCrews.isEmpty()) {
			btnEdit.setDisable(true);
			btnDelete.setDisable(true);
		} else if (selectedCrews.size() == 1) {
			btnEdit.setDisable(false);
			btnDelete.setDisable(false);
		} else {
			btnEdit.setDisable(true);
			btnDelete.setDisable(false);
		}
	}
    
    private void refreshTable() {
        CrewDAO crewDAO = new CrewDaoImpl();
        List<Crew> crew;
        if (txtSearchCrew.getText().equals("")) {
            crew = crewDAO.getAllCrew();
        } else {
            crew = crewDAO.findCrew(txtSearchCrew.getText());
        }
        
        int selectedindex = tableCrew.getSelectionModel().getSelectedIndex();
        
        list.clear();
        list.addAll(FXCollections.observableArrayList(crew));

        if (!list.isEmpty()) {
        	if (selectedindex > 0) {
        		tableCrew.getSelectionModel().select(selectedindex);
			} else {
				tableCrew.getSelectionModel().select(0);
			}
        }
    }

    private void editRow() {
        // check the table's selected item and open edit window
        if (tableCrew.getSelectionModel().getSelectedItem() != null) {
            Crew selectedCrew = tableCrew.getSelectionModel().getSelectedItem();
            int selectedindex = tableCrew.getSelectionModel().getSelectedIndex();
            new CrewEditDialog(selectedCrew);
            refreshTable();

            tableCrew.getSelectionModel().select(selectedindex);
        }
    }
}
