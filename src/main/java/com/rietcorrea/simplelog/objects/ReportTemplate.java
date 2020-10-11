package com.rietcorrea.simplelog.objects;

public class ReportTemplate {
	int numberOfLines;
	String fileNameString;
	String descriptionString;
	
	public ReportTemplate(int numberOfLines, String fileNameString, String descriptionString) {
		this.numberOfLines = numberOfLines;
		this.fileNameString = fileNameString;
		this.descriptionString = descriptionString;
	}
	
	@Override
	public String toString() {
		return this.descriptionString;
	}
	
	public void setNumberOfLines(int numberOfLines) {
		this.numberOfLines = numberOfLines;
	}
	
	public int getNumberOfLines() {
		return numberOfLines - 1;
	}
	
	public void setFileNameString(String fileNameString) {
		this.fileNameString = fileNameString;
	}
	
	public String getFileNameString() {
		return fileNameString;
	}
	
	public void setDescriptionString(String descriptionString) {
		this.descriptionString = descriptionString;
	}
	
	public String getDescriptionString() {
		return descriptionString;
	}
}
