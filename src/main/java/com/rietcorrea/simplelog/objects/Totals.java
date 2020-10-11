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
package com.rietcorrea.simplelog.objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author riet
 */
public class Totals {
    
    private StringProperty group = new SimpleStringProperty();
    
    private IntegerProperty sectors = new SimpleIntegerProperty();
    private IntegerProperty takeOffDay = new SimpleIntegerProperty();
    private IntegerProperty takeOffNight = new SimpleIntegerProperty();
    private IntegerProperty landingDay = new SimpleIntegerProperty();
    private IntegerProperty landingNight = new SimpleIntegerProperty();

    private IntegerProperty ifrTime = new SimpleIntegerProperty();
    private IntegerProperty nightTime = new SimpleIntegerProperty();
    private IntegerProperty xcTime = new SimpleIntegerProperty();
    private IntegerProperty picTime = new SimpleIntegerProperty();
    private IntegerProperty picUsTime = new SimpleIntegerProperty();
    private IntegerProperty sicTime = new SimpleIntegerProperty();
    private IntegerProperty dualTime = new SimpleIntegerProperty();
    private IntegerProperty instructorTime = new SimpleIntegerProperty();
    private IntegerProperty fstdTime = new SimpleIntegerProperty();
    
    private IntegerProperty mel = new SimpleIntegerProperty();
    private IntegerProperty sel = new SimpleIntegerProperty();
    private IntegerProperty mes = new SimpleIntegerProperty();
    private IntegerProperty ses = new SimpleIntegerProperty();
    
    private IntegerProperty multiPilot = new SimpleIntegerProperty();
    
    private IntegerProperty totalTime = new SimpleIntegerProperty();

    public Totals() {
        this.group.set("");
        this.sectors.set(0);
        this.takeOffDay.set(0);
        this.takeOffNight.set(0);
        this.landingDay.set(0);
        this.landingNight.set(0);
        this.ifrTime.set(0);
        this.nightTime.set(0);
        this.xcTime.set(0);
        this.picTime.set(0);
        this.picUsTime.set(0);
        this.sicTime.set(0);
        this.dualTime .set(0);
        this.instructorTime.set(0);
        this.fstdTime.set(0);
        this.mel.set(0);
        this.sel.set(0);
        this.mes.set(0);
        this.ses.set(0);
        this.multiPilot.set(0);
        this.totalTime.set(0);
    }
    
    public Totals(Totals newTotal) {
        this.sectors.set(newTotal.getSectors());
        this.takeOffDay.set(newTotal.getTakeOffDay());
        this.takeOffNight.set(newTotal.getTakeOffNight());
        this.landingDay.set(newTotal.getLandingDay());
        this.landingNight.set(newTotal.getLandingNight());
        this.ifrTime.set(newTotal.getIfrTime());
        this.nightTime.set(newTotal.getNightTime());
        this.xcTime.set(newTotal.getXcTime());
        this.picTime.set(newTotal.getPicTime());
        this.picUsTime.set(newTotal.getPicUsTime());
        this.sicTime.set(newTotal.getSicTime());
        this.dualTime .set(newTotal.getDualTime());
        this.instructorTime.set(newTotal.getInstructorTime());
        this.fstdTime.set(newTotal.getFstdTime());
        this.mel.set(newTotal.getMel());
        this.sel.set(newTotal.getSel());
        this.mes.set(newTotal.getMes());
        this.ses.set(newTotal.getSes());
        this.multiPilot.set(newTotal.getMultiPilot());
        this.totalTime.set(newTotal.getTotalTime());
    }
    
    public Totals(String group,
            int sectors,
            int takeOffDay,
            int takeOffNight,
            int landingDay,
            int landingNight,
            int ifrTime,
            int nightTime,
            int xcTime,
            int picTime,
            int picUsTime,
            int sicTime,
            int dualTime,
            int instructorTime,
            int fstdTime,
            int mel,
            int sel,
            int mes,
            int ses,
            int multiPilot,
            int totalTime) {
        this.group.set(group);
        this.sectors.set(sectors);
        this.takeOffDay.set(takeOffDay);
        this.takeOffNight.set(takeOffNight);
        this.landingDay.set(landingDay);
        this.landingNight.set(landingNight);
        this.ifrTime.set(ifrTime);
        this.nightTime.set(nightTime);
        this.xcTime.set(xcTime);
        this.picTime.set(picTime);
        this.picUsTime.set(picUsTime);
        this.sicTime.set(sicTime);
        this.dualTime .set(dualTime);
        this.instructorTime.set(instructorTime);
        this.fstdTime.set(fstdTime);
        this.mel.set(mel);
        this.sel.set(sel);
        this.mes.set(mes);
        this.ses.set(ses);
        this.multiPilot.set(multiPilot);
        this.totalTime.set(totalTime);
    }
    
    public void addTotal(Totals addTotal) {
        this.sectors.set(this.sectors.getValue() + addTotal.getSectors());
        this.takeOffDay.set(this.takeOffDay.getValue() + addTotal.getTakeOffDay());
        this.takeOffNight.set(this.takeOffNight.getValue() + addTotal.getTakeOffNight());
        this.landingDay.set(this.landingDay.getValue() + addTotal.getLandingDay());
        this.landingNight.set(this.landingNight.getValue() + addTotal.getLandingNight());
        this.ifrTime.set(this.ifrTime.getValue() + addTotal.getIfrTime());
        this.nightTime.set(this.nightTime.getValue() + addTotal.getNightTime());
        this.xcTime.set(this.xcTime.getValue() + addTotal.getXcTime());
        this.picTime.set(this.picTime.getValue() + addTotal.getPicTime());
        this.picUsTime.set(this.picUsTime.getValue() + addTotal.getPicUsTime());
        this.sicTime.set(this.sicTime.getValue() + addTotal.getSicTime());
        this.dualTime .set(this.dualTime.getValue() + addTotal.getDualTime());
        this.instructorTime.set(this.instructorTime.getValue() + addTotal.getInstructorTime());
        this.fstdTime.set(this.fstdTime.getValue() + addTotal.getFstdTime());
        this.mel.set(this.mel.getValue() + addTotal.getMel());
        this.sel.set(this.sel.getValue() + addTotal.getSel());
        this.mes.set(this.mes.getValue() + addTotal.getMes());
        this.ses.set(this.ses.getValue() + addTotal.getSes());
        this.multiPilot.set(this.multiPilot.getValue() + addTotal.getMultiPilot());
        this.totalTime.set(this.totalTime.getValue() + addTotal.getTotalTime());
    }
    
    public void addFlight(Flight flight) {
    	this.sectors.set(this.sectors.getValue() + 1);
        this.takeOffDay.set(this.takeOffDay.getValue() + flight.getTakeOffDay());
        this.takeOffNight.set(this.takeOffNight.getValue() + flight.getTakeOffNight());
        this.landingDay.set(this.landingDay.getValue() + flight.getLandingDay());
        this.landingNight.set(this.landingNight.getValue() + flight.getLandingNight());
        this.ifrTime.set(this.ifrTime.getValue() + flight.getIfrTime());
        this.nightTime.set(this.nightTime.getValue() + flight.getNightTime());
        this.xcTime.set(this.xcTime.getValue() + flight.getXcTime());
        this.picTime.set(this.picTime.getValue() + flight.getPicTime());
        this.picUsTime.set(this.picUsTime.getValue() + flight.getPicUsTime());
        this.sicTime.set(this.sicTime.getValue() + flight.getSicTime());
        this.dualTime .set(this.dualTime.getValue() + flight.getDualTime());
        this.instructorTime.set(this.instructorTime.getValue() + flight.getInstructorTime());
        this.fstdTime.set(this.fstdTime.getValue() + flight.getFstdTime());
        
        // if NOT Multi Pilot
        if (!flight.getAircraft().getAircraftModel().getMultiPilot()) {
	        // if NOT a seaplane
	        if (!flight.getAircraft().getAircraftModel().getSeaplane()) {
	        	// if Multi engine
				if (flight.getAircraft().getAircraftModel().getMultiEngine()) {
					this.mel.set(this.mel.getValue() + flight.getTotalTime());
				} else {
			        this.sel.set(this.sel.getValue() + flight.getTotalTime());
				}
			} else {
				if (flight.getAircraft().getAircraftModel().getMultiEngine()) {
					this.mes.set(this.mes.getValue() + flight.getTotalTime());
				} else {
			        this.ses.set(this.ses.getValue() + flight.getTotalTime());
				}
			}
        }
        
        // if Multi Pilot
        if (flight.getAircraft().getAircraftModel().getMultiPilot()) {
			this.setMultiPilot(this.multiPilot.getValue() + flight.getTotalTime());
		} else {

		}
        this.totalTime.set(this.totalTime.getValue() + flight.getTotalTime());
	}

    public String getGroup() {
        return group.get();
    }

    public void setGroup(String group) {
        this.group.set(group);
    }

    public IntegerProperty getSectorsProperty() {
        return sectors;
    }
    
    public Integer getSectors() {
        return sectors.get();
    }

    public void setSectors(Integer sectors) {
        this.sectors.set(sectors);
    }
    
    public Integer getTakeOffDay() {
        return takeOffDay.get();
    }

    public void setTakeOffDay(Integer takeOffDay) {
        this.takeOffDay.set(takeOffDay);
    }

    public Integer getTakeOffNight() {
        return takeOffNight.get();
    }

    public void setTakeOffNight(Integer takeOffNight) {
        this.takeOffNight.set(takeOffNight);
    }

    public Integer getLandingDay() {
        return landingDay.get();
    }

    public void setLandingDay(Integer landingDay) {
        this.landingDay.set(landingDay);
    }

    public Integer getLandingNight() {
        return landingNight.get();
    }

    public void setLandingNight(Integer landingNight) {
        this.landingNight.set(landingNight);
    }

    public Integer getLanding() {
        return landingNight.get() + landingDay.get();
    }

    public int getIfrTime() {
        return ifrTime.get();
    }

    public void setIfrTime(int ifrTime) {
        this.ifrTime.set(ifrTime);
    }

    public int getNightTime() {
        return nightTime.get();
    }

    public void setNightTime(int nightTime) {
        this.nightTime.set(nightTime);
    }

    public int getXcTime() {
        return xcTime.get();
    }

    public void setXcTime(int xcTime) {
        this.xcTime.set(xcTime);
    }

    public int getPicTime() {
        return picTime.get();
    }

    public void setPicTime(int picTime) {
        this.picTime.set(picTime);
    }
    
    public int getPicUsTime() {
        return picUsTime.get();
    }

    public void setPicUsTime(int picUsTime) {
        this.picUsTime.set(picUsTime);
    }

    public int getSicTime() {
        return sicTime.get();
    }

    public void setSicTime(int sicTime) {
        this.sicTime.set(sicTime);
    }

    public int getDualTime() {
        return dualTime.get();
    }

    public void setDualTime(int dualTime) {
        this.dualTime.set(dualTime);
    }

    public int getInstructorTime() {
        return instructorTime.get();
    }

    public void setInstructorTime(int instructorTime) {
        this.instructorTime.set(instructorTime);
    }

    public int getFstdTime() {
        return fstdTime.get();
    }

    public void setFstdTime(int fstdTime) {
        this.fstdTime.set(fstdTime);
    }
    
    public void setMel(int mel) {
        this.mel.set(mel);
    }

    public int getMel() {
        return mel.get();
    }
    
    public void setSel(int sel) {
        this.sel.set(sel);
    }

    public int getSel() {
        return sel.get();
    }
    
    public void setMes(int mes) {
        this.mes.set(mes);
    }

    public int getMes() {
        return mes.get();
    }
    
    public void setSes(int ses) {
        this.ses.set(ses);
    }

    public int getSes() {
        return ses.get();
    }
    
    public void setMultiPilot(int multiPilot) {
        this.multiPilot.set(multiPilot);
    }

    public int getMultiPilot() {
        return multiPilot.get();
    }

    public int getTotalTime() {
        return totalTime.get();
    }

    public void setTotalTime(int totalTime) {
        this.totalTime.set(totalTime);
    }
}
