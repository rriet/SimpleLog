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

import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.converters.TimeConverter;
import com.rietcorrea.simplelog.objects.Aircraft;
import com.rietcorrea.simplelog.objects.Airport;
import com.rietcorrea.simplelog.objects.Crew;
import com.rietcorrea.simplelog.objects.Flight;
import com.rietcorrea.simplelog.objects.Model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.text.WordUtils;


/**
 *
 * @author riet
 */
public class Importer {

	private String message;
	private int lineNumber;

	public Importer(int lineNumber) {
		this.message = "";
		this.lineNumber = lineNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Crew importCrew(String[] line, String nameFormat, int crewNameCol, int crewPhoneCol, int crewEmailCol,
			int crewNotesCol) {
		Crew newCrew = new Crew();

		String crewName = "";
		
		if (nameFormat.equalsIgnoreCase(MyTranslate.text("PilotNameUnchanged"))) {
			crewName = line[crewNameCol];
		}else if (nameFormat.equalsIgnoreCase(MyTranslate.text("PilotNameUpperFirst"))) {
			String nameLower = line[crewNameCol].toLowerCase();
			crewName = WordUtils.capitalize(nameLower);
		} else if (nameFormat.equalsIgnoreCase(MyTranslate.text("PilotNameUppercase"))) {
			crewName = line[crewNameCol].toUpperCase();
		} else if (nameFormat.equalsIgnoreCase(MyTranslate.text("PilotNameLowercase"))) {
			crewName = line[crewNameCol].toLowerCase();
		}

		// Set crew name
		newCrew.setCrewName(crewName);

		// Set phone if selection was made
		if (crewPhoneCol >= 0) {
			newCrew.setPhone(line[crewPhoneCol]);
		}

		// Set email if selection was made
		if (crewEmailCol >= 0) {
			newCrew.setEmail(line[crewEmailCol]);
		}

		// Set comments if selection was made
		if (crewNotesCol >= 0) {
			newCrew.setComments(line[crewNotesCol]);
		}

		return newCrew;
	}

	public Airport importAirport(String[] line, int airportIcaoCol, int airportIataCol, int airportNameCol,
			int airportCityCol, int airportCountryCol, int airportLatCol, int airportLonCol) {

		Airport newAirport = new Airport();

		// Set ICAO (Already verified
		newAirport.setIcao(line[airportIcaoCol]);

		// Set IATA if selection was made
		if (airportIataCol >= 0) {
			newAirport.setIata(line[airportIataCol]);
		}

		// Set Airport Name if selection was made
		if (airportNameCol >= 0) {
			newAirport.setAirportName(line[airportNameCol]);
		}

		// Set City if selection was made
		if (airportCityCol >= 0) {
			newAirport.setAirportCity(line[airportCityCol]);
		}

		// Set Country if selection was made
		if (airportCountryCol >= 0) {
			newAirport.setAirportCountry(line[airportCountryCol]);
		}

		// Set Latitude if selection was made
		if (airportLatCol >= 0) {
			try {
				newAirport.setLatitude(Double.valueOf(line[airportLatCol]));
			} catch (NumberFormatException e) {
				setMessage("Invalid Latitude on line " + (lineNumber + 1) + " - ignored.\n");
			}
		}

		// Set Longitude if selection was made
		if (airportLonCol >= 0) {
			try {
				newAirport.setLongitude(Double.valueOf(line[airportLonCol]));
			} catch (NumberFormatException e) {
				setMessage("Invalid Longitude on line " + (lineNumber + 1) + " - ignored.\n");
			}
		}
		return newAirport;

	}


	public Model importModel(String[] line, int modelNameCol, int modelGroupCol, int modelMtowCol,
			int modelEngineTypeCol, int modelMultiEngineCol, int modelMultiPilotCol, int modelEfisCol,
			int modelSeaplaneCol) throws Exception {

		Model newModel = new Model();

		// Set Model Name (Already verified
		newModel.setModelName(line[modelNameCol].toUpperCase());

		// Set Model Group if selection was made, if not copy model name
		if (modelGroupCol >= 0) {
			newModel.setModelGroup(line[modelGroupCol].toUpperCase());
		} else {
			newModel.setModelGroup(line[modelNameCol].toUpperCase());
		}

		// Set MTOW Name if selection was made
		if (modelMtowCol >= 0) {
			try {
				newModel.setMtow(Integer.valueOf(line[modelMtowCol]));
			} catch (NumberFormatException e) {
				// already checked in previous loop
				throw new Exception();
			}
		}

		// Set Engine Type if selection was made
		if (modelEngineTypeCol >= 0) {
			newModel.setEngineType(line[modelEngineTypeCol]);
		}

		// Set Multi-Engine if selection was made
		if (modelMultiEngineCol >= 0) {
			newModel.setMultiEngine(boolConoverter(line[modelMultiEngineCol]));
		}

		// Set Multi-Pilot if selection was made
		if (modelMultiPilotCol >= 0) {
			newModel.setMultiPilot(boolConoverter(line[modelMultiPilotCol]));
		}

		// Set Model if selection was made
		if (modelEfisCol >= 0) {
			newModel.setEfis(boolConoverter(line[modelEfisCol]));
		}
		// Set Model if selection was made
		if (modelSeaplaneCol >= 0) {
			newModel.setSeaplane(boolConoverter(line[modelSeaplaneCol]));
		}

		return newModel;
	}


	public Aircraft importAircraft(String[] line, int aircraftRegistrationCol, int aircraftMtowCol, int modelMtow,
			int aircraftSimulatorCol) throws Exception {
		Aircraft newAircraft = new Aircraft();

		// Set Aircraft Tail (Already verified
		newAircraft.setRegistration(line[aircraftRegistrationCol].toUpperCase());

		// Set Aircraft MTOW if selection was made
		if (aircraftMtowCol >= 0) {
			try {
				newAircraft.setAircraftMtow(Integer.valueOf(line[aircraftMtowCol]));
			} catch (NumberFormatException e) {
				throw new RuntimeException();
			}
		} else {
			newAircraft.setAircraftMtow(modelMtow);
		}

		// Set Simulator if selection was made
		if (aircraftSimulatorCol >= 0) {
			newAircraft.setSimulator(boolConoverter(line[aircraftSimulatorCol]));
		}
		return newAircraft;
	}

	public Flight importFlight(String[] line, boolean reCalcToLdg, boolean reCalcIfr, boolean reCalcNight, boolean reCalcXc,
			String dateFormat, int depDate, int depTime, int arrTime, Airport depAirport, Airport arrAirport,
			Aircraft aircraft, int remarks, int privateNotes, int pfPnf, int takeOffDay, int takeOffNight,
			int landingDay, int landingNight, int timeIfr, int timeNight, int timeXc, int timePic, int timePicus,
			int timeSic, int timeDual, int timeInstructor, int timeSim, int timeTotal, String hourFormat) {

		Flight flight = new Flight();
		UserPreferences pref = new UserPreferences();

		// Set dep and arr date and time
		if (!dateFormat.equals("Epoch time")) {
			try {
				String dateFormatString;
				switch (dateFormat) {
					case "MM/DD/YYYY HH:MM":
						dateFormatString = "MM/dd/yyyy HH:mm";
						break;
					case "DD-MM-YYYY HH:MM":
						dateFormatString = "dd-MM-yyyy HH:mm";
						break;
					case "YYYY-MM-DD HH:MM":
						dateFormatString = "yyyy-MM-dd HH:mm";
						break;
					default:  //"DD/MM/YYYY HH:MM":
						dateFormatString = "dd/MM/yyyy HH:mm";
						break;
				}
				SimpleDateFormat dateFormater = new SimpleDateFormat(dateFormatString);
				dateFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
				
				// if departureTime was not selected
				String depTimeString = "00:00";
				String arrTimeString = "00:00";
				// Only if both deptime and arr time are selected they are used...
				if (depTime >= 0 && arrTime >=0) {
					depTimeString = line[depTime];
					arrTimeString = line[arrTime];
				}

				String departureString = line[depDate] + " " + depTimeString;
				Date newDepartureDate = dateFormater.parse(departureString);
				flight.setDepartureDate(newDepartureDate.getTime() / 1000);

				String arrivalString = line[depDate] + " " + arrTimeString;
				Date newArrivalDate = dateFormater.parse(arrivalString);
				flight.setArrivalDate(newArrivalDate.getTime() / 1000);

				// If the departure is before the arrival add 24hs (in seconds) to the value
				if (flight.getArrivalDate() < flight.getDepartureDate()) {
					flight.setArrivalDate(flight.getArrivalDate() + 86400);
				}
			} catch (ParseException ex) {
				LogException.getMessage(ex);
			}
		} else {
			flight.setDepartureDate(Long.parseLong(line[depTime]));
			flight.setArrivalDate(Long.parseLong(line[arrTime]));
		}

		flight.setDepartureAirport(depAirport);
		flight.setArrivalAirport(arrAirport);
		flight.setAircraft(aircraft);

		if (remarks >= 0) {
			flight.setRemarks(line[remarks]);
		}

		if (privateNotes >= 0) {
			flight.setPrivateNotes(line[privateNotes]);
		}

		FlightCalculations calc = new FlightCalculations(flight);
		
		// Check if time sim was selected and save the time as a new variable
		int timeSimInt = 0;
		if (timeSim >= 0) {
			timeSimInt = TimeConverter.convert(hourFormat, line[timeSim]);
		}

		// If the aircraft is SIM or Sim time is more than 0
		if (flight.getAircraft().getSimulator() || timeSimInt != 0) {
			if (timeSimInt != 0) {
				flight.setFstdTime(timeSimInt);
			}else {
				flight.setFstdTime(TimeConverter.convert(hourFormat, line[timeTotal]));
			}
		} else {
			flight.setTotalTime(TimeConverter.convert(hourFormat, line[timeTotal]));

			if (reCalcToLdg) {
				if (line[pfPnf].equals("PF") || line[pfPnf].equals("PF/PNF")) {
					if (calc.isDayTakeOff()) {
						flight.setTakeOffDay(1);
					} else {
						flight.setTakeOffNight(1);
					}
				}
				if (line[pfPnf].equals("PF") || line[pfPnf].equals("PNF/PF")) {
					if (calc.isDayLanding()) {
						flight.setLandingDay(1);
					} else {
						flight.setLandingNight(1);
					}
				}
			} else {
				// If it's not a number -> set to 0
				try {
					flight.setTakeOffDay(Integer.parseInt(line[takeOffDay]));
				} catch (Exception e) {
					flight.setTakeOffDay(0);
				}			
				try {
					flight.setTakeOffNight(Integer.parseInt(line[takeOffNight]));
				} catch (Exception e) {
					flight.setTakeOffNight(0);
				}
				try {
					flight.setLandingDay(Integer.parseInt(line[landingDay]));
				} catch (Exception e) {
					flight.setLandingDay(0);
				}
				try {
					flight.setLandingNight(Integer.parseInt(line[landingNight]));
				} catch (Exception e) {
					flight.setLandingNight(0);
				}
			}

			if (pfPnf >= 0) {
				flight.setPfPnf(line[pfPnf]);
			} else {
				if (flight.getTakeOffDay() + flight.getTakeOffNight() > 0) {
					if (flight.getLandingDay() + flight.getLandingNight() > 0) {
						flight.setPfPnf("PF");
					} else {
						flight.setPfPnf("PF/PNF");
					}
				} else {
					if (flight.getLandingDay() + flight.getLandingNight() > 0) {
						flight.setPfPnf("PNF/PF");
					} else {
						flight.setPfPnf("PNF");
					}
				}
			}
			if (reCalcNight) {
				switch (line[pfPnf]) {
					case "IRP 3":
						flight.setNightTime(calc.getNightTime() * 2 / 3);
						break;
					case "IRP 4":
						flight.setNightTime(calc.getNightTime() * 1 / 2);
						break;
					default:
						flight.setNightTime(calc.getNightTime());
						break;
				}
			} else if (timeNight >= 0) {
				flight.setNightTime(TimeConverter.convert(hourFormat, line[timeNight]));
			}
			if (reCalcXc) {
				// Cross Country if distance more than configuration limit OR
				// if configuration is 0 and departure ICAO is defferent than Arrival
				if (calc.getFlightDistance() >= pref.getXcDistance()
						|| (pref.getXcDistance() == 0 && !depAirport.getIcao().equals(arrAirport.getIcao()))) {
					flight.setXcTime(flight.getTotalTime());
				}
			} else if (timeXc >= 0) {
				flight.setXcTime(TimeConverter.convert(hourFormat, line[timeXc]));
			}
			if (reCalcIfr) {
				flight.setIfrTime(flight.getIfrMinutes(TimeConverter.convert(hourFormat, line[timeTotal])));
			} else if (timeIfr >= 0) {
				flight.setIfrTime(TimeConverter.convert(hourFormat, line[timeIfr]));
			}
			if (timePic >= 0) {
				flight.setPicTime(TimeConverter.convert(hourFormat, line[timePic]));
			}
			if (timePicus >= 0) {
				flight.setPicUsTime(TimeConverter.convert(hourFormat, line[timePicus]));
			}
			if (timeSic >= 0) {
				flight.setSicTime(TimeConverter.convert(hourFormat, line[timeSic]));
			}
			if (timeDual >= 0) {
				flight.setDualTime(TimeConverter.convert(hourFormat, line[timeDual]));
			}
			if (timeInstructor >= 0) {
				flight.setInstructorTime(TimeConverter.convert(hourFormat, line[timeInstructor]));
			}
		}
		return flight;
	}

	private Boolean boolConoverter(String boolString) {
		switch (boolString.toLowerCase()) {
			case "1":
			case "true":
			case "sim":
				return true;
			default:
				return false;
		}
	}

}
