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
public class LongitudeStringConverter extends NumberStringConverter {

    @Override
    public Number fromString(String inputLongitude) {

        try {
            String cardinal = inputLongitude.substring(0, 2);
            inputLongitude = inputLongitude.substring(2);

            Double longitudeDouble = 0.0;
            String longitudeSplit[] = inputLongitude.split("° ");
            longitudeDouble = Double.valueOf(longitudeSplit[0])
                    + (Double.valueOf(longitudeSplit[1]) / 60);

            if (cardinal.equals("W ")) {
                longitudeDouble = longitudeDouble * -1;
            }

            return longitudeDouble;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString(Number inputLongitude) {
        Double storedLongitude = inputLongitude.doubleValue();
        String cardinal = "E ";
        if (storedLongitude < 0) {
            storedLongitude = storedLongitude * -1;
            cardinal = "W ";
        }

        Integer degrees = storedLongitude.intValue();
        Double minutes = (storedLongitude - degrees) * 60;

        return cardinal + String.format("%03d", degrees) + "° " + String.format("%04.1f", minutes);
    }
}
