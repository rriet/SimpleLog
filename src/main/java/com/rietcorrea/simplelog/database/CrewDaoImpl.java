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
import com.rietcorrea.simplelog.objects.Crew;
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
public class CrewDaoImpl implements CrewDAO{

    String selectString = "SELECT crew_id,crew_name, email, phone, comments FROM crew ";
    
    @Override
    public List<Crew> getAllCrew() {
        List<Crew> crew = new ArrayList<>();
        String searchString = selectString + " ORDER BY crew_name COLLATE NOCASE ASC";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(searchString)){
            
            crew = crewsQuery(stm);
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return crew;
    }

    @Override
    public List<Crew> findCrew(String searchString) {
        List<Crew> crew = new ArrayList<>();
        String query = selectString + " WHERE crew_name like ? OR email like ? OR phone like ? OR comments like ? ORDER BY crew_name COLLATE NOCASE ASC";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            stm.setString(1, "%" + searchString.trim() + "%");
            stm.setString(2, "%" + searchString.trim() + "%");
            stm.setString(3, "%" + searchString.trim() + "%");
            stm.setString(4, "%" + searchString.trim() + "%");
            crew = crewsQuery(stm);
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return crew;
    }

    @Override
    public Crew getCrew(int crewId) {
        String query = selectString + " WHERE crew_id = ?";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setInt(1, crewId);
            ResultSet rs = stm.executeQuery();
            return loadCrew(rs);

        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return null;
    }
    
    @Override
    public Crew getCrew(String crewName) {
        String query = selectString + " WHERE crew_name = ? COLLATE NOCASE";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setString(1, crewName.trim());
            
            return crewQuery(stm);
            
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return null;
    }
    
    private Crew crewQuery(PreparedStatement stm) {
		try (ResultSet rs = stm.executeQuery()){
			if (!rs.isClosed()) {
	            return loadCrew(rs);
	        }
		} catch (SQLException e) {
			LogException.getMessage(e);
		}
        
        return null;
	}

    @Override
    public void insertCrew(Crew crew) {
        String query = "INSERT INTO crew (crew_name,email,phone,comments) VALUES (?,?,?,?)";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setString(1, crew.getCrewName());
            stm.setString(2, crew.getEmail());
            stm.setString(3, crew.getPhone());
            stm.setString(4, crew.getComments());
            
            stm.execute();
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
    }

    @Override
    public void updateCrew(Crew crew) {
        String query = "UPDATE crew SET crew_name = ? ,email = ?,phone = ?,comments = ? "
                + " WHERE crew_id = ?";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setString(1, crew.getCrewName());
            stm.setString(2, crew.getEmail());
            stm.setString(3, crew.getPhone());
            stm.setString(4, crew.getComments());
            stm.setInt(5, crew.getCrewId());

            stm.execute();
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
    }

    @Override
    public void deleteCrew(Crew crew) {
        String query = "DELETE FROM crew WHERE crew_id = ?";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query)){
            
            stm.setInt(1, crew.getCrewId());
            stm.execute();
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
    }

    private List<Crew> crewsQuery(PreparedStatement stm) {
        List<Crew> crew = new ArrayList<>();

        try (ResultSet rs = stm.executeQuery()){
            
            while (rs.next()) {
                crew.add(loadCrew(rs));
            }
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return crew;
    }

    private Crew loadCrew(ResultSet rs) {
        try {
            return new Crew()	.setCrewId(rs.getInt("crew_id"))
					    		.setCrewName(rs.getString("crew_name"))
					    		.setEmail(rs.getString("email"))
					    		.setPhone(rs.getString("phone"))
					    		.setComments(rs.getString("comments"));
            
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return null;
    }

    @Override
    public List<Crew> search(String searchString) {
        return this.findCrew(searchString.trim());
    }

    @Override
    public boolean crewExist(Crew crew) {
        return getCrew(crew.getCrewName()) != null;
    }
    
    @Override
    public boolean crewExist(String crewName) {
        return getCrew(crewName.trim()) != null;
    }
}
