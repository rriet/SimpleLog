package com.rietcorrea.simplelog.objects;

import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlRootElement(name = "report")
public class Report {
	ObservableList<Page> pages = FXCollections.observableArrayList();
	
	public Report() {
		this.pages.clear();
	}
	
	public ObservableList<Page> getPages() {
		return pages;
	}
	
	public void setPages(ObservableList<Page> pages) {
		this.pages = pages;
	}
	
	public void addPage(Page page) {
		this.pages.add(page);
	}
}
