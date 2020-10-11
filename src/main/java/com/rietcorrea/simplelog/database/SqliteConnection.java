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

import com.rietcorrea.constants.StrLen;
import com.rietcorrea.simplelog.LogException;
import com.rietcorrea.simplelog.UserPreferences;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riet
 */
public class SqliteConnection {
	
	private SqliteConnection () {
		// do nothing
	}
    
    public static Connection getConnection() {
        
        // Get user preferences for the location of the Database
        UserPreferences pref = new UserPreferences();
        String dbPath = pref.getDbPath();
        
        Connection connection;

        
        // check if the file exists
        File f = new File(dbPath);
        boolean dabtaseFileExist = true;
        if (!f.exists()) {
        	// If file doesn't exist, create new databese tables
        	dabtaseFileExist = false;
        	if (!new File(f.getParent()).exists()) {
        		// if directory doesn't exist, change to app directory
        		dbPath = System.getProperty("user.dir") + "/SimpleLog.db";
        		// update user preferences to this directory
        		pref.setDbPath(dbPath);
        		f = new File(dbPath);
        		
            	// If file doesn't exist, create new databese tables
        		dabtaseFileExist = f.exists();
        	}
        }
        
        try {
            Class.forName("org.sqlite.JDBC");

            // create a connection to the database
            connection = DriverManager.getConnection("jdbc:sqlite:"+dbPath);

        } catch (ClassNotFoundException | SQLException ex) {
            LogException.getMessage(ex);
            return null;
        }

        // if there was no database before we configure initial tables for the new database
        if (!dabtaseFileExist) {
            createNewTables(connection);
        }
        
        return connection;
    }
    
    public static void createNewTables(Connection conn) {

        // SQL statement for creating a new table
        List<String> sqlList = new ArrayList<>();
        sqlList.add("CREATE TABLE model\n"
                + "(\n"
                + "    model_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    model_name VARCHAR("+StrLen.MODEL_NAME_MAX+"),\n"
                + "    model_group VARCHAR("+StrLen.MODEL_GROUP+"),\n"
                + "    engine_type VARCHAR("+StrLen.MEDEL_ENGINE_TYPE+"),\n"
                + "    mtow INT,\n"
                + "    multi_engine BIT,\n"
                + "    multi_pilot BIT,\n"
                + "    efis BIT,\n"
                + "    seaplane BIT\n"
                + ");\n");

        sqlList.add("CREATE TABLE aircraft\n"
                + "(\n"
                + "    aircraft_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    model_id INT,\n"
                + "    registration VARCHAR("+StrLen.AIRCRAFT_REGISTRATION_MAX+"),\n"
                + "    aircraft_mtow INT,\n"
                + "    simulator BIT,\n"
                + "    FOREIGN KEY (model_id) REFERENCES model (model_id)\n"
                + ");\n");

        sqlList.add("CREATE TABLE airport\n"
                + "(\n"
                + "    airport_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    icao VARCHAR("+StrLen.AIRPORT_ICAO+"),\n"
                + "    iata VARCHAR("+StrLen.AIRPORT_IATA+"),\n"
                + "    airport_name VARCHAR("+StrLen.AIRPORT_NAME+"),\n"
                + "    airport_city VARCHAR("+StrLen.AIRPORT_CITY+"),\n"
                + "    airport_country VARCHAR("+StrLen.AIRPORT_COUNTRY+"),\n"
                + "    latitude DECIMAL(5),\n"
                + "    longitude DECIMAL(6)\n"
                + ");\n");

        sqlList.add("create table crew\n"
                + "(\n"
                + "	crew_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	crew_name VARCHAR("+StrLen.CREW_NAME_MAX+"),\n"
                + "	email VARCHAR("+StrLen.CREW_EMAIL+"),\n"
                + "	phone VARCHAR("+StrLen.CREW_PHONE+"),\n"
                + "	comments VARCHAR("+StrLen.CREW_COMMENTS+")\n"
                + ");\n");

        sqlList.add("create table flight\n"
                + "(\n"
                + "	flight_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	departure_date BIGINT(19),\n"
                + "	arrival_date BIGINT(19),\n"
                + "	departure_airport_id INT,\n"
                + "	arrival_airport_id INT,\n"
                + "	aircraft_id INT,\n"
                + "	crew_pic_id INT,\n"
                + "	crew_sic_id INT,\n"
                + "	remarks VARCHAR("+StrLen.FLIGHT_REMARKS+"),\n"
                + "	private_notes VARCHAR("+StrLen.FLIGHT_PRIVATE_NOTES+"),\n"
                + "	take_off_day INT,\n"
                + "	take_off_night INT,\n"
                + "	landing_day INT,\n"
                + "	landing_night INT,\n"
                + "	ifr_time INT,\n"
                + "	night_time INT,\n"
                + "	xc_time INT,\n"
                + "	pic_time INT,\n"
                + "	picus_time INT,\n"
                + "	sic_time INT,\n"
                + "	dual_time INT,\n"
                + "	instructor_time INT,\n"
                + "	fstd_time INT,\n"
                + "     total_time INT,\n"
                + "     pf_pnf VARCHAR(10),\n"
                + "     FOREIGN KEY (departure_airport_id) REFERENCES airport (airport_id),\n"
                + "     FOREIGN KEY (arrival_airport_id) REFERENCES airport (airport_id),\n"
                + "     FOREIGN KEY (aircraft_id) REFERENCES aircraft (aircraft_id),\n"
                + "     FOREIGN KEY (crew_pic_id) REFERENCES crew (crew_id),\n"
                + "     FOREIGN KEY (crew_sic_id) REFERENCES crew (crew_id)\n"
                + ")");
        try (Statement stmt = conn.createStatement()){
            
            // create a new table
            for (String sql : sqlList) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            LogException.getMessage(e);
        }
    }
}
