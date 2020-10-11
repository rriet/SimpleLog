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
package com.rietcorrea.simplelog.database;

import com.rietcorrea.simplelog.objects.Aircraft;
import com.rietcorrea.simplelog.objects.Airport;
import com.rietcorrea.simplelog.objects.Crew;
import com.rietcorrea.simplelog.objects.Flight;
import com.rietcorrea.simplelog.objects.SearchCriteria;
import com.rietcorrea.simplelog.objects.Totals;

import java.util.List;

/**
 *
 * @author riet
 */
public interface FlightDAO {

    public List<Flight> getAllFlights();

    public List<Flight> getFlightsByDate(Long startDate, Long endDate);

    public List<Flight> searchFlights(Long startDate, Long endDate, String searchString);
    
    public List<Flight> searchFlightsWhere(Long startDate, Long endDate, List<SearchCriteria> criteriaList, boolean andJoin);
    
    public Totals searchTotalsWhere(Long startDate, Long endDate, List<SearchCriteria> criteriaList, boolean andJoin);

    public int getTimeSum(Long startDate, Long endDate, String columnName);

    public Flight getFlight(int flightId);
    
    public Flight getLastFlight();
    
    public boolean getAirportInUse(Airport airport);
    
    public boolean getAirportInUse(List<Airport> airportList);
    
    public boolean getCrewInUse(Crew crew);
    
    public boolean getCrewInUse(List<Crew> crewList);
    
    public boolean getAircraftInUse(Aircraft aircraft);
    
    public boolean getAircraftInUse(List<Aircraft> aircraftList);
    
    public List<Totals> getTotals(Long startDate, Long endDate);
    
    public List<Totals> getTotalsByTail(Long startDate, Long endDate);
    
    public List<Totals> getTotalsByType(Long startDate, Long endDate);
    
    public List<Totals> getTotalsByTypeGroup(Long startDate, Long endDate);
    
    public List<Totals> getTotalsByEngineType(Long startDate, Long endDate);
    
    public List<Totals> getTotalsByEngineNumber(Long startDate, Long endDate);
    
    public List<Totals> getTotalsByMultiPilot(Long startDate, Long endDate);
    
    public List<Totals> getTotalsByEfis(Long startDate, Long endDate);
    
    public List<Totals> getTotalsByDepartureAirport(Long startDate, Long endDate);
    
    public List<Totals> getTotalsByArrivalAirport(Long startDate, Long endDate);
    
    public List<Totals> getTotalsByPicName(Long startDate, Long endDate);
    
    public List<Totals> getTotalsBySicName(Long startDate, Long endDate);

    public void insertFlight(Flight flight);

    public void updateFlight(Flight flight);

    public void deleteFlight(Flight flight);
}
