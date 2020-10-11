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
public class LatitudeStringConverter extends NumberStringConverter {

    @Override
    public Number fromString(String inputLatitude) {

        try {
            String cardinal = inputLatitude.substring(0, 2);
            inputLatitude = inputLatitude.substring(2);

            Double latitudeDouble = 0.0;
            String latitudeSplit[] = inputLatitude.split("° ");
            latitudeDouble = Double.valueOf(latitudeSplit[0])
                    + (Double.valueOf(latitudeSplit[1]) / 60);

            if (cardinal.equals("S ")) {
                latitudeDouble = latitudeDouble * -1;
            }
            return latitudeDouble;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString(Number inputLatitude) {
        
        Double storedLatitude = inputLatitude.doubleValue();
        String cardinal = "N ";
        if (storedLatitude < 0) {
            storedLatitude = storedLatitude * -1;
            cardinal = "S ";
        }

        Integer degrees = storedLatitude.intValue();
        Double minutes = (storedLatitude - degrees) * 60;

        return cardinal + String.format("%02d", degrees) + "° " + String.format("%04.1f", minutes);
    }
}
