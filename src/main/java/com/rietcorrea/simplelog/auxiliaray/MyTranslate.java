package com.rietcorrea.simplelog.auxiliaray;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.rietcorrea.constants.StrEng;
import com.rietcorrea.simplelog.UserPreferences;

public class MyTranslate {
	
	private MyTranslate() {
		throw new IllegalStateException("Utility class");
	}
	
	public static ResourceBundle getResourceBundle() {
		UserPreferences pref = new UserPreferences();
		return ResourceBundle.getBundle(StrEng.DICTIONARY_STRING, pref.getLocale());
	}
	
	public static String text(String messageString) {
		UserPreferences pref = new UserPreferences();
		ResourceBundle messages = ResourceBundle.getBundle(StrEng.DICTIONARY_STRING, pref.getLocale());
		try {
			return messages.getString(messageString);
		} catch (MissingResourceException e) {
			return "Translation Key '" + messageString + "' not found";
		}
		
	}
	
	public static String formated(String messageString, Object[] argumentsObjects) {
		UserPreferences pref = new UserPreferences();
		ResourceBundle messages = ResourceBundle.getBundle(StrEng.DICTIONARY_STRING, pref.getLocale());
		MessageFormat formatter = new MessageFormat("");
		
		formatter.setLocale(Locale.UK);
		
		try {
			formatter.applyPattern(messages.getString(messageString));
			return formatter.format(argumentsObjects);
		} catch (MissingResourceException e) {
			return "Translation Key '" + messageString + "' not found";
		}
		
		
	}
	
	
}
