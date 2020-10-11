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

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.prefs.Preferences;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.rietcorrea.constants.StrEng;
import com.rietcorrea.constants.StrLen;
import com.rietcorrea.controls.AdvancedDatePicker;
import com.rietcorrea.controls.HourField;
import com.rietcorrea.controls.SearchComboBox;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.converters.IntegerConverter;
import com.rietcorrea.simplelog.database.AircraftDaoImpl;
import com.rietcorrea.simplelog.database.AirportDaoImpl;
import com.rietcorrea.simplelog.database.CrewDaoImpl;
import com.rietcorrea.simplelog.database.FlightDAO;
import com.rietcorrea.simplelog.database.FlightDaoImpl;
import com.rietcorrea.simplelog.objects.Aircraft;
import com.rietcorrea.simplelog.objects.Airport;
import com.rietcorrea.simplelog.objects.Crew;
import com.rietcorrea.simplelog.objects.Flight;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author riet
 */
public class FlightEditController implements Initializable {

	@FXML
	private AnchorPane anchorPane;
	@FXML
	private TabPane tabPane;
	@FXML
	private Tab tabFirst;
	@FXML
	private Tab tabSecond;
	@FXML
	private Button btnDefault;
	@FXML
	private Button btnCancel;
	@FXML
	private AdvancedDatePicker dateFlight;
	@FXML
	private ComboBox<String> cmbPfPnf;
	@FXML
	private SearchComboBox<Aircraft> cmbAircraft;
	@FXML
	private SearchComboBox<Crew> cmbPic;
	@FXML
	private SearchComboBox<Crew> cmbSic;
	@FXML
	private SearchComboBox<Airport> cmbDepartureAirport;
	@FXML
	private HourField hourChocksOff;
	@FXML
	private SearchComboBox<Airport> cmbArrivalAirport;
	@FXML
	private HourField hourChocksOn;
	@FXML
	private TextField txtRemarks;
	@FXML
	private TextField txtTakeoffDay;
	@FXML
	private TextField txtTakeoffNight;
	@FXML
	private TextField txtLandingDay;
	@FXML
	private TextField txtLandingNight;
	@FXML
	private TextArea txtPrivateNotes;
	@FXML
	private HourField hourTotal;
	@FXML
	private HourField hourNight;
	@FXML
	private HourField hourIfr;
	@FXML
	private HourField hourXc;
	@FXML
	private HourField hourSic;
	@FXML
	private HourField hourPic;
	@FXML
	private CheckBox chkPicus;
	@FXML
	private HourField hourInstructor;
	@FXML
	private HourField hourDual;
	@FXML
	private HourField hourFstd;
	@FXML
	private Label chkFstd;
	@FXML
	private CheckBox chkDual;
	@FXML
	private CheckBox chkPic;
	@FXML
	private HourField hourPicus;
	@FXML
	private CheckBox chkSic;
	@FXML
	private CheckBox chkInstructor;
	@FXML
	private CheckBox chkXc;
	@FXML
	private CheckBox chkIfr;
	@FXML
	private Label lblModel;

	private Flight flight;
	private Preferences prefs;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		// Get preferences
		prefs = Preferences.userRoot().node(this.getClass().getName());
		chkIfr.setSelected(prefs.getBoolean("CALC_IFR", true));
		chkPic.setSelected(prefs.getBoolean("CALC_PIC", true));
		chkPicus.setSelected(prefs.getBoolean("CALC_PICUS", true));
		chkSic.setSelected(prefs.getBoolean("CALC_SIC", false));
		chkDual.setSelected(prefs.getBoolean("CALC_DUAL", false));
		chkInstructor.setSelected(prefs.getBoolean("CALC_INSTRUCTOR", false));
		chkXc.setSelected(prefs.getBoolean("CALC_XC", true));

		addTimeListeners();

		initializeTabs();
		initializeCmbPfPnf();

		cmbAircraft.setDao(new AircraftDaoImpl());
		cmbDepartureAirport.setDao(new AirportDaoImpl());
		cmbArrivalAirport.setDao(new AirportDaoImpl());
		cmbPic.setDao(new CrewDaoImpl());
		cmbSic.setDao(new CrewDaoImpl());
		cmbSic.setAllowNull(true);

		// Add listener to change model name Label when aircraft changes
		this.cmbAircraft.valueProperty().addListener(e -> {
			Aircraft acft = this.cmbAircraft.getValue();
			if (acft != null) {
				this.lblModel.setText(acft.getAircraftModel().getModelName());
			}
		});

		setMaxLenghtTextFields();
	}

	private void addTimeListeners() {
		chkIfr.selectedProperty().addListener(e -> {
			prefs.putBoolean("CALC_IFR", chkIfr.isSelected());
			if (chkIfr.isSelected()) {
				hourIfr.setMinutes(flight.getIfrMinutes(hourTotal.getMinutes()));
			}
		});
		chkPic.selectedProperty().addListener(e -> {
			prefs.putBoolean("CALC_PIC", chkPic.isSelected());
			if (chkPic.isSelected()) {
				hourPic.setMinutes(hourTotal.getMinutes());
			}
		});
		chkPicus.selectedProperty().addListener(e -> {
			prefs.putBoolean("CALC_PICUS", chkPicus.isSelected());
			if (chkPicus.isSelected()) {
				hourPicus.setMinutes(hourTotal.getMinutes());
			}
		});
		chkSic.selectedProperty().addListener(e -> {
			prefs.putBoolean("CALC_SIC", chkSic.isSelected());
			if (chkSic.isSelected()) {
				hourSic.setMinutes(hourTotal.getMinutes());
			}
		});
		chkDual.selectedProperty().addListener(e -> {
			prefs.putBoolean("CALC_DUAL", chkDual.isSelected());
			if (chkDual.isSelected()) {
				hourDual.setMinutes(hourTotal.getMinutes());
			}
		});
		chkInstructor.selectedProperty().addListener(e -> {
			prefs.putBoolean("CALC_INSTRUCTOR", chkInstructor.isSelected());
			if (chkInstructor.isSelected()) {
				hourInstructor.setMinutes(hourTotal.getMinutes());
			}
		});
		chkXc.selectedProperty().addListener(e -> {
			prefs.putBoolean("CALC_XC", chkXc.isSelected());
			if (chkXc.isSelected()) {
				hourXc.setMinutes(hourTotal.getMinutes());
			}
		});
	}

	private void setMaxLenghtTextFields() {
		txtRemarks.textProperty().addListener((ov, oldValue, newValue) -> {
			// Maximum characters
			if (txtRemarks.getText().length() > StrLen.FLIGHT_REMARKS) {
				txtRemarks.setText(txtRemarks.getText().substring(0, StrLen.FLIGHT_REMARKS));
			}
		});

		txtPrivateNotes.textProperty().addListener((ov, oldValue, newValue) -> {
			// Maximum characters
			if (txtPrivateNotes.getText().length() > StrLen.FLIGHT_PRIVATE_NOTES) {
				txtPrivateNotes.setText(txtPrivateNotes.getText().substring(0, StrLen.FLIGHT_PRIVATE_NOTES));
			}
		});
	}

	void newFlight(Flight newFlight) {
		this.flight = newFlight;
		bindFields();
	}

	void editFlight(Flight selectedFlight) {
		this.flight = selectedFlight;
		bindFields();
	}

	private void initializeTabs() {
		tabFirst.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
			if (isNowSelected.booleanValue()) {
				btnDefault.setText(MyTranslate.text("ButtonNext"));
				btnCancel.setText(MyTranslate.text("ButtonCancel"));
			} else {
				btnDefault.setText(MyTranslate.text("ButtonSave"));
				btnCancel.setText(MyTranslate.text("ButtonBack"));
			}
		});
	}

	private void bindFields() {

		Bindings.bindBidirectional(cmbPfPnf.valueProperty(), flight.pfPnfProperty());

		cmbAircraft.setValue(flight.getAircraft());
		lblModel.setText(flight.getAircraft().getAircraftModel().getModelName());

		cmbDepartureAirport.setValue(flight.getDepartureAirport());
		cmbArrivalAirport.setValue(flight.getArrivalAirport());

		initializeFlightDates(this.flight.getDepartureDate(), this.flight.getArrivalDate());

		cmbPic.setValue(flight.getCrewPic());
		cmbSic.setValue(flight.getCrewSic());

		Bindings.bindBidirectional(txtRemarks.textProperty(), flight.remarksProperty());

		Bindings.bindBidirectional(txtTakeoffDay.textProperty(), flight.takeOffDayProperty(), new IntegerConverter());
		Bindings.bindBidirectional(txtTakeoffNight.textProperty(), flight.takeOffNightProperty(),
				new IntegerConverter());
		Bindings.bindBidirectional(txtLandingDay.textProperty(), flight.landingDayProperty(), new IntegerConverter());
		Bindings.bindBidirectional(txtLandingNight.textProperty(), flight.landingNightProperty(),
				new IntegerConverter());

		Bindings.bindBidirectional(hourTotal.minutesProperty(), flight.totalTimeProperty());
		Bindings.bindBidirectional(hourNight.minutesProperty(), flight.nightTimeProperty());
		Bindings.bindBidirectional(hourIfr.minutesProperty(), flight.ifrTimeProperty());
		Bindings.bindBidirectional(hourPic.minutesProperty(), flight.picTimeProperty());
		Bindings.bindBidirectional(hourPicus.minutesProperty(), flight.picUsTimeProperty());
		Bindings.bindBidirectional(hourSic.minutesProperty(), flight.sicTimeProperty());
		Bindings.bindBidirectional(hourDual.minutesProperty(), flight.dualTimeProperty());
		Bindings.bindBidirectional(hourInstructor.minutesProperty(), flight.instructorTimeProperty());
		Bindings.bindBidirectional(hourXc.minutesProperty(), flight.xcTimeProperty());
		Bindings.bindBidirectional(hourFstd.minutesProperty(), flight.fstdTimeProperty());

		Bindings.bindBidirectional(txtPrivateNotes.textProperty(), flight.privateNotesProperty());
	}

	// Function gets departureDate and arrivalDate as Minutes since EPOCH
	// and Sets initial Date, departure time and arrival time
	private void initializeFlightDates(Long departureDate, Long arrivalDate) {

		// Diveded By 86400 (number of seconds in a day)
		this.dateFlight.setValue(LocalDate.ofEpochDay(departureDate / 86400));

		Calendar departureCal = new GregorianCalendar();
		departureCal.setTimeInMillis(departureDate * 1000);

		Calendar arrivalCal = new GregorianCalendar();
		arrivalCal.setTimeInMillis(arrivalDate * 1000);

		SimpleDateFormat newTimeFormat = new SimpleDateFormat("HH:mm");
		newTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		this.hourChocksOff.setText(newTimeFormat.format(departureCal.getTime()));
		this.hourChocksOn.setText(newTimeFormat.format(arrivalCal.getTime()));
	}

	// Gets Flight Date, TimeDeparture and TimeArrival fields and set flight
	// properties
	private void getFlightDates() {
		this.flight.setDepartureDate(dateFlight.getEpoch(hourChocksOff.getMinutes()));
		this.flight.setArrivalDate(dateFlight.getEpoch(hourChocksOn.getMinutes()));

		// If the departure is before the arrival add 24hs (in seconds) to the value
		if (this.flight.getArrivalDate() <= this.flight.getDepartureDate()) {
			this.flight.setArrivalDate(this.flight.getArrivalDate() + 86400);
		}
	}

	private void initializeCmbPfPnf() {
		// Initialize values on EngineType ComboBox
		ObservableList<String> engineTypeList = FXCollections.observableArrayList(StrEng.PF_STRING, StrEng.PNF_STRING,
				StrEng.PF_PNF_STRING, StrEng.PNF_PF_STRING, StrEng.IRP3_STRING, StrEng.IRP4_STRING);
		cmbPfPnf.setItems(engineTypeList);
	}

	private void showRequiredFields() {
		// Set validator decorations
		ValidationSupport vs = new ValidationSupport();
		if (cmbAircraft.getValue().getRegistration().equals("")) {
			vs.registerValidator(cmbAircraft, Validator.createEmptyValidator(""));
		}
		if (cmbDepartureAirport.getValue().getIcao().equals("")) {
			vs.registerValidator(cmbDepartureAirport, Validator.createEmptyValidator(""));
		}
		if (cmbArrivalAirport.getValue().getIcao().equals("")) {
			vs.registerValidator(cmbArrivalAirport, Validator.createEmptyValidator(""));
		}
		if (cmbPic.getValue().getCrewName().equals("")) {
			vs.registerValidator(cmbPic, Validator.createEmptyValidator(""));
		}
	}

	@FXML
	private void btnCancelAction(ActionEvent event) {
		if (btnCancel.getText().equals(MyTranslate.text("ButtonBack"))) {
			// Goes back to the firs tab
			tabPane.getSelectionModel().select(tabFirst);
		} else {
			// Close window
			Stage stage = (Stage) anchorPane.getScene().getWindow();
			stage.close();
		}
	}

	@FXML
	private void btnDefaultAction(ActionEvent event) {
		if (btnDefault.getText().equals(MyTranslate.text("ButtonNext"))) {
			// Select second tab
			tabPane.getSelectionModel().select(tabSecond);
		} else {
			getFlightDates();
			if (isValidFlight(flight)) {
				Stage stage = (Stage) anchorPane.getScene().getWindow();
				FlightDAO flightDao = new FlightDaoImpl();

				if (flight.getFlightId() == 0) {
					// New flight
					flightDao.insertFlight(flight);
					stage.close();
				} else {
					// edit flight
					flightDao.updateFlight(flight);
					stage.close();
				}
			} else {
				// Select first tab
				tabPane.getSelectionModel().select(tabFirst);
				showRequiredFields();
			}
		}
	}

	@FXML
	private void btnNewAircraftAction(ActionEvent event) {
		new AircraftEditDialog();
		this.cmbAircraft.setSearchString("");
	}

	@FXML
	private void btnNewDepartureAirportAction(ActionEvent event) {
		new AirportEditDialog();
		this.cmbDepartureAirport.setSearchString("");
	}

	@FXML
	private void btnNewArrivalAirportAction(ActionEvent event) {
		new AirportEditDialog();
		this.cmbArrivalAirport.setSearchString("");
	}

	@FXML
	private void btnNewPicAction(ActionEvent event) {
		new CrewEditDialog();
		this.cmbPic.setSearchString("");
	}

	@FXML
	private void btnNewSicAction(ActionEvent event) {
		new CrewEditDialog();
		this.cmbSic.setSearchString("");
	}

	@FXML
	private void btnCalculateAction(ActionEvent event) {
		getFlightDates();
		tabPane.getSelectionModel().select(tabSecond);
		if (isValidFlight(flight)) {

			setAllToZero();
			FlightCalculations calc = new FlightCalculations(flight);

			if (flight.getAircraft().getSimulator()) {
				hourFstd.setMinutes(calc.getFlightTime());
			} else {

				if (cmbPfPnf.getValue().equals(StrEng.PF_STRING) || cmbPfPnf.getValue().equals(StrEng.PF_PNF_STRING)) {
					if (calc.isDayTakeOff()) {
						txtTakeoffDay.setText("1");
					} else {
						txtTakeoffNight.setText("1");
					}
				}
				if (cmbPfPnf.getValue().equals(StrEng.PF_STRING) || cmbPfPnf.getValue().equals(StrEng.PNF_PF_STRING)) {
					if (calc.isDayLanding()) {
						txtLandingDay.setText("1");
					} else {
						txtLandingNight.setText("1");
					}
				}
				if (cmbPfPnf.getValue().equals(StrEng.IRP3_STRING)) {
					int hours = calc.getFlightTime() * 2 / 3;
					hourTotal.setMinutes(hours);
					int night = calc.getNightTime() * 2 / 3;
					hourNight.setMinutes(night);
				} else if (cmbPfPnf.getValue().equals(StrEng.IRP4_STRING)) {
					int hours = calc.getFlightTime() * 1 / 2;
					hourTotal.setMinutes(hours);
					int night = calc.getNightTime() * 1 / 2;
					hourNight.setMinutes(night);
				} else {
					hourTotal.setMinutes(calc.getFlightTime());
					hourNight.setMinutes(calc.getNightTime());
				}

				if (chkIfr.isSelected()) {
					hourIfr.setMinutes(flight.getIfrMinutes(hourTotal.getMinutes()));
				}
				if (chkPic.isSelected()) {
					hourPic.setMinutes(hourTotal.getMinutes());
				}
				if (chkPicus.isSelected()) {
					hourPicus.setMinutes(hourTotal.getMinutes());
				}
				if (chkSic.isSelected()) {
					hourSic.setMinutes(hourTotal.getMinutes());
				}
				if (chkDual.isSelected()) {
					hourDual.setMinutes(hourTotal.getMinutes());
				}
				if (chkInstructor.isSelected()) {
					hourInstructor.setMinutes(hourTotal.getMinutes());
				}
				if (chkXc.isSelected()) {
					hourXc.setMinutes(hourTotal.getMinutes());
				}
			}
		} else {
			// Select first tab
			tabPane.getSelectionModel().select(tabFirst);
			showRequiredFields();
		}
	}

	private void setAllToZero() {
		txtTakeoffDay.setText("0");
		txtTakeoffNight.setText("0");
		txtLandingDay.setText("0");
		txtLandingNight.setText("0");

		hourTotal.setMinutes(0);
		hourNight.setMinutes(0);
		hourIfr.setMinutes(0);
		hourPic.setMinutes(0);
		hourPicus.setMinutes(0);
		hourSic.setMinutes(0);
		hourDual.setMinutes(0);
		hourInstructor.setMinutes(0);
		hourXc.setMinutes(0);
		hourFstd.setMinutes(0);
	}

	private boolean isValidFlight(Flight flight) {
		if (cmbAircraft.getSelectionModel().getSelectedItem().toString().equals("")) {
			return false;
		} else {
			flight.setAircraft(cmbAircraft.getValue());
		}

		if (cmbDepartureAirport.getSelectionModel().getSelectedItem().toString().equals("")) {
			return false;
		} else {
			flight.setDepartureAirport(cmbDepartureAirport.getValue());
		}

		if (cmbArrivalAirport.getSelectionModel().getSelectedItem().toString().equals(" -  - ")) {
			return false;
		} else {
			flight.setArrivalAirport(cmbArrivalAirport.getValue());
		}

		if (cmbPic.getSelectionModel().getSelectedItem().toString().equals("")) {
			return false;
		} else {
			flight.setCrewPic(cmbPic.getValue());
		}

		try {
			if (cmbSic.getValue().toString().equals("")) {
				flight.setCrewSic(new Crew());
			} else {
				flight.setCrewSic(cmbSic.getValue());
			}
		} catch (NullPointerException e) {
			flight.setCrewSic(new Crew());
		}

		return true;
	}
}
