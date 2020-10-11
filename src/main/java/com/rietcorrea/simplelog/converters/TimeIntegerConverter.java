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
public class TimeIntegerConverter extends NumberStringConverter{

    
        @Override
        public Number fromString(String inputTime) {
            try {
                String[] hourMin = inputTime.split(":");
                int hour = Integer.parseInt(hourMin[0]);
                int mins = Integer.parseInt(hourMin[1]);
                return (hour * 60) + mins;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public String toString(Number inputMinutes) {
            int hours = inputMinutes.intValue() / 60; //since both are ints, you get an int
            int minutes = inputMinutes.intValue() % 60;
            return String.format("%02d", hours) + ":" + String.format("%02d", minutes);
        }
    
}
