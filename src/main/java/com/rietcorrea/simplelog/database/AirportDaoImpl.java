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

import com.rietcorrea.simplelog.LogException;
import com.rietcorrea.simplelog.objects.Airport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riet
 */
public class AirportDaoImpl implements AirportDAO {

    private static final String AIRPORT_QUERY_STRING = "SELECT airport_id, icao,iata,airport_name,airport_city,"
            + "airport_country,latitude,longitude";

    @Override
    public List<Airport> getAllAirports() {
        List<Airport> airports = new ArrayList<>();
        try {
            Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(AIRPORT_QUERY_STRING + " FROM airport ORDER BY icao");
            airports = airportsQuery(stm);
            stm.close();
            connection.close();
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return airports;
    }

    @Override
    public List<Airport> searchIcao(String icao) {
        List<Airport> airports = new ArrayList<>();
        String query = AIRPORT_QUERY_STRING + " FROM airport WHERE icao like ? ORDER BY icao";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setString(1, "%" + icao + "%");
            airports = airportsQuery(stm);
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return airports;
    }

    @Override
    public List<Airport> searchIata(String iata) {
        List<Airport> airports = new ArrayList<>();
        String query = AIRPORT_QUERY_STRING + " FROM airport WHERE iata like ? ORDER BY iata";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setString(1, "%" + iata + "%");
            airports = airportsQuery(stm);
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return airports;
    }

    @Override
    public List<Airport> searchAll(String inputString) {
        List<Airport> airports = new ArrayList<>();
        String query = AIRPORT_QUERY_STRING + " FROM airport WHERE icao like ? OR iata like ? or "
                + "airport_name like ? or airport_city like ? or airport_country like ? ORDER BY icao";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setString(1, "%" + inputString + "%");
            stm.setString(2, "%" + inputString + "%");
            stm.setString(3, "%" + inputString + "%");
            stm.setString(4, "%" + inputString + "%");
            stm.setString(5, "%" + inputString + "%");
            airports = airportsQuery(stm);
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return airports;
    }

    @Override
    public Airport getAirport(int airportId) {
        String query = AIRPORT_QUERY_STRING + " FROM airport WHERE airport_id = ?";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setInt(1, airportId);
            return airportQuery(stm);
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return null;
    }

    @Override
    public Airport getIcao(String icao) {
        String query = AIRPORT_QUERY_STRING + " FROM airport WHERE icao = ?";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setString(1, icao);
            return airportQuery(stm);
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return null;
    }

    @Override
    public Airport getIata(String iata) {
        String query = AIRPORT_QUERY_STRING + " FROM airport WHERE iata = ?";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setString(1, iata);
            return airportQuery(stm);
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return null;
    }

    @Override
    public void insertAirport(Airport airport) {
        String query = "INSERT INTO airport (icao,iata,airport_name,airport_city,"
                + "airport_country,latitude,longitude) VALUES (?,?,?,?,?,?,?)";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setString(1, airport.getIcao());
            stm.setString(2, airport.getIata());
            stm.setString(3, airport.getAirportName());
            stm.setString(4, airport.getAirportCity());
            stm.setString(5, airport.getAirportCountry());
            stm.setDouble(6, airport.getLatitude());
            stm.setDouble(7, airport.getLongitude());

            stm.execute();

        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
    }

    @Override
    public void updateAirport(Airport airport) {
        String query = "UPDATE airport set icao = ? ,iata = ?,airport_name = ?,airport_city = ?,"
                + "airport_country = ?,latitude = ?,longitude = ? WHERE airport_id = ?";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setString(1, airport.getIcao());
            stm.setString(2, airport.getIata());
            stm.setString(3, airport.getAirportName());
            stm.setString(4, airport.getAirportCity());
            stm.setString(5, airport.getAirportCountry());
            stm.setDouble(6, airport.getLatitude());
            stm.setDouble(7, airport.getLongitude());
            stm.setInt(8, airport.getAirportId());

            stm.execute();
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
    }

    @Override
    public void deleteAirport(Airport airport) {
        String query = "DELETE FROM airport WHERE airport_id = ?";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setInt(1, airport.getAirportId());
            stm.execute();
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
    }

    @Override
    public void deleteAirportId(Integer airportId) {
        String query = "DELETE FROM airport WHERE airport_id = ?";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setInt(1, airportId);
            stm.execute();
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
    }

    // Auxiliar functions
    // Common function to interate over a prepared statement with multiple results
    private List<Airport> airportsQuery(PreparedStatement statement) {
        List<Airport> airports = new ArrayList<>();

        try (ResultSet rs = statement.executeQuery()){
//        	OutputStream os = null;
//        	os = new FileOutputStream(new File("/Users/riet/Desktop/airport.txt"));
//        	String data = "";
            while (rs.next()) {
            	
                Airport nextAirport = loadAirport(rs);
                airports.add(nextAirport);
//                data += "{icao:\""+nextAirport.getIcao() + 
//                		"\",iata:\""+ nextAirport.getIata() +
//                		"\",name:\""+ nextAirport.getAirportName() +
//                		"\",city:\""+ nextAirport.getAirportCity() +
//                		"\",country:\""+ nextAirport.getAirportCountry() +
//                		"\",latitude:"+ nextAirport.getLatitude() +
//                		",longitude:"+ nextAirport.getLongitude() +"},\n";                		
                
                
            }
            //os.write(data.getBytes(), 0, data.length());
        } catch (Exception ex) {
            LogException.getMessage(ex);
        }
        return airports;
    }

    // Common function to load a prepared statement with single results
    private Airport airportQuery(PreparedStatement stm) {
        try (ResultSet rs = stm.executeQuery()){
            
            if (!rs.isClosed()) {
                return loadAirport(rs);
            }
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return null;
    }

    // Receives a resultSet and loads the values on the airport object
    private Airport loadAirport(ResultSet rs) {
        try {
            return new Airport()
            		.setAirportId(rs.getInt("airport_id"))
            		.setIcao(rs.getString("icao"))
            		.setIata(rs.getString("iata"))
            		.setAirportName(rs.getString("airport_name"))
            		.setAirportCity(rs.getString("airport_city"))
            		.setAirportCountry(rs.getString("airport_country"))
            		.setLatitude(rs.getDouble("latitude"))
            		.setLongitude(rs.getDouble("longitude"));
            		
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return null;
    }

    @Override
    public List<Airport> search(String searchString) {
        return this.searchAll(searchString);
    }

    @Override
    public boolean airportExist(Airport airport) {    			
        return getIcao(airport.getAirportName()) != null;
    }

    @Override
    public boolean airportExist(String icao) {
        return getIcao(icao) != null;
    }
    
    @Override
    public boolean airportIataExist(String iata) {
        return getIata(iata) != null;
    }
}
