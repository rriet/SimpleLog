package com.rietcorrea.simplelog.objects;

import com.rietcorrea.simplelog.converters.HoursIntegerConverter;

import javafx.util.StringConverter;

public class PageTotals extends Totals{
	
	public PageTotals() {
		super();
	}
	public PageTotals(PageTotals pageTotals) {
		super(pageTotals);
	}
	
	public PageTotals(Totals pageTotals) {
		super(pageTotals);
	}
	
	public String getMelTimeString() {
        return minToString(this.getMel());
	}
    
    public void setMelTimeString(String a) {
        // required to generate XML output
	}
    
    public String getSelTimeString() {
        return minToString(this.getSel());
	}
    
    public void setSelTimeString(String a) {
        // required to generate XML output
	}
    
    public String getMesTimeString() {
        return minToString(this.getMes());
	}
    
    public void setMesTimeString(String a) {
        // required to generate XML output
	}
    
    public String getSesTimeString() {
        return minToString(this.getSes());
	}
    
    public void setSesTimeString(String a) {
        // required to generate XML output
	}
    
    public String getMultiPilotTimeString() {
        return minToString(this.getMultiPilot());
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
    
    private String minToString(int minutes) {
        StringConverter<Number> converter = new HoursIntegerConverter();
        if (converter.toString(minutes).equals("0:00")) {
        	return "";
		}
        return converter.toString(minutes);
    }
}
