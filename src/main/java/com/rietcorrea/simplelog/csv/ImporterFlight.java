package com.rietcorrea.simplelog.csv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.rietcorrea.constants.StrEng;
import com.rietcorrea.simplelog.LogException;
import com.rietcorrea.simplelog.FlightCalculations;
import com.rietcorrea.simplelog.UserPreferences;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.converters.TimeConverter;
import com.rietcorrea.simplelog.database.AircraftDAO;
import com.rietcorrea.simplelog.database.AircraftDaoImpl;
import com.rietcorrea.simplelog.database.AirportDAO;
import com.rietcorrea.simplelog.database.AirportDaoImpl;
import com.rietcorrea.simplelog.database.CrewDAO;
import com.rietcorrea.simplelog.database.CrewDaoImpl;
import com.rietcorrea.simplelog.database.FlightDAO;
import com.rietcorrea.simplelog.database.FlightDaoImpl;
import com.rietcorrea.simplelog.objects.Aircraft;
import com.rietcorrea.simplelog.objects.Airport;
import com.rietcorrea.simplelog.objects.Crew;
import com.rietcorrea.simplelog.objects.Flight;

import javafx.concurrent.Task;


public class ImporterFlight {
	// Store all the lines in the file
	List<String[]> lines = new ArrayList<>();
	int numOfCols = 0;

	// Handle duplicate flights
	private String duplicateFlights = StrEng.DUPLICATED_IGNORE;

	// Date and Time
	private int     date                  = -1;
	private int     depTime               = -1;
	private int     arrTime               = -1;
	private String  dateFormat            = StrEng.DATE_DD_MM_YYYY;
	private boolean ignoreDepArrTimeError = true;

	// Notes
	private int     remarks                 = -1;
	private int     privateNotes            = -1;

	// Take-offs and Landings
	private int     pfPnf                      = -1;
	private int     takeoffDay                 = -1;
	private int     takeoffNight               = -1;
	private int     landingDay                 = -1;
	private int     landingNight               = -1;
	private boolean recalculateTakeoffLandings = false;

	// Times
	private String  hourFormat            = StrEng.MINUTES_HOUR;
	private int     timeIfr               = -1;
	private int     timeNight             = -1;
	private int     timeXc                = -1;
	private int     timePic               = -1;
	private int     timePicus             = -1;
	private int     timeSic               = -1;
	private int     timeDual              = -1;
	private int     timeInstructor        = -1;
	private int     timeSimulator         = -1;
	private int     timeTotal             = -1;
	private boolean recalculateIfr        = false;
	private boolean recalculateNight      = false;
	private boolean recalculateXc         = false;
	
	// Aircraft data
	private int     aircraftRegistration    = -1;
	private int     aircraftMtow            = -1;
	private int     aircraftModel           = -1;
	private int     aircraftModelGroup      = -1;
	private int     aircraftSimulator       = -1;
	private boolean overrideAircraft        = false;

	// All Airports
	private boolean useIata                = false;
	private boolean overrideAirports       = false;

	// Dep Airport
	private int depIcao    = -1;
	private int depIata    = -1;
	private int depName    = -1;
	private int depCity    = -1;
	private int depCountry = -1;
	private int depLat     = -1;
	private int depLon     = -1;

	// Arr Airport
	private int arrIcao    = -1;
	private int arrIata    = -1;
	private int arrName    = -1;
	private int arrCity    = -1;
	private int arrCountry = -1;
	private int arrLat     = -1;
	private int arrLon     = -1;


	// All Crew
	private String  nameFormat              = StrEng.PILOT_NAME_UNCHANGED;
	private boolean overrideCrew            = false;

	// PIC
	private int picName     = -1;
	private int picEmail    = -1;
	private int picPhone    = -1;
	private int picComments = -1;


	// SIC
	private int sicName     = -1;
	private int sicEmail    = -1;
	private int sicPhone    = -1;
	private int sicComments = -1;

	// Setters
	
	// Open file
	public ImporterFlight setFile(String file) {
		this.lines.clear();
		// Open file
		this.lines = ImporterCsvOpener.open(file);
		// Count number of columns in the first line
		numOfCols = lines.get(0).length;
		// remove first line
		lines.remove(0);
		return this;
	}
	
	// Duplicate Flights
	public ImporterFlight setDuplicateFlights(String duplicateFlights) {
		this.duplicateFlights = duplicateFlights;
		return this;
	}

	// Date and Time
	public ImporterFlight setDate(int date) {
		this.date = date - 1;
		return this;
	}

	public ImporterFlight setDepTime(int depTime) {
		this.depTime = depTime - 1;
		return this;
	}

	public ImporterFlight setArrTime(int arrTime) {
		this.arrTime = arrTime - 1;
		return this;
	}

	public ImporterFlight setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
		return this;
	}

	public ImporterFlight setIgnoreDepArrTimeError(boolean ignoreDepArrTimeError) {
		this.ignoreDepArrTimeError = ignoreDepArrTimeError;
		return this;
	}

	// Notes

	// Notes
	public ImporterFlight setRemarks(int remarks) {
		this.remarks = remarks - 1;
		return this;
	}

	public ImporterFlight setPrivateNotes(int privateNotes) {
		this.privateNotes = privateNotes - 1;
		return this;
	}

	// Take-offs and Landings
	public ImporterFlight setPfPnf(int pfPnf) {
		this.pfPnf = pfPnf - 1;
		return this;
	}

	public ImporterFlight setTakeoffDay(int takeoffDay) {
		this.takeoffDay = takeoffDay - 1;
		return this;
	}

	public ImporterFlight setTakeoffNight(int takeoffNight) {
		this.takeoffNight = takeoffNight - 1;
		return this;
	}

	public ImporterFlight setLandingDay(int landingDay) {
		this.landingDay = landingDay - 1;
		return this;
	}

	public ImporterFlight setLandingNight(int landingNight) {
		this.landingNight = landingNight - 1;
		return this;
	}

	public ImporterFlight setRecalculateTakeoffLandings(boolean recalculateTakeoffLandings) {
		this.recalculateTakeoffLandings = recalculateTakeoffLandings;
		return this;
	}

	// Times
	public ImporterFlight setHourFormat(String hourFormat) {
		this.hourFormat = hourFormat;
		return this;
	}

	public ImporterFlight setTimeIfr(int timeIfr) {
		this.timeIfr = timeIfr - 1;
		return this;
	}

	public ImporterFlight setTimeNight(int timeNight) {
		this.timeNight = timeNight - 1;
		return this;
	}

	public ImporterFlight setTimeXc(int timeXc) {
		this.timeXc = timeXc - 1;
		return this;
	}

	public ImporterFlight setTimePic(int timePic) {
		this.timePic = timePic - 1;
		return this;
	}

	public ImporterFlight setTimePicus(int timePicus) {
		this.timePicus = timePicus - 1;
		return this;
	}

	public ImporterFlight setTimeSic(int timeSic) {
		this.timeSic = timeSic - 1;
		return this;
	}

	public ImporterFlight setTimeDual(int timeDual) {
		this.timeDual = timeDual - 1;
		return this;
	}

	public ImporterFlight setTimeInstructor(int timeInstructor) {
		this.timeInstructor = timeInstructor - 1;
		return this;
	}

	public ImporterFlight setTimeSimulator(int timeSimulator) {
		this.timeSimulator = timeSimulator - 1;
		return this;
	}

	public ImporterFlight setTimeTotal(int timeTotal) {
		this.timeTotal = timeTotal - 1;
		return this;
	}

	public ImporterFlight setRecalculateIfr(boolean recalculateIfr) {
		this.recalculateIfr = recalculateIfr;
		return this;
	}

	public ImporterFlight setRecalculateNight(boolean recalculateNight) {
		this.recalculateNight = recalculateNight;
		return this;
	}

	public ImporterFlight setRecalculateXc(boolean recalculateXc) {
		this.recalculateXc = recalculateXc;
		return this;
	}


	// Aircraft data
	public ImporterFlight setAircraftRegistration(int aircraftRegistration) {
		this.aircraftRegistration = aircraftRegistration - 1;
		return this;
	}
	
	public ImporterFlight setAircraftMtow(int aircraftMtow) {
		this.aircraftMtow = aircraftMtow - 1;
		return this;
	}

	public ImporterFlight setAircraftModel(int aircraftModel) {
		this.aircraftModel = aircraftModel - 1;
		return this;
	}

	public ImporterFlight setAircraftModelGroupFlight(int aircraftModelGroup) {
		this.aircraftModelGroup = aircraftModelGroup - 1;
		return this;
	}

	public ImporterFlight setAircraftSimulator(int aircraftSimulator) {
		this.aircraftSimulator = aircraftSimulator;
		return this;
	}
	
	public ImporterFlight setOverrideAircraft(boolean overrideAircraft) {
		this.overrideAircraft = overrideAircraft;
		return this;
	}
	
	// All Airports
	public ImporterFlight setUseIata(boolean useIata) {
		this.useIata = useIata;
		return this;
	}
	
	public ImporterFlight setOverrideAirports(boolean overrideAirports) {
		this.overrideAirports = overrideAirports;
		return this;
	}

	// Dep Airport
	public ImporterFlight setDepIcao(int depIcao) {
		this.depIcao = depIcao - 1;
		return this;
	}
	
	public ImporterFlight setDepIata(int depIata) {
		this.depIata = depIata - 1;
		return this;
	}
	
	public ImporterFlight setDepName(int depName) {
		this.depName = depName - 1;
		return this;
	}
	
	public ImporterFlight setDepCity(int depCity) {
		this.depCity = depCity - 1;
		return this;
	}
	
	public ImporterFlight setDepCountry(int depCountry) {
		this.depCountry = depCountry - 1;
		return this;
	}
	
	public ImporterFlight setDepLat(int depLat) {
		this.depLat = depLat - 1;
		return this;
	}
	
	public ImporterFlight setDepLon(int depLon) {
		this.depLon = depLon - 1;
		return this;
	}
	
	// Arr Airport
	public ImporterFlight setArrIcao(int arrIcao) {
		this.arrIcao = arrIcao - 1;
		return this;
	}
	
	public ImporterFlight setArrIata(int arrIata) {
		this.arrIata = arrIata - 1;
		return this;
	}
	
	public ImporterFlight setArrName(int arrName) {
		this.arrName = arrName - 1;
		return this;
	}
	
	public ImporterFlight setArrCity(int arrCity) {
		this.arrCity = arrCity - 1;
		return this;
	}
	
	public ImporterFlight setArrCountry(int arrCountry) {
		this.arrCountry = arrCountry - 1;
		return this;
	}
	
	public ImporterFlight setArrLat(int arrLat) {
		this.arrLat = arrLat - 1;
		return this;
	}
	
	public ImporterFlight setArrLon(int arrLon) {
		this.arrLon = arrLon - 1;
		return this;
	}

	// All Crew
	public ImporterFlight setNameFormat(String nameFormat) {
		this.nameFormat = nameFormat;
		return this;
	}
	
	public ImporterFlight setOverrideCrew(boolean overrideCrew) {
		this.overrideCrew = overrideCrew;
		return this;
	}
	
	// PIC
	public ImporterFlight setPicName(int picName) {
		this.picName = picName - 1;
		return this;
	}
	
	public ImporterFlight setPicEmail(int picEmail) {
		this.picEmail = picEmail - 1;
		return this;
	}
	
	public ImporterFlight setPicPhone(int picPhone) {
		this.picPhone = picPhone - 1;
		return this;
	}
	
	public ImporterFlight setPicComments(int picComments) {
		this.picComments = picComments - 1;
		return this;
	}

	// SIC
	public ImporterFlight setSicName(int sicName) {
		this.sicName = sicName - 1;
		return this;
	}
	
	public ImporterFlight setSicEmail(int sicEmail) {
		this.sicEmail = sicEmail - 1;
		return this;
	}
	
	public ImporterFlight setSicPhone(int sicPhone) {
		this.sicPhone = sicPhone - 1;
		return this;
	}
	
	public ImporterFlight setSicComments(int sicComments) {
		this.sicComments = sicComments - 1;
		return this;
	}
	
	/**
	 * Check if the file contains the same number of columns until the end
	 * 
	 * Returns the number of the first line where the error occurred
	 */
	public List<String> checkFileColumns() {
		// Get the number of lines in the first column to compare with the others
		int lineNumber = 1;
		List<String> result = new ArrayList<String>();
		for (String[] line : lines) {
			if (line.length != numOfCols) {
				result.add("Line: "+lineNumber+"\n");
			}
			lineNumber++;
		}
		return result;
	}
	

	/**
	 *  Check the date and time against the format provided
	 *  
	 *  Return TRUE if valid Date
	 */
	public List<String> checkDateFormat() {
		String dateFormatString;
		switch (dateFormat) {
			case StrEng.DATE_FORMAT_DD_MM_YYYY_LINE:
				dateFormatString = "dd-MM-yyyy";
				break;
			case StrEng.DATE_FORMAT_MM_DD_YYYY:
				dateFormatString = "MM/dd/yyyy";
				break;
			case StrEng.DATE_FORMAT_MM_DD_YYYY_LINE:
				dateFormatString = "MM-dd-yyyy";
				break;
			case StrEng.DATE_FORMAT_YYYY_MM_DD:
				dateFormatString = "yyyy/MM/dd";
				break;
			case StrEng.DATE_FORMAT_YYYY_MM_DD_LINE:
				dateFormatString = "yyyy-MM-dd";
				break;
			default: // Default "DD/MM/YYYY"
				dateFormatString = "dd/MM/yyyy";
				break;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatString);

		int lineNumber = 1;
		List<String> result = new ArrayList<String>();
		for (String[] line : lines) {
			try {
				simpleDateFormat.parse(line[date]);
			} catch (ParseException e) {
				result.add("Invalid date format on line: " + lineNumber+"\n");
			}
			lineNumber++;
		}
		return result;
	}

	public List<String> checkTimeFormat() {
		List<String> result = new ArrayList<String>();
		if (this.dateFormat == StrEng.DATE_EPOCH_TIME) {
			int lineNumber = 1;
			for (String[] line : lines) {
				try {
					Long.parseLong(line[depTime]);
				} catch (NumberFormatException e) {
					result.add("Invalid departure time format on line: " + lineNumber+"\n");
				}
				try {
					Long.parseLong(line[arrTime]);
				} catch (NumberFormatException e) {
					result.add("Invalid arrival time format on line: " + lineNumber+"\n");
				}
				lineNumber++;
			}
		} else {
			SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");
			int lineNumber = 1;
			for (String[] line : lines) {
				if(depTime >= 0) {
					try {
						simpleTimeFormat.parse(line[depTime]);
					} catch (ParseException e) {
						result.add("Invalid departure time format on line: " + lineNumber+" (departure)\n");
					}
				}
	
				if(arrTime >= 0) {
					try {
						simpleTimeFormat.parse(line[arrTime]);
					} catch (ParseException e) {
						result.add("Invalid arrival time format on line: " + lineNumber+" (arrival)\n");
					}
				}
				lineNumber++;
			}
		}
		return result;
	}
	
	public List<String> checkTakeOffLandings() {
		List<String> result = new ArrayList<String>();
		int lineNumber = 1;
		for (String[] line : lines) {
			if(takeoffDay >= 0) {
				try {
					Integer.valueOf(line[takeoffDay]);
				} catch (NumberFormatException e) {
					result.add("Invalid Take-off Day on line: "+lineNumber+"\n");
				}
			}
		
			if(takeoffNight >= 0) {
				try {
					Integer.valueOf(line[takeoffNight]);
				} catch (NumberFormatException e) {
					result.add("Invalid Take-off Night on line: "+lineNumber+"\n");
				}
			}
			
			if(landingDay >= 0) {
				try {
					Integer.valueOf(line[landingDay]);
				} catch (NumberFormatException e) {
					result.add("Invalid Landing Day on line: "+lineNumber+"\n");
				}
			}
			
			if(landingNight >= 0) {
				try {
					Integer.valueOf(line[landingNight]);
				} catch (NumberFormatException e) {
					result.add("Invalid Landing Night on line: "+lineNumber+"\n");
				}
			}
			lineNumber++;
		}
		return result;
	}
	
	public List<String> checkTimeFormatLong(int columnNumber) {
		List<String> result = new ArrayList<String>();
		int lineNumber = 1;
		for (String[] line : lines) {
			if (!TimeConverter.isValid(hourFormat, line[columnNumber -1])) {
				result.add("Line: "+lineNumber+"\n");
			}
			lineNumber++;
		}
		return result;
	}
	
	public List<String> checkIata() {
		List<String> result = new ArrayList<String>();
		
		AirportDAO airportDAO = new AirportDaoImpl();
		
		int lineNumber = 1;
		for (String[] line : lines) {
			if (!airportDAO.airportIataExist(line[depIata].replaceAll("\\s+", ""))) {
				result.add("Departure airport IATA is not on database on line: "+lineNumber+"\n");
			}
			if (!airportDAO.airportIataExist(line[arrIata].replaceAll("\\s+", ""))) {
				result.add("Arrival airport IATA is not on database on line: "+lineNumber+"\n");
			}
			lineNumber++;
		}
		return result;
	}
	
	public List<String> checkIcao() {
		List<String> result = new ArrayList<String>();
		int lineNumber = 1;
		for (String[] line : lines) {
			String depIcaoString = line[depIcao].replaceAll("\\s+", "");
			if (depIcaoString.length() != 4) {
				result.add("Invalid Departure ICAO on line: "+lineNumber+"\n");
			}
			String arrIcaoString = line[arrIcao].replaceAll("\\s+", "");
			if (arrIcaoString.length() != 4) {
				result.add("Invalid Arrival ICAO on line: "+lineNumber+"\n");
			}
			lineNumber++;
		}
		return result;
	}
	
	public Task<Void> importFile(String file) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {

				List<String[]> lines = ImporterCsvOpener.open(file);
				lines.remove(0);

				// connect to databases....
				AirportDAO airportDAO = new AirportDaoImpl();
				CrewDAO crewDAO = new CrewDaoImpl();
				AircraftDAO aircraftDAO = new AircraftDaoImpl();
				FlightDAO flightDAO = new FlightDaoImpl();

				updateMessage(MyTranslate.text("ImportingFlights"));
				// Time to update message...
				Thread.sleep(100);

				UserPreferences pref = new UserPreferences();
				Integer numberOfLines = lines.size();
				int lineNumber = 1;
				for (String[] line : lines) {

					// update progress bar
					updateProgress(lineNumber, numberOfLines.doubleValue());
					lineNumber++;

					Flight flight = new Flight();

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
								default: // Default "DD/MM/YYYY HH:MM"
									dateFormatString = "dd/MM/yyyy HH:mm";
									break;

							}
							SimpleDateFormat dateFormater = new SimpleDateFormat(dateFormatString);
							dateFormater.setTimeZone(TimeZone.getTimeZone("UTC"));

							// if departureTime was not selected
							String depTimeString = "00:00";
							String arrTimeString = "00:00";

							if (depTime >= 0) {
								depTimeString = line[depTime];
							}
							if (arrTime >= 0) {
								arrTimeString = line[arrTime];
							}

							String departureString = line[date] + " " + depTimeString;
							Date newDepartureDate = dateFormater.parse(departureString);
							flight.setDepartureDate(newDepartureDate.getTime() / 1000);

							String arrivalString = line[date] + " " + arrTimeString;
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


					// fix for files were the airport is not set....
					String depIcaoCodeString = "";
					if (depIcao >= 0) {
						// remove white spaces in ICAO code
						depIcaoCodeString = line[depIcao].replaceAll("\\s+", "");
					}

					String arrIcaoCodeString = "";
					if (arrIcao >= 0) {
						arrIcaoCodeString = line[arrIcao].replaceAll("\\s+", "");
					}

					

					// IF using IATA and Iata exists.... Use IATA, if not Must have been using
					// ICAO...
					Airport depAirport;
					if (useIata && airportDAO.airportIataExist(line[depIata])) {
						depAirport = airportDAO.getIata(line[depIata].replaceAll("\\s+", ""));
					} else {
						depAirport = airportDAO.getIcao(depIcaoCodeString);
					}
					flight.setDepartureAirport(depAirport);

					Airport arrAirport;
					if (useIata && airportDAO.airportIataExist(line[arrIata])) {
						arrAirport = airportDAO.getIata(line[arrIata].replaceAll("\\s+", ""));
					} else {
						arrAirport = airportDAO.getIcao(arrIcaoCodeString);
					}
					flight.setArrivalAirport(arrAirport);

					Aircraft aircraft = aircraftDAO.getAircraft(line[aircraftRegistration]);
					flight.setAircraft(aircraft);

					Crew crewPic = crewDAO.getCrew(line[picName]);
					flight.setCrewPic(crewPic);

					if (sicName >= 0 && !line[sicName].equals("")) {
						Crew crewSic = crewDAO.getCrew(line[sicName]);
						flight.setCrewSic(crewSic);
					}
					if (remarks >= 0) {
						flight.setRemarks(line[remarks]);
					}

					if (privateNotes >= 0) {
						flight.setPrivateNotes(line[privateNotes]);
					}

					FlightCalculations calc = new FlightCalculations(flight);

					// Check if time sim was selected and save the time as a new variable
					int timeSimInt = 0;
					if (timeSimulator >= 0) {
						timeSimInt = TimeConverter.convert(hourFormat, line[timeSimulator]);
					}

					// If the aircraft is SIM or Sim time is more than 0
					if (flight.getAircraft().getSimulator().booleanValue() || timeSimInt != 0) {
						if (timeSimInt != 0) {
							flight.setFstdTime(timeSimInt);
						} else {
							flight.setFstdTime(TimeConverter.convert(hourFormat, line[timeTotal]));
						}
					} else {
						flight.setTotalTime(TimeConverter.convert(hourFormat, line[timeTotal]));

						if (recalculateTakeoffLandings) {
							if (pfPnf >= 0) {
								if (line[pfPnf].equalsIgnoreCase(StrEng.PF_STRING)
										|| line[pfPnf].equals(StrEng.PF_PNF_STRING)) {
									if (calc.isDayTakeOff()) {
										flight.setTakeOffDay(1);
									} else {
										flight.setTakeOffNight(1);
									}
								}
								if (line[pfPnf].equalsIgnoreCase(StrEng.PF_STRING)
										|| line[pfPnf].equals(StrEng.PNF_PF_STRING)) {
									if (calc.isDayLanding()) {
										flight.setLandingDay(1);
									} else {
										flight.setLandingNight(1);
									}
								}
							} else {

								// if we dont't have the PF/PNF information consider all as PF
								if (calc.isDayTakeOff()) {
									flight.setTakeOffDay(1);
								} else {
									flight.setTakeOffNight(1);
								}
								if (calc.isDayLanding()) {
									flight.setLandingDay(1);
								} else {
									flight.setLandingNight(1);
								}
							}
						} else {
							// If it's not a number -> set to 0
							try {
								flight.setTakeOffDay(Integer.parseInt(line[takeoffDay]));
							} catch (Exception e) {
								flight.setTakeOffDay(0);
							}
							try {
								flight.setTakeOffNight(Integer.parseInt(line[takeoffNight]));
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

						if (pfPnf >= 0 && pfPnf < line.length) {
							List<String> pfPnfOptions = StrEng.PF_PNF_OPTION_STRINGS;

							String pfPnfString = line[pfPnf].toUpperCase();
							if (pfPnfOptions.contains(pfPnfString)) {
								flight.setPfPnf(pfPnfString);
							} else {
								flight.setPfPnf(StrEng.PF_STRING);
							}

						} else {
							if (flight.getTakeOffDay() + flight.getTakeOffNight() > 0) {
								if (flight.getLandingDay() + flight.getLandingNight() > 0) {
									flight.setPfPnf(StrEng.PF_STRING);
								} else {
									flight.setPfPnf(StrEng.PF_PNF_STRING);
								}
							} else {
								if (flight.getLandingDay() + flight.getLandingNight() > 0) {
									flight.setPfPnf(StrEng.PNF_PF_STRING);
								} else {
									flight.setPfPnf(StrEng.PNF_STRING);
								}
							}
						}
					}

					if (recalculateNight) {
						if (pfPnf >= 0) {
							// Check if the flight is IRP and divide the night time proportionaly
							switch (line[pfPnf]) {
								case StrEng.IRP3_STRING:
									flight.setNightTime(calc.getNightTime() * 2 / 3);
									break;
								case StrEng.IRP4_STRING:
									flight.setNightTime(calc.getNightTime() * 1 / 2);
									break;
								default: // Default all time is night
									flight.setNightTime(calc.getNightTime());
									break;
							}
						} else {
							flight.setNightTime(calc.getNightTime());
						}
					} else if (timeNight >= 0) {
						flight.setNightTime(TimeConverter.convert(hourFormat, line[timeNight]));
					}

					if (recalculateXc) {
						// Cross Country if distance more than configuration limit OR
						// if configuration is 0 and departure ICAO is defferent than Arrival
						if (calc.getFlightDistance() >= pref.getXcDistance()
								|| (pref.getXcDistance() == 0 && !depAirport.getIcao().equals(arrAirport.getIcao()))) {
							flight.setXcTime(flight.getTotalTime());
						}
					} else if (timeXc >= 0) {
						flight.setXcTime(TimeConverter.convert(hourFormat, line[timeXc]));
					}

					if (recalculateIfr) {
						flight.setIfrTime(flight.getIfrMinutes(flight.getTotalTime()));
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

					flightDAO.insertFlight(flight);

				}
				updateMessage(MyTranslate.text("FinishedImportingFlights"));
				return null;
			}

		};
	}
}
