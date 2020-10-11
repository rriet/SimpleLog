package com.rietcorrea.simplelog.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Page {
    
	ObservableList<ReportLine> reportLine = FXCollections.observableArrayList();
    PageTotals totalsBefore;
    PageTotals totalsPage;
    PageTotals totalsAfter;
    Integer pageNumber;
    
    public Page() {
        this.reportLine.clear();
        this.totalsBefore = new PageTotals();
        this.totalsPage = new PageTotals();
        this.totalsAfter = new PageTotals();
        this.pageNumber = 0;
    }

    public void setReportLine(ObservableList<ReportLine> reportLine) {
		this.reportLine = reportLine;
	}
    public ObservableList<ReportLine> getReportLine() {
		return reportLine;
	}
    
    public void setTotalsBefore(PageTotals totalsBefore) {
		this.totalsBefore = new PageTotals(totalsBefore);
	}
    
    public PageTotals getTotalsBefore() {
		return totalsBefore;
	}
    
    public void setTotalsPage(PageTotals totalsPage) {
		this.totalsPage = new PageTotals(totalsPage);
	}
    
    public PageTotals getTotalsPage() {
		return totalsPage;
	}
    
    public void setTotalsAfter(PageTotals totalsAfter) {
		this.totalsAfter = new PageTotals(totalsAfter);
	}
    
    public PageTotals getTotalsAfter() {
		return totalsAfter;
	}
    
    public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
    
    public Integer getPageNumber() {
		return pageNumber;
	}
    
    public void addFlight(Flight flight) {
		this.reportLine.add(new ReportLine(flight));
	}
}
