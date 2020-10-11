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
import com.rietcorrea.simplelog.objects.Aircraft;
import com.rietcorrea.simplelog.objects.Airport;
import com.rietcorrea.simplelog.objects.Crew;
import com.rietcorrea.simplelog.objects.Flight;
import com.rietcorrea.simplelog.objects.Model;
import com.rietcorrea.simplelog.objects.SearchCriteria;
import com.rietcorrea.simplelog.objects.Totals;

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
public class FlightDaoImpl implements FlightDAO {

	private static final String MAIN_QUERY_STRING = "SELECT \n"
			+ "flight.flight_id,  flight.departure_date,  flight.arrival_date, flight.pf_pnf, \n"
			+ "dep_airport.airport_id as dep_airport_id,\n" + "dep_airport.icao as dep_airport_icao, \n"
			+ "dep_airport.iata as dep_airport_iata, \n" + "dep_airport.airport_name as dep_airport_name, \n"
			+ "dep_airport.airport_city as dep_airport_city,\n"
			+ "dep_airport.airport_country as dep_airport_country, \n"
			+ "dep_airport.latitude as dep_airport_latitude, \n" + "dep_airport.longitude as dep_airport_longitude, \n"
			+ "arr_airport.airport_id as arr_airport_id,\n" + "arr_airport.icao as arr_airport_icao, \n"
			+ "arr_airport.iata as arr_airport_iata, \n" + "arr_airport.airport_name as arr_airport_name, \n"
			+ "arr_airport.airport_city as arr_airport_city,\n"
			+ "arr_airport.airport_country as arr_airport_country, \n"
			+ "arr_airport.latitude as arr_airport_latitude, \n" + "arr_airport.longitude as arr_airport_longitude, \n"
			+ "aircraft.aircraft_id,  aircraft.registration, aircraft.aircraft_mtow, aircraft.simulator,\n"
			+ "model.model_id,  model.model_name, model.model_group, model.engine_type, \n"
			+ "model.mtow, model.multi_engine, model.multi_pilot, model.efis, model.seaplane, \n"
			+ "pic.crew_id as pic_id, pic.crew_name as pic_name, pic.email as pic_email, \n"
			+ "pic.phone as pic_phone, pic.comments as pic_comments,\n"
			+ "sic.crew_id as sic_id, sic.crew_name as sic_name, sic.email as sic_email, \n"
			+ "sic.phone as sic_phone, sic.comments as sic_comments,\n"
			+ "flight.remarks, flight.private_notes, flight.take_off_day, flight.take_off_night, \n"
			+ "flight.landing_day, flight.landing_night, flight.ifr_time,\n"
			+ "flight.night_time, flight.xc_time, flight.pic_time, flight.picus_time, flight.sic_time, flight.dual_time,\n"
			+ "flight.instructor_time, flight.fstd_time, flight.total_time\n" + " FROM flight \n"
			+ " LEFT OUTER JOIN airport as dep_airport ON flight.departure_airport_id = dep_airport.airport_id\n"
			+ " LEFT OUTER JOIN airport as arr_airport ON flight.arrival_airport_id = arr_airport.airport_id\n"
			+ " LEFT OUTER JOIN aircraft ON flight.aircraft_id = aircraft.aircraft_id\n"
			+ " LEFT OUTER JOIN model ON aircraft.model_id = model.model_id\n"
			+ " LEFT OUTER JOIN crew as pic ON flight.crew_pic_id = pic.crew_id\n"
			+ " LEFT OUTER JOIN crew as sic ON flight.crew_sic_id = sic.crew_id ";

	private static final String TOTALS_QUERY_STRING = " COUNT(flight.flight_id) as sectors, "
			+ "SUM(flight.take_off_day) as total_take_off_day, "
			+ "SUM(flight.take_off_night) as total_take_off_night\n, " + "SUM(flight.landing_day) as landing_day\n, "
			+ "SUM(flight.landing_night) as landing_night\n, " + "SUM(flight.ifr_time) as ifr_time\n, "
			+ "SUM(flight.night_time) as night_time\n, " + "SUM(flight.xc_time) as xc_time\n, "
			+ "SUM(flight.pic_time) as pic_time\n, " + "SUM(flight.picus_time) as picus_time\n, "
			+ "SUM(flight.sic_time) as sic_time\n, " + "SUM(flight.dual_time) as dual_time\n, "
			+ "SUM(flight.instructor_time) as instructor_time\n, " + "SUM(flight.fstd_time) as fstd_time\n, "
			+ "SUM(flight.total_time) as total_time\n " + " FROM flight \n"
			+ " LEFT OUTER JOIN airport as dep_airport ON flight.departure_airport_id = dep_airport.airport_id\n"
			+ " LEFT OUTER JOIN airport as arr_airport ON flight.arrival_airport_id = arr_airport.airport_id\n"
			+ " LEFT OUTER JOIN aircraft ON flight.aircraft_id = aircraft.aircraft_id\n"
			+ " LEFT OUTER JOIN model ON aircraft.model_id = model.model_id\n"
			+ " LEFT OUTER JOIN crew as pic ON flight.crew_pic_id = pic.crew_id\n"
			+ " LEFT OUTER JOIN crew as sic ON flight.crew_sic_id = sic.crew_id ";

	private static final String SELECT_DATE_RANGE_STRING = " WHERE flight.departure_date >= ? AND flight.departure_date <= ? ";

	@Override
	public List<Flight> getAllFlights() {
		List<Flight> flights = new ArrayList<>();

		String queryString = MAIN_QUERY_STRING + " ORDER BY flight.departure_date DESC, flight.flight_id DESC;";
		try (Connection connection = SqliteConnection.getConnection();
				PreparedStatement stm = connection.prepareStatement(queryString)) {

			flights = flightsQuery(stm);

		} catch (SQLException ex) {
			LogException.getMessage(ex);
		}
		return flights;
	}

	@Override
	public List<Flight> getFlightsByDate(Long startDate, Long endDate) {
		List<Flight> flights = new ArrayList<>();

		String query = MAIN_QUERY_STRING + SELECT_DATE_RANGE_STRING
				+ " ORDER BY flight.departure_date DESC, flight.flight_id DESC;";

		try (Connection connection = SqliteConnection.getConnection();
				PreparedStatement stm = connection.prepareStatement(query)) {

			stm.setString(1, startDate.toString());
			stm.setString(2, endDate.toString());

			flights = flightsQuery(stm);

		} catch (SQLException ex) {
			LogException.getMessage(ex);
		}
		return flights;
	}

	@Override
	public List<Flight> searchFlights(Long startDate, Long endDate, String searchString) {
		List<Flight> flights = new ArrayList<>();

		String query = MAIN_QUERY_STRING + SELECT_DATE_RANGE_STRING
				+ " AND (dep_airport_icao LIKE ? OR dep_airport_iata LIKE ? OR dep_airport_name LIKE ? OR "
				+ " dep_airport_city LIKE ? OR dep_airport_country LIKE ? OR "
				+ " arr_airport_icao LIKE ? OR arr_airport_iata LIKE ? OR arr_airport_name LIKE ? OR "
				+ " arr_airport_city LIKE ? OR arr_airport_country LIKE ? OR "
				+ " aircraft.registration LIKE ? OR model.model_name LIKE ? OR model.model_group LIKE ? OR "
				+ " model.engine_type LIKE ? OR pic_name LIKE ? OR pic_email LIKE ? OR pic_phone LIKE ? OR "
				+ " pic_comments LIKE ? OR sic_name LIKE ? OR sic_email LIKE ? OR sic_phone LIKE ? OR "
				+ " sic_comments LIKE ? OR flight.remarks LIKE ? OR flight.private_notes LIKE ?) ORDER BY flight.arrival_date DESC, "
				+ " flight.flight_id DESC;";

		try (Connection connection = SqliteConnection.getConnection();
				PreparedStatement stm = connection.prepareStatement(query)) {

			stm.setString(1, startDate.toString());
			stm.setString(2, endDate.toString());
			stm.setString(3, "%" + searchString + "%");
			stm.setString(4, "%" + searchString + "%");
			stm.setString(5, "%" + searchString + "%");
			stm.setString(6, "%" + searchString + "%");
			stm.setString(7, "%" + searchString + "%");
			stm.setString(8, "%" + searchString + "%");
			stm.setString(9, "%" + searchString + "%");
			stm.setString(10, "%" + searchString + "%");
			stm.setString(11, "%" + searchString + "%");
			stm.setString(12, "%" + searchString + "%");
			stm.setString(13, "%" + searchString + "%");
			stm.setString(14, "%" + searchString + "%");
			stm.setString(15, "%" + searchString + "%");
			stm.setString(16, "%" + searchString + "%");
			stm.setString(17, "%" + searchString + "%");
			stm.setString(18, "%" + searchString + "%");
			stm.setString(19, "%" + searchString + "%");
			stm.setString(20, "%" + searchString + "%");
			stm.setString(21, "%" + searchString + "%");
			stm.setString(22, "%" + searchString + "%");
			stm.setString(23, "%" + searchString + "%");
			stm.setString(24, "%" + searchString + "%");
			stm.setString(25, "%" + searchString + "%");
			stm.setString(26, "%" + searchString + "%");

			flights = flightsQuery(stm);

		} catch (SQLException ex) {
			LogException.getMessage(ex);
		}
		return flights;
	}

	@Override
	public List<Flight> searchFlightsWhere(Long startDate, Long endDate, List<SearchCriteria> criteriaList,
			boolean andJoin) {

		String query = MAIN_QUERY_STRING + SELECT_DATE_RANGE_STRING + 
				buildSerchCriteria(criteriaList, andJoin) +
				" ORDER BY flight.arrival_date DESC, flight.flight_id DESC;";

		try (Connection connection = SqliteConnection.getConnection();
				PreparedStatement stm = connection.prepareStatement(query)) {

			stm.setString(1, startDate.toString());
			stm.setString(2, endDate.toString());

			if (!criteriaList.isEmpty()) {
				for (int i = 0; i < criteriaList.size(); i++) {
					stm.setString(i + 3, criteriaList.get(i).getSearchString());
				}
			}

			return flightsQuery(stm);
		} catch (SQLException ex) {
			LogException.getMessage(ex);
		}
		return new ArrayList<>();
	}

	@Override
	public Totals searchTotalsWhere(Long startDate, Long endDate, List<SearchCriteria> criteriaList, boolean andJoin) {

		String query = "SELECT 'Totals' as search_group, " + TOTALS_QUERY_STRING + SELECT_DATE_RANGE_STRING +
				buildSerchCriteria(criteriaList, andJoin);
		

		try (Connection connection = SqliteConnection.getConnection();
				PreparedStatement stm = connection.prepareStatement(query)) {

			stm.setString(1, startDate.toString());
			stm.setString(2, endDate.toString());

			if (!criteriaList.isEmpty()) {

				for (int i = 0; i < criteriaList.size(); i++) {
					stm.setString(i + 3, criteriaList.get(i).getSearchString());
				}
			}

			return totalQuery(stm);
			
		} catch (SQLException ex) {
			LogException.getMessage(ex);
		}
		return null;
	}

	private String buildSerchCriteria(List<SearchCriteria> criteriaList, boolean andJoin) {
		
		if (!criteriaList.isEmpty()) {
			StringBuilder bld = new StringBuilder();
			for (SearchCriteria criteria : criteriaList) {
				if (bld.length() > 0) {
					if (andJoin) {
						bld.append(" AND ");
					} else {
						bld.append(" OR ");
					}
				}
				bld.append(criteria.getCommand());
			}
			return " AND  (" + bld.toString() + ") ";
		}
		return "";
	}

	@Override
	public int getTimeSum(Long startDate, Long endDate, String columnName) {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}

	@Override
	public Flight getFlight(int flightId) {
		Flight flight = new Flight();
		String query = MAIN_QUERY_STRING + " WHERE flight.flight_id = ?";
		try (Connection connection = SqliteConnection.getConnection();
			PreparedStatement stm = connection.prepareStatement(query)){
			
			stm.setInt(1, flightId);
			ResultSet rs = stm.executeQuery();
			
			flight = loadFlight(rs);

		} catch (SQLException ex) {
			LogException.getMessage(ex);
		}
		return flight;
	}

	@Override
	public Flight getLastFlight() {
		Flight flight = new Flight();
		String query = MAIN_QUERY_STRING + " ORDER BY flight.arrival_date DESC, flight.flight_id DESC LIMIT 1;";
		try (Connection connection = SqliteConnection.getConnection();
			PreparedStatement stm = connection.prepareStatement(query)){
			
			ResultSet rs = stm.executeQuery();
			
			flight = loadFlight(rs);
			
		} catch (SQLException ex) {
			LogException.getMessage(ex);
		}
		return flight;
	}

	private Boolean itemExist(String serchItem) {
		String query = "SELECT count(*) FROM flight " + serchItem;

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
	public boolean getAirportInUse(Airport airport) {
		int airportId = airport.getAirportId();
		return this.itemExist(
				" WHERE departure_airport_id = '" + airportId + "' OR arrival_airport_id = '" + airportId + "' ");
	}

	@Override
	public boolean getAirportInUse(List<Airport> airportList) {
		for (Airport airport : airportList) {
			if (getAirportInUse(airport)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean getCrewInUse(Crew crew) {
		int crewId = crew.getCrewId();
		return this.itemExist(" WHERE crew_pic_id = '" + crewId + "' OR crew_sic_id = '" + crewId + "' ");
	}
	
	@Override
	public boolean getCrewInUse(List<Crew> crewList) {
		for (Crew crew : crewList) {
    		if (getCrewInUse(crew)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean getAircraftInUse(Aircraft aircraft) {
		int aircraftId = aircraft.getAircraftId();
		return this.itemExist(" WHERE aircraft_id = '" + aircraftId + "'; ");
	}

	public boolean getAircraftInUse(List<Aircraft> aircrafts) {
		for (Aircraft aircraft : aircrafts) {
			if (getAircraftInUse(aircraft)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void insertFlight(Flight flight) {

		String query = "INSERT INTO flight (departure_date," + "arrival_date," + "departure_airport_id,"
				+ "arrival_airport_id," + "aircraft_id," + "crew_pic_id," + "crew_sic_id," + "remarks,"
				+ "private_notes," + "take_off_day," + "take_off_night," + "landing_day," + "landing_night,"
				+ "ifr_time," + "night_time," + "xc_time," + "pic_time," + "picus_time," + "sic_time," + "dual_time,"
				+ "instructor_time," + "fstd_time," + "total_time,"
				+ "pf_pnf) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try (Connection connection = SqliteConnection.getConnection();
				PreparedStatement stm = connection.prepareStatement(query)){
			
			stm.setLong(1, flight.getDepartureDate());
			stm.setLong(2, flight.getArrivalDate());
			stm.setInt(3, flight.getDepartureAirport().getAirportId());
			stm.setInt(4, flight.getArrivalAirport().getAirportId());
			stm.setInt(5, flight.getAircraft().getAircraftId());
			stm.setInt(6, flight.getCrewPic().getCrewId());
			stm.setInt(7, flight.getCrewSic().getCrewId());
			stm.setString(8, flight.getRemarks());
			stm.setString(9, flight.getPrivateNotes());
			stm.setInt(10, flight.getTakeOffDay());
			stm.setInt(11, flight.getTakeOffNight());
			stm.setInt(12, flight.getLandingDay());
			stm.setInt(13, flight.getLandingNight());
			stm.setInt(14, flight.getIfrTime());
			stm.setInt(15, flight.getNightTime());
			stm.setInt(16, flight.getXcTime());
			stm.setInt(17, flight.getPicTime());
			stm.setInt(18, flight.getPicUsTime());
			stm.setInt(19, flight.getSicTime());
			stm.setInt(20, flight.getDualTime());
			stm.setInt(21, flight.getInstructorTime());
			stm.setInt(22, flight.getFstdTime());
			stm.setInt(23, flight.getTotalTime());
			stm.setString(24, flight.getPfPnf());

			stm.execute();
		} catch (SQLException ex) {
			LogException.getMessage(ex);
		}
	}

	@Override
	public void updateFlight(Flight flight) {
		String query = "UPDATE flight SET departure_date = ? ," + "arrival_date = ? ," + "departure_airport_id = ?,"
				+ "arrival_airport_id = ?," + "aircraft_id = ?," + "crew_pic_id = ?," + "crew_sic_id = ?,"
				+ "remarks = ?," + "private_notes = ?," + "take_off_day = ?," + "take_off_night = ?,"
				+ "landing_day = ?," + "landing_night = ?," + "ifr_time = ?," + "night_time = ?," + "xc_time = ?,"
				+ "pic_time = ?," + "picus_time = ?," + "sic_time = ?," + "dual_time = ?," + "instructor_time = ?,"
				+ "fstd_time = ?," + "total_time = ?," + "pf_pnf = ? " + " WHERE flight_id = ?";

		try (Connection connection = SqliteConnection.getConnection();
				PreparedStatement stm = connection.prepareStatement(query)){
			
			stm.setLong(1, flight.getDepartureDate());
			stm.setLong(2, flight.getArrivalDate());
			stm.setInt(3, flight.getDepartureAirport().getAirportId());
			stm.setInt(4, flight.getArrivalAirport().getAirportId());
			stm.setInt(5, flight.getAircraft().getAircraftId());
			stm.setInt(6, flight.getCrewPic().getCrewId());
			stm.setInt(7, flight.getCrewSic().getCrewId());
			stm.setString(8, flight.getRemarks());
			stm.setString(9, flight.getPrivateNotes());
			stm.setInt(10, flight.getTakeOffDay());
			stm.setInt(11, flight.getTakeOffNight());
			stm.setInt(12, flight.getLandingDay());
			stm.setInt(13, flight.getLandingNight());
			stm.setInt(14, flight.getIfrTime());
			stm.setInt(15, flight.getNightTime());
			stm.setInt(16, flight.getXcTime());
			stm.setInt(17, flight.getPicTime());
			stm.setInt(18, flight.getPicUsTime());
			stm.setInt(19, flight.getSicTime());
			stm.setInt(20, flight.getDualTime());
			stm.setInt(21, flight.getInstructorTime());
			stm.setInt(22, flight.getFstdTime());
			stm.setInt(23, flight.getTotalTime());
			stm.setString(24, flight.getPfPnf());
			stm.setInt(25, flight.getFlightId());

			stm.execute();
		} catch (SQLException ex) {
			LogException.getMessage(ex);
		}
	}

	@Override
	public void deleteFlight(Flight flight) {
		String query = "DELETE FROM flight WHERE flight_id = ?";
		try (Connection connection = SqliteConnection.getConnection();
			PreparedStatement stm = connection.prepareStatement(query)){
			
			stm.setInt(1, flight.getFlightId());
			stm.execute();
		} catch (SQLException ex) {
			LogException.getMessage(ex);
		}
	}

	private List<Totals> totalsSearch(String searchGroup, String field, Long startDate, Long endDate) {
		List<Totals> totals = new ArrayList<>();
		
		String query = "SELECT \n " + searchGroup + " as search_group, " + TOTALS_QUERY_STRING
				+ SELECT_DATE_RANGE_STRING + field;
		
		try (Connection connection = SqliteConnection.getConnection();
			PreparedStatement stm = connection.prepareStatement(query)){
			
			stm.setString(1, startDate.toString());
			stm.setString(2, endDate.toString());
			
			totals = totalsQuery(stm);
		} catch (SQLException ex) {
			LogException.getMessage(ex);
		}
		return totals;
	}

	@Override
	public List<Totals> getTotals(Long startDate, Long endDate) {
		return totalsSearch("'This logbook Totals'", "", startDate, endDate);
	}

	@Override
	public List<Totals> getTotalsByTail(Long startDate, Long endDate) {
		return totalsSearch("aircraft.registration", " GROUP BY aircraft.registration ", startDate, endDate);
	}

	@Override
	public List<Totals> getTotalsByType(Long startDate, Long endDate) {
		return totalsSearch("model.model_name", " GROUP BY model.model_name ", startDate, endDate);
	}

	@Override
	public List<Totals> getTotalsByTypeGroup(Long startDate, Long endDate) {
		return totalsSearch("model.model_group", " GROUP BY model.model_group ", startDate, endDate);
	}

	@Override
	public List<Totals> getTotalsByEngineType(Long startDate, Long endDate) {
		return totalsSearch("model.engine_type", " GROUP BY model.engine_type ", startDate, endDate);
	}

	@Override
	public List<Totals> getTotalsByEngineNumber(Long startDate, Long endDate) {
		List<Totals> totals = new ArrayList<>();

		for (Totals total : totalsSearch("model.multi_engine", " GROUP BY model.multi_engine ", startDate, endDate)) {
			if (total.getGroup().equals("0")) {
				total.setGroup("Single Engine");
			} else {
				total.setGroup("Multi Engine");
			}
			totals.add(total);
		}
		return totals;
	}

	@Override
	public List<Totals> getTotalsByMultiPilot(Long startDate, Long endDate) {
		List<Totals> totals = new ArrayList<>();

		for (Totals total : totalsSearch("model.multi_pilot", " GROUP BY model.multi_pilot ", startDate, endDate)) {
			if (total.getGroup().equals("0")) {
				total.setGroup("Single Pilot");
			} else {
				total.setGroup("Multi Pilot");
			}
			totals.add(total);
		}
		return totals;
	}

	@Override
	public List<Totals> getTotalsByEfis(Long startDate, Long endDate) {
		List<Totals> totals = new ArrayList<>();

		for (Totals total : totalsSearch("model.efis", " GROUP BY model.efis ", startDate, endDate)) {
			if (total.getGroup().equals("0")) {
				total.setGroup("Non-EFIS");
			} else {
				total.setGroup("EFIS");
			}
			totals.add(total);
		}
		return totals;
	}

	@Override
	public List<Totals> getTotalsByDepartureAirport(Long startDate, Long endDate) {
		return totalsSearch("dep_airport.icao", " GROUP BY dep_airport.icao ", startDate, endDate);
	}

	@Override
	public List<Totals> getTotalsByArrivalAirport(Long startDate, Long endDate) {
		return totalsSearch("arr_airport.icao", " GROUP BY arr_airport.icao ", startDate, endDate);
	}

	@Override
	public List<Totals> getTotalsByPicName(Long startDate, Long endDate) {
		return totalsSearch("pic.crew_name", " GROUP BY pic.crew_name ", startDate, endDate);
	}

	@Override
	public List<Totals> getTotalsBySicName(Long startDate, Long endDate) {
		return totalsSearch("sic.crew_name", " GROUP BY sic.crew_name ", startDate, endDate);
	}

	private List<Flight> flightsQuery(PreparedStatement stm) {
		List<Flight> flights = new ArrayList<>();

		try (ResultSet rs = stm.executeQuery()){
			
			while (rs.next()) {
				Flight nextFlight = loadFlight(rs);
				flights.add(nextFlight);
			}
			
		} catch (SQLException ex) {
			LogException.getMessage(ex);
		}
		return flights;
	}

	private List<Totals> totalsQuery(PreparedStatement stm) {
		List<Totals> totals = new ArrayList<>();

		try (ResultSet rs = stm.executeQuery()){
			
			while (rs.next()) {
				Totals nextTotal = loadTotals(rs);
				totals.add(nextTotal);
			}
		} catch (SQLException ex) {
			LogException.getMessage(ex);
		}
		return totals;
	}
	
	private Totals totalQuery(PreparedStatement stm) {
		
		try (ResultSet rs = stm.executeQuery()){
			if (rs.next()) {
				return loadTotals(rs);
			}
		} catch (SQLException e) {
			LogException.getMessage(e);
		}
		return null;
	}

	private Flight loadFlight(ResultSet rs) {
		Flight nextFlight = new Flight();

		try {
			if (!rs.isClosed()) {
				Airport nextDepAirport = new Airport().setAirportId(rs.getInt("dep_airport_id"))
						.setIcao(rs.getString("dep_airport_icao")).setIata(rs.getString("dep_airport_iata"))
						.setAirportName(rs.getString("dep_airport_name"))
						.setAirportCity(rs.getString("dep_airport_city"))
						.setAirportCountry(rs.getString("dep_airport_country"))
						.setLatitude(rs.getDouble("dep_airport_latitude"))
						.setLongitude(rs.getDouble("dep_airport_longitude"));

				Airport nextArrAirport = new Airport().setAirportId(rs.getInt("arr_airport_id"))
						.setIcao(rs.getString("arr_airport_icao")).setIata(rs.getString("arr_airport_iata"))
						.setAirportName(rs.getString("arr_airport_name"))
						.setAirportCity(rs.getString("arr_airport_city"))
						.setAirportCountry(rs.getString("arr_airport_country"))
						.setLatitude(rs.getDouble("arr_airport_latitude"))
						.setLongitude(rs.getDouble("arr_airport_longitude"));

				Model nextModel = new Model()	.setModelId(rs.getInt("model_id"))
									    		.setModelName(rs.getString("model_name"))
									    		.setModelGroup(rs.getString("model_group"))
									    		.setEngineType(rs.getString("engine_type"))
									    		.setMtow(rs.getInt("mtow"))
									    		.setMultiEngine(rs.getBoolean("multi_engine"))
									    		.setMultiPilot(rs.getBoolean("multi_pilot"))
									    		.setEfis(rs.getBoolean("efis"))
									    		.setSeaplane(rs.getBoolean("seaplane"));

				Aircraft nextAircraft = new Aircraft()	.setAircraftId(rs.getInt("aircraft_id"))
											        	.setRegistration(rs.getString("registration"))
											        	.setAircraftMtow(rs.getInt("aircraft_mtow"))
											        	.setSimulator(rs.getBoolean("simulator"))
											        	.setAircraftModel(nextModel);

				Crew nextPic = new Crew()	.setCrewId(rs.getInt("pic_id"))
								    		.setCrewName(rs.getString("pic_name"))
								    		.setEmail(rs.getString("pic_email"))
								    		.setPhone(rs.getString("pic_phone"))
								    		.setComments(rs.getString("pic_comments"));
						

				Crew nextSic = new Crew()	.setCrewId(rs.getInt("sic_id"))
								    		.setCrewName(rs.getString("sic_name"))
								    		.setEmail(rs.getString("sic_email"))
								    		.setPhone(rs.getString("sic_phone"))
								    		.setComments(rs.getString("sic_comments"));

				nextFlight = new Flight().setFlightId(rs.getInt("flight_id"))
								    	.setDepartureDate(rs.getLong("departure_date"))
								    	.setArrivalDate(rs.getLong("arrival_date"))
								    	.setPfPnf(rs.getString("pf_pnf"))
								    	.setDepartureAirport(nextDepAirport)
								    	.setArrivalAirport(nextArrAirport)
								    	.setAircraft(nextAircraft)
								    	.setCrewPic(nextPic)
								    	.setCrewSic(nextSic)
								    	.setRemarks(rs.getString("remarks"))
								    	.setPrivateNotes(rs.getString("private_notes"))
								    	.setTakeOffDay(rs.getInt("take_off_day"))
								    	.setTakeOffNight(rs.getInt("take_off_night"))
								    	.setLandingDay(rs.getInt("landing_day"))
								    	.setLandingNight(rs.getInt("landing_night"))
								    	.setIfrTime(rs.getInt("ifr_time"))
								    	.setNightTime(rs.getInt("night_time"))
								    	.setXcTime(rs.getInt("xc_time"))
								    	.setPicTime(rs.getInt("pic_time"))
								    	.setPicUsTime(rs.getInt("picus_time"))
								    	.setSicTime(rs.getInt("sic_time"))
								    	.setDualTime(rs.getInt("dual_time"))
								    	.setInstructorTime(rs.getInt("instructor_time"))
								    	.setFstdTime(rs.getInt("fstd_time"))
								    	.setTotalTime(rs.getInt("total_time"));
			}
		} catch (SQLException ex) {
			LogException.getMessage(ex);
		}
		return nextFlight;
	}

	private Totals loadTotals(ResultSet rs) {
		Totals nextTotal = new Totals();

		try {
			if (!rs.isClosed()) {
				nextTotal = new Totals(rs.getString("search_group"), rs.getInt("sectors"),
						rs.getInt("total_take_off_day"), rs.getInt("total_take_off_night"), rs.getInt("landing_day"),
						rs.getInt("landing_night"), rs.getInt("ifr_time"), rs.getInt("night_time"),
						rs.getInt("xc_time"), rs.getInt("pic_time"), rs.getInt("picus_time"), rs.getInt("sic_time"),
						rs.getInt("dual_time"), rs.getInt("instructor_time"), rs.getInt("fstd_time"), 0, 0, 0, 0, 0,
						rs.getInt("total_time"));
			}
		} catch (SQLException ex) {
			LogException.getMessage(ex);
		}
		return nextTotal;
	}

}
