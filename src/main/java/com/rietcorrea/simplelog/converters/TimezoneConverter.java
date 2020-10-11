/*
*    Simple Log - Pilot logbook software
*    Copyright (C) 2018  Ricardo Brito Riet Correa
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.rietcorrea.simplelog.converters;

import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author riet 
 */
public class TimezoneConverter extends NumberStringConverter { // NO_UCD (unused code)

    @Override
    public Number fromString(String inputTime) {
        try {
            String[] hourMin = inputTime.split(":");
            Double hour = Double.parseDouble(hourMin[0]);
            Double mins = Double.parseDouble(hourMin[1]);
            if (hour >= 0) {
                return hour + (mins / 60);
            } else {
                return hour - (mins / 60);
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString(Number inputHours) {
        Double inputMinutes = inputHours.doubleValue() * 60;
        int hours = inputMinutes.intValue() / 60; //since both are ints, you get an int
        int minutes = inputMinutes.intValue() % 60;
        if(minutes < 0){
            minutes = minutes * -1;
        }
        return String.format("%02d", hours) + ":" + String.format("%02d", minutes);
    }

}
