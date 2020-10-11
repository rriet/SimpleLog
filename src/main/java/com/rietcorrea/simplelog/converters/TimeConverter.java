package com.rietcorrea.simplelog.converters;

import java.util.regex.Pattern;

import com.rietcorrea.constants.StrEng;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;

public class TimeConverter {
	
	private TimeConverter() {
		throw new IllegalStateException("Utility class");
	}
	
	public static boolean isValid(String format, String time) {
		
		if (format.equalsIgnoreCase(StrEng.MINUTES_HOUR)) {
			try {
				Integer.parseInt(time);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else if (format.equalsIgnoreCase(StrEng.DECIMAL_HOUR)) {
			try {
				Double.parseDouble(time);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else if (format.equalsIgnoreCase(StrEng.FORMATTED_HOUR)) {
			return Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]").matcher(time).matches();
		}
		return false;
	}

	public static Integer convert(String format, String time) {
		
		if (format.equalsIgnoreCase(MyTranslate.text("TimeFormatInteger"))) {
			try {
				return Integer.parseInt(time);
			} catch (NumberFormatException e) {
				return 0;
			}
		} else if (format.equalsIgnoreCase(MyTranslate.text("TimeFormatDecimal"))) {
			try {
				Double hours = Double.parseDouble(time);
				return (int) Math.round(hours * 60);
			} catch (NumberFormatException e) {
				return 0;
			}
		} else if (format.equalsIgnoreCase(MyTranslate.text("TimeFormatHour"))) {
			try {
				String[] hourMin = time.split(":");
				int hour = Integer.parseInt(hourMin[0]);
				int mins = Integer.parseInt(hourMin[1]);
				return (hour * 60) + mins;
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}
}
