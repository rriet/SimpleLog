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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import com.rietcorrea.controls.AdvancedDatePicker;
import com.rietcorrea.controls.HourField;
import com.rietcorrea.controls.IntegerTextField;
import com.rietcorrea.controls.TimeField;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.converters.TimeIntegerConverter;
import com.rietcorrea.simplelog.database.FlightDAO;
import com.rietcorrea.simplelog.database.FlightDaoImpl;
import com.rietcorrea.simplelog.objects.Flight;
import com.rietcorrea.simplelog.objects.ReportTemplate;
import com.rietcorrea.simplelog.objects.SearchCriteria;
import com.rietcorrea.simplelog.objects.Totals;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author riet
 */
public class ReportsController implements Initializable { // NO_UCD (unused code)

	ObservableList<SearchCriteria> criterias = FXCollections.observableArrayList();
	ObservableList<Flight> flightsList = FXCollections.observableArrayList();
	Totals totals;

	Boolean andJoin;
	Long startDateLong;
    Long endDateLong;
	
	@FXML
    private AnchorPane anchorPane;
	@FXML
	private AdvancedDatePicker dateStart;
	@FXML
	private HourField hourStart;
	@FXML
	private AdvancedDatePicker dateEnd;
	@FXML
	private HourField hourEnd;
	@FXML
	private TableView<SearchCriteria> tblCriteria;
	@FXML
	private TableColumn<SearchCriteria, String> colCriteria;
	@FXML
    private ComboBox<String> cmbMatch;
	@FXML
	private IntegerTextField txtSectors;
	@FXML
	private IntegerTextField txtTakeoffDay;
	@FXML
	private IntegerTextField txtTakeoffNight;
	@FXML
	private IntegerTextField txtLandingDay;
	@FXML
	private IntegerTextField txtLandingNight;
	@FXML
	private TimeField hourTotal;
	@FXML
	private TimeField hourNight;
	@FXML
	private TimeField hourIfr;
	@FXML
	private TimeField hourPic;
	@FXML
	private TimeField hourPicus;
	@FXML
	private TimeField hourSic;
	@FXML
	private TimeField hourDual;
	@FXML
	private TimeField hourInstructor;
	@FXML
	private TimeField hourXc;
	@FXML
	private TimeField hourFstd;
	@FXML
	private TimeField hourMel;
	@FXML
	private TimeField hourSel;
	@FXML
	private TimeField hourSes;
	@FXML
	private TimeField hourMes;
	@FXML
	private TimeField hourMultiPilot;
	@FXML
	private Label chkFstd;
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
    private CheckBox chkIncludeTotals;
    @FXML
    private ComboBox<ReportTemplate> cmbReports;
    @FXML
    private Label lblStatus;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		lblStatus.setText("");
		
		dateStart.setValue(LocalDate.of(1990, 01, 01));
		hourStart.setMinutes(0);
		hourEnd.setMinutes(1439);
		
		tblCriteria.setItems(criterias);
		colCriteria.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));
		
		// populate criterias dropdown
		ObservableList<String> matchCriteria = FXCollections.observableArrayList(
                "All", "Any"
        );
		cmbMatch.setItems(matchCriteria);
		cmbMatch.setValue("All");
		
		// populate Report list dropdown from XML file
		ObservableList<ReportTemplate> reportTemplateList = FXCollections.observableArrayList();
		try {
			File fXmlFile = new File(System.getProperty("user.dir") + "/reports/index.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			NodeList nList = doc.getElementsByTagName("template");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int numberOfLines = Integer.parseInt(eElement.getElementsByTagName("numberOfLines").item(0).getTextContent());
					String fileNameString = eElement.getElementsByTagName("fileNameString").item(0).getTextContent();
					String descriptionString = eElement.getElementsByTagName("descriptionString").item(0).getTextContent();
					ReportTemplate reportTemplate = new ReportTemplate(numberOfLines, fileNameString, descriptionString);
					reportTemplateList.add(reportTemplate);
				}
			}
			cmbReports.setItems(reportTemplateList);
			cmbReports.setValue(reportTemplateList.get(0));
		    
		}catch (Exception e) {
			// TODO: handle exception
		}
		
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
		tableFlights.setItems(flightsList);
        // Set double click ation to edit selected row
        tableFlights.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                editRow();
            }
        });
        
		// Disable edit and delete buttons if no line is selected
		tableFlights.getSelectionModel().selectedItemProperty().addListener((obs, previousAirport, selectedAirport) -> {
			if (selectedAirport == null) {
				btnEdit.setDisable(true);
				btnDelete.setDisable(true);
			} else {
				btnEdit.setDisable(false);
				btnDelete.setDisable(false);
			}
		});
		
		dateStart.valueProperty().addListener(e -> searchFlights());
        dateEnd.valueProperty().addListener(e -> searchFlights());
        hourStart.textProperty().addListener(e -> searchFlights());
        hourEnd.textProperty().addListener(e -> searchFlights());
        cmbMatch.valueProperty().addListener(e -> searchFlights());
		
		searchFlights();
	}

	@FXML
	void btnAddAction(ActionEvent event) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ReportsCriteria.fxml"));
			
            // Set locale for fxml translation
        	loader.setResources(MyTranslate.getResourceBundle());

			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene((Pane) loader.load()));
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);

			ReportsCriteriaController controller = loader.<ReportsCriteriaController>getController();

			stage.setTitle("New Search criteria");
			stage.showAndWait();
			SearchCriteria newCriteria = controller.getSearchCriteria();
			if (!newCriteria.getCommand().equals("")) {
				criterias.add(newCriteria);
				searchFlights();
			}
		} catch (IOException ex) {
			Logger.getLogger(AirportController.class.getName()).log(Level.SEVERE, null, ex);
		}

		if (!criterias.isEmpty()) {
			tblCriteria.getSelectionModel().selectFirst();
		}
	}

	@FXML
	void btnRemoveAction(ActionEvent event) {
		// check the table's selected item and open edit window
		if (tblCriteria.getSelectionModel().getSelectedItem() != null) {
			// Get selected line number to Reselect after refresh
			int selectedindex = tblCriteria.getSelectionModel().getSelectedIndex();

			criterias.remove(selectedindex);
			searchFlights();
		}

		if (!criterias.isEmpty()) {
			tblCriteria.getSelectionModel().selectFirst();
		}
	}
	
	private void searchFlights() {
		startDateLong = dateStart.getEpoch(hourStart.getMinutes());
        endDateLong = dateEnd.getEpoch(hourEnd.getMinutes());

        if (cmbMatch.getValue().equals("All")) {
        	andJoin = true;
        } else {
			andJoin = false;
		}
        
		FlightDAO flightDao = new FlightDaoImpl();
        List<Flight> flights = flightDao.searchFlightsWhere(startDateLong, endDateLong, criterias, andJoin);
        
        totals = new Totals();
        
        for (Flight flight : flights) {
			totals.addFlight(flight);
		}
        
        updateTotals();
        
        flightsList.clear();
        flightsList.addAll(FXCollections.observableArrayList(flights));
        
        if (!flightsList.isEmpty()) {
            tableFlights.getSelectionModel().selectFirst();
        }
	}
	
	private void updateTotals() {
		txtSectors.setText(totals.getSectors().toString());
		txtTakeoffDay.setText(totals.getTakeOffDay().toString());
		txtTakeoffNight.setText(totals.getTakeOffNight().toString());
		txtLandingDay.setText(totals.getLandingDay().toString());
		txtLandingNight.setText(totals.getLandingNight().toString());
		hourTotal.setMinutes(totals.getTotalTime());
		hourNight.setMinutes(totals.getNightTime());
		hourIfr.setMinutes(totals.getIfrTime());
		hourXc.setMinutes(totals.getXcTime());
		hourMel.setMinutes(totals.getMel());
		hourSel.setMinutes(totals.getSel());
		hourMes.setMinutes(totals.getMes());
		hourSes.setMinutes(totals.getSes());
		hourPic.setMinutes(totals.getPicTime());
		hourPicus.setMinutes(totals.getPicUsTime());
		hourSic.setMinutes(totals.getSicTime());
		hourDual.setMinutes(totals.getDualTime());
		hourInstructor.setMinutes(totals.getInstructorTime());
		hourFstd.setMinutes(totals.getFstdTime());
		hourMultiPilot.setMinutes(totals.getMultiPilot());
	}

	private void editRow() {
		// check the table's selected item and open edit window
        if (tableFlights.getSelectionModel().getSelectedItem() != null) {
            Flight selectedFlight = tableFlights.getSelectionModel().getSelectedItem();

            new FlightEditDialog(selectedFlight);
            searchFlights();
        }
	}
	
	@FXML
	void btnDeleteAction(ActionEvent event) {
		// check the table's selected item and open edit window
        if (tableFlights.getSelectionModel().getSelectedItem() != null) {
            Flight selectedFlight = tableFlights.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Flight");
            alert.setHeaderText("You are about to delete this Flight. This action is irreversible.");
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                FlightDAO flightDao = new FlightDaoImpl();
                flightDao.deleteFlight(selectedFlight);
                searchFlights();
            }
        }
	}

	@FXML
	void btnEditAction(ActionEvent event) {
		editRow();
	}

	@FXML
	void btnPdfAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = new Stage(StageStyle.DECORATED);
        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
        	UserPreferences pref = new UserPreferences();
        	Totals pastTotals = pref.getTotals();
        	if (chkIncludeTotals.isSelected()) {
        		FlightDAO flightDao = new FlightDaoImpl();
        		
                // get flight since year 0 until the start of the report
                List<Flight> oldFlights = flightDao.searchFlightsWhere( 0l , startDateLong, criterias, andJoin);
                for (Flight flight : oldFlights) {
                	pastTotals.addFlight(flight);
        		}
			}
        	
        	ReportTemplate template = cmbReports.getValue();
        	
            ReportPdf pdf = new ReportPdf(flightsList, pastTotals, file.getPath(), template.getNumberOfLines(), template.getFileNameString());
            
            lblStatus.setText("Generating PDF. Please wait...");

            new Thread(pdf).start();
            
            pdf.setOnSucceeded( e -> lblStatus.setText("PDF saved successfully.") );
        }
	}
}
