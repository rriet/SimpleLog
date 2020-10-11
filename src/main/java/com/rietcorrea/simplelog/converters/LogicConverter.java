package com.rietcorrea.simplelog.converters;

public class LogicConverter {
	
	private LogicConverter() {
		throw new IllegalStateException("Utility class");
	}
	public static boolean isBoolean(String boolString) {
		switch (boolString.toLowerCase()) {
			case "1":
			case "true":
			case "sim":
			case "yes":
			case "checked":
				return true;
			default:
				return false;
		}
	}
	
	public static boolean isSimulator(String[] line, int AircraftSimulator, int TimeSimulator, String timeFormat) {
		if (AircraftSimulator >= 0 && AircraftSimulator < line.length && LogicConverter.isBoolean(line[AircraftSimulator])) {
			return true;
		}

		return (TimeSimulator >= 0 && TimeSimulator < line.length && TimeConverter.convert(timeFormat, line[TimeSimulator]) > 0);
	}
}
