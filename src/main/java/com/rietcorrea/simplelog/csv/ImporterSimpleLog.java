package com.rietcorrea.simplelog.csv;

import com.rietcorrea.simplelog.ImportActionController;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;


public class ImporterSimpleLog {
	
	private static final String DATE_FORMAT = "Epoch time";
	private static final int DEPARTURE_EPOCH = 4;
	private static final int ARRIVAL_EPOCH = 5;
	
	private static final int DEPARTURE_AIRPORT_ICAO = 6;
	private static final int DEPARTURE_AIRPORT_IATA = 7;
	private static final int DEPARTURE_AIRPORT_NAME = 8;
	private static final int DEPARTURE_AIRPORT_CITY = 9;
	private static final int DEPARTURE_AIRPORT_COUNTRY = 10;
	private static final int DEPARTURE_AIRPORT_LAT = 11;
	private static final int DEPARTURE_AIRPORT_LON = 12;
	
	private static final int ARRIVAL_AIRPORT_ICAO = 13;
	private static final int ARRIVAL_AIRPORT_IATA = 14;
	private static final int ARRIVAL_AIRPORT_NAME = 15;
	private static final int ARRIVAL_AIRPORT_CITY = 16;
	private static final int ARRIVAL_AIRPORT_COUNTRY = 17;
	private static final int ARRIVAL_AIRPORT_LAT = 18;
	private static final int ARRIVAL_AIRPORT_LON = 19;
	
	private static final int REGISTRATION = 20;
	private static final int AIRCRAFT_MTOW = 21;
	private static final int AIRCRAFT_IS_SIM = 22;
	
	private static final int AIRCRAFT_MODEL = 23;
	private static final int MODEL_GROUP = 24;
	private static final int MODEL_ENGINE_TYPE = 25;
	private static final int MODEL_MTOW = 26;
	private static final int MODEL_MULTI_ENGINE = 27;
	private static final int MODEL_MULTI_PILOT = 28;
	private static final int MODEL_EFIS = 29;
	private static final int MODEL_SEAPLANE = 30;
	
	private static final int PIC_NAME = 31;
	private static final int PIC_EMAIL = 32;
	private static final int PIC_PHONE = 33;
	private static final int PIC_COMMENTS = 34;
	
	private static final int SIC_NAME = 35;
	private static final int SIC_EMAIL = 36;
	private static final int SIC_PHONE = 37;
	private static final int SIC_COMMENTS = 38;
	
	private static final int PILOT_FUNCTION = 39;
	
	private static final int REMARKS = 40;
	private static final int PRIVATE_NOTES = 41;
	
	private static final int TAKEOFF_DAY = 42;
	private static final int TAKEOFF_NIGHT = 43;
	private static final int LANDING_DAY = 44;
	private static final int LANDING_NIGHT = 45;
	
	private static final int TIME_IFR = 46;
	private static final int TIME_NIGHT = 47;
	private static final int TIME_XC = 48;
	private static final int TIME_PIC = 49;
	private static final int TIME_PICUS = 50;
	private static final int TIME_SIC = 51;
	private static final int TIME_DUAL = 52;
	private static final int TIME_INSTRUCTOR = 53;
	private static final int TIME_SIMULATOR = 54;
	private static final int TIME_TOTAL = 55;
	private static final String TIME_FORMAT = MyTranslate.text("TimeFormatInteger");
	
	private boolean recalculateTakeoffLandings = false;
	private boolean recalculateIfr             = false;
	private boolean recalculateNight           = false;
	private boolean recalculateXc              = false;
	private String  crewNameFormat			   = MyTranslate.text("PilotNameUnchanged");
	
	ImportActionController controller;
	Task<Void> importTask;
	String file;
	
	public ImporterSimpleLog(String file, ImportActionController controller) {
		this.file = file;
		this.controller = controller;
	}
	
	public ImporterSimpleLog setRecalculateTakeoffLandings(boolean recalculateTakeoffLandings) {
		this.recalculateTakeoffLandings = recalculateTakeoffLandings;
		return this;
	}
	
	public ImporterSimpleLog setRecalculateIfr(boolean recalculateIfr) {
		this.recalculateIfr = recalculateIfr;
		return this;
	}
	
	public ImporterSimpleLog setRecalculateNight(boolean recalculateNight) {
		this.recalculateNight = recalculateNight;
		return this;
	}
	
	public ImporterSimpleLog setRecalculateXc(boolean recalculateXc) {
		this.recalculateXc = recalculateXc;
		return this;
	}
	
	public ImporterSimpleLog setCrewNameFormat(String crewNameFormat) {
		this.crewNameFormat = crewNameFormat;
		return this;
	}

	public void importFile() {
		checkModels();
	}
	
	private void checkModels() {
		ImporterModel importerModel = new ImporterModel()
				.setName(AIRCRAFT_MODEL)
				.setGroup(MODEL_GROUP)
				.setMtow(MODEL_MTOW)
				.setEngineType(MODEL_ENGINE_TYPE)
				.setMultiEngine(MODEL_MULTI_ENGINE)
				.setMultiPilot(MODEL_MULTI_PILOT)
				.setEfis(MODEL_EFIS)
				.setSeaplane(MODEL_SEAPLANE);
				
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
				.setSimulator(AIRCRAFT_IS_SIM)
				.setMtow(AIRCRAFT_MTOW)
				.setModel(AIRCRAFT_MODEL);
		
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
//				.setIcao(DEPARTURE_AIRPORT_ICAO)
//				.setIata(DEPARTURE_AIRPORT_IATA)
//				.setName(DEPARTURE_AIRPORT_NAME)
//				.setCity(DEPARTURE_AIRPORT_CITY)
//				.setCountry(DEPARTURE_AIRPORT_COUNTRY)
//				.setLat(DEPARTURE_AIRPORT_LAT)
//				.setLon(DEPARTURE_AIRPORT_LON);
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
//				.setIcao(ARRIVAL_AIRPORT_ICAO)
//				.setIata(ARRIVAL_AIRPORT_IATA)
//				.setName(ARRIVAL_AIRPORT_NAME)
//				.setCity(ARRIVAL_AIRPORT_CITY)
//				.setCountry(ARRIVAL_AIRPORT_COUNTRY)
//				.setLat(ARRIVAL_AIRPORT_LAT)
//				.setLon(ARRIVAL_AIRPORT_LON);
//        
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
		ImporterCrew importerPicCrew = new ImporterCrew()
				.setName(PIC_NAME)
				.setNameFormat(crewNameFormat)
				.setEmail(PIC_EMAIL)
				.setPhone(PIC_PHONE)
				.setComments(PIC_COMMENTS);
        
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
		ImporterCrew importerSicCrew = new ImporterCrew()
				.setName(SIC_NAME)
				.setNameFormat(crewNameFormat)
				.setEmail(SIC_EMAIL)
				.setPhone(SIC_PHONE)
				.setComments(SIC_COMMENTS);
        
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
				.setDateFormat(DATE_FORMAT)
				.setDepTime(DEPARTURE_EPOCH)
				.setArrTime(ARRIVAL_EPOCH)
				.setRemarks(REMARKS)
				.setPrivateNotes(PRIVATE_NOTES)
				.setPfPnf(PILOT_FUNCTION)
				.setTakeoffDay(TAKEOFF_DAY)
				.setTakeoffNight(TAKEOFF_NIGHT)
				.setLandingDay(LANDING_DAY)
				.setLandingNight(LANDING_NIGHT)
				.setRecalculateTakeoffLandings(recalculateTakeoffLandings)
				.setTimeIfr(TIME_IFR)
				.setRecalculateIfr(recalculateIfr)
				.setTimeNight(TIME_NIGHT)
				.setRecalculateNight(recalculateNight)
				.setTimeXc(TIME_XC)
				.setRecalculateXc(recalculateXc)
				.setTimePic(TIME_PIC)
				.setTimePicus(TIME_PICUS)
				.setTimeSic(TIME_SIC)
				.setTimeDual(TIME_DUAL)
				.setTimeInstructor(TIME_INSTRUCTOR)
				.setTimeSimulator(TIME_SIMULATOR)
				.setTimeTotal(TIME_TOTAL)
				.setDepIcao(DEPARTURE_AIRPORT_ICAO)
				.setArrIcao(ARRIVAL_AIRPORT_ICAO)
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