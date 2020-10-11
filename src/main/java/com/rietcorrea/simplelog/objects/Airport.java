/*
*    Simple Log - Pilot logbook software
*    Copyright (C) 2018  Ricardo Brito Riet Correa
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.rietcorrea.simplelog.objects;

import java.util.ArrayList;
import java.util.List;

import com.rietcorrea.constants.StrLen;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author riet
 */
public class Airport {
	
    private IntegerProperty airportId = new SimpleIntegerProperty();
    private StringProperty icao = new SimpleStringProperty();
    private StringProperty iata = new SimpleStringProperty();
    private StringProperty airportName = new SimpleStringProperty();
    private StringProperty airportCity = new SimpleStringProperty();
    private StringProperty airportCountry = new SimpleStringProperty();
    private DoubleProperty latitude = new SimpleDoubleProperty();
    private DoubleProperty longitude = new SimpleDoubleProperty();
    
    public Airport(){
    	this.setIcao("")
			.setIata("")
			.setAirportName("")
			.setAirportCity("")
			.setAirportCountry("")
			.setLatitude(0.0)
			.setLongitude(0.0);
    	
    }
    
    
    // Clear every value EXCEPT ICAO code.
    public void resetAirport() {
    	this.setIata("")
			.setAirportName("")
			.setAirportCity("")
			.setAirportCountry("")
			.setLatitude(0.0)
			.setLongitude(0.0);
	}
    
    public Airport(Airport that){
    	this.setAirportId(that.getAirportId())
    		.setIcao(that.getIcao())
    		.setIata(that.getIata())
    		.setAirportName(that.getAirportName())
    		.setAirportCity(that.getAirportCity())
    		.setAirportCountry(that.getAirportCountry())
    		.setLatitude(that.getLatitude())
    		.setLongitude(that.getLongitude());
    }
    
    public String[] getAirportArray() {
        List<String> airportArray = new ArrayList<>();
    
        airportArray.add(this.getIcao());
        // Add departure IATA
        airportArray.add(this.getIata());
        // Add departure Name
        airportArray.add(this.getAirportName());
        // Add departure City
        airportArray.add(this.getAirportCity());
        // Add departure Country
        airportArray.add(this.getAirportCountry());
        // Add departure Latitude
        airportArray.add(this.getLatitude().toString());
        // Add departure Longitude
        airportArray.add(this.getLongitude().toString());
        
        return airportArray.toArray(new String[0]);
    }

    public Integer getAirportId() {
        return airportId.get();
    }

    public Airport setAirportId(Integer airportId) {
        this.airportId.set(airportId);
        return this;
    }
    
    public String getIcao() {
        return icao.get();
    }

    public Airport setIcao(String icao) {
    	// Clean blank spaces
    	icao = icao.replaceAll("\\s+","");
    	
    	// only acepts IATA with 4 chars
        if (icao  != null && icao.length() == StrLen.AIRPORT_ICAO) {
        	this.icao.set(icao);
        } else {
        	this.icao.set("");
        }
        return this;
    }
    
    public StringProperty icaoProperty (){
        return icao;
    }

    public String getIata() {
        return iata.get();
    }

    public Airport setIata(String iata) {
    	// only acepts IATA with 3 chars
        if (iata  != null && iata.trim().length() == StrLen.AIRPORT_IATA) {
        	this.iata.set(iata);
        } else {
        	this.iata.set("");
        }
        return this;
    }
    
    public StringProperty iataProperty (){
        return iata;
    }

    public String getAirportName() {
        return airportName.get().trim();
    }

    public Airport setAirportName(String airportName) {
    	if (airportName  != null) {
    		this.airportName.set(airportName.trim().substring(0, Math.min(airportName.length(), StrLen.AIRPORT_NAME)));
    	}
        return this;
    }
    
    public StringProperty airportNameProperty (){
        return airportName;
    }

    public String getAirportCity() {
        return airportCity.get().trim();
    }

    public Airport setAirportCity(String airportCity) {
    	if (airportCity != null) {
    		this.airportCity.set(airportCity.trim().substring(0, Math.min(airportCity.length(), StrLen.AIRPORT_CITY)));
    	}
        return this;
    }
    
    public StringProperty airportCityProperty (){
        return airportCity;
    }

    public String getAirportCountry() {
        return airportCountry.get().trim();
    }

    public Airport setAirportCountry(String airportCountry) {
    	if (airportCountry != null) {
    		this.airportCountry.set(airportCountry.trim().substring(0, Math.min(airportCountry.length(), StrLen.AIRPORT_COUNTRY)));
    	}
        return this;
    }
    
    public StringProperty airportCountryProperty (){
        return airportCountry;
    }

    public Double getLatitude() {
        return latitude.get();
    }

    public Airport setLatitude(Double latitude) {
        this.latitude.set(latitude);
        return this;
    }
    
    public Airport setLatitudeDoubleString(String latitudeString) {
    	try {
			this.setLatitude(Double.valueOf(latitudeString));
		} catch (NumberFormatException e) {
			this.setLatitude(0.0);
		}
        return this;
    }
    
    public DoubleProperty latitudeProperty (){
        return latitude;
    }
    
    public String getLatitudeString() {
        Double storedLatitude = latitude.get();
        String cardinal = "N ";
        if (storedLatitude < 0){
            storedLatitude = storedLatitude * -1;
            cardinal = "S ";
        }
        
        Integer degrees = storedLatitude.intValue();
        Double minutes = (storedLatitude - degrees) * 60;
        
        return cardinal + String.format("%02d", degrees) + "° "+String.format("%04.1f", minutes);
    }

    public Airport setLatitudeString(String latitudeString) {
        String cardinal = latitudeString.substring(0, 2);
        latitudeString = latitudeString.substring(2);
        
        Double latitudeDouble;
        String[] latitudeSplit = latitudeString.split("° ");
        latitudeDouble = Double.valueOf(latitudeSplit[0]) + 
                (Double.valueOf(latitudeSplit[1])/60);
        
        if(cardinal.equals("S ")){
            latitudeDouble = latitudeDouble * -1;
        }
        this.latitude.set(latitudeDouble);
        return this;
    }

    public Double getLongitude() {
        return longitude.get();
    }

    public Airport setLongitude(Double longitude) {
        this.longitude.set(longitude);
        return this;
    }
    
    public Airport setLongitudeDoubleString(String longitudeString) {
    	try {
			this.setLongitude(Double.valueOf(longitudeString));
		} catch (NumberFormatException e) {
			this.setLongitude(0.0);
		}
        return this;
    }
    
    public DoubleProperty longitudeProperty (){
        return longitude;
    }
    
    public String getLongitudeString() {
        Double storedLongitude = longitude.get();
        String cardinal = "E ";
        if (storedLongitude < 0){
            storedLongitude = storedLongitude * -1;
            cardinal = "W ";
        }
        
        Integer degrees = storedLongitude.intValue();
        Double minutes = (storedLongitude - degrees) * 60;
        
        return cardinal + String.format("%03d", degrees) + "° "+String.format("%04.1f", minutes);
    }

    public Airport setLongitudeString(String longitudeString) {
        String cardinal = longitudeString.substring(0, 2);
        longitudeString = longitudeString.substring(2);
        
        Double longitudeDouble;
        String[] longitudeSplit = longitudeString.split("° ");
        longitudeDouble = Double.valueOf(longitudeSplit[0]) + 
                (Double.valueOf(longitudeSplit[1])/60);
        
        if(cardinal.equals("E ")){
            longitudeDouble = longitudeDouble * -1;
        }
        this.longitude.set(longitudeDouble);
        
        return this;
    }
    
    public String toString(){
        return this.getIcao() + " - " + this.getAirportName() + " - " + this.getAirportCountry();
    }
}
