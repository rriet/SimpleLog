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

import com.opencsv.CSVWriter;
import com.rietcorrea.constants.StrEng;
import com.rietcorrea.simplelog.database.AircraftDAO;
import com.rietcorrea.simplelog.database.AircraftDaoImpl;
import com.rietcorrea.simplelog.database.AirportDAO;
import com.rietcorrea.simplelog.database.AirportDaoImpl;
import com.rietcorrea.simplelog.database.CrewDAO;
import com.rietcorrea.simplelog.database.CrewDaoImpl;
import com.rietcorrea.simplelog.database.FlightDAO;
import com.rietcorrea.simplelog.database.FlightDaoImpl;
import com.rietcorrea.simplelog.database.ModelDAO;
import com.rietcorrea.simplelog.database.ModelDaoImpl;
import com.rietcorrea.simplelog.objects.Aircraft;
import com.rietcorrea.simplelog.objects.Airport;
import com.rietcorrea.simplelog.objects.Crew;
import com.rietcorrea.simplelog.objects.Flight;
import com.rietcorrea.simplelog.objects.Model;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author riet
 */
public class Export { // NO_UCD (use default)

	private Export() {
	    throw new IllegalStateException("Utility class");
	}
	
    public static void export(String group, String exportPath) { // NO_UCD (use default)
        try (Writer writer = Files.newBufferedWriter(Paths.get(exportPath));
            
        		CSVWriter csvWriter = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR, '"', '\"',
                    CSVWriter.DEFAULT_LINE_END);){
            

            FlightDAO flightDao;
            List<Flight> flights;
            
            switch (group) {
                case StrEng.SIMPLELOG:
                    flightDao = new FlightDaoImpl();
                    flights = flightDao.getAllFlights();

                    String[] simpleLogHeaderRecord = StrEng.SIMPLELOG_EXPORT_HEATHER.split(",");
                    
                    csvWriter.writeNext(simpleLogHeaderRecord);

                    for (Flight flight : flights) {
                    	csvWriter.writeNext(flight.getFlightSimpleLogArray());
					}
                    break;
                    
                case StrEng.FLIGHTS:
                    flightDao = new FlightDaoImpl();
                    flights = flightDao.getAllFlights();

                    String[] flightHeaderRecord = StrEng.FLIGHT_EXPORT_HEATHER.split(",");
                    csvWriter.writeNext(flightHeaderRecord);
                    
                    for (Flight flight : flights) {
                    	csvWriter.writeNext(flight.getFlightArray());
					}
                    
                    break;
                case StrEng.AIRCRAFTS:
                    AircraftDAO aircraftDao = new AircraftDaoImpl();
                    List<Aircraft> aircrafts = aircraftDao.getAllAircrafts();

                    String[] aircraftHeaderRecord = StrEng.AIRCRAFT_EXPORT_HEATHER.split(",");
                    csvWriter.writeNext(aircraftHeaderRecord);

                    aircrafts.forEach( aircraft -> csvWriter.writeNext(aircraft.getAircraftArray()));

                    break;
                case StrEng.MODELS:
                    ModelDAO modelDao = new ModelDaoImpl();
                    List<Model> models = modelDao.getAllModels();

                    String[] modelHeaderRecord = StrEng.MODEL_EXPORT_HEATHER.split(",");
                    csvWriter.writeNext(modelHeaderRecord);

                    for (Model model : models) {
                    	csvWriter.writeNext(model.getModelArray());
					}
                    
                    break;
                case StrEng.AIRPORTS:
                    AirportDAO airportDao = new AirportDaoImpl();
                    List<Airport> airports = airportDao.getAllAirports();

                    String[] airportHeaderRecord = StrEng.AIRPORT_EXPORT_HEATHER.split(",");
                    csvWriter.writeNext(airportHeaderRecord);

                    for (Airport airport : airports) {
                    	csvWriter.writeNext(airport.getAirportArray());
					}

                    break;
                case StrEng.CREW:
                    CrewDAO crewDao = new CrewDaoImpl();
                    List<Crew> crews = crewDao.getAllCrew();

                    String[] headerRecord = StrEng.CREW_EXPORT_HEATHER.split(",");
                    csvWriter.writeNext(headerRecord);
                    
                    for (Crew crew : crews) {
                    	csvWriter.writeNext(crew.getCrewArray());
					}

                    break;
                default:
                	throw new IllegalArgumentException("Could not match the export combo selection text with the available options");
            }
        } catch (IOException | IllegalArgumentException e) {
            LogException.getMessage(e);
        }
    }

}
