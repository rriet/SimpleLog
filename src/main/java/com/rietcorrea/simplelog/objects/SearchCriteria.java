package com.rietcorrea.simplelog.objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SearchCriteria {
	private StringProperty description = new SimpleStringProperty();
	private StringProperty command = new SimpleStringProperty();
	private StringProperty searchString = new SimpleStringProperty();
	
    public SearchCriteria() {
		description.set("");
		command.set("");
		searchString.set("");
	}
    
    public void setDescription(String description) {
		this.description.set(description);
	}
    
    public String getDescription() {
		return description.get();
	}
    
    public StringProperty descriptionProperty() {
		return description;
	}
    
    public void setCommand(String command) {
		this.command.set(command);
	}
    
    public String getCommand() {
		return command.get();
	}
    
    public StringProperty commandProperty() {
		return description;
	}
    
    public void setSearchString(String searchString) {
		this.searchString.set(searchString);
	}
    
    public String getSearchString() {
		return searchString.get();
	}
    
    public StringProperty SearchStringProperty() {
		return searchString;
	}
}
