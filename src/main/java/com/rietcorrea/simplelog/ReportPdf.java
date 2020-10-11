package com.rietcorrea.simplelog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
 
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import com.rietcorrea.simplelog.objects.Flight;
import com.rietcorrea.simplelog.objects.Page;
import com.rietcorrea.simplelog.objects.PageTotals;
import com.rietcorrea.simplelog.objects.Report;
import com.rietcorrea.simplelog.objects.Totals;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class ReportPdf extends Task<Object>{

	Report report = new Report();
	ObservableList<Flight> flightList;
    PageTotals pastTotals;
    String selectedFile;
    int linesPerPage;
    String reportName;
	
	ReportPdf(ObservableList<Flight> flightList, Totals pastTotals, String selectedFile, int linesPerPage, String reportName) {
		this.flightList = FXCollections.observableArrayList(flightList);
        this.pastTotals = new PageTotals(pastTotals);
        this.selectedFile = selectedFile;
        this.linesPerPage = linesPerPage;
        this.reportName = reportName;
    }

	@Override
    protected Object call() throws Exception {
		
		// reverse the order so the flights print cronologically
    	Collections.reverse(flightList);

    	// Total pages is the round UP of the number of flights divided by flights per page
    	int pages = (int) Math.ceil(flightList.size() / (double) (linesPerPage + 1));
    	
    	for (int p = 0; p <= pages - 1; p++) {
			
    		// creates new page
    		Page page = new Page();
    		
    		page.setPageNumber(p + 1);
			
    		// set the initial page totals to the previous value
			page.setTotalsBefore(pastTotals);
			
			// create a new variable to hold the page totals
			PageTotals pageTotals = new PageTotals();
			
			// For each line of the page
			for (int l = 0; l <= linesPerPage; l++) {
				Flight thisFlight;
				try {
				// get flight
					thisFlight = flightList.get(p * (linesPerPage + 1)  + l);
					
					updateProgress( (p * (linesPerPage + 1)  + l) , flightList.size());
					
					// Add times/landings to the page totals
					pageTotals.addFlight(thisFlight);
				}catch (Exception e) {
					thisFlight = new Flight();
				}
				// Add flight to the line
				page.addFlight(thisFlight);
			}
			// set the totals of the page
			page.setTotalsPage(pageTotals);
			
			// add the page totals to the previus totals
			pastTotals.addTotal(pageTotals);
			
			// Set page totals after
			page.setTotalsAfter(pastTotals);
			
			// add page to the report
			report.addPage(page);
		}
    	
		try {
			
			//Create JAXB Context
	        JAXBContext jaxbContext = JAXBContext.newInstance(Report.class);
	        
	        //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
            //Print XML String to Console
            StringWriter sw = new StringWriter();
            
            //Write XML to StringWriter
            jaxbMarshaller.marshal(report, sw);
             
            //Verify XML Content
            String xmlContent = sw.toString();
            
	        File xsltfile = new File("reports/" + reportName + ".xsl");
	        
	        // configure fopFactory as desired
	        final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
	    
	        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
	        // configure foUserAgent as desired
	    
	        // Setup output
	        OutputStream outputStream = null;
	        try {
	        	outputStream = new FileOutputStream(selectedFile);
		        outputStream = new java.io.BufferedOutputStream(outputStream);
		        
                  // Construct fop with desired output format
                Fop fop;
            
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, outputStream);
            
                // Setup XSLT
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer(new StreamSource(xsltfile));
                
                // Setup input for XSLT transformation
                Source src = new StreamSource(new StringReader(xmlContent));
                
                // Resulting SAX events (the generated FO) must be piped through to FOP
                Result res = new SAXResult(fop.getDefaultHandler());
                
                // Start XSLT transformation and FOP processing
                transformer.transform(src, res);
	        } catch (Exception e) {
	            LogException.getMessage(e);
	        } finally {
	        	if (outputStream != null) {
	        		outputStream.close();
	        	}
	        }
	    }catch(Exception exp){
	        exp.printStackTrace();
	    }
		return false;
	}
}
