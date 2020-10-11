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
public class Model {
    
    private IntegerProperty modelId = new SimpleIntegerProperty();
    private StringProperty modelName = new SimpleStringProperty();
    private StringProperty modelGroup = new SimpleStringProperty();
    private StringProperty engineType = new SimpleStringProperty();
    private IntegerProperty mtow = new SimpleIntegerProperty();
    private BooleanProperty multiEngine = new SimpleBooleanProperty();
    private BooleanProperty multiPilot = new SimpleBooleanProperty();
    private BooleanProperty efis = new SimpleBooleanProperty();
    private BooleanProperty seaplane = new SimpleBooleanProperty();
    
    public Model() {
        this.modelId.set(0);
        this.modelName.set("");
        this.modelGroup.set("");
        this.engineType.set("Turbofan");
        this.mtow.set(0);
        this.multiEngine.set(true);
        this.multiPilot.set(true);
        this.efis.set(true);
        this.seaplane.set(false);
    }
    
    public Model resetModel() {
    	this.modelName.set("");
        this.modelGroup.set("");
        this.engineType.set("Turbofan");
        this.mtow.set(0);
        this.multiEngine.set(true);
        this.multiPilot.set(true);
        this.efis.set(true);
        this.seaplane.set(false);
        
        return this;
	}
    
    public Model(Model that){
    	
    	this.setModelId(that.getModelId())
    		.setModelName(that.getModelName())
    		.setModelGroup(that.getModelGroup())
    		.setEngineType(that.getEngineType())
    		.setMtow(that.getMtow())
    		.setMultiEngine(that.getMultiEngine())
    		.setMultiPilot(that.getMultiPilot())
    		.setEfis(that.getEfis())
    		.setSeaplane(that.getSeaplane());
    }
    
    public String[] getModelArray() {
        List<String> modelArray = new ArrayList<>();
        
        modelArray.add(this.getModelName());
        modelArray.add(this.getModelGroup());
        modelArray.add(this.getEngineType());
        modelArray.add(this.getMtow().toString());
        modelArray.add(this.getMultiEngine().toString());
        modelArray.add(this.getMultiPilot().toString());
        modelArray.add(this.getEfis().toString());
        modelArray.add(this.getSeaplane().toString());
        
        return modelArray.toArray(new String[0]);
    }

    public Integer getModelId() {
        return modelId.get();
    }

    public Model setModelId(Integer typeId) {
        this.modelId.set(typeId);
        return this;
    }
    
    public String getModelName() {
        return modelName.get();
    }

    public Model setModelName(String modelName) {
    	if (modelName != null) {
    		this.modelName.set(modelName.substring(0, Math.min(modelName.length(), StrLen.MODEL_NAME_MAX)));
    	}
        return this;
    }
    
    public StringProperty modelNameProperty(){
        return modelName;
    }

    public String getModelGroup() {
        return modelGroup.get();
    }

    public Model setModelGroup(String modelGroup) {
    	if (modelGroup != null) {
			this.modelGroup.set(modelGroup.substring(0, Math.min(modelGroup.length(), StrLen.MODEL_GROUP)));
		}
        
        return this;
    }
    
    public StringProperty modelGroupProperty(){
        return modelGroup;
    }

    public String getEngineType() {
        return engineType.get();
    }

    public Model setEngineType(String engineType) {
    	if (engineType != null) {
			        this.engineType.set(engineType.substring(0, Math.min(engineType.length(), StrLen.MEDEL_ENGINE_TYPE)));
		}
        return this;
    }
    
    public StringProperty engineTypeProperty(){
        return engineType;
    }

    public Integer getMtow() {
        return mtow.get();
    }

    public Model setMtow(Integer mtow) {
        this.mtow.set(mtow);
        return this;
    }
    
    public IntegerProperty mtowProperty(){
        return mtow;
    }

    public Boolean getMultiEngine() {
        return multiEngine.get();
    }

    public Model setMultiEngine(Boolean multiEngine) {
        this.multiEngine.set(multiEngine);
        return this;
    }
    
    public BooleanProperty multiEngineProperty(){
        return multiEngine;
    }

    public Boolean getMultiPilot() {
        return multiPilot.get();
    }

    public Model setMultiPilot(Boolean multiPilot) {
        this.multiPilot.set(multiPilot);
        return this;
    }
    
    public BooleanProperty multiPilotProperty(){
        return multiPilot;
    }

    public Boolean getEfis() {
        return efis.get();
    }

    public Model setEfis(Boolean efis) {
        this.efis.set(efis);
        return this;
    }
    
    public BooleanProperty efisProperty(){
        return efis;
    }
    
    public Boolean getSeaplane() {
        return seaplane.get();
    }
    
    public Model setSeaplane(Boolean seaplane) {
        this.seaplane.set(seaplane);
        return this;
    }
    
    public BooleanProperty seaplaneProperty(){
        return seaplane;
    }
    
    @Override
    public String toString() {
        return this.getModelName();
    }
}
