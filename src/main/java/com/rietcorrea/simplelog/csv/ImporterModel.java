package com.rietcorrea.simplelog.csv;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.text.WordUtils;

import com.rietcorrea.constants.StrEng;
import com.rietcorrea.constants.StrLen;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.converters.LogicConverter;
import com.rietcorrea.simplelog.database.ModelDAO;
import com.rietcorrea.simplelog.database.ModelDaoImpl;
import com.rietcorrea.simplelog.objects.Model;

import javafx.concurrent.Task;

public class ImporterModel {

	// Counter for the progress bar
	private Integer numberOfLines;
	private int lineNumber = 1;

	// column number for each field
	private int name = -1;
	private int group = -1;
	private int mtow = -1;
	private int engineType = -1;
	private int multiEngine = -1;
	private int multiPilot = -1;
	private int efis = -1;
	private int seaplane = -1;
	
	private boolean ignoreNameError = true;
	private boolean ignoreGroupError = true;
	private boolean ignoreMtowError = true;
	private boolean ignoreEngineTypeError = true;
	private boolean override = false;
	
	public ImporterModel setName(int name) {
		this.name = name - 1;
		return this;
	}
	
	public ImporterModel setIgnoreNameError(boolean ignoreNameError) {
		this.ignoreNameError = ignoreNameError;
		return this;
	}
	
	public ImporterModel setGroup(int group) {
		this.group = group - 1;
		return this;
	}
	
	public ImporterModel setIgnoreGroupError(boolean ignoreGroupError) {
		this.ignoreGroupError = ignoreGroupError;
		return this;
	}
	
	public ImporterModel setMtow(int mtow) {
		this.mtow = mtow - 1;
		return this;
	}
	
	public ImporterModel setIgnoreMtowError(boolean ignoreMtowError) {
		this.ignoreMtowError = ignoreMtowError;
		return this;
	}
	
	public ImporterModel setEngineType(int engineType) {
		this.engineType = engineType - 1;
		return this;
	}
	
	public ImporterModel setIgnoreEngineTypeError(boolean ignoreEngineTypeError) {
		this.ignoreEngineTypeError = ignoreEngineTypeError;
		return this;
	}
	
	public ImporterModel setMultiEngine(int multiEngine) {
		this.multiEngine = multiEngine - 1;
		return this;
	}
	
	public ImporterModel setMultiPilot(int multiPilot) {
		this.multiPilot = multiPilot - 1;
		return this;
	}
	
	public ImporterModel setEfis(int efis) {
		this.efis = efis - 1;
		return this;
	}
	
	public ImporterModel setSeaplane(int seaplane) {
		this.seaplane = seaplane - 1;
		return this;
	}
	
	public ImporterModel setOverride(boolean override) {
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
					lineNumber++;
				}
				
				// Reset lineNumber
				lineNumber = 1;

				updateMessage(MyTranslate.text("ImportingFile") + MyTranslate.text("CheckingSelection"));
				// Time to update message...
				Thread.sleep(100);
				
				// check if "Name" has been selected
				if (name < 0) {
					failImporting(MyTranslate.text("YouMustSelectModel"));
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
					
					if (!checkModelName(line)) {
						throw new IllegalArgumentException();
					}
					
					if (!checkModelGroup(line)) {
						throw new IllegalArgumentException();
					}
					
					if (!checkMtow(line)) {
						throw new IllegalArgumentException();
					}
					
					if (!checkEngineType(line)) {
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
				updateMessage(MyTranslate.formated("ImportWarning", new Object[] {failString}));
			}
			
			private boolean checkModelName(String[] line) {
				if (name >= 0 && name < line.length) {
					if ((line[name].length() < StrLen.MODEL_NAME_MIN  || line[name].length() > StrLen.MODEL_NAME_MAX) && !ignoreNameError) {
						failImporting(MyTranslate.formated("InvalidModelName", 
								new Object[] {lineNumber, StrLen.CREW_NAME_MIN, StrLen.MODEL_NAME_MAX}));
						return false;
					} else if (line[name].length() < StrLen.MODEL_NAME_MIN  || line[name].length() > StrLen.MODEL_NAME_MAX) {
						warningImporting(MyTranslate.formated("InvalidModelName", 
								new Object[] {lineNumber, StrLen.MODEL_NAME_MIN, StrLen.MODEL_NAME_MAX}));
					}
				}
				return true;
			}
			
			private boolean checkModelGroup(String[] line) {
				// Check if the field is selected AND less than array boundary...
				if (group >= 0 && group < line.length) {
					// Check if the MORE is less than maximum AND ignore is NOT selected 
					if (line[group].length() > StrLen.MODEL_GROUP && !ignoreGroupError) {
						failImporting(MyTranslate.formated("InvalidFieldLenght", new Object[] 
								{StrEng.MODEL_GROUP, lineNumber, StrLen.MODEL_GROUP}));
						return false;
					} else if(line[group].length() > StrLen.MODEL_GROUP) {
						warningImporting(MyTranslate.formated("InvalidFieldLenght", new Object[] 
								{StrEng.MODEL_GROUP, lineNumber, StrLen.MODEL_GROUP}));
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
							failImporting(MyTranslate.formated("ModelMtowShouldbeNumber", new Object[] {lineNumber}));
							return false;
						} else {
							warningImporting(MyTranslate.formated("ModelMtowShouldbeNumber", new Object[] {lineNumber}));
						}
					}
				}
				return true;
			}
			
			private boolean checkEngineType(String[] line) {
				// Check lenght and Fail or Trim
				if (engineType >= 0 && engineType < line.length) {
					
					// Make fist letter capital
					String engineTypeString = line[engineType].toLowerCase();
					engineTypeString = WordUtils.capitalize(engineTypeString);
					
					// Set a list of engine types that can be inported.
					List<String> engineOptions = StrEng.ENGINE_OPTION_STRINGS;
					
					
					Arrays.asList(
							StrEng.ENGINE_TURBOFAN, 
							StrEng.ENGINE_TURBOPROP, 
							StrEng.ENGINE_PISTON, 
							StrEng.ENGINE_GLIDER);

					if (!engineOptions.contains(engineTypeString) && !ignoreEngineTypeError) {
						failImporting(MyTranslate.formated("InvalidEngineType", 
								new Object[] { lineNumber,
												StrEng.ENGINE_TURBOFAN, 
												StrEng.ENGINE_TURBOPROP, 
												StrEng.ENGINE_PISTON, 
												StrEng.ENGINE_GLIDER} ));
						
						return false;
								
								
					} else if (!engineOptions.contains(engineTypeString)) {
						warningImporting(MyTranslate.formated("InvalidEngineType", 
								new Object[] { lineNumber,
										StrEng.ENGINE_TURBOFAN, 
										StrEng.ENGINE_TURBOPROP, 
										StrEng.ENGINE_PISTON, 
										StrEng.ENGINE_GLIDER} ));
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
				ModelDAO modelDAO = new ModelDaoImpl();
				
				updateMessage(MyTranslate.text("ImportingModel"));
				// Time to update message...
				Thread.sleep(100);

				for (String[] line : lines) {

					// update progress bar
					updateProgress(lineNumber, numberOfLines.doubleValue());
					lineNumber++;
					
					// trim first to check if exist
					String modelNameString = line[name].substring(0, Math.min(line[name].length(), StrLen.MODEL_NAME_MAX));
					
					// only insert Crew that dont exist OR are selected for override
					boolean modelExist = modelDAO.modelExist(modelNameString);
					if (!modelExist || override) {
						
						Model model = new Model();
						if (modelExist) {
							model = modelDAO.getModel(modelNameString);
							model.resetModel();
						}
						
						model.setModelName(modelNameString);
						
						// Set Email if selection was made
						if (group >= 0) {
							model.setModelGroup(line[group]);
						} else {
							model.setModelGroup(modelNameString);
						}
						
						if (mtow >= 0) {
							try {
								model.setMtow(Integer.valueOf(line[mtow]));
							} catch (NumberFormatException e) {
								// ignore errors....
							}
						}
						
						// Set Engine Type if selection was made
						// if it doesn't exist default = turbofan
						if (engineType >= 0) {
							String engineTypeString = line[engineType];
							engineTypeString = WordUtils.capitalize(engineTypeString);
							
							switch (engineTypeString) {
								case StrEng.ENGINE_TURBOPROP:
									model.setEngineType(StrEng.ENGINE_TURBOPROP);
									break;
								case StrEng.ENGINE_PISTON:
									model.setEngineType(StrEng.ENGINE_PISTON);
									break;
								case StrEng.ENGINE_GLIDER:
									model.setEngineType(StrEng.ENGINE_GLIDER);
									break;
								default:
									model.setEngineType(StrEng.ENGINE_TURBOFAN);
							}
						}

						// Set Multi-Engine if selection was made
						if (multiEngine >= 0) {
							model.setMultiEngine(LogicConverter.isBoolean(line[multiEngine]));
						}

						// Set Multi-Pilot if selection was made
						if (multiPilot >= 0) {
							model.setMultiPilot(LogicConverter.isBoolean(line[multiPilot]));
						}

						// Set Model if selection was made
						if (efis >= 0) {
							model.setEfis(LogicConverter.isBoolean(line[efis]));
						}
						
						// Set Model if selection was made
						if (seaplane >= 0) {
							model.setSeaplane(LogicConverter.isBoolean(line[seaplane]));
						}
						
						// if Crew exist (and the code reaches here) is becaus override is TRUE
						if (modelExist) {
							modelDAO.updateModel(model);
						} else {
							modelDAO.insertModel(model);
						}
					}
				}
				updateMessage(MyTranslate.text("FinishedImportingModel"));
				return null;
			}

		};
	}
	
	
}
