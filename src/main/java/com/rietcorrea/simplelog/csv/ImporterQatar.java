package com.rietcorrea.simplelog.csv;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import org.apache.commons.text.WordUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rietcorrea.simplelog.UserPreferences;
import com.rietcorrea.simplelog.converters.TimeConverter;
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
import com.rietcorrea.simplelog.objects.Crew;
import com.rietcorrea.simplelog.objects.Flight;
import com.rietcorrea.simplelog.objects.Model;

import javafx.concurrent.Task;

public class ImporterQatar {
	
	ArrayList<ArrayList<String>> lines = new ArrayList<>();
	String messageString = "";
	
	public ImporterQatar(String fileLocaString) throws Exception {
		
		File myFile = new File(fileLocaString);
		
		try (FileInputStream fis = new FileInputStream(myFile);
				// Finds the workbook instance for XLSX file
				XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);){

			// Return first sheet from the XLSX workbook
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);

			// Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = mySheet.iterator();
			
			boolean isData = false;

			// Traversing over each row of XLSX file
			while (rowIterator.hasNext()) {
				ArrayList<String> columns = new ArrayList<>();
				Row row = rowIterator.next();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				isData = false;
				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							if (isData) {
								columns.add(cell.getStringCellValue());
							}
							break;
						case Cell.CELL_TYPE_NUMERIC:
							isData = true;
							break;
						default:
							// add blank to blank lines
							columns.add("");

					}
				}
				if (isData && columns.size() == 22) {
					lines.add(columns);
				}
			}
		} catch (Exception e) {
			throw new Exception(fileLocaString);
		}
	}
	
	public Task<Object> importFile() {
		return new Task<Object>() {
			@Override
			protected Object call() {
				try {
					
					AirportDAO airportDAO = new AirportDaoImpl();
					AircraftDAO aircraftDAO = new AircraftDaoImpl();
					ModelDAO modelDAO = new ModelDaoImpl();
					CrewDAO crewDAO = new CrewDaoImpl();
					FlightDAO flightDAO = new FlightDaoImpl();
					
					
					messageString = "Checking airports...\n";
					updateMessage(messageString);
					
					
					// Number of records
					Integer numberOfLines = lines.size() * 2;
					int lineNumber = 1;

					// Get the value of the message string before the loop to add just one line to
					// the loop at time
					String tempMessageString = messageString;

					boolean missingAirports = false;
					for (ArrayList<String> line : lines) {
						messageString = tempMessageString + "checking line " + lineNumber + "...\n";
						updateMessage(messageString);
						
						if (!airportDAO.airportIataExist(line.get(2))) {
							missingAirports = true;
							messageString += "Departure airport " + line.get(2) + " on line " + lineNumber + " doen's exist in the database.\n";
							tempMessageString = messageString;
							updateMessage(messageString);
						}
						if (!airportDAO.airportIataExist(line.get(4))) {
							missingAirports = true;
							messageString += "Arrival airport " + line.get(4) + " on line " + lineNumber + " doen's exist in the database.\n";
							tempMessageString = messageString;
							updateMessage(messageString);
						}
						
						// update progress bar
						updateProgress(lineNumber, numberOfLines.doubleValue());
						lineNumber++;
					}
					
					if (missingAirports) {
						throw new Exception("Missing airports must be inserted manualy before importing this file.\n");
					}
					
					// get preferences to set IFR and PIC/SIC name (case it's blank)
					UserPreferences pref = new UserPreferences();
					
					messageString += "\n\nImporting flights...\n";
					updateMessage(messageString);
					tempMessageString = messageString;
					int realLine = 1;
					for (ArrayList<String> line : lines) {
						lineNumber++;
						// update progress bar
						updateProgress(lineNumber, numberOfLines.doubleValue());
						
						messageString = tempMessageString + "Importing line " + realLine++ + "...\n";
						updateMessage(messageString);
						
						// get the values here, if file changes it's faster to fix...
						String dateString = line.get(0);
						String flightNumberString = line.get(1);
						String depStationString  = line.get(2);
						String depTimeString = line.get(3);
						String arrStationString = line.get(4);
						String arrTimeString = line.get(5).substring(0, 5);
						String modelNameString = line.get(6).toUpperCase();
						String aircraftRegistration = line.get(7).toUpperCase();
						String picNameString = line.get(8).toLowerCase();
						String sicNameString = line.get(9);
						String hoursPisString = line.get(11);
						String hoursSicString = line.get(12);
						String hoursInstructorString = line.get(13);
						String hoursLogbookString = line.get(15);
						String hoursSimulatorString = line.get(16);
						String hoursNightString = line.get(17);
						String takeoffDayString = line.get(18);
						String takeoffNightString = line.get(19);
						String landingDayString = line.get(20);
						String landingNightString = line.get(21);
						
						
						Flight flight = new Flight();
						
						// Start setting date and time....
						SimpleDateFormat dateFormater = new SimpleDateFormat("dd-MM-yyyy HH:mm");
						dateFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
						
						String departureString = dateString + " " + depTimeString;
						Date newDepartureDate = dateFormater.parse(departureString);
						flight.setDepartureDate(newDepartureDate.getTime() / 1000);

						String arrivalString = dateString + " " + arrTimeString;
						Date newArrivalDate = dateFormater.parse(arrivalString);
						flight.setArrivalDate(newArrivalDate.getTime() / 1000);

						// If the departure is before the arrival add 24hs (in seconds) to the value
						if (flight.getArrivalDate() < flight.getDepartureDate()) {
							flight.setArrivalDate(flight.getArrivalDate() + 86400);
						}
						
						
						
						// Get, Create, and set aircraft
						
						// If Aircraft if "" than, it's a SIM duty...  Set Aircraft and Model to FlightNumber (usualy MFTD) or
						// if flight number is not set... "MFTD"
						if (aircraftRegistration.length() < 2) {
							if (flightNumberString.length() > 2) {
								aircraftRegistration = flightNumberString;
								modelNameString = flightNumberString;
							} else {
								aircraftRegistration = "MFTD";
								modelNameString = "MFTD";
							}
						}
						
						Aircraft aircraft = new Aircraft();
						if (!aircraftDAO.aircraftExist(aircraftRegistration.toUpperCase())) {
							Model model = new Model();
							if (!modelDAO.modelExist(modelNameString)) {
								model.setModelName(modelNameString);
								model.setModelGroup(modelNameString);
								
								modelDAO.insertModel(model);
							}
							
							model = modelDAO.getModel(modelNameString);
							
							aircraft.setRegistration(aircraftRegistration);
							aircraft.setAircraftModel(model);
							
							aircraftDAO.insertAircraft(aircraft);
							
						}
						
						aircraft = aircraftDAO.getAircraft(aircraftRegistration);
						flight.setAircraft(aircraft);
						
						// Get Create and Set Crew
						
						// PIC first....
						Crew crewPic = new Crew();
						
						// if Pic is blank...  use default values....
						if (picNameString.length() < 1) {
							// Check if the defaul crew is PIC or SIC
							if (pref.getCrewPosition().equalsIgnoreCase("Pilot in Command")) {
								picNameString = "Self";
							} else {
								picNameString = "Unknown";
							}
						}
						
						if (!crewDAO.crewExist(picNameString)) { 
							crewPic.setCrewName(picNameString);
							crewDAO.insertCrew(crewPic);
						}
						
						crewPic = crewDAO.getCrew(picNameString);
						flight.setCrewPic(crewPic);
						
						// Now SIC....
						
						// if SIC is blank...  use default values....
						if (sicNameString.length() < 1) {
							// Check if the defaul crew is PIC or SIC
							if (pref.getCrewPosition() != "Pilot in Command") {
								sicNameString = "Self (00000 - FO)";
							} else {
								sicNameString = "Unknown (00000 - FO)";
							}
						}
						
						// Lets check if there is more than one line....  and add all the crew to the database
						// The first crew is added to SIC and the others to PrivateNotes
						String[] crewLine = sicNameString.split("\n");
						
						boolean hasSic = false;
						for (String crewInfoString : crewLine) {
							Crew crewSic = new Crew();
							
							String crewName =  crewInfoString.split(" \\(")[0].toLowerCase();
							crewName = WordUtils.capitalize(crewName);
							
							String staffNumber = crewInfoString.split(" \\(")[1].split(" - ")[0];
							String position = crewInfoString.split(" \\(")[1].split(" - ")[1].replace(")", ".");
							
							
							// Add all the crew to database, so you can select later
							if (!crewDAO.crewExist(crewName)) {
								crewSic.setCrewName(crewName);
								crewSic.setComments("Staff Number: "+staffNumber);
								crewDAO.insertCrew(crewSic);
							}
							
							if (!hasSic) {
								hasSic = true;
								crewSic = crewDAO.getCrew(crewName);
								flight.setCrewSic(crewSic);
							}
							
							if (crewLine.length > 1) {
								// if there are more than 1 crew, add all the crew to the private comments so you can select the correct one
								String oldPrivateNotes = flight.getPrivateNotes();
								flight.setPrivateNotes(oldPrivateNotes + "Other crew: " + position + crewName + "\n");
							}
						}
						
						// Add flight number to Private notes...
						String oldPrivateNotes = flight.getPrivateNotes();
						flight.setPrivateNotes(oldPrivateNotes + flightNumberString);
						
						flight.setDepartureAirport(airportDAO.getIata(depStationString));
						flight.setArrivalAirport(airportDAO.getIata(arrStationString));
						
						flight.setTotalTime(TimeConverter.convert("Formated hour (HH:MM)", hoursLogbookString));
						flight.setPicTime(TimeConverter.convert("Formated hour (HH:MM)", hoursPisString));
						flight.setSicTime(TimeConverter.convert("Formated hour (HH:MM)", hoursSicString));
						flight.setInstructorTime(TimeConverter.convert("Formated hour (HH:MM)", hoursInstructorString));
						flight.setFstdTime(TimeConverter.convert("Formated hour (HH:MM)", hoursSimulatorString));
						flight.setNightTime(TimeConverter.convert("Formated hour (HH:MM)", hoursNightString));
						
						// if DEP is deferent that ARR set Xcountry as total time.
						if (depStationString != arrStationString) {
							flight.setXcTime(flight.getTotalTime());
						}
						
						// calculate IFR time
						flight.setIfrTime(flight.getIfrMinutes(flight.getTotalTime()));
						
						
						flight.setTakeOffDay(Integer.valueOf(takeoffDayString));
						flight.setTakeOffNight(Integer.valueOf(takeoffNightString));
						flight.setLandingDay(Integer.valueOf(landingDayString));
						flight.setLandingNight(Integer.valueOf(landingNightString));
						
						
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
						
						
						flightDAO.insertFlight(flight);
						
					}
					
					messageString += "Import finished!\n\n";
					updateMessage(messageString);
					
				} catch (Exception e) {

					messageString += "Import failed!\n\n";
					messageString += e.getMessage();
					updateMessage(messageString);
					throw new IllegalArgumentException();
				}
				return null;
			}

		};
	}
}
