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
package com.rietcorrea.simplelog.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rietcorrea.constants.StrLen;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author riet
 */
public class Aircraft {
    
    private IntegerProperty aircraftId = new SimpleIntegerProperty();
    private StringProperty registration = new SimpleStringProperty();
    private IntegerProperty aircraftMtow = new SimpleIntegerProperty();
    private BooleanProperty simulator = new SimpleBooleanProperty();
    private Model aircraftModel;

    public Aircraft() {
        this.aircraftId.set(0);
        this.registration.set("");
        this.aircraftMtow.set(0);
        this.simulator.set(false);
        aircraftModel = new Model();
    }
    
    public Aircraft resetAircraft() {
    	this.registration.set("");
        this.aircraftMtow.set(0);
        this.simulator.set(false);
        
        return this;
	}
    
    public Aircraft(Aircraft that){
        this.setAircraftId(that.getAircraftId())
        	.setRegistration(that.getRegistration())
        	.setAircraftMtow(that.getAircraftMtow())
        	.setSimulator(that.getSimulator())
        	.setAircraftModel(that.getAircraftModel());
    }
    
    public String[] getAircraftArray() {
        List<String> aircraftArray = new ArrayList<>();
        
        aircraftArray.add(this.getRegistration());
        aircraftArray.add(this.getAircraftMtow().toString());
        aircraftArray.add(Boolean.toString(this.getSimulator()));
        
        // Add Model information
        aircraftArray.addAll(Arrays.asList(this.getAircraftModel().getModelArray()));
        
        return aircraftArray.toArray(new String[0]);
    }

    public Integer getAircraftId() {
        return aircraftId.get();
    }

    public Aircraft setAircraftId(Integer aircraftId) {
        this.aircraftId.set(aircraftId);
        return this;
    }
    
    public String getRegistration() {
        return registration.get().trim();
    }

    public Aircraft setRegistration(String registration) {
    	if (registration != null) {
    		this.registration.set(registration.trim().substring(0, Math.min(registration.length(), StrLen.AIRCRAFT_REGISTRATION_MAX)));
    	}
        return this;
    }
    
    public StringProperty registrationProperty(){
        return this.registration;
    }

    public Integer getAircraftMtow() {
        return aircraftMtow.get();
    }

    public Aircraft setAircraftMtow(Integer mtow) {
        this.aircraftMtow.set(mtow);
        return this;
    }
    
    public IntegerProperty aircraftMtowProperty(){
        return this.aircraftMtow;
    }

    public Boolean getSimulator() {
        return simulator.get();
    }

    public Aircraft setSimulator(Boolean simulator) {
        this.simulator.set(simulator);
        return this;
    }
    
    public BooleanProperty simulatorProperty(){
        return simulator;
    }
    

    public Model getAircraftModel() {
        return aircraftModel;
    }

    public Aircraft setAircraftModel(Model aircraftModel) {
        this.aircraftModel = new Model(aircraftModel);
        return this;
    }
    
    @Override
    public String toString(){
        return this.getRegistration();
    }
}
