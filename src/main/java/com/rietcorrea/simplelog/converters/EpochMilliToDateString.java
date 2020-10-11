package com.rietcorrea.simplelog.converters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javafx.util.converter.LongStringConverter;

public class EpochMilliToDateString extends LongStringConverter{

    
    @Override
    public Long fromString(String inputTime) {
        try {
        	// Start setting date and time....
			SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			dateFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
			
			Date newDepartureDate = dateFormater.parse(inputTime);
			return newDepartureDate.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString(Long inputMilliseconds) {
    	Date date = new Date(inputMilliseconds);
    	SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		dateFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
    	return dateFormater.format(date);
    }

}
