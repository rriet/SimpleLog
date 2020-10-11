package com.rietcorrea.constants;

public class StrLen {
	
	private StrLen() {
	    throw new IllegalStateException("Utility class");
	}

	
	public static final int MODEL_NAME_MIN = 2;
	public static final int MODEL_NAME_MAX = 20;
	public static final int MODEL_GROUP = 20;
	public static final int MEDEL_ENGINE_TYPE = 20;
	
	public static final int AIRCRAFT_REGISTRATION_MIN = 2;
	public static final int AIRCRAFT_REGISTRATION_MAX = 20;
	
	public static final int AIRPORT_ICAO = 4;
	public static final int AIRPORT_IATA = 3;
	public static final int AIRPORT_NAME = 100;
	public static final int AIRPORT_CITY = 100;
	public static final int AIRPORT_COUNTRY = 100;
	
	public static final int CREW_NAME_MIN = 2;
	public static final int CREW_NAME_MAX = 100;
	public static final int CREW_EMAIL = 100;
	public static final int CREW_PHONE = 20;
	public static final int CREW_COMMENTS = 500;
	
	public static final int FLIGHT_REMARKS = 500;
	public static final int FLIGHT_PRIVATE_NOTES = 500;
}
