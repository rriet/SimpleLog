package com.rietcorrea.simplelog.csv;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.rietcorrea.simplelog.LogException;

public class ImporterCsvOpener {
	
	private ImporterCsvOpener() {
		throw new IllegalStateException("Utility class");
	}
	
	public static List<String[]> open(String fileLocation) {
		
		CSVReader reader;
		String firstLine = "";
		
		
		
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileLocation), StandardCharsets.UTF_8))){
			firstLine = bufferedReader.readLine();
		} catch (FileNotFoundException e) {
			LogException.getMessage(e);
		} catch (IOException ex) {
			LogException.getMessage(ex);
		}
		
		// Check if the file is separated by comas or semi-comas
        int comas = StringUtils.countMatches(firstLine, ",");
        int semicolon = StringUtils.countMatches(firstLine, ";");
		
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileLocation), StandardCharsets.UTF_8))){
			
	        // check if the file is separated by comas or semicolons
	        if (comas >= semicolon) {
	        	reader = new CSVReader(bufferedReader);
	        }else {
	        	CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
	        	reader = new CSVReaderBuilder(bufferedReader).withCSVParser(parser).build();
			}
	        
	        String[] nextLine;
	        List<String[]> lineStrings = new ArrayList<>();
	        while ((nextLine = reader.readNext()) != null) {
	        	ArrayList<String> thisLine = new ArrayList<>();
                for (String cell : nextLine) {
                	thisLine.add(cell.trim());
                }
                String[] myArray = new String[thisLine.size()];
                lineStrings.add(thisLine.toArray(myArray));
            }
			reader.close();
			
			
			return lineStrings;
		} catch (Exception e) {
			LogException.getMessage(e);
		}
		return new ArrayList<>();
	}
}
