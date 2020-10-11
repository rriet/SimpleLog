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
import com.rietcorrea.simplelog.converters.HoursIntegerConverter;
import com.rietcorrea.simplelog.database.FlightDAO;
import com.rietcorrea.simplelog.database.FlightDaoImpl;
import com.rietcorrea.simplelog.objects.Totals;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


/**
 * FXML Controller class
 *
 * @author riet
 */
public class TotalsController implements Initializable { // NO_UCD (unused code)

	ObservableList<Totals>              list                     = FXCollections.observableArrayList();

	@FXML
	private ComboBox<String>            cmbSelection;
	@FXML
	private AdvancedDatePicker          dateStart;
	@FXML
	private AdvancedDatePicker          dateEnd;
	@FXML
	private TableView<Totals>           tableTotals;
	@FXML
	private TableColumn<Totals, String> colTitle;
	@FXML
	private TableColumn<Totals, Number> colSectors;
	@FXML
	private TableColumn<Totals, Number> colLandings;
	@FXML
	private TableColumn<Totals, String> colPic;
	@FXML
	private TableColumn<Totals, String> colSic;
	@FXML
	private TableColumn<Totals, String> colDual;
	@FXML
	private TableColumn<Totals, String> colXc;
	@FXML
	private TableColumn<Totals, String> colIfr;
	@FXML
	private TableColumn<Totals, String> colNight;
	@FXML
	private TableColumn<Totals, String> colTotal;
	@FXML
	private TableColumn<Totals, String> colPicus;
	@FXML
	private TableColumn<Totals, String> colInstructor;

	private String                      totalString            = MyTranslate.text("OptionTotal");
	private String                      tailNumberString       = MyTranslate.text("OptionTailNumber");
	private String                      aircraftTypeString    = MyTranslate.text("OptionAircraftTypes");
	private String                      aircraftGroupString    = MyTranslate.text("OptionAircraftGroup");
	private String                      engineTypeString      = MyTranslate.text("OptionEngineType");
	private String                      multiEngineString      = MyTranslate.text("OptionMultiEngine");
	private String                      multiPilotString       = MyTranslate.text("OptionMultiPilot");
	private String                      efiString              = MyTranslate.text("OptionEfis");
	private String                      departureAirportString = MyTranslate.text("OptionDepartureAirport");
	private String                      arrivalAirportString   = MyTranslate.text("OptionArrivalAirport");
	private String                      picNameString          = MyTranslate.text("OptionPICName");
	private String                      sicNameString          = MyTranslate.text("OptionSICName");

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeComboSelection();
		initializeTableTotals();
		dateStart.setValue(LocalDate.of(1990, 01, 01));

		cmbSelection.valueProperty().addListener(e -> refreshTableTotals());
		dateStart.valueProperty().addListener(e -> refreshTableTotals());
		dateEnd.valueProperty().addListener(e -> refreshTableTotals());

		refreshTableTotals();

	}

	private void initializeComboSelection() {
		// Initialize values on EngineType ComboBox
		ObservableList<String> engineTypeList = FXCollections.observableArrayList(totalString, tailNumberString,
				aircraftTypeString, aircraftGroupString, engineTypeString, multiEngineString,
				multiPilotString, efiString, departureAirportString, arrivalAirportString, picNameString,
				sicNameString);

		cmbSelection.setItems(engineTypeList);
		cmbSelection.setValue(MyTranslate.text("OptionTotal"));
	}

	private void initializeTableTotals() {
		// Link the table columns to the property of the object

		colTitle.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGroup()));

		colSectors.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getSectors()));

		colLandings.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getLanding()));

		colPic.setCellValueFactory(data -> {
			int minutes = data.getValue().getPicTime();
			HoursIntegerConverter converter = new HoursIntegerConverter();
			return new SimpleStringProperty(converter.toString(minutes));
		});

		colPicus.setCellValueFactory(data -> {
			int minutes = data.getValue().getPicUsTime();
			HoursIntegerConverter converter = new HoursIntegerConverter();
			return new SimpleStringProperty(converter.toString(minutes));
		});

		colSic.setCellValueFactory(data -> {
			int minutes = data.getValue().getSicTime();
			HoursIntegerConverter converter = new HoursIntegerConverter();
			return new SimpleStringProperty(converter.toString(minutes));
		});

		colDual.setCellValueFactory(data -> {
			int minutes = data.getValue().getDualTime();
			HoursIntegerConverter converter = new HoursIntegerConverter();
			return new SimpleStringProperty(converter.toString(minutes));
		});

		colInstructor.setCellValueFactory(data -> {
			int minutes = data.getValue().getInstructorTime();
			HoursIntegerConverter converter = new HoursIntegerConverter();
			return new SimpleStringProperty(converter.toString(minutes));
		});

		colXc.setCellValueFactory(data -> {
			int minutes = data.getValue().getXcTime();
			HoursIntegerConverter converter = new HoursIntegerConverter();
			return new SimpleStringProperty(converter.toString(minutes));
		});

		colIfr.setCellValueFactory(data -> {
			int minutes = data.getValue().getIfrTime();
			HoursIntegerConverter converter = new HoursIntegerConverter();
			return new SimpleStringProperty(converter.toString(minutes));
		});

		colNight.setCellValueFactory(data -> {
			int minutes = data.getValue().getNightTime();
			HoursIntegerConverter converter = new HoursIntegerConverter();
			return new SimpleStringProperty(converter.toString(minutes));
		});

		colTotal.setCellValueFactory(data -> {
			int minutes = data.getValue().getTotalTime();
			HoursIntegerConverter converter = new HoursIntegerConverter();
			return new SimpleStringProperty(converter.toString(minutes));
		});

		tableTotals.setItems(list);
	}

	private void refreshTableTotals() {
		FlightDAO flightDao = new FlightDaoImpl();
		List<Totals> totals = new ArrayList<>();

		ZoneId zoneId = ZoneId.of("UTC");
		long dateStartLong = dateStart.getValue().atStartOfDay(zoneId).toEpochSecond();
		long dateEndLong = dateEnd.getValue().atStartOfDay(zoneId).toEpochSecond();

		// Bugfix
		// Make it midnight of the END day, so all flight at that day will show...
		dateEndLong += 86399;


		if (cmbSelection.getValue().equals(totalString)) {
			UserPreferences pref = new UserPreferences();
			List<Totals> newTotals = flightDao.getTotals(dateStartLong, dateEndLong);

			Totals previousTotals = pref.getTotals();
			Totals allTotals = new Totals(newTotals.get(0));
			allTotals.setGroup("Total");
			allTotals.addTotal(previousTotals);

			newTotals.add(previousTotals);
			newTotals.add(allTotals);
			totals = newTotals;

		} else if (cmbSelection.getValue().equals(tailNumberString)) {
			totals = flightDao.getTotalsByTail(dateStartLong, dateEndLong);
		} else if (cmbSelection.getValue().equals(aircraftTypeString)) {
			totals = flightDao.getTotalsByType(dateStartLong, dateEndLong);
		} else if (cmbSelection.getValue().equals(aircraftGroupString)) {
			totals = flightDao.getTotalsByTypeGroup(dateStartLong, dateEndLong);
		} else if (cmbSelection.getValue().equals(engineTypeString)) {
			totals = flightDao.getTotalsByEngineType(dateStartLong, dateEndLong);
		} else if (cmbSelection.getValue().equals(multiEngineString)) {
			totals = flightDao.getTotalsByEngineNumber(dateStartLong, dateEndLong);
		} else if (cmbSelection.getValue().equals(multiPilotString)) {
			totals = flightDao.getTotalsByMultiPilot(dateStartLong, dateEndLong);
		} else if (cmbSelection.getValue().equals(efiString)) {
			totals = flightDao.getTotalsByEfis(dateStartLong, dateEndLong);
		} else if (cmbSelection.getValue().equals(departureAirportString)) {
			totals = flightDao.getTotalsByDepartureAirport(dateStartLong, dateEndLong);
		} else if (cmbSelection.getValue().equals(arrivalAirportString)) {
			totals = flightDao.getTotalsByArrivalAirport(dateStartLong, dateEndLong);
		} else if (cmbSelection.getValue().equals(picNameString)) {
			totals = flightDao.getTotalsByPicName(dateStartLong, dateEndLong);
		} else if (cmbSelection.getValue().equals(sicNameString)) {
			totals = flightDao.getTotalsBySicName(dateStartLong, dateEndLong);
		}


		list.clear();
		list.addAll(FXCollections.observableArrayList(totals));

		if (!list.isEmpty()) {
			tableTotals.getSelectionModel().selectFirst();
		}
	}
}
