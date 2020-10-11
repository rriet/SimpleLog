package com.rietcorrea.simplelog;

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
import com.rietcorrea.controls.IntegerTextField;
import com.rietcorrea.controls.TimeField;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author riet
 */
public class PreferencesController implements Initializable { // NO_UCD (unused code)

    @FXML
    private ComboBox<String> cmbIfrPercentage;
    @FXML
    private IntegerTextField txtIfrDeduction;
    @FXML
    private IntegerTextField txtIftMinimum;
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
    private ComboBox<String> cmbFunction;
    @FXML
    private TimeField hourTotal;
    @FXML
    private TimeField hourNight;
    @FXML
    private TimeField hourIfr;
    @FXML
    private TimeField hourPic;
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
    private IntegerTextField txtNavigationLimit;
    @FXML
    private TimeField hourPicus;
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
    private ComboBox<MyLocale> cmbLanguage;
    @FXML
    private CheckBox chkSubmitErrorLog;


    
    UserPreferences pref;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCmbIfrPercentage();
        initializeCmbFunction();
        loadPreferences();
        initializeLanguages();
        
        chkSubmitErrorLog.setSelected(pref.getErrorReport());

        // IFR calculation preferences
        cmbIfrPercentage.valueProperty().addListener(e
                -> pref.setIfrPercentage(cmbIfrPercentage.getValue()));

        txtIfrDeduction.textProperty().addListener(e
                -> pref.setIfrDeduction(txtIfrDeduction.getText())
        );
        
        txtIftMinimum.textProperty().addListener(e
                -> pref.setIfrMinimum(txtIftMinimum.getText())
        );
        
        // Navigation preference
        txtNavigationLimit.textProperty().addListener(e
                -> pref.setXcDistance(txtNavigationLimit.getInteger())
        );
        
        cmbFunction.valueProperty().addListener(e
                -> pref.setCrewPosition(cmbFunction.getValue()));
        
        //Initial Time
        txtSectors.textProperty().addListener(e
                -> pref.setSectorsBefore(txtSectors.getInteger())
        );
        
        txtTakeoffDay.textProperty().addListener(e
                -> pref.setToDayBefore(txtTakeoffDay.getInteger())
        );
        
        txtTakeoffNight.textProperty().addListener(e
                -> pref.setToNightBefore(txtTakeoffNight.getInteger())
        );
        
        txtLandingDay.textProperty().addListener(e
                -> pref.setLdgDayBefore(txtLandingDay.getInteger())
        );
        
        
        txtLandingNight.textProperty().addListener(e
                -> pref.setLdgNightBefore(txtLandingNight.getInteger())
        );
        
        hourTotal.textProperty().addListener(e
                -> pref.setTotalTimeBefore(hourTotal.getMinutes())
        );
        
        hourNight.textProperty().addListener(e
                -> pref.setNightTimeBefore(hourNight.getMinutes())
        );
        
        hourIfr.textProperty().addListener(e
                -> pref.setIfrTimeBefore(hourIfr.getMinutes())
        );
        
        hourPic.textProperty().addListener(e
                -> pref.setPicTimeBefore(hourPic.getMinutes())
        );
        
        hourPicus.textProperty().addListener(e
                -> pref.setPicUsTimeBefore(hourPicus.getMinutes())
        );
        
        hourSic.textProperty().addListener(e
                -> pref.setSicTimeBefore(hourSic.getMinutes())
        );
        
        hourDual.textProperty().addListener(e
                -> pref.setDualTimeBefore(hourDual.getMinutes())
        );
        
        hourInstructor.textProperty().addListener(e
                -> pref.setInstructorTimeBefore(hourInstructor.getMinutes())
        );
        
        hourXc.textProperty().addListener(e
                -> pref.setXcTimeBefore(hourXc.getMinutes())
        );
        
        hourFstd.textProperty().addListener(e
                -> pref.setFstdTimeBefore(hourFstd.getMinutes())
        );
        
        hourMel.textProperty().addListener(e
                -> pref.setMelTimeBefore(hourMel.getMinutes())
        );
        
        hourSel.textProperty().addListener(e
                -> pref.setSelTimeBefore(hourSel.getMinutes())
        );
        
        hourSes.textProperty().addListener(e
                -> pref.setSesTimeBefore(hourSes.getMinutes())
        );
        
        hourMes.textProperty().addListener(e
                -> pref.setMesTimeBefore(hourMes.getMinutes())
        );
        
        hourMultiPilot.textProperty().addListener(e
                -> pref.setMultiPilotTimeBefore(hourMultiPilot.getMinutes())
        );
    }

    private void loadPreferences() {
        // IFR calculation preferences
        pref = new UserPreferences();
        cmbIfrPercentage.setValue(pref.getIfrPercentage() + "%");
        txtIfrDeduction.setText(pref.getIfrDeduction());
        txtIftMinimum.setText(pref.getIfrMinimun());
        
        cmbFunction.setValue(pref.getCrewPosition());
        
        // Navigation Limit
        txtNavigationLimit.setInteger(pref.getXcDistance());
        
        // Initial hours preferences
        txtSectors.setInteger(pref.getSectorsBefore());
        txtTakeoffDay.setInteger(pref.getToDayBefore());
        txtTakeoffNight.setInteger(pref.getToNightBefore());
        txtLandingDay.setInteger(pref.getLdgDayBefore());
        txtLandingNight.setInteger(pref.getLdgNightBefore());
        
        hourTotal.setMinutes(pref.getTotalTimeBefore());
        hourNight.setMinutes(pref.getNightTimeBefore());
        hourIfr.setMinutes(pref.getIfrTimeBefore());
        hourPic.setMinutes(pref.getPicTimeBefore());
        hourPicus.setMinutes(pref.getPicUsTimeBefore());
        hourSic.setMinutes(pref.getSicTimeBefore());
        hourDual.setMinutes(pref.getDualTimeBefore());
        hourInstructor.setMinutes(pref.getInstructorTimeBefore());
        hourXc.setMinutes(pref.getXcTimeBefore());
        hourFstd.setMinutes(pref.getFstdTimeBefore());
        hourMel.setMinutes(pref.getMelTimeBefore());
        hourSel.setMinutes(pref.getSelTimeBefore());
        hourSes.setMinutes(pref.getSesTimeBefore());
        hourMes.setMinutes(pref.getMesTimeBefore());
        hourMultiPilot.setMinutes(pref.getMultiPilotTimeBefore());
    }
    
    private void initializeLanguages() {
    	// List to colect languages availables
    	ObservableList<MyLocale> languageList = FXCollections.observableArrayList();
    	
    	int selectedLang = 0;
    	int index = 0;
    	String selectedLocale = pref.getLocale().toString();
    	
    	// check all available ISO languages if the file exist...
    	for(String lang: Locale.getISOLanguages()) {
    	      URL rb = ClassLoader.getSystemResource("dictionary_"+lang+".properties");
    	      if(rb!=null) {
    	    	  Locale locale = new Locale(lang);
    	    	  
    	    	  // if exist add to list...
    	    	  languageList.add(new MyLocale(locale.getDisplayLanguage() ,locale));
    	    	  if (lang.equalsIgnoreCase(selectedLocale)) {
    	    		  selectedLang = index;
    	    	  }
    	    	  index++;
    	      }
    	}
    	// populate ComboBox...
    	cmbLanguage.setItems(languageList);
    	if(!languageList.isEmpty()) {
    		cmbLanguage.setValue(languageList.get(selectedLang));
    	}
	}

    private void initializeCmbIfrPercentage() {
        // Initialize values on EngineType ComboBox
        ObservableList<String> percentageOptions = FXCollections.observableArrayList(
                "100%", "90%", "80%", "70%", "60%", "50%",
                "40%", "30%", "20%", "10%"
        );
        cmbIfrPercentage.setItems(percentageOptions);
    }
    
    private void initializeCmbFunction() {
        // Initialize values on EngineType ComboBox
        ObservableList<String> functionOptions = FXCollections.observableArrayList(
        		"Pilot in Command",
                "Second in Command"
        );
        cmbFunction.setItems(functionOptions);
    }

    @FXML
    void cbmLanguageAction(ActionEvent event) {
    	pref.setLocale(cmbLanguage.getValue().getLocale());
    }

    @FXML
    void chkSubmitErrorLogAction(ActionEvent event) {
    	pref.setErrorReport(chkSubmitErrorLog.isSelected());
    }
}


class MyLocale {
	Locale locale;
	String languageNameString;
	
	public MyLocale(String languageNameString, Locale locale) {
		this.languageNameString = languageNameString;
		this.locale = locale;
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	@Override
	public String toString() {
		return languageNameString;
	}
}





