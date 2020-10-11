package com.rietcorrea.constants;

import java.util.Arrays;
import java.util.List;


public class StrEng {

	private StrEng() {
		throw new IllegalStateException("Utility class");
	}

	// Importing options //

	public static final String DUPLICATED_IGNORE   = "Don't Import Duplicate";
	public static final String DUPLICATED_OVERRIDE = "Override Flight";
	public static final String DUPLICATED_IMPORT   = "Import Duplicated";

	// Date Format
	public static final String DATE_FORMAT_DD_MM_YYYY      = "dd/mm/yyyy HH:MM";
	public static final String DATE_FORMAT_DD_MM_YYYY_LINE = "dd-mm-yyyy HH:MM";
	public static final String DATE_FORMAT_MM_DD_YYYY      = "mm/dd/yyyy HH:MM";
	public static final String DATE_FORMAT_MM_DD_YYYY_LINE = "mm-dd-yyyy HH:MM";
	public static final String DATE_FORMAT_YYYY_MM_DD      = "yyyy/mm/dd HH:MM";
	public static final String DATE_FORMAT_YYYY_MM_DD_LINE = "yyyy-mm-dd HH:MM";
	public static final String DATE_EPOCH_TIME     = "Epoch";

	// Hour Format
	public static final String FORMATTED_HOUR = "Formatted";
	public static final String MINUTES_HOUR   = "Minutes";
	public static final String DECIMAL_HOUR   = "Decimal";

	// Pilot Name
	public static final String PILOT_NAME_UNCHANGED   = "Unchanged";
	public static final String PILOT_NAME_UPPER_FIRST = "Upper Case First";
	public static final String PILOT_NAME_UPPER       = "Upper Case";
	public static final String PILOT_NAME_LOWER       = "Lower Case";

	// END OF - Importing options //

	// Dictionary name
	public static final String DICTIONARY_STRING = "dictionary";

	// Pilot functions
	public static final String       PF_STRING             = "PF";
	public static final String       PNF_STRING            = "PNF";
	public static final String       PF_PNF_STRING         = "PF/PNF";
	public static final String       PNF_PF_STRING         = "PNF/PF";
	public static final String       IRP3_STRING           = "IRP 3";
	public static final String       IRP4_STRING           = "IRP 4";

	public static final List<String> PF_PNF_OPTION_STRINGS = Arrays.asList(PF_STRING, PNF_STRING, PF_PNF_STRING,
			PNF_PF_STRING, IRP3_STRING, IRP4_STRING);

	// Engine types (Must be Capital First Letter only)
	public static final String       ENGINE_TURBOFAN       = "Turbofan";
	public static final String       ENGINE_TURBOPROP      = "Turboprop";
	public static final String       ENGINE_PISTON         = "Piston";
	public static final String       ENGINE_GLIDER         = "Glider";

	public static final List<String> ENGINE_OPTION_STRINGS = Arrays.asList(ENGINE_TURBOFAN, ENGINE_TURBOPROP,
			ENGINE_PISTON, ENGINE_GLIDER);

	// Databade file name
	public static final String DB_FILE_NAME = "SimpleLog.db";

	// Strings for CSV generation

	public static final String SIMPLELOG          = "SimpleLog";
	public static final String FLIGHTS            = "Flights";
	public static final String AIRCRAFTS          = "Aircraft";
	public static final String MODELS             = "Models";
	public static final String AIRPORTS           = "Airports";
	public static final String CREW               = "Crew";
	public static final String QATAR_AIRWAYS_XLSX = "Qatar Airways xlsx";
	public static final String MCC_PILOT_LOG      = "Mcc Pilot Log";
	public static final String AIRPORTS_WEB       = "Download Airports";

	// Export File Field Names

	public static final String DATE_DD_MM_YYYY       = "Date (DD/MM/YYYY)";
	public static final String DEPARTURE_TIME_HH_MM  = "Departure Time (HH:MM)";
	public static final String ARRIVAL_TIME_HH_MM    = "Arrival Time (HH:MM)";

	public static final String DEPARTURE_EPOCH       = "Departure Epoch";
	public static final String ARRIVAL_EPOCH         = "Arrival Epoch";

	public static final String DEPARTURE_ICAO        = "Departure Icao";
	public static final String DEPARTURE_IATA        = "Departure Iata";
	public static final String DEPARTURE_NAME        = "Departure Airport Name";
	public static final String DEPARTURE_CITY        = "Departure City";
	public static final String DEPARTURE_COUNTRY     = "Departure Country";
	public static final String DEPARTURE_LAT         = "Departure Latitude";
	public static final String DEPARTURE_LON         = "Departure Longitude";

	public static final String ARRIVAL_ICAO          = "Arrival Icao";
	public static final String ARRIVAL_IATA          = "Arrival Iata";
	public static final String ARRIVAL_NAME          = "Arrival Airport Name";
	public static final String ARRIVAL_CITY          = "Arrival City";
	public static final String ARRIVAL_COUNTRY       = "Arrival Country";
	public static final String ARRIVAL_LAT           = "Arrival Latitude";
	public static final String ARRIVAL_LON           = "Arrival Longitude";

	public static final String AIRCRAFT_REGISTRATION = "Aircraft Registration";
	public static final String AIRCRAFT_MTOW         = "Aircraft MTOW";
	public static final String AIRCRAFT_SIMULATOR    = "Aircraft Simulator";

	public static final String MAKE_AND_MODEL        = "Model Make & Model";
	public static final String MODEL_GROUP           = "Model Group";
	public static final String MODEL_ENGINE_TYPE     = "Model Engine Type";
	public static final String MODEL_MTOW            = "Model MTOW";
	public static final String MODEL_MULTI_ENGINE    = "Model Multi Engine";
	public static final String MODEL_MULTI_PILOT     = "Model Multi Pilot";
	public static final String MODEL_EFIS            = "Model EFIS";
	public static final String MODEL_SEAPLANE        = "Model Seaplane";

	public static final String PIC_NAME              = "PIC Name";
	public static final String PIC_EMAIL             = "PIC Email";
	public static final String PIC_PHONE             = "PIC Phone";
	public static final String PIC_COMMENTS          = "PIC Comments";

	public static final String SIC_NAME              = "SIC Name";
	public static final String SIC_EMAIL             = "SIC Email";
	public static final String SIC_PHONE             = "SIC Phone";
	public static final String SIC_COMMENTS          = "SIC Comments";

	public static final String PILOT_FUNCTION        = "Pilot Function";
	public static final String FLIGHT_REMARKS        = "Remarks";
	public static final String PRIVATE_NOTES         = "Private notes";

	public static final String TAKEOFF_DAY           = "Takeoff day";
	public static final String TAKEOFF_NIGHT         = "Takeoff night";
	public static final String LANDING_DAY           = "Landing day";
	public static final String LANDING_NIGHT         = "Landing night";

	public static final String TURBOFAN_MINUTES      = "Turbofan Minutes";
	public static final String TURBOFAN_HOURS        = "Turbofan Hours";
	public static final String TURBOFAN_DECIMAL      = "Turbofan Decimal";
	public static final String TURBOPROP_MINUTES     = "Turboprop Minutes";
	public static final String TURBOPROP_HOURS       = "Turboprop Hours";
	public static final String TURBOPROP_DECIMAL     = "Turboprop Decimal";
	public static final String PISTON_MINUTES        = "Piston Minutes";
	public static final String PISTON_HOURS          = "Piston Hours";
	public static final String PISTON_DECIMAL        = "Piston Decimal";
	public static final String GLIDER_MINUTES        = "Glider Minutes";
	public static final String GLIDER_HOURS          = "Glider Hours";
	public static final String GLIDER_DECIMAL        = "Glider Decimal";
	public static final String EFIS_MINUTES          = "EFIS Minutes";
	public static final String EFIS_HOURS            = "EFIS Hours";
	public static final String EFIS_DECIMAL          = "EFIS Decimal";
	public static final String SEL_MINUTES           = "SEL Minutes";
	public static final String SEL_HOURS             = "SEL Hours";
	public static final String SEL_DECIMAL           = "SEL Decimal";
	public static final String MEL_MINUTES           = "MEL Minutes";
	public static final String MEL_HOURS             = "MEL Hours";
	public static final String MEL_DECIMAL           = "MEL Decimal";
	public static final String SES_MINUTES           = "SES Minutes";
	public static final String SES_HOURS             = "SES Hours";
	public static final String SES_DECIMAL           = "SES Decimal";
	public static final String MES_MINUTES           = "MES Minutes";
	public static final String MES_HOURS             = "MES Hours";
	public static final String MES_DECIMAL           = "MES Decimal";
	public static final String MULTI_PILOT_MINUTES   = "Multi-Pilot Minutes";
	public static final String MULTI_PILOT_HOURS     = "Multi-Pilot Hours";
	public static final String MULTI_PILOT_DECIMAL   = "Multi-Pilot Decimal";

	public static final String IFR_MINUTES           = "IFR Minutes";
	public static final String IFR_HOURS             = "IFR Hours";
	public static final String IFR_DECIMAL           = "IFR Decimal";
	public static final String NIGHT_MINUTES         = "Night Minutes";
	public static final String NIGHT_HOURS           = "Night Hours";
	public static final String NIGHT_DECIMAL         = "Night Decimal";
	public static final String XC_MINUTES            = "Corss country Minutes";
	public static final String XC_HOURS              = "Corss country Hours";
	public static final String XC_DECIMAL            = "Corss country Decimal";
	public static final String PIC_MINUTES           = "PIC Minutes";
	public static final String PIC_HOURS             = "PIC Hours";
	public static final String PIC_DECIMAL           = "PIC Decimal";
	public static final String PICUS_MINUTES         = "PICUS Minutes";
	public static final String PICUS_HOURS           = "PICUS Hours";
	public static final String PICUS_DECIMAL         = "PICUS Decimal";
	public static final String SIC_MINUTES           = "SIC Minutes";
	public static final String SIC_HOURS             = "SIC Hours";
	public static final String SIC_DECIMAL           = "SIC Decimal";
	public static final String DUAL_MINUTES          = "Dual Minutes";
	public static final String DUAL_HOURS            = "Dual Hours";
	public static final String DUAL_DECIMAL          = "Dual Decimal";
	public static final String INSTRUCTOR_MINUTES    = "Instructor Minutes";
	public static final String INSTRUCTOR_HOURS      = "Instructor Hours";
	public static final String INSTRUCTOR_DECIMAL    = "Instructor Decimal";
	public static final String SIMULATOR_MINUTES     = "Simulator Minutes";
	public static final String SIMULATOR_HOURS       = "Simulator Hours";
	public static final String SIMULATOR_DECIMAL     = "Simulator Decimal";
	public static final String TOTAL_MINUTES         = "Total Minutes";
	public static final String TOTAL_HOURS           = "Total Hours";
	public static final String TOTAL_DECIMAL         = "Total Decimal";

	public static final String AIRPORT_ICAO          = "Icao";
	public static final String AIRPORT_IATA          = "Iata";
	public static final String AIRPORT_NAME          = "Name";
	public static final String AIRPORT_CITY          = "City";
	public static final String AIRPORT_COUNTRY       = "Country";
	public static final String AIRPORT_LAT           = "Latitude";
	public static final String AIRPORT_LON           = "Longitude";

	public static final String CREW_NAME             = "Name";
	public static final String CREW_EMAIL            = "Email";
	public static final String CREW_PHONE            = "Phone";
	public static final String CREW_COMMENTS         = "Comments";

	// EXPORT HEATHER
	public static final String MODEL_EXPORT_HEATHER    = MAKE_AND_MODEL + "," + MODEL_GROUP + "," + MODEL_ENGINE_TYPE
			+ "," + MODEL_MTOW + "," + MODEL_MULTI_ENGINE + "," + MODEL_MULTI_PILOT + "," + MODEL_EFIS + ","
			+ MODEL_SEAPLANE;

	public static final String AIRCRAFT_EXPORT_HEATHER = AIRCRAFT_REGISTRATION + "," + AIRCRAFT_MTOW + ","
			+ AIRCRAFT_REGISTRATION + "," + MODEL_EXPORT_HEATHER;


	public static final String CREW_EXPORT_HEATHER = CREW_NAME + "," + CREW_EMAIL + "," + CREW_PHONE + ","
			+ CREW_COMMENTS;


	public static final String AIRPORT_EXPORT_HEATHER = AIRPORT_ICAO + "," + AIRPORT_IATA + "," + AIRPORT_NAME + ","
			+ AIRPORT_CITY + "," + AIRPORT_COUNTRY + "," + AIRPORT_LAT + "," + AIRPORT_LON;


	public static final String SIMPLELOG_EXPORT_HEATHER = DATE_DD_MM_YYYY + "," + DEPARTURE_TIME_HH_MM + ","
			+ ARRIVAL_TIME_HH_MM + "," + DEPARTURE_EPOCH + "," + ARRIVAL_EPOCH + "," + DEPARTURE_ICAO + ","
			+ DEPARTURE_IATA + "," + DEPARTURE_NAME + "," + DEPARTURE_CITY + "," + DEPARTURE_COUNTRY + ","
			+ DEPARTURE_LAT + "," + DEPARTURE_LON + "," + ARRIVAL_ICAO + "," + ARRIVAL_IATA + "," + ARRIVAL_NAME + ","
			+ ARRIVAL_CITY + "," + ARRIVAL_COUNTRY + "," + ARRIVAL_LAT + "," + ARRIVAL_LON + "," +

			AIRCRAFT_EXPORT_HEATHER + "," +

			PIC_NAME + "," + PIC_EMAIL + "," + PIC_PHONE + "," + PIC_COMMENTS + "," + SIC_NAME + "," + SIC_EMAIL + ","
			+ SIC_PHONE + "," + SIC_COMMENTS + "," + PILOT_FUNCTION + "," + FLIGHT_REMARKS + "," + PRIVATE_NOTES + ","
			+ TAKEOFF_DAY + "," + TAKEOFF_NIGHT + "," + LANDING_DAY + "," + LANDING_NIGHT + "," + IFR_MINUTES + ","
			+ NIGHT_MINUTES + "," + XC_MINUTES + "," + PIC_MINUTES + "," + PICUS_MINUTES + "," + SIC_MINUTES + ","
			+ DUAL_MINUTES + "," + INSTRUCTOR_MINUTES + "," + SIMULATOR_MINUTES + "," + TOTAL_MINUTES;


	public static final String FLIGHT_EXPORT_HEATHER = DATE_DD_MM_YYYY + "," + DEPARTURE_TIME_HH_MM + ","
			+ ARRIVAL_TIME_HH_MM + "," + DEPARTURE_EPOCH + "," + ARRIVAL_EPOCH + "," +

			DEPARTURE_ICAO + "," + DEPARTURE_IATA + "," + DEPARTURE_NAME + "," + DEPARTURE_CITY + ","
			+ DEPARTURE_COUNTRY + "," + DEPARTURE_LAT + "," + DEPARTURE_LON + "," +

			ARRIVAL_ICAO + "," + ARRIVAL_IATA + "," + ARRIVAL_NAME + "," + ARRIVAL_CITY + "," + ARRIVAL_COUNTRY + ","
			+ ARRIVAL_LAT + "," + ARRIVAL_LON + "," +

			AIRCRAFT_EXPORT_HEATHER + "," +

			TURBOFAN_MINUTES + "," + TURBOFAN_HOURS + "," + TURBOFAN_DECIMAL + "," + TURBOPROP_MINUTES + ","
			+ TURBOPROP_HOURS + "," + TURBOPROP_DECIMAL + "," + PISTON_MINUTES + "," + PISTON_HOURS + ","
			+ PISTON_DECIMAL + "," + GLIDER_MINUTES + "," + GLIDER_HOURS + "," + GLIDER_DECIMAL + "," + EFIS_MINUTES
			+ "," + EFIS_HOURS + "," + EFIS_DECIMAL + "," + SEL_MINUTES + "," + SEL_HOURS + "," + SEL_DECIMAL + ","
			+ MEL_MINUTES + "," + MEL_HOURS + "," + MEL_DECIMAL + "," + SES_MINUTES + "," + SES_HOURS + ","
			+ SES_DECIMAL + "," + MES_MINUTES + "," + MES_HOURS + "," + MES_DECIMAL + "," + MULTI_PILOT_MINUTES + ","
			+ MULTI_PILOT_HOURS + "," + MULTI_PILOT_DECIMAL + "," +

			PIC_NAME + "," + PIC_EMAIL + "," + PIC_PHONE + "," + PIC_COMMENTS + "," + SIC_NAME + "," + SIC_EMAIL + ","
			+ SIC_PHONE + "," + SIC_COMMENTS + "," + PILOT_FUNCTION + "," + FLIGHT_REMARKS + "," + PRIVATE_NOTES + "," +

			TAKEOFF_DAY + "," + TAKEOFF_NIGHT + "," + LANDING_DAY + "," + LANDING_NIGHT + "," +

			IFR_MINUTES + "," + IFR_HOURS + "," + IFR_DECIMAL + "," + NIGHT_MINUTES + "," + NIGHT_HOURS + ","
			+ NIGHT_DECIMAL + "," + XC_MINUTES + "," + XC_HOURS + "," + XC_DECIMAL + "," + PIC_MINUTES + "," + PIC_HOURS
			+ "," + PIC_DECIMAL + "," + PICUS_MINUTES + "," + PICUS_HOURS + "," + PICUS_DECIMAL + "," + SIC_MINUTES
			+ "," + SIC_HOURS + "," + SIC_DECIMAL + "," + DUAL_MINUTES + "," + DUAL_HOURS + "," + DUAL_DECIMAL + ","
			+ INSTRUCTOR_MINUTES + "," + INSTRUCTOR_HOURS + "," + INSTRUCTOR_DECIMAL + "," + SIMULATOR_MINUTES + ","
			+ SIMULATOR_HOURS + "," + SIMULATOR_DECIMAL + "," + TOTAL_MINUTES + "," + TOTAL_HOURS + "," + TOTAL_DECIMAL;
}

