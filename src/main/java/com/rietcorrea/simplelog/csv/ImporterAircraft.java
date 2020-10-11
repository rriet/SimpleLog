package com.rietcorrea.simplelog.csv;

import java.util.List;

import com.rietcorrea.constants.StrLen;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.converters.LogicConverter;
import com.rietcorrea.simplelog.converters.TimeConverter;
import com.rietcorrea.simplelog.database.AircraftDAO;
import com.rietcorrea.simplelog.database.AircraftDaoImpl;
import com.rietcorrea.simplelog.database.ModelDAO;
import com.rietcorrea.simplelog.database.ModelDaoImpl;
import com.rietcorrea.simplelog.objects.Aircraft;
import com.rietcorrea.simplelog.objects.Model;

import javafx.concurrent.Task;


public class ImporterAircraft {

	// Counter for the progress bar
	private Integer numberOfLines;
	private int     lineNumber              = 1;

	private int     registration;
	private int     simulator               = -1;
	private int     simulatorTime           = -1;
	private String  timeFormat              = "";
	private int     mtow                    = -1;
	private int     model                   = -1;
	private boolean ignoreRegistrationError = true;
	private boolean ignoreMtowError         = true;
	private boolean override                = false;

	public ImporterAircraft setRegistration(int registration) {
		this.registration = registration - 1;
		return this;
	}

	public ImporterAircraft setIgnoreRegistrationError(boolean ignoreRegistrationError) {
		this.ignoreRegistrationError = ignoreRegistrationError;
		return this;
	}

	public ImporterAircraft setSimulator(int simulator) {
		this.simulator = simulator - 1;
		return this;
	}

	public ImporterAircraft setSimulatorTime(int simulatorTime) {
		this.simulatorTime = simulatorTime;
		return this;
	}
	
	public ImporterAircraft setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
		return this;
	}

	public ImporterAircraft setMtow(int mtow) {
		this.mtow = mtow - 1;
		return this;
	}

	public ImporterAircraft setIgnoreMtowError(boolean ignoreMtowError) {
		this.ignoreMtowError = ignoreMtowError;
		return this;
	}

	public ImporterAircraft setModel(int model) {
		this.model = model - 1;
		return this;
	}

	public ImporterAircraft setOverride(boolean override) {
		this.override = override;
		return this;
	}

	public Task<Void> checkFile(String file) {
		return new Task<Void>() {
			@Override
			protected Void call() throws InterruptedException {

				List<String[]> lines = ImporterCsvOpener.open(file);
				int numOfCols = lines.get(0).length;
				lines.remove(0);

				lineNumber = 1;
				for (String[] line : lines) {
					if (line.length != numOfCols) {
						failImporting(MyTranslate.formated("IrregularNumberOfColumns", new Object[] {
								lineNumber + 1 }));
						throw new IllegalArgumentException();
					}
				}

				// Reset lineNumber
				lineNumber = 1;

				updateMessage(MyTranslate.text("ImportingFile") + MyTranslate.text("CheckingSelection"));
				// Time to update message...
				Thread.sleep(100);

				// check if "Name" has been selected
				if (registration < 0) {
					failImporting(MyTranslate.text("YouMustSelectRegistration"));
					throw new IllegalArgumentException();
				}

				updateMessage(MyTranslate.text("CheckImportOk") + MyTranslate.text("CheckImportFileFormat"));
				// Time to update message...
				Thread.sleep(100);


				// Number of records x2 to account for the double loop
				numberOfLines = lines.size() * 2;

				for (String[] line : lines) {
					// update progress bar
					updateProgress(lineNumber, numberOfLines.doubleValue());
					lineNumber++;

					if (!checkRegistration(line)) {
						throw new IllegalArgumentException();
					}

					if (!checkMtow(line)) {
						throw new IllegalArgumentException();
					}
				}

				updateMessage(MyTranslate.text("CheckImportOk"));
				return null;
			}

			private void failImporting(String failString) {
				updateMessage(MyTranslate.text("ImportFailed") + failString);
			}

			private void warningImporting(String failString) {
				updateMessage(MyTranslate.formated("ImportWarning", new Object[] {
						failString }));
			}

			private boolean checkRegistration(String[] line) {
				if (registration >= 0 && registration < line.length) {
					String registrationString = line[registration];

					if (LogicConverter.isSimulator(line, simulator, simulatorTime, timeFormat) 
							&& registrationString.length() == 0 && ignoreRegistrationError) {
						registrationString = "SIM";
					}


					if ((registrationString.length() < StrLen.AIRCRAFT_REGISTRATION_MIN
							|| registrationString.length() > StrLen.AIRCRAFT_REGISTRATION_MAX)
							&& !ignoreRegistrationError) {

						failImporting(MyTranslate.formated("InvalidAircraftRegistration", new Object[] {
								lineNumber, StrLen.AIRCRAFT_REGISTRATION_MIN, StrLen.AIRCRAFT_REGISTRATION_MAX }));
						return false;

					} else if (line[registration].length() < StrLen.AIRCRAFT_REGISTRATION_MIN
							|| line[registration].length() > StrLen.AIRCRAFT_REGISTRATION_MAX) {
						warningImporting(MyTranslate.formated("InvalidAircraftRegistration", new Object[] {
								lineNumber, StrLen.CREW_NAME_MIN, StrLen.MODEL_NAME_MAX }));
					}
				}
				return true;
			}

			private boolean checkMtow(String[] line) {
				if (mtow >= 0 && mtow < line.length) {
					try {
						Integer.valueOf(line[mtow]);
					} catch (NumberFormatException e) {
						if (!ignoreMtowError) {
							failImporting(MyTranslate.formated("AircraftMtowShouldbeNumber", new Object[] {
									lineNumber }));
							return false;
						} else {
							warningImporting(MyTranslate.formated("AircraftMtowShouldbeNumber", new Object[] {
									lineNumber }));
						}
					}
				}
				return true;
			}
		};
	}
	
	public Task<Void> importFile(String file) {
		return new Task<Void>() {
			@Override
			protected Void call() throws InterruptedException {

				List<String[]> lines = ImporterCsvOpener.open(file);
				lines.remove(0);

				// connect to database....
				AircraftDAO aircraftDAO = new AircraftDaoImpl();
				ModelDAO modelDAO = new ModelDaoImpl();

				updateMessage(MyTranslate.text("ImportingAircraft"));
				// Time to update message...
				Thread.sleep(100);

				for (String[] line : lines) {
					// update progress bar
					updateProgress(lineNumber, numberOfLines.doubleValue());
					lineNumber++;

					// trim first to check if exist
					String registrationString = line[registration].substring(0,
							Math.min(line[registration].length(), StrLen.AIRCRAFT_REGISTRATION_MAX));

					Aircraft aircraft = new Aircraft();
					
					if (LogicConverter.isSimulator(line, simulator, simulatorTime, timeFormat)  
							&& registrationString.length() == 0 && ignoreRegistrationError) {
						registrationString = "SIM";
						aircraft.setSimulator(true);
					}

					// only insert Aircraft that dont exist OR are selected for override
					boolean aircraftExist = aircraftDAO.aircraftExist(registrationString);
					if (!aircraftExist || override) {

						if (aircraftExist) {
							aircraft = aircraftDAO.getAircraft(registrationString);
							aircraft.resetAircraft();
						}

						aircraft.setRegistration(registrationString);

						// Set Email if selection was made
						if (simulator >= 0) {
							aircraft.setSimulator(LogicConverter.isBoolean(line[simulator]));
						}

						if (mtow >= 0) {
							try {
								aircraft.setAircraftMtow(Integer.valueOf(line[mtow]));
							} catch (NumberFormatException e) {
								// ignore errors....
							}
						}

						// trim first to match the database
						String modelNameString = line[model].substring(0,
								Math.min(line[model].length(), StrLen.MODEL_NAME_MAX));

						// get model by string.... Must exist since we inset model first...
						Model aircraftModel = modelDAO.getModel(modelNameString);

						// set aircraft model
						aircraft.setAircraftModel(aircraftModel);


						// if Crew exist (and the code reaches here) is becaus override is TRUE
						if (aircraftExist) {
							aircraftDAO.updateAircraft(aircraft);
						} else {
							aircraftDAO.insertAircraft(aircraft);
						}
					}

				}
				updateMessage(MyTranslate.text("FinishedImportingAircrafts"));
				return null;
			}
		};
	}


}
