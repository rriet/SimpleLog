/*
 * Copyright (C) 2019 Ricardo Riet Correa - rietcorrea.com
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

import com.rietcorrea.constants.StrEng;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.csv.ImporterCsvOpener;
import com.rietcorrea.simplelog.csv.ImporterFlight;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author riet
 */
public class ImportFlightController implements Initializable {
	
	
	
	@FXML
	private AnchorPane anchorPane;
	@FXML
    private TextArea txtWarning;
	@FXML
	private TabPane tabPane;
    @FXML
    private Tab tabDate;
    @FXML
    private Button btnNextDate;
    @FXML
    private ComboBox<String> cmbDuplicatedFlight;
    @FXML
    private ComboBox<String> cmbDateFormat;
    @FXML
    private ComboBox<String> cmbDate;
    @FXML
    private Label lblDepDate;
    @FXML
    private Label lblDepTime;
    @FXML
    private ComboBox<String> cmbDepTime;
    @FXML
    private Label lblArrTime;
    @FXML
    private ComboBox<String> cmbArrTime;
    @FXML
    private CheckBox chkIgnoreDepArrTime;
    @FXML
    private Tab tabRmk;
    @FXML
    private ComboBox<String> cmbRemarks;
    @FXML
    private ComboBox<String> cmbPrivateNotes;
    @FXML
    private Tab tabLanding;
    @FXML
    private ComboBox<String> cmbPfPnf;
    @FXML
    private Label lblToDay;
    @FXML
    private Label lblToNight;
    @FXML
    private Label lblLdgDay;
    @FXML
    private Label lblLdgNight;
    @FXML
    private ComboBox<String> cmbTakeOffDay;
    @FXML
    private ComboBox<String> cmbTakeOffNight;
    @FXML
    private ComboBox<String> cmbLandingDay;
    @FXML
    private ComboBox<String> cmbLandingNight;
    @FXML
    private CheckBox chkReCalcToLdg;
    @FXML
    private Tab tabTimes;
    @FXML
    private ComboBox<String> cmbHourFormat;
    @FXML
    private ComboBox<String> cmbTimeIfr;
    @FXML
    private CheckBox chkReCalcIfr;
    @FXML
    private ComboBox<String> cmbTimeNight;
    @FXML
    private CheckBox chkReCalcTimeNight;
    @FXML
    private ComboBox<String> cmbTimeXc;
    @FXML
    private CheckBox chkReCalcTimeXc;
    @FXML
    private ComboBox<String> cmbTimePic;
    @FXML
    private ComboBox<String> cmbTimePicus;
    @FXML
    private ComboBox<String> cmbTimeSic;
    @FXML
    private ComboBox<String> cmbTimeDual;
    @FXML
    private ComboBox<String> cmbTimeInstructor;
    @FXML
    private ComboBox<String> cmbTimeSimulator;
    @FXML
    private ComboBox<String> cmbTimeTotal;
    @FXML
    private Tab tabType;
    @FXML
    private ComboBox<String> cmbAircraftModel;
    @FXML
    private ComboBox<String> cmbAircraftGroup;
    @FXML
    private ComboBox<String> cmbAircraftModelMtow;
    @FXML
    private ComboBox<String> cmbAircraftEngineType;
    @FXML
    private ComboBox<String> cmbAircraftMultiPilot;
    @FXML
    private ComboBox<String> cmbAircraftMultiEngine;
    @FXML
    private ComboBox<String> cmbAircraftEfis;
    @FXML
    private ComboBox<String> cmbAircraftSeaplane;
    @FXML
    private CheckBox chkOverrideModel;
    @FXML
    private Tab tabAircraft;
    @FXML
    private ComboBox<String> cmbAircraftRegistration;
    @FXML
    private ComboBox<String> cmbAircraftMtow;
    @FXML
    private ComboBox<String> cmbAircraftSimulator;
    @FXML
    private CheckBox chkOverrideAircraft;
    @FXML
    private Tab tabAirport;
    @FXML
    private RadioButton radioUseIcao;
    @FXML
    private ToggleGroup grpAirport;
    @FXML
    private RadioButton radioUseIata;
    @FXML
    private Label lblDepIcao;
    @FXML
    private Label lblDepIata;
    @FXML
    private ComboBox<String> cmbDepIcao;
    @FXML
    private ComboBox<String> cmbDepIata;
    @FXML
    private ComboBox<String> cmbDepName;
    @FXML
    private ComboBox<String> cmbDepCity;
    @FXML
    private ComboBox<String> cmbDepCountry;
    @FXML
    private ComboBox<String> cmbDepLat;
    @FXML
    private ComboBox<String> cmbDepLon;
    @FXML
    private ComboBox<String> cmbArrIcao;
    @FXML
    private ComboBox<String> cmbArrIata;
    @FXML
    private ComboBox<String> cmbArrName;
    @FXML
    private ComboBox<String> cmbArrCity;
    @FXML
    private ComboBox<String> cmbArrCountry;
    @FXML
    private ComboBox<String> cmbArrLat;
    @FXML
    private ComboBox<String> cmbArrLon;
    @FXML
    private CheckBox chkOverrideAirports;
    @FXML
    private Tab tabCrew;
    @FXML
    private ComboBox<String> cmbCrewNameFormat;
    @FXML
    private ComboBox<String> cmbPicName;
    @FXML
    private ComboBox<String> cmbPicEmail;
    @FXML
    private ComboBox<String> cmbPicPhone;
    @FXML
    private ComboBox<String> cmbPicComments;
    @FXML
    private ComboBox<String> cmbSicEmail;
    @FXML
    private ComboBox<String> cmbSicPhone;
    @FXML
    private ComboBox<String> cmbSicComments;
    @FXML
    private ComboBox<String> cmbSicName;
    @FXML
    private CheckBox chkOverrideCrew;

    Task<Void> importTask;
    String file;
    ImporterFlight importer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // do nothing
    }

    public void readFile(String selectedFile) {
        this.file = selectedFile;
        	
    	List<String[]> lineStrings = ImporterCsvOpener.open(selectedFile);
    	
        ObservableList<String> options = FXCollections.observableArrayList(MyTranslate.text("DontImport"));
        options.addAll(lineStrings.get(0));
        
        cmbDate.setItems(options);
        cmbDate.setValue(MyTranslate.text("DontImport"));
        cmbDepTime.setItems(options);
        cmbDepTime.setValue(MyTranslate.text("DontImport"));
        cmbArrTime.setItems(options);
        cmbArrTime.setValue(MyTranslate.text("DontImport"));
        cmbRemarks.setItems(options);
        cmbRemarks.setValue(MyTranslate.text("DontImport"));
        cmbPrivateNotes.setItems(options);
        cmbPrivateNotes.setValue(MyTranslate.text("DontImport"));
        cmbPfPnf.setItems(options);
        cmbPfPnf.setValue(MyTranslate.text("DontImport"));
        cmbTakeOffDay.setItems(options);
        cmbTakeOffDay.setValue(MyTranslate.text("DontImport"));
        cmbTakeOffNight.setItems(options);
        cmbTakeOffNight.setValue(MyTranslate.text("DontImport"));
        cmbLandingDay.setItems(options);
        cmbLandingDay.setValue(MyTranslate.text("DontImport"));
        cmbLandingNight.setItems(options);
        cmbLandingNight.setValue(MyTranslate.text("DontImport"));
        cmbTimeIfr.setItems(options);
        cmbTimeIfr.setValue(MyTranslate.text("DontImport"));
        cmbTimeNight.setItems(options);
        cmbTimeNight.setValue(MyTranslate.text("DontImport"));
        cmbTimeXc.setItems(options);
        cmbTimeXc.setValue(MyTranslate.text("DontImport"));
        cmbTimePic.setItems(options);
        cmbTimePic.setValue(MyTranslate.text("DontImport"));
        cmbTimePicus.setItems(options);
        cmbTimePicus.setValue(MyTranslate.text("DontImport"));
        cmbTimeSic.setItems(options);
        cmbTimeSic.setValue(MyTranslate.text("DontImport"));
        cmbTimeDual.setItems(options);
        cmbTimeDual.setValue(MyTranslate.text("DontImport"));
        cmbTimeInstructor.setItems(options);
        cmbTimeInstructor.setValue(MyTranslate.text("DontImport"));
        cmbTimeSimulator.setItems(options);
        cmbTimeSimulator.setValue(MyTranslate.text("DontImport"));
        cmbTimeTotal.setItems(options);
        cmbTimeTotal.setValue(MyTranslate.text("DontImport"));
        cmbAircraftModel.setItems(options);
        cmbAircraftModel.setValue(MyTranslate.text("DontImport"));
        cmbAircraftGroup.setItems(options);
        cmbAircraftGroup.setValue(MyTranslate.text("DontImport"));
        cmbAircraftModelMtow.setItems(options);
        cmbAircraftModelMtow.setValue(MyTranslate.text("DontImport"));
        cmbAircraftEngineType.setItems(options);
        cmbAircraftEngineType.setValue(MyTranslate.text("DontImport"));
        cmbAircraftMultiPilot.setItems(options);
        cmbAircraftMultiPilot.setValue(MyTranslate.text("DontImport"));
        cmbAircraftMultiEngine.setItems(options);
        cmbAircraftMultiEngine.setValue(MyTranslate.text("DontImport"));
        cmbAircraftEfis.setItems(options);
        cmbAircraftEfis.setValue(MyTranslate.text("DontImport"));
        cmbAircraftRegistration.setItems(options);
        cmbAircraftRegistration.setValue(MyTranslate.text("DontImport"));
        cmbAircraftSimulator.setItems(options);
        cmbAircraftSimulator.setValue(MyTranslate.text("DontImport"));
        cmbAircraftMtow.setItems(options);
        cmbAircraftMtow.setValue(MyTranslate.text("DontImport"));
        cmbAircraftSeaplane.setItems(options);
        cmbAircraftSeaplane.setValue(MyTranslate.text("DontImport"));
        cmbDepIcao.setItems(options);
        cmbDepIcao.setValue(MyTranslate.text("DontImport"));
        cmbDepIata.setItems(options);
        cmbDepIata.setValue(MyTranslate.text("DontImport"));
        cmbDepName.setItems(options);
        cmbDepName.setValue(MyTranslate.text("DontImport"));
        cmbDepCity.setItems(options);
        cmbDepCity.setValue(MyTranslate.text("DontImport"));
        cmbDepCountry.setItems(options);
        cmbDepCountry.setValue(MyTranslate.text("DontImport"));
        cmbDepLat.setItems(options);
        cmbDepLat.setValue(MyTranslate.text("DontImport"));
        cmbDepLon.setItems(options);
        cmbDepLon.setValue(MyTranslate.text("DontImport"));
        cmbArrIcao.setItems(options);
        cmbArrIcao.setValue(MyTranslate.text("DontImport"));
        cmbArrIata.setItems(options);
        cmbArrIata.setValue(MyTranslate.text("DontImport"));
        cmbArrName.setItems(options);
        cmbArrName.setValue(MyTranslate.text("DontImport"));
        cmbArrCity.setItems(options);
        cmbArrCity.setValue(MyTranslate.text("DontImport"));
        cmbArrCountry.setItems(options);
        cmbArrCountry.setValue(MyTranslate.text("DontImport"));
        cmbArrLat.setItems(options);
        cmbArrLat.setValue(MyTranslate.text("DontImport"));
        cmbArrLon.setItems(options);
        cmbArrLon.setValue(MyTranslate.text("DontImport"));
        cmbPicName.setItems(options);
        cmbPicName.setValue(MyTranslate.text("DontImport"));
        cmbPicEmail.setItems(options);
        cmbPicEmail.setValue(MyTranslate.text("DontImport"));
        cmbPicPhone.setItems(options);
        cmbPicPhone.setValue(MyTranslate.text("DontImport"));
        cmbPicComments.setItems(options);
        cmbPicComments.setValue(MyTranslate.text("DontImport"));
        cmbSicName.setItems(options);
        cmbSicName.setValue(MyTranslate.text("DontImport"));
        cmbSicEmail.setItems(options);
        cmbSicEmail.setValue(MyTranslate.text("DontImport"));
        cmbSicPhone.setItems(options);
        cmbSicPhone.setValue(MyTranslate.text("DontImport"));
        cmbSicComments.setItems(options);
        cmbSicComments.setValue(MyTranslate.text("DontImport"));
        
        ObservableList<String> duplicateFlightList = FXCollections.observableArrayList(
        		StrEng.DUPLICATED_IGNORE, StrEng.DUPLICATED_OVERRIDE, StrEng.DUPLICATED_IMPORT
        );
        cmbDuplicatedFlight.setItems(duplicateFlightList);
        cmbDuplicatedFlight.setValue(StrEng.DUPLICATED_IGNORE);

        ObservableList<String> dateFormatList = FXCollections.observableArrayList(
        		StrEng.DATE_FORMAT_DD_MM_YYYY,
        		StrEng.DATE_FORMAT_DD_MM_YYYY_LINE,
        		StrEng.DATE_FORMAT_MM_DD_YYYY,
        		StrEng.DATE_FORMAT_MM_DD_YYYY_LINE,
        		StrEng.DATE_FORMAT_YYYY_MM_DD,
        		StrEng.DATE_FORMAT_YYYY_MM_DD_LINE,
        		StrEng.DATE_EPOCH_TIME
        );
        cmbDateFormat.setItems(dateFormatList);
        cmbDateFormat.setValue(StrEng.DATE_FORMAT_DD_MM_YYYY);

        ObservableList<String> hourFormatList = FXCollections.observableArrayList(
        		StrEng.FORMATTED_HOUR,
        		StrEng.MINUTES_HOUR,
        		StrEng.DECIMAL_HOUR
        );
        cmbHourFormat.setItems(hourFormatList);
        cmbHourFormat.setValue(StrEng.FORMATTED_HOUR);

        ObservableList<String> crewNameFormat = FXCollections.observableArrayList(
        		StrEng.PILOT_NAME_UNCHANGED,
        		StrEng.PILOT_NAME_UPPER_FIRST,
        		StrEng.PILOT_NAME_UPPER,
        		StrEng.PILOT_NAME_LOWER
        );
        cmbCrewNameFormat.setItems(crewNameFormat);
        cmbCrewNameFormat.setValue(StrEng.PILOT_NAME_UNCHANGED);
        
        importer = new ImporterFlight();
        importer.setFile(this.file);
        List<String> errorStrings = importer.checkFileColumns();
        if (!errorStrings.isEmpty()) {
        	txtWarning.setText("Invalid file.\nThe number of columns in the following lines doesn't match the table header:\n");
        	for (String error : errorStrings) {
				txtWarning.appendText(error);
			}
        	txtWarning.appendText("Please fix the file and open the import wizzard again.");
        	btnNextDate.setDisable(true);
        }
    }
    
    @FXML
    void cmbDateFormatAction(ActionEvent event) {
    	if (cmbDateFormat.getValue().equals(StrEng.DATE_EPOCH_TIME)) {
    		cmbDate.setValue(MyTranslate.text("DontImport"));
    		cmbDate.setDisable(true);
    		lblDepDate.setTextFill(Color.BLACK);
    	} else {
			cmbDate.setDisable(false);
			lblDepDate.setTextFill(Color.RED);
		}
    	requiredDepArrTimes();
    }
    
    @FXML
    void clickReCalcToLdg(ActionEvent event) { // NO_UCD (unused code)
    	if (chkReCalcToLdg.isSelected()) {
    		lblToDay.setTextFill(Color.BLACK);
    		lblToNight.setTextFill(Color.BLACK);
    		lblLdgDay.setTextFill(Color.BLACK);
    		lblLdgNight.setTextFill(Color.BLACK);
    		
    		cmbTakeOffDay.setValue(MyTranslate.text("DontImport"));
    		cmbTakeOffDay.setDisable(true);
    		cmbTakeOffNight.setValue(MyTranslate.text("DontImport"));
    		cmbTakeOffNight.setDisable(true);
    		cmbLandingDay.setValue(MyTranslate.text("DontImport"));
    		cmbLandingDay.setDisable(true);
    		cmbLandingNight.setValue(MyTranslate.text("DontImport"));
    		cmbLandingNight.setDisable(true);
    	} else {
    		chkIgnoreDepArrTime.setDisable(false);
    		lblToDay.setTextFill(Color.RED);
    		lblToNight.setTextFill(Color.RED);
    		lblLdgDay.setTextFill(Color.RED);
    		lblLdgNight.setTextFill(Color.RED);
    		
    		cmbTakeOffDay.setDisable(false);
    		cmbTakeOffNight.setDisable(false);
    		cmbLandingDay.setDisable(false);
    		cmbLandingNight.setDisable(false);
		}
    	requiredDepArrTimes();
    }
    
    @FXML
    void clickReCalcNightTime(ActionEvent event) { // NO_UCD (unused code)
    	if (chkReCalcTimeNight.isSelected()) {
    		cmbTimeNight.setValue(MyTranslate.text("DontImport"));
    		cmbTimeNight.setDisable(true);
    		
    	} else {
    		cmbTimeNight.setDisable(false);
		}
    	requiredDepArrTimes();
    }
    
    private void requiredDepArrTimes() {
    	// Dep and Arr times are required when:
    	// Recalc Night or RecalcToLndg or using EPOCH time
    	if (chkReCalcToLdg.isSelected() || chkReCalcTimeNight.isSelected() || 
    			cmbDateFormat.getValue().equals(StrEng.DATE_EPOCH_TIME)) {
    		chkIgnoreDepArrTime.setSelected(false);
    		chkIgnoreDepArrTime.setDisable(true);
    		
    		lblArrTime.setTextFill(Color.RED);
    		lblDepTime.setTextFill(Color.RED);    		
    	} else {
    		chkIgnoreDepArrTime.setDisable(false);
    		
    		lblArrTime.setTextFill(Color.BLACK);
    		lblDepTime.setTextFill(Color.BLACK);
		}
	}
    
    @FXML
    void recalcIfrAction(ActionEvent event) { // NO_UCD (unused code)
    	if (chkReCalcIfr.isSelected()) {
    		cmbTimeIfr.setValue(MyTranslate.text("DontImport"));
    		cmbTimeIfr.setDisable(true);
    	} else {
    		cmbTimeIfr.setDisable(false);
		}
    }
    
    @FXML
    void recalcXcAction(ActionEvent event) { // NO_UCD (unused code)
    	if (chkReCalcTimeXc.isSelected()) {
    		cmbTimeXc.setValue(MyTranslate.text("DontImport"));
    		cmbTimeXc.setDisable(true);
    		
    	} else {
    		cmbTimeXc.setDisable(false);
		}
    }
    
    @FXML
    void clickUseIata(ActionEvent event) { // NO_UCD (unused code)
    	lblDepIcao.setTextFill(Color.BLACK);
    	lblDepIata.setTextFill(Color.RED);
    }

    @FXML
    void clickUseIcao(ActionEvent event) { // NO_UCD (unused code)
    	lblDepIcao.setTextFill(Color.RED);
    	lblDepIata.setTextFill(Color.BLACK);
    }
    
    // Finish //
    @FXML
    void btnFinishAction(ActionEvent event) {
    	close();
    }
    
    // Cancel //
    @FXML
    private void btnCancelAction(ActionEvent event) {
        close();
    }

    /**
     *  Close window
     */
    public void close() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }
    
    
    
    // Navigation //
    // Forward //
    
    @FXML
    void btnNextDateAction(ActionEvent event) {
    	
    	List<String> warninList = new ArrayList<String>();
    	
    	txtWarning.setText("");
    	boolean error = false;
    	
    	importer.setDuplicateFlights(cmbDuplicatedFlight.getValue())
	    		.setDateFormat(cmbDateFormat.getValue())
	    		.setDate(cmbDate.getSelectionModel().getSelectedIndex())
	    		.setDepTime(cmbDepTime.getSelectionModel().getSelectedIndex())
	    		.setArrTime(cmbArrTime.getSelectionModel().getSelectedIndex())
	    		.setIgnoreDepArrTimeError(chkIgnoreDepArrTime.isSelected());
    	
    	// check selection
    	
    	// Departure date if format is NOT epoch
    	if (cmbDate.getSelectionModel().getSelectedIndex() < 1 &&
    			!cmbDateFormat.getValue().equals(StrEng.DATE_EPOCH_TIME)) {
    		txtWarning.appendText("You must select a column for \"Flight Departure Date\".\n");
    		error = true;
    	}
    	
    	// Departure and Arrival Times are required
    	if (chkReCalcToLdg.isSelected() || chkReCalcTimeNight.isSelected() || 
    			cmbDateFormat.getValue().equals(StrEng.DATE_EPOCH_TIME)){
    		// Check if Dep time is selected
    		if (cmbDepTime.getSelectionModel().getSelectedIndex() < 1) {
    			txtWarning.appendText("You must select a column for \"Flight Chocks-Off Time\".\n");
    			error = true;
    		}
    		
    		// Check if Arr time is selected
    		if (cmbArrTime.getSelectionModel().getSelectedIndex() < 1) {
    			txtWarning.appendText("You must select a column for \"Flight Chocks-On Time\".\n");
    			error = true;
    		}
    	}
    	
    	// If selection was not made, return to avoid null pointer on array
    	if (error) {
    		return;
    	}
    	
    	// Check data
    	
    	// If there are warnings and Format is NOT Epoch
    	if (!cmbDateFormat.getValue().equals(StrEng.DATE_EPOCH_TIME)){
    		warninList = importer.checkDateFormat();
    		if (warninList.size() > 0) {
    			writeErrors("Invalid date format in line: ", warninList);
	    		error = true;
    		}
    	}
    	
    	// Check Dep and Arr time values
		warninList = importer.checkTimeFormat();
		if (warninList.size() > 0) {
			writeErrors("Invalid time format in line: ", warninList);
			if(!chkIgnoreDepArrTime.isSelected()) {
				error = true;
			}
		}
		
		if (!error) {
			tabPane.getSelectionModel().select(tabRmk);
		}
    }
    
    @FXML
    void btnNextRmkAction(ActionEvent event) {
    	txtWarning.setText("");
    	tabPane.getSelectionModel().select(tabLanding);
    }
    
    @FXML
    void btnNextLandingAction(ActionEvent event) {
    	txtWarning.setText("");
    	tabPane.getSelectionModel().select(tabTimes);
    }
    
    @FXML
    void btnNextTimesAction(ActionEvent event) {
    	txtWarning.setText("");
    	tabPane.getSelectionModel().select(tabType);
    }
    
    @FXML
    void btnNextTypeAction(ActionEvent event) {
    	txtWarning.setText("");
    	tabPane.getSelectionModel().select(tabAircraft);
    }

    @FXML
    void btnNextAircraftAction(ActionEvent event) {
    	txtWarning.setText("");
    	tabPane.getSelectionModel().select(tabAirport);
    }

    @FXML
    void btnNextAirportAction(ActionEvent event) {
    	txtWarning.setText("");
    	tabPane.getSelectionModel().select(tabCrew);
    }

    // Back // 
    @FXML
    void btnBackCrewAction(ActionEvent event) {
    	txtWarning.setText("");
    	tabPane.getSelectionModel().select(tabAirport);
    }
    
    @FXML
    void btnBackAirportAction(ActionEvent event) {
    	txtWarning.setText("");
    	tabPane.getSelectionModel().select(tabAircraft);
    }
    
    @FXML
    void btnBackAircraftAction(ActionEvent event) {
    	txtWarning.setText("");
    	tabPane.getSelectionModel().select(tabType);
    }
    
    @FXML
    void btnBackTypeAction(ActionEvent event) {
    	txtWarning.setText("");
    	tabPane.getSelectionModel().select(tabTimes);
    }
    
    @FXML
    void btnBackTimesAction(ActionEvent event) {
    	txtWarning.setText("");
    	tabPane.getSelectionModel().select(tabLanding);
    }

    @FXML
    void btnBackLandingAction(ActionEvent event) {
    	txtWarning.setText("");
    	tabPane.getSelectionModel().select(tabRmk);
    }

    @FXML
    void btnBackRmkAction(ActionEvent event) {
    	txtWarning.setText("");
    	tabPane.getSelectionModel().select(tabDate);
    }    
    
    private void writeErrors(String error, List<String> errorList) {
    	StringBuilder sb=new StringBuilder("ERROR!\n");  
    	for (String errorLine : errorList) {
    		sb.append(error + errorLine);
    		//txtWarning.appendText(error + errorLine);
		}
    	txtWarning.appendText(sb.toString());
	}
}
