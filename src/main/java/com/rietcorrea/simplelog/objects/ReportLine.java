package com.rietcorrea.simplelog.objects;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.rietcorrea.simplelog.converters.HoursIntegerConverter;
import javafx.util.StringConverter;

public class ReportLine extends Flight{
	
	public ReportLine(Flight flight) {
		super(flight);
	}

	public String getDepartureDateString() {
		if (!nullFlight()) {
			long epoch = this.getDepartureDate();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	
	        return dateFormat.format(new java.util.Date(epoch * 1000));
		}else {
			return "";
		}
    }

    public void setDepartureDateString(String a) {
        // required to generate XML output
    }
    
    public String getDepartureTimeString() {
    	if (!nullFlight()) {
	    	long epoch = this.getDepartureDate();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
	        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	
	        return dateFormat.format(new java.util.Date(epoch * 1000));
    	}else {
			return "";
		}
	}
    
    private boolean nullFlight() {
		return (this.getCrewPic().getCrewName().equals("") && this.getTotalTime().equals(0) && this.getFstdTime().equals(0));
	}

	public void setDepartureTimeString(String a) {
        // required to generate XML output
	}
    
    public String getArrivalTimeString() {
    	if (!nullFlight()) {
	    	long epoch = this.getArrivalDate();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
	        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	
	        return dateFormat.format(new java.util.Date(epoch * 1000));
    	}else {
			return "";
		}
	}
    
    public void setArrivalTimeString(String a) {
        // required to generate XML output
	}
    
    public String getMelTimeString() {
    	// If Multi-Engine but NOT multi pilot and not seaplane
    	if (this.getAircraft().getAircraftModel().getMultiEngine().booleanValue() 
    			&& !this.getAircraft().getAircraftModel().getSeaplane().booleanValue()
    			&& !this.getAircraft().getAircraftModel().getMultiPilot().booleanValue()) {
    		return minToString(this.getTotalTime());
		} else {
			return minToString(0);
		}
	}

    public void setMelTimeString(String a) {
        // required to generate XML output
	}
    
    public String getSelTimeString() {
    	// If Multi-Engine but NOT multi pilot and not seaplane
    	if (!this.getAircraft().getAircraftModel().getMultiEngine().booleanValue() 
    			&& !this.getAircraft().getAircraftModel().getSeaplane().booleanValue()
    			&& !this.getAircraft().getAircraftModel().getMultiPilot().booleanValue()) {
    		return minToString(this.getTotalTime());
		} else {
			return minToString(0);
		}
	}

    public void setSelTimeString(String a) {
        // required to generate XML output
	}
    
    public String getMesTimeString() {
    	// If Multi-Engine but NOT multi pilot and not seaplane
    	if (this.getAircraft().getAircraftModel().getMultiEngine().booleanValue() 
    			&& this.getAircraft().getAircraftModel().getSeaplane().booleanValue()
    			&& !this.getAircraft().getAircraftModel().getMultiPilot().booleanValue()) {
    		return minToString(this.getTotalTime());
		} else {
			return minToString(0);
		}
	}

    public void setMesTimeString(String a) {
        // required to generate XML output
	}
    
    public String getSesTimeString() {
    	// If Multi-Engine but NOT multi pilot and not seaplane
    	if (!this.getAircraft().getAircraftModel().getMultiEngine().booleanValue() 
    			&& this.getAircraft().getAircraftModel().getSeaplane().booleanValue()
    			&& !this.getAircraft().getAircraftModel().getMultiPilot().booleanValue()) {
    		return minToString(this.getTotalTime());
		} else {
			return minToString(0);
		}
	}
    
    public void setSesTimeString(String a) {
        // required to generate XML output
	}
    
    public String getMultiPilotTimeString() {
    	// If Multi-Engine but NOT multi pilot and not seaplane
    	if (this.getAircraft().getAircraftModel().getMultiPilot().booleanValue()) {
    		return minToString(this.getTotalTime());
		} else {
			return minToString(0);
		}
	}

    public void setMultiPilotTimeString(String a) {
        // required to generate XML output
	}
    
    public String getTotalTimeString() {
        return minToString(this.getTotalTime());
	}
    
    public void setTotalTimeString(String a) {
        // required to generate XML output
	}
    
    public String getNightTimeString() {
        return minToString(this.getNightTime());
	}
    
    public void setNightTimeString(String a) {
        // required to generate XML output
	}
    
    public String getIfrTimeString() {
        return minToString(this.getIfrTime());
	}
    
    public void setIfrTimeString(String a) {
        // required to generate XML output
	}
    
    public String getPicTimeString() {
        return minToString(this.getPicTime());
	}
    
    public void setPicTimeString(String a) {
        // required to generate XML output
	}
    
    public String getPicusTimeString() {
        return minToString(this.getPicUsTime());
	}
    
    public void setPicusTimeString(String a) {
        // required to generate XML output
	}
    
    public String getSicTimeString() {
        return minToString(this.getSicTime());
	}
    
    public void setSicTimeString(String a) {
        // required to generate XML output
	}
    
    public String getDualTimeString() {
        return minToString(this.getDualTime());
	}
    
    public void setDualTimeString(String a) {
        // required to generate XML output
	}
    
    public String getInstructorTimeString() {
        return minToString(this.getInstructorTime());
	}
    
    public void setInstructorTimeString(String a) {
        // required to generate XML output
	}
    
    public String getFstdTimeString() {
        return minToString(this.getFstdTime());
	}
    
    public void setFstdTimeString(String a) {
        // required to generate XML output
	}
    
    public String getTakeOffDayString() {
    	if (this.getTakeOffDay() == 0) {
        	return "";
		}
        return this.getTakeOffDay().toString();
	}
    
    public void setTakeOffDayString(String a) {
        // required to generate XML output
	}
    
    public String getTakeOffNightString() {
    	if (this.getTakeOffNight() == 0) {
        	return "";
		}
        return this.getTakeOffNight().toString();
	}
    
    public void setTakeOffNightString(String a) {
        // required to generate XML output
	}
    
    public String getLandingDayString() {
    	if (this.getLandingDay() == 0) {
        	return "";
		}
        return this.getLandingDay().toString();
	}
    
    public void setLandingDayString(String a) {
        // required to generate XML output
	}
    
    public String getLandingNightString() {
    	if (this.getLandingNight() == 0) {
        	return "";
		}
        return this.getLandingNight().toString();
	}
    
    public void setLandingNightString(String a) {
        // required to generate XML output
	}
    
    private String minToString(int minutes) {
        StringConverter<Number> converter = new HoursIntegerConverter();
        if (converter.toString(minutes).equals("0:00")) {
        	return "";
		}
        return converter.toString(minutes);
    }
    
}
