package com.rietcorrea.simplelog.csv;

import java.util.List;

import org.apache.commons.text.WordUtils;

import com.rietcorrea.constants.StrEng;
import com.rietcorrea.constants.StrLen;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.database.CrewDAO;
import com.rietcorrea.simplelog.database.CrewDaoImpl;
import com.rietcorrea.simplelog.objects.Crew;

import javafx.concurrent.Task;

public class ImporterCrew {
	// Counter for the progress bar
	private Integer numberOfLines;
	private int lineNumber = 1;

	private int name = - 1;
	private int email = - 1;
	private int phone = - 1;
	private int comments = - 1;
	private boolean ignoreNameError = true;
	private String nameFormat = MyTranslate.text("PilotNameUnchanged");
	private boolean ignoreEmailError = true;
	private boolean ignorePhoneError = true;
	private boolean ignoreCommentsError = true;
	private boolean override = false;
	
	public ImporterCrew setName(int name) {
		this.name = name - 1;
		return this;
	}
	
	public ImporterCrew setIgnoreNameError(boolean ignoreNameError) {
		this.ignoreNameError = ignoreNameError;
		return this;
	}
	
	public ImporterCrew setNameFormat(String nameFormat) {
		this.nameFormat = nameFormat;
		return this;
	}
	
	public ImporterCrew setEmail(int email) {
		this.email = email - 1;
		return this;
	}
	
	public ImporterCrew setIgnoreEmailError(boolean ignoreEmailError) {
		this.ignoreEmailError = ignoreEmailError;
		return this;
	}
	
	public ImporterCrew setPhone(int phone) {
		this.phone = phone - 1;
		return this;
	}
	
	public ImporterCrew setIgnorePhoneError(boolean ignorePhoneError) {
		this.ignorePhoneError = ignorePhoneError;
		return this;
	}
	
	public ImporterCrew setComments(int comments) {
		this.comments = comments - 1;
		return this;
	}
	
	public ImporterCrew setIgnoreCommentsError(boolean ignoreCommentsError) {
		this.ignoreCommentsError = ignoreCommentsError;
		return this;
	}
	
	public ImporterCrew setOverride(boolean override) {
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
						failImporting(MyTranslate.formated("IrregularNumberOfColumns", new Object[] {lineNumber + 1}));
						throw new IllegalArgumentException();
					}
				}
				
				// Reset lineNumber
				lineNumber = 1;
			
				updateMessage(MyTranslate.text("ImportingFile") + MyTranslate.text("CheckingSelection"));
				// Time to update message...
				Thread.sleep(100);

				// check if "Name" has been selected
				if (name < 0) {
					failImporting(MyTranslate.text("YouMustSelectCrew"));
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

					// add the line number before to compensate for the removal of the title line
					lineNumber++;
					
					if (!checkName(line)) {
						throw new IllegalArgumentException();
					}
					
					if (!checkFieldLenght(line, email, StrEng.CREW_EMAIL, StrLen.CREW_EMAIL, ignoreEmailError)) {
						throw new IllegalArgumentException();
					}
					
					if (!checkFieldLenght(line, phone, StrEng.CREW_PHONE, StrLen.CREW_PHONE, ignorePhoneError)) {
						throw new IllegalArgumentException();
					}
					
					if (!checkFieldLenght(line, comments, StrEng.CREW_COMMENTS, StrLen.CREW_COMMENTS, ignoreCommentsError)) {
						throw new IllegalArgumentException();
					}				}
				
				updateMessage(MyTranslate.text("CheckImportOk"));
				return null;
			}
			
			private void failImporting(String failString) {
				updateMessage(MyTranslate.text("ImportFailed") + failString);
			}
			
			private void warningImporting(String failString) {
				updateMessage(MyTranslate.formated("ImportWarning", new Object[] {failString}));
			}
			
			private boolean checkName(String[] line) {
				if (name >= 0 && name < line.length) {
					if ((line[name].length() < StrLen.CREW_NAME_MIN  || line[name].length() > StrLen.CREW_NAME_MAX) && !ignoreNameError) {
						failImporting(MyTranslate.formated("InvalidCrewName", new Object[] {lineNumber, StrLen.CREW_NAME_MIN, StrLen.CREW_NAME_MAX}));
						return false;
					} else if (line[name].length() < StrLen.CREW_NAME_MIN  || line[name].length() > StrLen.CREW_NAME_MAX) {
						warningImporting(MyTranslate.formated("InvalidCrewName", new Object[] {lineNumber, StrLen.CREW_NAME_MIN, StrLen.CREW_NAME_MAX}));
					}
				}
				return true;
			}
			
			private boolean checkFieldLenght(String[] line, int fieldIndex, String fieldName, int maxLenght, boolean ignoreError) {
				// Check if the field is selected AND less than array boundary...
				if (fieldIndex >= 0 && fieldIndex < line.length) {
					// Check if the MORE is less than maximum AND ignore is NOT selected 
					if (line[fieldIndex].length() > maxLenght && !ignoreError) {
						failImporting(MyTranslate.formated("InvalidFieldLenght", new Object[] {fieldName, lineNumber, maxLenght}));
						return false;
					} else if(line[fieldIndex].length() > maxLenght) {
						warningImporting(MyTranslate.formated("InvalidFieldLenght", new Object[] {fieldName, lineNumber, maxLenght}));
					}
				}
				return true;
			}
			

		};
	}
	
	public Task<Void> importFile(String file) {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				
				List<String[]> lines = ImporterCsvOpener.open(file);
				lines.remove(0);
				
				// connect to database to check airports....
				CrewDAO crewDAO = new CrewDaoImpl();
				
				updateMessage(MyTranslate.text("ImportingPilots"));
				// Time to update message...
				Thread.sleep(100);
				
				for (String[] line : lines) {

					// update progress bar
					updateProgress(lineNumber, numberOfLines.doubleValue());

					lineNumber++;
					
					// trim first to check if exist
					String crewNameString = line[name].substring(0, Math.min(line[name].length(), StrLen.CREW_NAME_MAX));
					
						// IF UNCHANGED do nothing...... 
					if (nameFormat.equalsIgnoreCase(MyTranslate.text("PilotNameUpperFirst"))) {
						crewNameString = crewNameString.toLowerCase();
						crewNameString = WordUtils.capitalize(crewNameString);
					} else if (nameFormat.equalsIgnoreCase(MyTranslate.text("PilotNameUppercase"))) {
						crewNameString = crewNameString.toUpperCase();
					} else if (nameFormat.equalsIgnoreCase(MyTranslate.text("PilotNameLowercase"))) {
						crewNameString = crewNameString.toLowerCase();
					}

					// only insert Crew that dont exist OR are selected for override
					boolean crewExist = crewDAO.crewExist(crewNameString);
					if (!crewExist || override) {
						
						Crew crew = new Crew();
						if (crewExist) {
							crew = crewDAO.getCrew(crewNameString);
							crew.resetCrew();
						}
						
						crew.setCrewName(crewNameString);
						
						// Set Email if selection was made
						if (email >= 0) {
							crew.setEmail(line[email]);
						}

						// Set Phone if selection was made
						if (phone >= 0) {
							crew.setPhone(line[phone]);
						}
						
						// Set Comments if selection was made
						if (comments >= 0) {
							crew.setComments(line[comments]);
						}

						// if Crew exist (and the code reaches here) is becaus override is TRUE
						if (crewExist) {
							crewDAO.updateCrew(crew);
						} else {
							crewDAO.insertCrew(crew);
						}
					}
				}
				updateMessage(MyTranslate.text("FinishedImportingCrew"));
				return null;
			}

		};
	}
}
