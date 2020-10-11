/*
 * Copyright (C) 2018 Ricardo Riet Correa - rietcorrea.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.rietcorrea.simplelog;

import com.rietcorrea.simplelog.objects.Flight;
import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author riet
 */
public class FlightCalculations {

    private final double latDep;
    private final double longDep;
    private final double latArr;
    private final double longArr;
    private final long depTime;
    private final long arrTime;
    private final int sunriseDep;
    private final int sunsetDep;
    private final int sunriseArr;
    private final int sunsetArr;
    private final int flightTime;
    private final double flightDistance;
    private final boolean dayTakeOff;
    private final boolean dayLanding;
    private int nightTime;

    public FlightCalculations(Flight flight) {
        this.latDep = flight.getDepartureAirport().getLatitude();
        this.longDep = flight.getDepartureAirport().getLongitude();
        this.latArr = flight.getArrivalAirport().getLatitude();
        this.longArr = flight.getArrivalAirport().getLongitude();
        this.depTime = flight.getDepartureDate();
        this.arrTime = flight.getArrivalDate();

        // Calculate sunrise and sunset
        sunriseDep = calcSunriseUTC(depTime, latDep, longDep);
        sunsetDep = calcSunsetUTC(depTime, latDep, longDep);
        sunriseArr = calcSunriseUTC(arrTime, latArr, longArr);
        sunsetArr = calcSunsetUTC(arrTime, latArr, longArr);

        // Calculate if takeoff and landing are day time
        dayTakeOff = isDay(depTime, sunriseDep, sunsetDep, latDep);
        dayLanding = isDay(arrTime, sunriseArr, sunsetArr, latArr);

        //Calculate flight times and distance
        flightTime = flightTime(depTime, arrTime);
        flightDistance = flightDistance(latDep, longDep, latArr, longArr);

        calculatePreciseNightTime();
    }

    // <editor-fold defaultstate="collapsed" desc="Getters">
    /**
     * Purpose: return sunrise time at departure airport
     *
     * @return number of minutes from midnight UTC for sunrise
     */  
    public int getSunriseDep() {
        return sunriseDep;
    }
    /**
     * Purpose: return sunset time at departure airport
     *
     * @return number of minutes from midnight UTC for sunset
     */
    public int getSunsetDep() {
        return sunsetDep;
    }
    /**
     * Purpose: return sunrise time at arrival airport
     *
     * @return number of minutes from midnight UTC for sunrise
     */
    public int getSunriseArr() {
        return sunriseArr;
    }
    /**
     * Purpose: return sunset time at arrival airport
     *
     * @return number of minutes from midnight UTC for sunset
     */
    public int getSunsetArr() {
        return sunsetArr;
    }
    /**
     * Purpose: return night time
     *
     * @return number of minutes of night flight
     */
    public int getNightTime() {
        return nightTime;
    }
    /**
     * Purpose: check if the takeoff was during day
     *
     * @return true if the takeoff was during day time
     */
    public boolean isDayTakeOff() {
        return dayTakeOff;
    }
    /**
     * Purpose: check if the landing was during day
     *
     * @return true if the landing was during day time
     */
    public boolean isDayLanding() {
        return dayLanding;
    }
    /**
     * Purpose: get the total flight time
     *
     * @return total flight time in minutes
     */
    public int getFlightTime() {
        return flightTime;
    }
    /**
     * Purpose: get the total flight distance
     *
     * @return total flight distance in nautical miles
     */
    public double getFlightDistance() {
        return flightDistance;
    }
    /**
     * Purpose: return formated sunrise time
     *
     * @return formated string with sunrise time
     */
    public String getSunriseTime() {
        String flightHour = Integer.toString(sunriseDep / 60);
        while (flightHour.length() < 2) {
            flightHour = "0" + flightHour;
        }
        String flightMinute = Integer.toString(sunriseDep % 60);
        while (flightMinute.length() < 2) {
            flightMinute = "0" + flightMinute;
            
        }
        return flightHour + ":" + flightMinute;
    }

    /**
     * Purpose: return formated sunset time
     *
     * @return formated string with sunset time
     */
    public String getSunsetTime() {
        String flightHour = Integer.toString(sunsetDep / 60);
        while (flightHour.length() < 2) {
            flightHour = "0" + flightHour;
        }
        String flightMinute = Integer.toString(sunsetDep % 60);
        while (flightMinute.length() < 2) {
            flightMinute = "0" + flightMinute;
        }
        return flightHour + ":" + flightMinute;
    }
    // </editor-fold>
    
    /**
     *Purpose: determines if it is day at a specific latitude and time
     * @param epoch Date in epoch (/1000)
     * @param sunrise
     * @param sunset latitude of observer in degrees
     * @param latitude latitude of observer in degrees
     * 
     * @return true if it is day
     */
    private boolean isDay(long epoch, int sunrise, int sunset, double latitude) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(epoch * 1000);

        int doy = calendar.get(Calendar.DAY_OF_YEAR);

        int timeMinutes = minutesOfDay(calendar);
        
        if (sunrise == 0 && sunset == 0) {
            // if Northern hemisphere and spring or summer, OR  
            // if Southern hemisphere and fall or winter, 
            // it's day.

        	// Return true if the statement is correct
        	return ((latitude > 66.4) && (doy > 79) && (doy < 267)) || ((latitude < -66.4) && ((doy < 83) || (doy > 263)));
        }

        // Determines if the sunrise UTC at location is before sunset
        if (sunrise < sunset) {
        	// if time is before sunrise FALSE (night)
            if (timeMinutes < sunrise) {
                return false;
            } else {
            	// if time after sunset FALSE (night)
                return timeMinutes < sunset;
            }
        } else {
        	// IF sunset is before sunrise
            if (timeMinutes < sunset) {
            	// return TURE if time is before sunset
                return true;
            } else {
            	// return TURE if time is after sunrise
            	return timeMinutes > sunrise;
            }
        }
    }
    
    /**
     * Purpose: calculate number of minutes from midnight
     *
     * @param cal Calendar date
     * @return minutes from midnight
     */
    private int minutesOfDay(Calendar cal) {
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        return hour * 60 + minutes;
    }

    /**
     * Purpose: calculate the difference between two times
     *
     * @param chockOff Departure time in seconds from Epoch
     * @param chockOn Departure time in seconds from Epoch
     * @return flight Time in minutes
     */
    private int flightTime(long chockOff, long chockOn) {
        Long longTime = (chockOn - chockOff) / 60;
        return longTime.intValue();
    }

    /**
     * Purpose: calculate the distance between two waypoints
     *
     * @param latDep Departure latitude
     * @param longDep Departure Longitude
     * @param latArr Arrival latitude
     * @param longArr Arrival longitude
     * @return distance in nautical miles
     */
    private double flightDistance(double latDep, double longDep, double latArr, double longArr) {
        double dLat = Math.toRadians(latArr - latDep);
        double dLon = Math.toRadians(longArr - longDep);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(latDep)) * Math.cos(Math.toRadians(latArr))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return 3443.89849 * c;
    }

    /**
     * Purpose: calculate the required bearing to fly a great circle between two points
     *
     * @param latDep first point latitude
     * @param longDep first point Longitude
     * @param latArr second point latitude
     * @param longArr second point longitude
     * @return Bearing in degrees from North of earth
     */
    private double flightBearing(double latDep, double longDep, double latArr, double longArr) {
        double latDepR = Math.toRadians(latDep);
        double longDepR = Math.toRadians(longDep);
        double latArrR = Math.toRadians(latArr);
        double longArrR = Math.toRadians(longArr);
        double y = Math.sin(longArrR - longDepR) * Math.cos(latArrR);
        double x = Math.cos(latDepR) * Math.sin(latArrR) - Math.sin(latDepR) * Math.cos(latArrR) * Math.cos(longArrR - longDepR);
        double bearing = Math.toDegrees(Math.atan2(y, x));
        if (bearing < 0) {
            bearing = 360 + bearing;
        }
        return bearing;
    }

    /**
     * *Purpose: calculate location of the next waypoint
     *
     * @param latDep first point latitude
     * @param longDep first point Longitude
     * @param bearing flight bearing
     * @param distance distance flown
     * @return array containing latitude / longitude of the waypoint
     */
    private double[] flightNextWaypoint(double latDep, double longDep, double bearing, double distance) {
        double latDepR = Math.toRadians(latDep);
        double longDepR = Math.toRadians(longDep);
        double bearingR = Math.toRadians(bearing);

        double radius = 3443.89849;
        double latNext = Math.asin(Math.sin(latDepR) * Math.cos(distance / radius)
                + Math.cos(latDepR) * Math.sin(distance / radius) * Math.cos(bearingR));
        double longNext = longDepR + Math.atan2(Math.sin(bearingR) * Math.sin(distance / radius) * Math.cos(latDepR),
                Math.cos(distance / radius) - Math.sin(latDepR) * Math.sin(latNext));
        latNext = Math.toDegrees(latNext);
        longNext = Math.toDegrees(longNext);

        if (longNext > 180) {
            longNext -= 360;
        }
        if (longNext < -180) {
            longNext += 360;
        }

        return new double[]{latNext, longNext};

    }

    /**
     *Purpose: calculate night time of a flight
     */
    private void calculatePreciseNightTime() {
        if (dayTakeOff) {
            nightTime = 0;
        } else {
            nightTime = 1;
        }

        Calendar now = Calendar.getInstance();
        now.setTimeZone(TimeZone.getTimeZone("UTC"));
        now.setTimeInMillis(depTime * 1000);
        Calendar arr = Calendar.getInstance();
        arr.setTimeZone(TimeZone.getTimeZone("UTC"));
        arr.setTimeInMillis(arrTime * 1000);

        double latNow = latDep;
        double longNow = longDep;
        double milesPerMinute = flightDistance / flightTime;

        boolean isDay = isDayTakeOff();
        boolean isDayNow;

        while (now.before(arr)) {
            now.add(Calendar.MINUTE, 1);

            double bearing = flightBearing(latNow, longNow, latArr, longArr);
            double[] coordNow = flightNextWaypoint(latNow, longNow, bearing, milesPerMinute);
            latNow = coordNow[0];
            longNow = coordNow[1];

            int sunriseNow = calcSunriseUTC(now.getTimeInMillis() / 1000, latNow, longNow);
            int sunsetNow = calcSunsetUTC(now.getTimeInMillis() / 1000, latNow, longNow);

            // Is it still night or day?
            isDayNow = isDay(now.getTimeInMillis() / 1000, sunriseNow, sunsetNow, latNow);
            if (isDay == isDayNow && !isDay && now.before(arr)) {
                nightTime += 1;
            }
            isDay = isDayNow;
        }
    }

    /**
     *Purpose: calculate the julian day of the most recent sunrise
     *      starting from the given day at the given location on earth
     * @param d Date in epoch (/1000)
     * @param latitude latitude of observer in degrees
     * @param longitude longitude of observer in degrees
     * 
     * @return julian day of the most recent sunrise
     */
    public double[] findRecentSunrise(long d, double latitude, double longitude) { // NO_UCD (unused code)
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(d * 1000);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        double julianDay = calcJD(year, month, day);
        double time = calcSunriseUTC(d, latitude, longitude);
        while (time <= 0) {
            cal.add(Calendar.DAY_OF_MONTH, -1);

            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH) + 1;
            day = cal.get(Calendar.DAY_OF_MONTH);

            julianDay = calcJD(year, month, day);

            time = calcSunriseUTC(cal.getTimeInMillis() / 1000, latitude, longitude);
        }
        
        return new double[] {julianDay, time};
    }

    /**
     *Purpose: calculate the Universal Coordinated Time (UTC) of sunrise
     *      for the given day at the given location on earth
     * @param d Date in epoch (/1000)
     * @param latitude latitude of observer in degrees
     * @param longitude longitude of observer in degrees
     * 
     * @return time in minutes from zero Z
     */
    private int calcSunriseUTC(long d, double latitude, double longitude) {
        longitude = longitude * -1; // Positive is east instead of standard

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(d * 1000);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        double julianDay = calcJD(year, month, day);

        double t = calcTimeJulianCent(julianDay);

        /*
         // *** Find the time of solar noon at the location, and use
         //     that declination. This is better than start of the 
         //     Julian day
         */
        double noonmin = calcSolNoonUTC(t, longitude);
        double tnoon = calcTimeJulianCent(julianDay + noonmin / 1440.0);

        // *** First pass to approximate sunrise (using solar noon)
        double eqTime = calcEquationOfTime(tnoon);
        double solarDec = calcSunDeclination(tnoon);
        double hourAngle = calcHourAngleSunrise(latitude, solarDec);

        double delta = longitude - radToDeg(hourAngle);
        double timeDiff = 4 * delta;	// in minutes of time
        double timeUTC = 720 + timeDiff - eqTime;	// in minutes

        // *** Second pass includes fractional jday in gamma calc
        double newt = calcTimeJulianCent(calcJDFromJulianCent(t) + timeUTC / 1440.0);
        eqTime = calcEquationOfTime(newt);
        solarDec = calcSunDeclination(newt);
        hourAngle = calcHourAngleSunrise(latitude, solarDec);
        delta = longitude - radToDeg(hourAngle);
        timeDiff = 4 * delta;
        timeUTC = 720 + timeDiff - eqTime; // in minutes

        // if next day, remove 24 hours to get time from UTC
        if (timeUTC > 1440) {
            timeUTC -= 1440;
        }
        // if previous day, add 24 hours to get time from UTC
        if (timeUTC < 0) {
            timeUTC += 1440;
        }
        return (int) Math.round(timeUTC);
    }
    
    /**
     *Purpose: calculate the Universal Coordinated Time (UTC) of sunset
     *      for the given day at the given location on earth
     * @param d Date in epoch (/1000)
     * @param latitude latitude of observer in degrees
     * @param longitude longitude of observer in degrees
     * 
     * @return time in minutes from zero Z
     */
    private int calcSunsetUTC(long d, double latitude, double longitude) {
        longitude = longitude * -1; // Positive is east instead of standard

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(d * 1000);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        double julianDay = calcJD(year, month, day);

        double t = calcTimeJulianCent(julianDay);

        // *** Find the time of solar noon at the location, and use
        //     that declination. This is better than start of the 
        //     Julian day
        double noonmin = calcSolNoonUTC(t, longitude);
        double tnoon = calcTimeJulianCent(julianDay + noonmin / 1440.0);

        // First calculates sunrise and approx length of day
        double eqTime = calcEquationOfTime(tnoon);
        double solarDec = calcSunDeclination(tnoon);
        double hourAngle = calcHourAngleSunset(latitude, solarDec);

        double delta = longitude - radToDeg(hourAngle);
        double timeDiff = 4 * delta;
        double timeUTC = 720 + timeDiff - eqTime;

        // first pass used to include fractional day in gamma calc
        double newt = calcTimeJulianCent(calcJDFromJulianCent(t) + timeUTC / 1440.0);
        eqTime = calcEquationOfTime(newt);
        solarDec = calcSunDeclination(newt);
        hourAngle = calcHourAngleSunset(latitude, solarDec);

        delta = longitude - radToDeg(hourAngle);
        timeDiff = 4 * delta;
        timeUTC = 720 + timeDiff - eqTime; // in minutes

        // if next day, remove 24 hours to get time from UTC
        if (timeUTC > 1440) {
            timeUTC -= 1440;
        }
        // if previous day, add 24 hours to get time from UTC
        if (timeUTC < 0) {
            timeUTC += 1440;
        }
        return (int) Math.round(timeUTC);
    }

    /**
     *Purpose: calculate the hour angle of the sun at sunset for the latitude
     * @param latitude latitude of observer in degrees
     * @param solarDec declination angle of sun in degrees
     * 
     * @return hour angle of sunset in radians
     */
    private double calcHourAngleSunset(double latitude, double solarDec) {
        double latRad = degToRad(latitude);
        double sdRad = degToRad(solarDec);

        Math.cos(degToRad(90.833));
		Math.cos(latRad);
		Math.cos(sdRad);
		Math.tan(latRad);
		Math.tan(sdRad);

		// in radians
		return -(Math.acos(Math.cos(degToRad(90.833)) / (Math.cos(latRad) * Math.cos(sdRad)) - Math.tan(latRad) * Math.tan(sdRad)));
    }

    /**
     *Purpose: calculate the hour angle of the sun at sunrise for the latitude
     * @param latitude latitude of observer in degrees
     * @param solarDec declination angle of sun in degrees
     * 
     * @return hour angle of sunrise in radians
     */
    private double calcHourAngleSunrise(double latitude, double solarDec) {
        double latRad = degToRad(latitude);
        double sdRad = degToRad(solarDec);

        Math.cos(degToRad(90.833));
		Math.cos(latRad);
		Math.cos(sdRad);
		Math.tan(latRad);
		Math.tan(sdRad);

		// in radians
		return (Math.acos(Math.cos(degToRad(90.833)) / (Math.cos(latRad) * Math.cos(sdRad)) - Math.tan(latRad) * Math.tan(sdRad)));	
    }

    /**
     *Purpose: calculate the declination of the sun
     * @param jCenturies number of Julian centuries since J2000.0
     * 
     * @return sun's declination in degrees
     */
    private double calcSunDeclination(double jCenturies) {
        double e = calcObliquityCorrection(jCenturies);
        double lambda = calcSunApparentLong(jCenturies);

        double sint = Math.sin(degToRad(e)) * Math.sin(degToRad(lambda));
        return radToDeg(Math.asin(sint)); // in degrees
    }

    /**
     *Purpose: calculate the apparent longitude of the sun
     * @param jCenturies number of Julian centuries since J2000.0
     * 
     * @return sun's apparent longitude in degrees
     */
    private double calcSunApparentLong(double jCenturies) {
        double o = calcSunTrueLong(jCenturies);

        double omega = 125.04 - 1934.136 * jCenturies;
        return o - 0.00569 - 0.00478 * Math.sin(degToRad(omega));		// in degrees
    }

    /**
     *Purpose: calculate the true longitude of the sun
     * @param jCenturies number of Julian centuries since J2000.0
     * 
     * @return sun's true longitude in degrees
     */
    private double calcSunTrueLong(double jCenturies) {
        double l0 = calcGeomMeanLongSun(jCenturies);
        double c = calcSunEqOfCenter(jCenturies);

        return l0 + c;		// in degrees
    }

    /**
     *Purpose: calculate the equation of center for the sun
     * @param jCenturies number of Julian centuries since J2000.0
     * 
     * @return in degrees
     */
    private double calcSunEqOfCenter(double jCenturies) {
        double m = calcGeomMeanAnomalySun(jCenturies);

        double mrad = degToRad(m);
        double sinm = Math.sin(mrad);
        double sin2m = Math.sin(mrad + mrad);
        double sin3m = Math.sin(mrad + mrad + mrad);

        return sinm * (1.914602 - jCenturies * (0.004817 + 0.000014 * jCenturies)) + 
                sin2m * (0.019993 - 0.000101 * jCenturies) + sin3m * 0.000289;		// in degrees
    }

    /**
     *Purpose: Julian day from calendar day
     * @param year 4 digit year
     * @param month January = 1
     * @param day 1 - 31
     * 
     * @return Number is returned for start of day.  Fractional days should be added later.
     */
    private double calcJD(int year, int month, int day) {
        if (month <= 2) {
            year -= 1;
            month += 12;
        }
        double a = Math.floor(year / (double) 100);
        double b = 2 - a + Math.floor(a / 4);

        return Math.floor(365.25 * (year + 4716)) + Math.floor(30.6001 * (month + 1)) + day + b - 1524.5;
    }

    /**
     *Purpose: convert Julian Day to centuries since J2000.0.
     * @param jd the Julian Day to convert
     * 
     * @return value corresponding to the Julian Day
     */
    private double calcTimeJulianCent(double jd) {
    	return (jd - 2451545.0) / 36525.0;
    }

    /**
     *Purpose: convert centuries since J2000.0 to Julian Day.
     * @param jCenturies number of Julian centuries since J2000.0
     * 
     * @return the Julian Day corresponding to the t value
     */
    private double calcJDFromJulianCent(double jCenturies) {
    	return jCenturies * 36525.0 + 2451545.0;
    }

    /**
     *Purpose: calculate the mean obliquity of the ecliptic
     * @param jCenturies number of Julian centuries since J2000.0
     * 
     * @return mean obliquity in degrees
     */
    private double calcMeanObliquityOfEcliptic(double t) {
        double seconds = 21.448 - t * (46.8150 + t * (0.00059 - t * (0.001813)));
        return 23.0 + (26.0 + (seconds / 60.0)) / 60.0;		// in degrees
    }

    /**
     *Purpose: Convert degree angle to radians
     * @param angleDeg angle in degrees
     * 
     * @return angle in radians
     */
    private double degToRad(double angleDeg) {
        return (Math.PI * angleDeg / 180.0);
    }
    
    /**
     *Purpose: Convert radian angle to degrees
     * @param angleRad angle in radians
     * 
     * @return angle in degrees
     */
    private double radToDeg(double angleRad) {
        return (180.0 * angleRad / Math.PI);
    }

    /**
     *Purpose: calculate the corrected obliquity of the ecliptic
     * @param jCenturies number of Julian centuries since J2000.0
     * 
     * @return corrected obliquity in degrees
     */
    private double calcObliquityCorrection(double jCenturies) {
        double e0 = calcMeanObliquityOfEcliptic(jCenturies);
        double omega = 125.04 - 1934.136 * jCenturies;
        return e0 + 0.00256 * Math.cos(degToRad(omega));		// in degrees
    }

    /**
     *Purpose: calculate the Geometric Mean Longitude of the Sun
     * @param jCenturies number of Julian centuries since J2000.0
     * 
     * @return the Geometric Mean Longitude of the Sun in degrees
     */
    private double calcGeomMeanLongSun(double jCenturies) {
        double l0 = 280.46646 + jCenturies * (36000.76983 + 0.0003032 * jCenturies);
        while (l0 > 360.0) {
            l0 -= 360.0;
        }
        while (l0 < 0.0) {
            l0 += 360.0;
        }
        return l0;		// in degrees
    }

    /**
     *Purpose: calculate the eccentricity of earth's orbit
     * @param jCenturies number of Julian centuries since J2000.0
     * 
     * @return the unitless eccentricity
     */
    private double calcEccentricityEarthOrbit(double jCenturies) {
        return 0.016708634 - jCenturies * (0.000042037 + 0.0000001267 * jCenturies);
    }

    /**
     *Purpose: calculate the Geometric Mean Anomaly of the Sun
     * @param jCenturies number of Julian centuries since J2000.0
     * 
     * @return the Geometric Mean Anomaly of the Sun in degrees
     */
    private double calcGeomMeanAnomalySun(double jCenturies) {
        return 357.52911 + jCenturies * (35999.05029 - 0.0001537 * jCenturies);
    }

    /**
     *Purpose: calculate the difference between true solar time and mean solar time
     * @param jCenturies number of Julian centuries since J2000.0
     * 
     * @return equation of time in minutes of time
     */
    private double calcEquationOfTime(double jCenturies) {
        double epsilon = calcObliquityCorrection(jCenturies);
        double l0 = calcGeomMeanLongSun(jCenturies);
        double e = calcEccentricityEarthOrbit(jCenturies);
        double m = calcGeomMeanAnomalySun(jCenturies);

        double y = Math.tan(degToRad(epsilon) / 2.0);
        y *= y;

        double sin2l0 = Math.sin(2.0 * degToRad(l0));
        double sinm = Math.sin(degToRad(m));
        double cos2l0 = Math.cos(2.0 * degToRad(l0));
        double sin4l0 = Math.sin(4.0 * degToRad(l0));
        double sin2m = Math.sin(2.0 * degToRad(m));

        double etime = y * sin2l0 - 2.0 * e * sinm + 4.0 * e * y * sinm * cos2l0
                - 0.5 * y * y * sin4l0 - 1.25 * e * e * sin2m;

        return radToDeg(etime) * 4.0;	// in minutes of time
    }

    /**
     *Purpose: calculate the Universal Coordinated Time (UTC) of solar
     *      noon for the given day at the given location on earth
     * @param jCenturies number of Julian centuries since J2000.0
     * @param longitude longitude of observer in degrees
     * 
     * @return time in minutes from zero Z
     */
    private double calcSolNoonUTC(double jCenturies, double longitude) {
        // First pass uses approximate solar noon to calculate eqtime
        double tnoon = calcTimeJulianCent(calcJDFromJulianCent(jCenturies) + longitude / 360.0);
        double eqTime = calcEquationOfTime(tnoon);
        double solNoonUTC = 720 + (longitude * 4) - eqTime; // min

        double newt = calcTimeJulianCent(calcJDFromJulianCent(jCenturies) - 0.5 + solNoonUTC / 1440.0);

        eqTime = calcEquationOfTime(newt);

        solNoonUTC = 720 + (longitude * 4) - eqTime; // min

        return solNoonUTC;
    }

}
