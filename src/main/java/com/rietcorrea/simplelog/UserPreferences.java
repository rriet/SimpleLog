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

import com.rietcorrea.simplelog.objects.Totals;

import java.io.File;
import java.util.Locale;
import java.util.prefs.Preferences;

/**
 *
 * @author riet
 */
public class UserPreferences {
    
    private Preferences prefs;

    public UserPreferences() {
        prefs = Preferences.userRoot().node(this.getClass().getName());
    }
    
    public Locale getLocale() { 
    	return new Locale(prefs.get("programLocale", "en"));
	}
    
    public void setLocale(Locale locale) {
    	prefs.put("programLocale", locale.toString());
	}
    
    public boolean getErrorReport() {
        return prefs.getBoolean("errorReport", true);
    }
    
    public void setErrorReport(boolean errorReport) {
        prefs.putBoolean("errorReport", errorReport);
    }
    
    public String getDbPath() {
        // Return preference location for the DB or the app directory
        String dbPath = System.getProperty("user.home") + File.separatorChar + "SimpleLog.db";
        return prefs.get("dbPath", dbPath);
    }
    
    public void setDbPath(String newPath) {
        prefs.put("dbPath", newPath);
    }
    
    public String getCrewPosition() {
        return prefs.get("crewPosition", "Pilot in Command");
    }
    
    public void setCrewPosition(String crewPosition) {
        prefs.put("crewPosition", crewPosition);
    }
    
    public boolean getBackupOnImport() {
        return prefs.getBoolean("backupOnImport", true);
    }
    
    public void setBackupOnImport(boolean backupOnImport) {
        prefs.putBoolean("backupOnImport", backupOnImport);
    }
    
    public boolean getBackupOnRestore() {
    	return prefs.getBoolean("backupOnRestore", false);
	}

	public void setBackupOnRestore(boolean backupOnRestore) {
		prefs.putBoolean("backupOnRestore", backupOnRestore);
	}
    
    public String getIfrPercentage() {
        return prefs.get("ifrPercentage", "100");
    }
    
    public void setIfrPercentage(String newPercentage) {
        prefs.put("ifrPercentage", newPercentage.replace("%", ""));
    }
    
    public String getIfrDeduction() {
        return prefs.get("ifrDeduction", "0");
    }
    
    public void setIfrDeduction(String newDeduction) {
        prefs.put("ifrDeduction", newDeduction);
    }
    
    public String getIfrMinimun() {
        return prefs.get("ifrMinimum", "15");
    }
    
    public void setIfrMinimum(String newMin) {
        prefs.put("ifrMinimum", newMin);
    }
    
    public Integer getXcDistance() {
        return prefs.getInt("xcDistance", 50);
    }
    
    public void setXcDistance(int newValue) {
        prefs.putInt("xcDistance", newValue);
    }
    
    public Integer getSectorsBefore() {
        return prefs.getInt("sectorsBefore", 0);
    }
    
    public void setSectorsBefore(int newValue) {
        prefs.putInt("sectorsBefore", newValue);
    }
    
    public Integer getToDayBefore() {
        return prefs.getInt("toDayBefore", 0);
    }
    
    public void setToDayBefore(int newValue) {
        prefs.putInt("toDayBefore", newValue);
    }
    
    public Integer getToNightBefore() {
        return prefs.getInt("toNightBefore", 0);
    }
    
    public void setToNightBefore(int newValue) {
        prefs.putInt("toNightBefore", newValue);
    }
    
    public Integer getLdgDayBefore() {
        return prefs.getInt("ldgDayBefore", 0);
    }
    
    public void setLdgDayBefore(int newValue) {
        prefs.putInt("ldgDayBefore", newValue);
    }
    
    public Integer getLdgNightBefore() {
        return prefs.getInt("ldgNightBefore", 0);
    }
    
    public void setLdgNightBefore(int newValue) {
        prefs.putInt("ldgNightBefore", newValue);
    }
    
    public Integer getTotalTimeBefore() {
        return prefs.getInt("totalTimeBefore", 0);
    }
    
    public void setTotalTimeBefore(int newValue) {
        prefs.putInt("totalTimeBefore", newValue);
    }
    
    public Integer getNightTimeBefore() {
        return prefs.getInt("nightTimeBefore", 0);
    }
    
    public void setNightTimeBefore(int newValue) {
        prefs.putInt("nightTimeBefore", newValue);
    }
    
    public Integer getIfrTimeBefore() {
        return prefs.getInt("ifrTimeBefore", 0);
    }
    
    public void setIfrTimeBefore(int newValue) {
        prefs.putInt("ifrTimeBefore", newValue);
    }
    
    public Integer getPicTimeBefore() {
        return prefs.getInt("picTimeBefore", 0);
    }
    
    public void setPicTimeBefore(int newValue) {
        prefs.putInt("picTimeBefore", newValue);
    }
    
    public Integer getPicUsTimeBefore() {
        return prefs.getInt("picUsTimeBefore", 0);
    }
    
    public void setPicUsTimeBefore(int newValue) {
        prefs.putInt("picUsTimeBefore", newValue);
    }
    
    public Integer getSicTimeBefore() {
        return prefs.getInt("sicTimeBefore", 0);
    }
    
    public void setSicTimeBefore(int newValue) {
        prefs.putInt("sicTimeBefore", newValue);
    }
    
    public Integer getDualTimeBefore() {
        return prefs.getInt("dualTimeBefore", 0);
    }
    
    public void setDualTimeBefore(int newValue) {
        prefs.putInt("dualTimeBefore", newValue);
    }
    
    public Integer getInstructorTimeBefore() {
        return prefs.getInt("instructorTimeBefore", 0);
    }
    
    public void setInstructorTimeBefore(int newValue) {
        prefs.putInt("instructorTimeBefore", newValue);
    }
    
    public Integer getXcTimeBefore() {
        return prefs.getInt("xcTimeBefore", 0);
    }
    
    public void setXcTimeBefore(int newValue) {
        prefs.putInt("xcTimeBefore", newValue);
    }
    
    public Integer getFstdTimeBefore() {
        return prefs.getInt("fstdTimeBefore", 0);
    }
    
    public void setFstdTimeBefore(int newValue) {
        prefs.putInt("fstdTimeBefore", newValue);
    }
    
    public Integer getMelTimeBefore() {
        return prefs.getInt("melTimeBefore", 0);
    }
    
    public void setMelTimeBefore(int newValue) {
        prefs.putInt("melTimeBefore", newValue);
    }
    
    public Integer getSelTimeBefore() {
        return prefs.getInt("selTimeBefore", 0);
    }
    
    public void setSelTimeBefore(int newValue) {
        prefs.putInt("selTimeBefore", newValue);
    }
    
    public Integer getSesTimeBefore() {
        return prefs.getInt("sesTimeBefore", 0);
    }
    
    public void setSesTimeBefore(int newValue) {
        prefs.putInt("sesTimeBefore", newValue);
    }
    
    public Integer getMesTimeBefore() {
        return prefs.getInt("mesTimeBefore", 0);
    }
    
    public void setMesTimeBefore(int newValue) {
        prefs.putInt("mesTimeBefore", newValue);
    }
    
    public Integer getMultiPilotTimeBefore() {
        return prefs.getInt("multiPilotTimeBefore", 0);
    }
    
    public void setMultiPilotTimeBefore(int newValue) {
        prefs.putInt("multiPilotTimeBefore", newValue);
    }
    
    public Totals getTotals() {
        return new Totals("Totals Before",
            getSectorsBefore(),
            getToDayBefore(),
            getToNightBefore(),
            getLdgDayBefore(),
            getLdgNightBefore(),
            getIfrTimeBefore(),
            getNightTimeBefore(),
            getXcTimeBefore(),
            getPicTimeBefore(),
            getPicUsTimeBefore(),
            getSicTimeBefore(),
            getDualTimeBefore(),
            getInstructorTimeBefore(),
            getFstdTimeBefore(),
            getMelTimeBefore(),
            getSelTimeBefore(),
            getMesTimeBefore(),
            getSesTimeBefore(),
            getMultiPilotTimeBefore(),
            getTotalTimeBefore());
    }
}
