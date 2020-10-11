package com.rietcorrea.simplelog.csv;

import com.rietcorrea.simplelog.ImportActionController;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;

public class ImporterMcc {

	ImportActionController controller;
	Task<Void> importTask;
	String file;
	
	private static final int DATE = 1;
	private static final String DATE_FORMAT = "DD/MM/YYYY HH:MM";
	private static final int IS_SIMULATOR = 3;
	private static final int FLIGHT_NUMBER = 4;
	private static final int DEPARTURE_AIRPORT = 5;
	private static final int DEPARTURE_TIME = 6;
	private static final int ARRIVAL_AIRPORT = 8;
	private static final int ARRIVAL_TIME = 9;
	private static final int MODEL_NAME = 11;
	private static final int REGISTRATION = 12;
	private static final int PIC_NAME = 14;
	private static final int SIC_NAME = 18;
	private static final int TIME_TOTAL = 29;
	private static final int TIME_PIC = 30;
	private static final int TIME_PICUS = 31;
	private static final int TIME_SIC = 32;
	private static final int TIME_DUAL = 33;
	private static final int TIME_INSTRUCTOR = 34;
	private static final int TIME_NIGHT = 36;
	private static final int TIME_IFR = 38;
	private static final int TIME_XC = 41;
	private static final int TAKEOFF_DAY = 43;
	private static final int TAKEOFF_NIGHT = 44;
	private static final int LANDING_DAY = 45;
	private static final int LANDING_NIGHT = 46;
	private static final int REMARKS = 51;

	public ImporterMcc(String file, ImportActionController controller) {
		this.file = file;
		this.controller = controller;
	}

	public void importFile() {
		checkModels();
	}
	
	private void checkModels() {
		ImporterModel importerModel = new ImporterModel()
				.setName(MODEL_NAME);


		importTask = importerModel.checkFile(file);

		bindController();

		importTask.setOnSucceeded(e -> importModels(importerModel));
		new Thread(importTask).start();
	}
	
	private void importModels(ImporterModel importerModel) {
		importTask = importerModel.importFile(file);
		bindController();

		importTask.setOnSucceeded(f -> checkAircrafts());
		new Thread(importTask).start();
	}
	
	private void checkAircrafts() {
		ImporterAircraft importerAircraft = new ImporterAircraft()
				.setRegistration(REGISTRATION)
				.setSimulator(IS_SIMULATOR)
				.setModel(MODEL_NAME);

		importTask = importerAircraft.checkFile(file);

		bindController();

		importTask.setOnSucceeded(g -> importAircraft(importerAircraft));

		new Thread(importTask).start();
	}
	
	private void importAircraft(ImporterAircraft importerAircraft) {
		importTask = importerAircraft.importFile(file);
		bindController();

		importTask.setOnSucceeded(h -> checkDepartureAirport());
		
		new Thread(importTask).start();
	}
	
	private void checkDepartureAirport() {
//		ImporterAirport importerDepartureAirport = new ImporterAirport()
//				.setIcao(DEPARTURE_AIRPORT)
//				.setIgnoreEmptyIcao(true);
//        
//        importTask = importerDepartureAirport.checkFile(file);
//
//        bindController();
//
//        importTask.setOnSucceeded(e  -> importDepartureAirport(importerDepartureAirport));
//        
//        new Thread(importTask).start();
	}
	
	private void importDepartureAirport(ImporterAirport importerDepartureAirport) {
//		importTask = importerDepartureAirport.importFile(file);
//    	bindController();
//
//        importTask.setOnSucceeded(f -> checkArrivalAirport());
//        new Thread(importTask).start();
	}
	
	private void checkArrivalAirport() {
//		ImporterAirport importerArrivalAirport = new ImporterAirport()
//				.setIcao(ARRIVAL_AIRPORT)
//				.setIgnoreEmptyIcao(true);
		
        
//        importTask = importerArrivalAirport.checkFile(file);
//
//        bindController();
//
//        importTask.setOnSucceeded(e -> importArivalAirport(importerArrivalAirport));
//        
//        new Thread(importTask).start();
	}
	
	private void importArivalAirport(ImporterAirport importerArrivalAirport) {
//		importTask = importerArrivalAirport.importFile(file);
//
//    	bindController();
//
//        importTask.setOnSucceeded(f -> checkPicCrew());
//        new Thread(importTask).start();
	}
	
	private void checkPicCrew() {
		ImporterCrew importerPicCrew = new ImporterCrew().setName(PIC_NAME);
        
        importTask = importerPicCrew.checkFile(file);

        bindController();

        importTask.setOnSucceeded(e -> importPicCrew(importerPicCrew));

        new Thread(importTask).start();
	}
	
	private void importPicCrew(ImporterCrew importerPicCrew) {
		importTask = importerPicCrew.importFile(file);

    	bindController();

        importTask.setOnSucceeded(f -> checkSicCrew());
        new Thread(importTask).start();
	}
	
	private void checkSicCrew() {
		ImporterCrew importerSicCrew = new ImporterCrew().setName(SIC_NAME);
        
        importTask = importerSicCrew.checkFile(file);

        bindController();

        importTask.setOnSucceeded(e -> importSicCrew(importerSicCrew));

        new Thread(importTask).start();
	}
	
	private void importSicCrew(ImporterCrew importerSicCrew) {
		importTask = importerSicCrew.importFile(file);

    	bindController();

        importTask.setOnSucceeded(f -> checkFlight());
        new Thread(importTask).start();
	}
	
	private void checkFlight() {
		ImporterFlight importerFlight = new ImporterFlight()
				.setDate(DATE)
				.setDateFormat(DATE_FORMAT)
				.setDepTime(DEPARTURE_TIME)
				.setArrTime(ARRIVAL_TIME)
				.setRemarks(REMARKS)
				.setPrivateNotes(FLIGHT_NUMBER)
				.setTakeoffDay(TAKEOFF_DAY)
				.setTakeoffNight(TAKEOFF_NIGHT)
				.setLandingDay(LANDING_DAY)
				.setLandingNight(LANDING_NIGHT)
				.setTimeIfr(TIME_IFR)
				.setTimeNight(TIME_NIGHT)
				.setTimeXc(TIME_XC)
				.setTimePic(TIME_PIC)
				.setTimePicus(TIME_PICUS)
				.setTimeSic(TIME_SIC)
				.setTimeDual(TIME_DUAL)
				.setTimeInstructor(TIME_INSTRUCTOR)
				.setTimeTotal(TIME_TOTAL)
				
				.setDepIcao(DEPARTURE_AIRPORT)
				.setArrIcao(ARRIVAL_AIRPORT)
				.setPicName(PIC_NAME)
				.setSicName(SIC_NAME);
        
		
        
        

        bindController();

        importTask.setOnSucceeded(e -> importFlight(importerFlight));

        new Thread(importTask).start();
	}
	
	private void importFlight(ImporterFlight importerFlight) {
		importTask = importerFlight.importFile(file);

    	bindController();

        new Thread(importTask).start();
	}
	

	private void bindController() {
		controller.progressBar.progressProperty().unbind();
		controller.progressBar.progressProperty().bind(importTask.progressProperty());

		importTask.messageProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> 
					controller.addText(newValue));
	}
}
