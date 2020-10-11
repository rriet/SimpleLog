/*
 * Copyright (C) 2018 Ricardo Riet Correa - rietcorrea.com
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
package com.rietcorrea.simplelog.database;

import com.rietcorrea.simplelog.LogException;
import com.rietcorrea.simplelog.objects.Aircraft;
import com.rietcorrea.simplelog.objects.Model;
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
public class AircraftDaoImpl implements AircraftDAO {

    String allAircraftFields = "SELECT aircraft.aircraft_id, aircraft.registration,"
            + "aircraft.aircraft_mtow, aircraft.simulator, model.model_id, model.model_name, model.model_group,"
            + "model.engine_type, model.mtow, model.multi_engine,"
            + "model.multi_pilot, model.efis, model.seaplane FROM aircraft "
            + "INNER JOIN model ON aircraft.model_id = model.model_id ";

    @Override
    public List<Aircraft> getAllAircrafts() {
        String queryString = allAircraftFields + " ORDER BY aircraft.registration COLLATE NOCASE ASC;";
        try (Connection connection = SqliteConnection.getConnection();
        		PreparedStatement stm = connection.prepareStatement(queryString)){
        	
        	return aircraftsQuery(stm);
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean getModelInUse(Model model) {
        String query = "SELECT count(*) FROM aircraft WHERE model_id = '" + model.getModelId().toString() + "';";
        
        try (Connection connection = SqliteConnection.getConnection();
        		PreparedStatement stm = connection.prepareStatement(query);
        		ResultSet rs = stm.executeQuery();){
            
            // Only expecting a single result
            if (rs.next()) {
            	return rs.getBoolean(1); // "found" column
            }
            
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return false;
    }
    
    @Override
    public boolean getModelInUse(List<Model> models) { 
    	for (Model model : models) {
    		if (getModelInUse(model)) {
    			return true;
    		}
		}
    	return false;
    }

    @Override
    public List<Aircraft> searchAircrafts(String searchString) {
        List<Aircraft> aircrafts = new ArrayList<>();
        
        String query = allAircraftFields + "WHERE aircraft.registration like ? "
                + "OR model.model_name like ? OR model.model_group like ? ORDER BY aircraft.registration COLLATE NOCASE ASC;";
        try (Connection connection = SqliteConnection.getConnection();
        		PreparedStatement stm = connection.prepareStatement(query)){
                        
        	stm.setString(1, "%" + searchString + "%");
            stm.setString(2, "%" + searchString + "%");
            stm.setString(3, "%" + searchString + "%");
            
            aircrafts = aircraftsQuery(stm);
            
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return aircrafts;
    }

    @Override
    public Aircraft getAircraft(int aircraftId) {

        String query = allAircraftFields + " WHERE aircraft_id = ?";
        try (Connection connection = SqliteConnection.getConnection();
        		PreparedStatement stm = connection.prepareStatement(query)){
        	
        	stm.setInt(1, aircraftId);
        	return extractAircraft(stm);
            
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return null;
    }

    @Override
    public Aircraft getAircraft(String registration) {

        String query = allAircraftFields + " WHERE aircraft.registration = ?";
        try (Connection connection = SqliteConnection.getConnection();
                PreparedStatement stm = connection.prepareStatement(query)) {
            
            stm.setString(1, registration);
            
            return extractAircraft(stm);
            
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return null;
    }
    
    private Aircraft extractAircraft(PreparedStatement stm) {
    	try (ResultSet rs = stm.executeQuery();) {
            if (!rs.isClosed()) {
                return loadAircraft(rs);
            }
        } catch (Exception e) {
			LogException.getMessage(e);
		}
    	return null;
	}

    @Override
    public void insertAircraft(Aircraft aircraft) {
        String query = "INSERT INTO aircraft (model_id,registration,aircraft_mtow,simulator) VALUES (?,?,?,?)";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setInt(1, aircraft.getAircraftModel().getModelId());
            stm.setString(2, aircraft.getRegistration());
            stm.setInt(3, aircraft.getAircraftMtow());
            stm.setBoolean(4, aircraft.getSimulator());

            stm.execute();
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
    }

    @Override
    public void updateAircraft(Aircraft aircraft) {
        String query = "UPDATE aircraft set model_id = ?,registration = ? ,"
                + "aircraft_mtow = ? ,simulator = ? WHERE aircraft_id = ?";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setInt(1, aircraft.getAircraftModel().getModelId());
            stm.setString(2, aircraft.getRegistration());
            stm.setInt(3, aircraft.getAircraftMtow());
            stm.setBoolean(4, aircraft.getSimulator());
            stm.setInt(5, aircraft.getAircraftId());

            stm.execute();
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
    }

    @Override
    public void deleteAircraft(Aircraft aircraft) {
        String query = "DELETE FROM aircraft WHERE aircraft_id = ?";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setInt(1, aircraft.getAircraftId());
            
            stm.execute();
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
    }

    private List<Aircraft> aircraftsQuery(PreparedStatement stm) {
        List<Aircraft> aircrafts = new ArrayList<>();

        try (ResultSet rs = stm.executeQuery()){
            while (rs.next()) {
                Aircraft nextAircraft = loadAircraft(rs);
                aircrafts.add(nextAircraft);
            }
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return aircrafts;
    }

    private Aircraft loadAircraft(ResultSet rs) {
        Aircraft nextAircraft = null;

        try {
        	
        	ModelDAO modelDAO = new ModelDaoImpl();
        	
            Model nextModel = modelDAO.loadModel(rs);
            		
            nextAircraft = new Aircraft()	.setAircraftId(rs.getInt("aircraft_id"))
								        	.setRegistration(rs.getString("registration"))
								        	.setAircraftMtow(rs.getInt("aircraft_mtow"))
								        	.setSimulator(rs.getBoolean("simulator"))
								        	.setAircraftModel(nextModel);
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return nextAircraft;
    }

    @Override
    public List<Aircraft> search(String searchString) {
        return this.searchAircrafts(searchString);
    }

    @Override
    public boolean aircraftExist(Aircraft aircraft) {
        return getAircraft(aircraft.getRegistration()) != null;
    }
    
    @Override
    public boolean aircraftExist(String registration) {
        return getAircraft(registration) != null;
    }
}
