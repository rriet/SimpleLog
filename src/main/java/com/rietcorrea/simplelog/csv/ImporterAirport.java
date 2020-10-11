package com.rietcorrea.simplelog.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rietcorrea.constants.AppSettings;
import com.rietcorrea.simplelog.LogException;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.database.AirportDAO;
import com.rietcorrea.simplelog.database.AirportDaoImpl;
import com.rietcorrea.simplelog.objects.Airport;
import javafx.concurrent.Task;


public class ImporterAirport {

	// Counter for the progress bar
	private Integer numberOfLines;
	private int     lineNumber = 1;

	private int     icao       = 0;
	private int     iata       = 1;
	private int     name       = 2;
	private int     city       = 3;
	private int     country    = 4;
	private int     lat        = 5;
	private int     lon        = 6;

	private boolean override   = false;

	public ImporterAirport(boolean override) {
		this.override = override;
	}

	public Task<Void> downloadImport() {
		return new Task<Void>() {
			@Override
			protected Void call() throws InterruptedException {

				updateMessage("Downloading file.\n");
				updateProgress(0, 100);

				try (BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(new URL(AppSettings.AIRPORT_URL).openStream()))) {

					CSVReader csvReader = new CSVReader(bufferedReader);


					String[] nextLine;
					List<String[]> lines = new ArrayList<>();
					while ((nextLine = csvReader.readNext()) != null) {
						ArrayList<String> thisLine = new ArrayList<>();
						for (String cell : nextLine) {
							thisLine.add(cell.trim());
						}
						String[] myArray = new String[thisLine.size()];
						lines.add(thisLine.toArray(myArray));
					}

					csvReader.close();

					int numOfCols = 7;
					lines.remove(0);
					lineNumber = 1;

					// Number of records x2 to account for the double loop
					numberOfLines = lines.size() * 2;

					updateMessage("Checking file.\n");

					for (String[] line : lines) {
						// update progress bar
						updateProgress(lineNumber++, numberOfLines.doubleValue());

						if (line.length != numOfCols) {
							updateMessage("File error: Invalid number of columns");
							throw new IllegalArgumentException();
						}

						// Remove spaces from ICAO
						String icaoCodeString = line[icao].replaceAll("\\s+", "");

						// Check if ICAO has 4 characters
						if (icaoCodeString.length() != 4) {
							updateMessage(MyTranslate.formated("InvalidIcaoLenght", new Object[] {
									lineNumber }));
							throw new IllegalArgumentException();
						}

						// Check if Latitude is a valid double
						try {
							Double.valueOf(line[lat]);
						} catch (NumberFormatException e) {
							updateMessage(MyTranslate.formated("InvalidLatitude", new Object[] {
									lineNumber }));
							throw new IllegalArgumentException();
						}

						// Check if Longitude is a valid double
						try {
							Double.valueOf(line[lon]);
						} catch (NumberFormatException e) {
							updateMessage(MyTranslate.formated("InvalidLongitude", new Object[] {
									lineNumber }));
							throw new IllegalArgumentException();
						}
					}

					updateMessage("File ok, importing.\n");

					// Connect to DB
					AirportDAO airportDao = new AirportDaoImpl();

					for (String[] line : lines) {
						updateProgress(lineNumber++, numberOfLines.doubleValue());

						// Remove spaces from ICAO (again)
						String icaoCodeString = line[icao].replaceAll("\\s+", "");

						// only insert airports that dont exist OR are selected for override
						boolean airportExist = airportDao.airportExist(icaoCodeString);
						if (!airportExist || override) {
							Airport airport = new Airport();

							if (airportExist) {
								airport = airportDao.getIcao(icaoCodeString);
								airport.resetAirport();
							}

							// Set ICAO (Already verified)
							airport.setIcao(icaoCodeString);

							airport.setIata(line[iata]);
							airport.setAirportName(line[name]);
							airport.setAirportCity(line[city]);
							airport.setAirportCountry(line[country]);
							airport.setLatitudeDoubleString(line[lat]);
							airport.setLongitudeDoubleString(line[lon]);

							// if Airport exist (and the code reaches here) is becaus override is TRUE
							if (airportExist) {
								airportDao.updateAirport(airport);
							} else {
								airportDao.insertAirport(airport);
							}
						}


					}
				} catch (IOException | CsvValidationException e) {
					LogException.getMessage(e);
				}
				updateMessage("\n\nFinished importing airports.");

				return null;
			}
		};
	}
}
