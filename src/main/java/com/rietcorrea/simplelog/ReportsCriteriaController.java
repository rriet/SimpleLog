package com.rietcorrea.simplelog;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.objects.SearchCriteria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ReportsCriteriaController implements Initializable {

	private SearchCriteria newSearchCriteria = new SearchCriteria();
	@FXML
	private AnchorPane anchorPanel;
	@FXML
	private ComboBox<String> cmbField;
	@FXML
	private ChoiceBox<String> cmbAction;
	@FXML
	private TextField txtSearchString;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ObservableList<String> fieldsList = FXCollections.observableArrayList(
				MyTranslate.text("CriteriaFlightFromIcao"), MyTranslate.text("CriteriaFlightFromIATA"),
				MyTranslate.text("CriteriaFlightFromCountry"), MyTranslate.text("CriteriaFlightToICAO"),
				MyTranslate.text("CriteriaFlightToIATA"), MyTranslate.text("CriteriaFlightToCountry"),
				MyTranslate.text("CriteriaPICName"), MyTranslate.text("CriteriaPICNotes"),
				MyTranslate.text("CriteriaSICName"), MyTranslate.text("CriteriaSICNotes"),
				MyTranslate.text("CriteriaTypeMake&Model"), MyTranslate.text("CriteriaTypeGroup"),
				MyTranslate.text("CriteriaTypeMTOW"), MyTranslate.text("CriteriaTypeEngine"),
				MyTranslate.text("CriteriaTypeMultiPilot"), MyTranslate.text("CriteriaTypeMultiEngine"),
				MyTranslate.text("CriteriaTypeSeaplane"), MyTranslate.text("CriteriaTypeEFIS"),
				MyTranslate.text("CriteriaAircraftRegistration"), MyTranslate.text("CriteriaAircraftSimulator"),
				MyTranslate.text("CriteriaAircraftMTOW"), MyTranslate.text("CriteriaRemarks"),
				MyTranslate.text("CriteriaPrivateNotes"), MyTranslate.text("CriteriaTotalTime"),
				MyTranslate.text("CriteriaNightTime"), MyTranslate.text("CriteriaIfrTime"),
				MyTranslate.text("CriteriaPicTime"), MyTranslate.text("CriteriaPicusTime"),
				MyTranslate.text("CriteriaCopilotTime"), MyTranslate.text("CriteriaDualTime"),
				MyTranslate.text("CriteriaInstructorTime"), MyTranslate.text("CriteriaCrossCountryTime"),
				MyTranslate.text("CriteriaSimulatorTime"));
		cmbField.setItems(fieldsList);
		cmbField.setValue(MyTranslate.text("CriteriaFlightFromIcao"));
		refreshAction();
	}

	@FXML
	private void refreshAction() {

		Map<String, Runnable> commands = new HashMap<>();
		commands.put(MyTranslate.text("CriteriaFlightFromIcao"), () -> setString());
		commands.put(MyTranslate.text("CriteriaFlightFromIATA"), () -> setString());
		commands.put(MyTranslate.text("CriteriaFlightFromCountry"), () -> setString());
		commands.put(MyTranslate.text("CriteriaFlightToICAO"), () -> setString());
		commands.put(MyTranslate.text("CriteriaFlightToIATA"), () -> setString());
		commands.put(MyTranslate.text("CriteriaFlightToCountry"), () -> setString());
		commands.put(MyTranslate.text("CriteriaPICName"), () -> setString());
		commands.put(MyTranslate.text("CriteriaPICNotes"), () -> setString());
		commands.put(MyTranslate.text("CriteriaSICName"), () -> setString());
		commands.put(MyTranslate.text("CriteriaSICNotes"), () -> setString());
		commands.put(MyTranslate.text("CriteriaTypeMake&Model"), () -> setString());
		commands.put(MyTranslate.text("CriteriaTypeGroup"), () -> setString());
		commands.put(MyTranslate.text("CriteriaTypeMTOW"), () -> setInteger());
		commands.put(MyTranslate.text("CriteriaTypeEngine"), () -> setString());
		commands.put(MyTranslate.text("CriteriaTypeMultiPilot"), () -> setBoolean());
		commands.put(MyTranslate.text("CriteriaTypeMultiEngine"), () -> setBoolean());
		commands.put(MyTranslate.text("CriteriaTypeSeaplane"), () -> setBoolean());
		commands.put(MyTranslate.text("CriteriaTypeEFIS"), () -> setBoolean());
		commands.put(MyTranslate.text("CriteriaAircraftRegistration"), () -> setString());
		commands.put(MyTranslate.text("CriteriaAircraftSimulator"), () -> setBoolean());
		commands.put(MyTranslate.text("CriteriaAircraftMTOW"), () -> setInteger());
		commands.put(MyTranslate.text("CriteriaRemarks"), () -> setString());
		commands.put(MyTranslate.text("CriteriaPrivateNotes"), () -> setString());
		commands.put(MyTranslate.text("CriteriaTotalTime"), () -> setTime());
		commands.put(MyTranslate.text("CriteriaNightTime"), () -> setTime());
		commands.put(MyTranslate.text("CriteriaIfrTime"), () -> setTime());
		commands.put(MyTranslate.text("CriteriaPicTime"), () -> setTime());
		commands.put(MyTranslate.text("CriteriaPicusTime"), () -> setTime());
		commands.put(MyTranslate.text("CriteriaCopilotTime"), () -> setTime());
		commands.put(MyTranslate.text("CriteriaDualTime"), () -> setTime());
		commands.put(MyTranslate.text("CriteriaInstructorTime"), () -> setTime());
		commands.put(MyTranslate.text("CriteriaCrossCountryTime"), () -> setTime());
		commands.put(MyTranslate.text("CriteriaSimulatorTime"), () -> setTime());

		commands.get(cmbField.getValue()).run();
	}

	private void setString() {

		ObservableList<String> actionsList = FXCollections.observableArrayList(
				MyTranslate.text("OperatorContains"),
				MyTranslate.text("OperatorStartsWith"),
				MyTranslate.text("OperatorDoesntStartWith"),
				MyTranslate.text("OperatorEndsWith"),
				MyTranslate.text("OperatorDoesntEndWith"),
				MyTranslate.text("OperatorIs"), 
				MyTranslate.text("OperatorIsNot"));
				
		txtSearchString.setPromptText("");
		txtSearchString.setDisable(false);
		txtSearchString.setText("");
		cmbAction.setItems(actionsList);
		cmbAction.getSelectionModel().select(0);
	}

	private void setBoolean() {
		ObservableList<String> actionsList = FXCollections.observableArrayList(MyTranslate.text("LogicalIsTrue"),
				MyTranslate.text("LogicalIsFalse"));
		txtSearchString.setPromptText("");
		txtSearchString.setDisable(true);
		txtSearchString.setText("");
		cmbAction.setItems(actionsList);
		cmbAction.getSelectionModel().select(0);
	}

	private void setInteger() {
		ObservableList<String> actionsList = FXCollections.observableArrayList(MyTranslate.text("LogicalGreaterThan"),
				MyTranslate.text("LogicalLessThan"), MyTranslate.text("LogicalEqual"));
		txtSearchString.setPromptText(MyTranslate.text("IntegerValue"));
		txtSearchString.setDisable(false);
		txtSearchString.setText("");
		cmbAction.setItems(actionsList);
		cmbAction.getSelectionModel().select(0);
	}

	private void setTime() {
		ObservableList<String> actionsList = FXCollections.observableArrayList(MyTranslate.text("LogicalGreaterThan"),
				MyTranslate.text("LogicalLessThan"), MyTranslate.text("LogicalEqual"));
		txtSearchString.setPromptText(MyTranslate.text("TimeInMinutes"));
		txtSearchString.setDisable(false);
		txtSearchString.setText("");
		cmbAction.setItems(actionsList);
		cmbAction.getSelectionModel().select(0);
	}

	@FXML
	void btnCancelAction(ActionEvent event) { // NO_UCD (unused code)
		Stage stage = (Stage) anchorPanel.getScene().getWindow();
		stage.close();
	}

	@FXML
	void btnSetAction(ActionEvent event) { // NO_UCD (unused code)

		Map<String, Runnable> commands = new HashMap<>();
		commands.put(MyTranslate.text("CriteriaFlightFromIcao"), () -> stringMatch("dep_airport.icao"));
		commands.put(MyTranslate.text("CriteriaFlightFromIATA"), () -> stringMatch("dep_airport.iata"));
		commands.put(MyTranslate.text("CriteriaFlightFromCountry"), () -> stringMatch("dep_airport.airport_country"));
		commands.put(MyTranslate.text("CriteriaFlightToICAO"), () -> stringMatch("arr_airport.icao"));
		commands.put(MyTranslate.text("CriteriaFlightToIATA"), () -> stringMatch("arr_airport.iata"));
		commands.put(MyTranslate.text("CriteriaFlightToCountry"), () -> stringMatch("arr_airport.airport_country"));
		commands.put(MyTranslate.text("CriteriaPICName"), () -> stringMatch("pic.crew_name"));
		commands.put(MyTranslate.text("CriteriaPICNotes"), () -> stringMatch("pic.comments"));
		commands.put(MyTranslate.text("CriteriaSICName"), () -> stringMatch("sic.crew_name"));
		commands.put(MyTranslate.text("CriteriaSICNotes"), () -> stringMatch("sic.comments"));
		commands.put(MyTranslate.text("CriteriaTypeMake&Model"), () -> stringMatch("model.model_name"));
		commands.put(MyTranslate.text("CriteriaTypeGroup"), () -> stringMatch("model.model_group"));
		commands.put(MyTranslate.text("CriteriaTypeMTOW"), () -> integerMatch("model.mtow"));
		commands.put(MyTranslate.text("CriteriaTypeEngine"), () -> stringMatch("model.engine_type"));
		commands.put(MyTranslate.text("CriteriaTypeMultiPilot"), () -> booleanMatch("model.multi_pilot"));
		commands.put(MyTranslate.text("CriteriaTypeMultiEngine"), () -> booleanMatch("model.multi_engine"));
		commands.put(MyTranslate.text("CriteriaTypeSeaplane"), () -> booleanMatch("model.seaplane"));
		commands.put(MyTranslate.text("CriteriaTypeEFIS"), () -> booleanMatch("model.efis"));
		commands.put(MyTranslate.text("CriteriaAircraftRegistration"), () -> stringMatch("aircraft.registration"));
		commands.put(MyTranslate.text("CriteriaAircraftSimulator"), () -> booleanMatch("aircraft.simulator"));
		commands.put(MyTranslate.text("CriteriaAircraftMTOW"), () -> integerMatch("aircraft.aircraft_mtow"));
		commands.put(MyTranslate.text("CriteriaRemarks"), () -> stringMatch("flight.remarks"));
		commands.put(MyTranslate.text("CriteriaPrivateNotes"), () -> stringMatch("flight.private_notes"));
		commands.put(MyTranslate.text("CriteriaTotalTime"), () -> integerMatch("flight.total_time"));
		commands.put(MyTranslate.text("CriteriaNightTime"), () -> integerMatch("flight.night_time"));
		commands.put(MyTranslate.text("CriteriaIfrTime"), () -> integerMatch("flight.ifr_time"));
		commands.put(MyTranslate.text("CriteriaPicTime"), () -> integerMatch("flight.pic_time"));
		commands.put(MyTranslate.text("CriteriaPicusTime"), () -> integerMatch("flight.picus_time"));
		commands.put(MyTranslate.text("CriteriaCopilotTime"), () -> integerMatch("flight.sic_time"));
		commands.put(MyTranslate.text("CriteriaDualTime"), () -> integerMatch("flight.dual_time"));
		commands.put(MyTranslate.text("CriteriaInstructorTime"), () -> integerMatch("flight.instructor_time"));
		commands.put(MyTranslate.text("CriteriaCrossCountryTime"), () -> integerMatch("flight.xc_time"));
		commands.put(MyTranslate.text("CriteriaSimulatorTime"), () -> integerMatch("flight.fstd_time"));

		commands.get(cmbField.getValue()).run();

		Stage stage = (Stage) anchorPanel.getScene().getWindow();
		stage.close();
	}

	private void integerMatch(String dataField) {
		String searchString = txtSearchString.getText();
		String fieldString = cmbField.getValue();
		
		if (txtSearchString.getText().matches("^[0-9]\\d*$")) {
			if (cmbAction.getValue().equals(MyTranslate.text("LogicalGreaterThan"))) {
				newSearchCriteria.setCommand(dataField + " > ? ");
				newSearchCriteria.setDescription(
						MyTranslate.formated("DescriptionGreaterThan", new Object[] { fieldString, searchString }));

			} else if (cmbAction.getValue().equals(MyTranslate.text("LogicalLessThan"))) {
				newSearchCriteria.setCommand(dataField + " < ? ");
				newSearchCriteria.setDescription(
						MyTranslate.formated("DescriptionLessThan", new Object[] { fieldString, searchString }));
				
			} else {
				newSearchCriteria.setCommand(dataField + " = ? ");
				newSearchCriteria.setDescription(
						MyTranslate.formated("DescriptionEqual", new Object[] { fieldString, searchString }));
				
			}
			newSearchCriteria.setSearchString(txtSearchString.getText());
		} else {
			newSearchCriteria.setCommand("");
			newSearchCriteria.setDescription(
					MyTranslate.formated("DescriptionErrorNotInteger", new Object[] { cmbField.getValue() }));
		}
	}

	private void booleanMatch(String dataField) {
		if (cmbAction.getValue().equals(MyTranslate.text("LogicalIsTrue"))) {
			newSearchCriteria.setCommand(dataField + " = ? ");
			newSearchCriteria.setSearchString("1");
			newSearchCriteria
					.setDescription(MyTranslate.formated("DescriptionTrue", new Object[] { cmbField.getValue() }));
		} else {
			newSearchCriteria.setCommand(dataField + " = ? ");
			newSearchCriteria.setSearchString("0");
			newSearchCriteria
					.setDescription(MyTranslate.formated("DescriptionFalse", new Object[] { cmbField.getValue() }));
		}
	}

	private void stringMatch(String dataField) {
		if (txtSearchString.getText().length() > 0) {

			String searchString = txtSearchString.getText();
			String fieldString = cmbField.getValue();

			if (cmbAction.getValue().equals(MyTranslate.text("OperatorContains"))) {
				newSearchCriteria.setCommand(dataField + " LIKE ? ");
				newSearchCriteria.setSearchString("%" + txtSearchString.getText() + "%");
				newSearchCriteria.setDescription(
						MyTranslate.formated("DescriptionContains", new Object[] { fieldString, searchString }));

			} else if (cmbAction.getValue().equals(MyTranslate.text("OperatorStartsWith"))) {
				newSearchCriteria.setCommand(dataField + " LIKE ? ");
				newSearchCriteria.setSearchString(txtSearchString.getText() + "%");
				newSearchCriteria.setDescription(
						MyTranslate.formated("DescriptionStartsWith", new Object[] { fieldString, searchString }));

			} else if (cmbAction.getValue().equals(MyTranslate.text("OperatorDoesntStartWith"))) {
				newSearchCriteria.setCommand(dataField + " NOT LIKE ? ");
				newSearchCriteria.setSearchString(txtSearchString.getText() + "%");
				newSearchCriteria.setDescription(
						MyTranslate.formated("DescriptionDoesntStartWith", new Object[] { fieldString, searchString }));

			} else if (cmbAction.getValue().equals(MyTranslate.text("OperatorEndsWith"))) {
				newSearchCriteria.setCommand(dataField + " LIKE ? ");
				newSearchCriteria.setSearchString("%" + txtSearchString.getText());
				newSearchCriteria.setDescription(
						MyTranslate.formated("DescriptionEndsWith", new Object[] { fieldString, searchString }));

			} else if (cmbAction.getValue().equals(MyTranslate.text("OperatorDoesntEndWith"))) {
				newSearchCriteria.setCommand(dataField + " NOT LIKE ? ");
				newSearchCriteria.setSearchString("%" + txtSearchString.getText());
				newSearchCriteria.setDescription(
						MyTranslate.formated("DescriptionDoesntEndWith", new Object[] { fieldString, searchString }));

			} else if (cmbAction.getValue().equals(MyTranslate.text("OperatorIs"))) {
				newSearchCriteria.setCommand(dataField + " LIKE ? ");
				newSearchCriteria.setSearchString(txtSearchString.getText());
				newSearchCriteria.setDescription(
						MyTranslate.formated("DescriptionIs", new Object[] { fieldString, searchString }));
			} else {
				newSearchCriteria.setCommand(dataField + " NOT LIKE ? ");
				newSearchCriteria.setSearchString(txtSearchString.getText());
				newSearchCriteria.setDescription(
						MyTranslate.formated("DescriptionIsNot", new Object[] { fieldString, searchString }));
			}
		} else {
			newSearchCriteria.setCommand("");
			newSearchCriteria.setDescription(
					MyTranslate.formated("DescriptionErrorEmpty", new Object[] { cmbField.getValue() }));
		}
	}

	public SearchCriteria getSearchCriteria() {
		return newSearchCriteria;
	}
}
