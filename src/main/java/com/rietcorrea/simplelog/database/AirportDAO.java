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

import com.rietcorrea.simplelog.objects.Airport;
import java.util.List;

/**
 *
 * @author riet
 */
public interface AirportDAO extends SearchDAO<Airport>{
    
    public List<Airport> getAllAirports();
    
    public List<Airport> searchIcao(String icao);
    
    public List<Airport> searchIata(String icao);
    
    public List<Airport> searchAll(String icao);
    
    public Airport getAirport(int airportId);
    
    public Airport getIcao(String icao);
    
    public Airport getIata(String iata);
    
    public void insertAirport(Airport airport);

    public void updateAirport(Airport airport);

    public void deleteAirport(Airport airport);
    
    public void deleteAirportId(Integer airportId);

    public boolean airportExist(Airport airport);

    public boolean airportExist(String icao);
    
    public boolean airportIataExist(String iata);
}
