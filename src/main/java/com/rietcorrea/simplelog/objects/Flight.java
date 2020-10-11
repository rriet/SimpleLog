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
package com.rietcorrea.simplelog.objects;

import com.rietcorrea.constants.StrEng;
import com.rietcorrea.constants.StrLen;
import com.rietcorrea.simplelog.UserPreferences;
import com.rietcorrea.simplelog.converters.DecimalHourIntegerConverter;
import com.rietcorrea.simplelog.converters.HoursIntegerConverter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;


/**
 *
 * @author riet
 */
public class Flight {

	private IntegerProperty flightId       = new SimpleIntegerProperty();

	private LongProperty    departureDate  = new SimpleLongProperty();
	private LongProperty    arrivalDate    = new SimpleLongProperty();

	private Airport         departureAirport;
	private Airport         arrivalAirport;

	private StringProperty  pfPnf          = new SimpleStringProperty();

	private Aircraft        aircraft;
	private Crew            crewPic;
	private Crew            crewSic;
	private StringProperty  remarks        = new SimpleStringProperty();
	private StringProperty  privateNotes   = new SimpleStringProperty();

	private IntegerProperty takeOffDay     = new SimpleIntegerProperty();
	private IntegerProperty takeOffNight   = new SimpleIntegerProperty();
	private IntegerProperty landingDay     = new SimpleIntegerProperty();
	private IntegerProperty landingNight   = new SimpleIntegerProperty();

	private IntegerProperty ifrTime        = new SimpleIntegerProperty();
	private IntegerProperty nightTime      = new SimpleIntegerProperty();
	private IntegerProperty xcTime         = new SimpleIntegerProperty();
	private IntegerProperty picTime        = new SimpleIntegerProperty();
	private IntegerProperty picUsTime      = new SimpleIntegerProperty();
	private IntegerProperty sicTime        = new SimpleIntegerProperty();
	private IntegerProperty dualTime       = new SimpleIntegerProperty();
	private IntegerProperty instructorTime = new SimpleIntegerProperty();
	private IntegerProperty fstdTime       = new SimpleIntegerProperty();
	private IntegerProperty totalTime      = new SimpleIntegerProperty();

	private Integer         turbofan       = 0;
	private Integer         turboprop      = 0;
	private Integer         piston         = 0;
	private Integer         glider         = 0;
	private Integer         efis           = 0;
	private Integer         mel            = 0;
	private Integer         sel            = 0;
	private Integer         mes            = 0;
	private Integer         ses            = 0;
	private Integer         multiPilot     = 0;

	public Flight() {
		// Set time to current system Epoch
		this.departureDate.set(System.currentTimeMillis() / 1000);
		this.arrivalDate.set(System.currentTimeMillis() / 1000);

		this.departureAirport = new Airport();
		this.arrivalAirport   = new Airport();
		this.pfPnf.set("");
		this.aircraft = new Aircraft();
		this.crewPic  = new Crew();
		this.crewSic  = new Crew();
		this.remarks.set("");
		this.privateNotes.set("");
		this.takeOffDay.set(0);
		this.takeOffNight.set(0);
		this.landingDay.set(0);
		this.landingNight.set(0);
		this.ifrTime.set(0);
		this.nightTime.set(0);
		this.xcTime.set(0);
		this.picTime.set(0);
		this.picUsTime.set(0);
		this.sicTime.set(0);
		this.dualTime.set(0);
		this.instructorTime.set(0);
		this.fstdTime.set(0);
		this.totalTime.set(0);
	}

	public Flight(Flight that) {

		this.setFlightId(that.getFlightId()).setDepartureDate(that.getDepartureDate())
				.setArrivalDate(that.getArrivalDate()).setPfPnf(that.getPfPnf())
				.setDepartureAirport(that.getDepartureAirport()).setArrivalAirport(that.getArrivalAirport())
				.setAircraft(that.getAircraft()).setCrewPic(that.getCrewPic()).setCrewSic(that.getCrewSic())
				.setRemarks(that.getRemarks()).setPrivateNotes(that.getPrivateNotes())
				.setTakeOffDay(that.getTakeOffDay()).setTakeOffNight(that.getTakeOffNight())
				.setLandingDay(that.getLandingDay()).setLandingNight(that.getLandingNight())
				.setIfrTime(that.getIfrTime()).setNightTime(that.getNightTime()).setXcTime(that.getXcTime())
				.setPicTime(that.getPicTime()).setPicUsTime(that.getPicUsTime()).setSicTime(that.getSicTime())
				.setDualTime(that.getDualTime()).setInstructorTime(that.getInstructorTime())
				.setFstdTime(that.getFstdTime()).setTotalTime(that.getTotalTime());
	}


	public String[] getFlightArray() {

		List<String> flightArray = new ArrayList<>();

		Calendar departureCal = new GregorianCalendar();
		departureCal.setTimeInMillis(this.getDepartureDate() * 1000);

		Calendar arrivalCal = new GregorianCalendar();
		arrivalCal.setTimeInMillis(this.getArrivalDate() * 1000);

		SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		newDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		// Add Departure day formated
		flightArray.add(newDateFormat.format(departureCal.getTime()));

		SimpleDateFormat newTimeFormat = new SimpleDateFormat("HH:mm");
		newTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		// Add departure time formated
		flightArray.add(newTimeFormat.format(departureCal.getTime()));
		// Add arrival time formated
		flightArray.add(newTimeFormat.format(arrivalCal.getTime()));

		// Add Departure Epoch
		flightArray.add(this.getDepartureDate().toString());
		// Add Arrival Epoch
		flightArray.add(this.getArrivalDate().toString());

		// Add all the Departure Airport properties
		flightArray.addAll(Arrays.asList(this.getDepartureAirport().getAirportArray()));

		// Add all the Arrival Airport properties
		flightArray.addAll(Arrays.asList(this.getArrivalAirport().getAirportArray()));

		// Add all the Aircraft/Model properties
		flightArray.addAll(Arrays.asList(this.getAircraft().getAircraftArray()));

		// Add hours for each engine type (minutes/Formated/Decimal)
		flightArray.addAll(getSplitedTimeArray(this.turbofan));
		flightArray.addAll(getSplitedTimeArray(this.turboprop));
		flightArray.addAll(getSplitedTimeArray(this.piston));
		flightArray.addAll(getSplitedTimeArray(this.glider));
		
		flightArray.addAll(getSplitedTimeArray(this.efis));
		
		flightArray.addAll(getSplitedTimeArray(this.sel));
		flightArray.addAll(getSplitedTimeArray(this.mel));
		flightArray.addAll(getSplitedTimeArray(this.ses));
		flightArray.addAll(getSplitedTimeArray(this.mes));
		
		flightArray.addAll(getSplitedTimeArray(this.multiPilot));
		
		// Add all PIC information
		flightArray.addAll(Arrays.asList(this.getCrewPic().getCrewArray()));

		// Add all SIC information
		flightArray.addAll(Arrays.asList(this.getCrewSic().getCrewArray()));

		// Get PF/PNF information
		flightArray.add(this.getPfPnf());

		// Add Remarks and Notes
		flightArray.add(this.getRemarks());
		flightArray.add(this.getPrivateNotes());

		// Add Takeoff and Landings
		flightArray.add(this.getTakeOffDay().toString());
		flightArray.add(this.getTakeOffNight().toString());
		flightArray.add(this.getLandingDay().toString());
		flightArray.add(this.getLandingNight().toString());

		// Add IFR Hours (minutes/Formated/Decimal)
		flightArray.addAll(getSplitedTimeArray(this.getIfrTime()));
		
		flightArray.addAll(getSplitedTimeArray(this.getNightTime()));
		
		flightArray.addAll(getSplitedTimeArray(this.getXcTime()));
		
		flightArray.addAll(getSplitedTimeArray(this.getPicTime()));
		
		
		
		flightArray.addAll(getSplitedTimeArray(this.getPicUsTime()));
		flightArray.addAll(getSplitedTimeArray(this.getSicTime()));
		flightArray.addAll(getSplitedTimeArray(this.getDualTime()));
		flightArray.addAll(getSplitedTimeArray(this.getInstructorTime()));
		flightArray.addAll(getSplitedTimeArray(this.getFstdTime()));
		flightArray.addAll(getSplitedTimeArray(this.getTotalTime()));
		
		return flightArray.toArray(new String[0]);
	}
	
	public List<String> getSplitedTimeArray(Integer inputTimeInteger) {
		StringConverter<Number> formatedConverter = new HoursIntegerConverter();
		StringConverter<Number> decimalConverter = new DecimalHourIntegerConverter();
		
		List<String> timeArray = new ArrayList<>();
		
		timeArray.add(inputTimeInteger.toString());
		timeArray.add(formatedConverter.toString(inputTimeInteger));
		timeArray.add(decimalConverter.toString(inputTimeInteger));
		
		return timeArray;
	}

	public String[] getFlightSimpleLogArray() {

		List<String> flightArray = new ArrayList<>();

		Calendar departureCal = new GregorianCalendar();
		departureCal.setTimeInMillis(this.getDepartureDate() * 1000);

		Calendar arrivalCal = new GregorianCalendar();
		arrivalCal.setTimeInMillis(this.getArrivalDate() * 1000);

		SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		newDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		// Add Departure day formated
		flightArray.add(newDateFormat.format(departureCal.getTime()));

		SimpleDateFormat newTimeFormat = new SimpleDateFormat("HH:mm");
		newTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		// Add departure time formated
		flightArray.add(newTimeFormat.format(departureCal.getTime()));
		// Add arrival time formated
		flightArray.add(newTimeFormat.format(arrivalCal.getTime()));

		// Add Departure Epoch
		flightArray.add(this.getDepartureDate().toString());
		// Add Arrival Epoch
		flightArray.add(this.getArrivalDate().toString());

		// Add all the Departure Airport properties
		flightArray.addAll(Arrays.asList(this.getDepartureAirport().getAirportArray()));

		// Add all the Arrival Airport properties
		flightArray.addAll(Arrays.asList(this.getArrivalAirport().getAirportArray()));

		// Add all the Aircraft/Model properties
		flightArray.addAll(Arrays.asList(this.getAircraft().getAircraftArray()));

		// Add all PIC information
		flightArray.addAll(Arrays.asList(this.getCrewPic().getCrewArray()));

		// Add all SIC information
		flightArray.addAll(Arrays.asList(this.getCrewSic().getCrewArray()));

		// Get PF/PNF information
		flightArray.add(this.getPfPnf());

		// Add Remarks and Notes
		flightArray.add(this.getRemarks());
		flightArray.add(this.getPrivateNotes());

		// Add Takeoff and Landings
		flightArray.add(this.getTakeOffDay().toString());
		flightArray.add(this.getTakeOffNight().toString());
		flightArray.add(this.getLandingDay().toString());
		flightArray.add(this.getLandingNight().toString());

		// Add IFR Hours (minutes/Formated/Decimal)
		flightArray.add(this.getIfrTime().toString());
		// Add Night time (minutes/Formated/Decimal)
		flightArray.add(this.getNightTime().toString());
		// Add Xcountry time (minutes/Formated/Decimal)
		flightArray.add(this.getXcTime().toString());
		// add Pic hours (minutes/Formated/Decimal)
		flightArray.add(this.getPicTime().toString());
		// add PicUs hours (minutes/Formated/Decimal)
		flightArray.add(this.getPicUsTime().toString());
		// Add Sic hours (minutes/Formated/Decimal)
		flightArray.add(this.getSicTime().toString());
		// Add Dual hours (minutes/Formated/Decimal)
		flightArray.add(this.getDualTime().toString());
		// Add Instructor hours (minutes/Formated/Decimal)
		flightArray.add(this.getInstructorTime().toString());
		// Simulator hours (minutes/Formated/Decimal)
		flightArray.add(this.getFstdTime().toString());
		// Add total hours (minutes/Formated/Decimal)
		flightArray.add(this.getTotalTime().toString());


		return flightArray.toArray(new String[0]);
	}

	public Integer getFlightId() {
		return flightId.get();
	}

	public Flight setFlightId(Integer flightId) {
		this.flightId.set(flightId);
		return this;
	}

	public Long getDepartureDate() {
		return this.departureDate.get();
	}

	public Flight setDepartureDate(Long departureDate) {
		this.departureDate.set(departureDate);
		return this;
	}

	public LongProperty departureDateProperty() { // NO_UCD (unused code)
		return departureDate;
	}

	public Long getArrivalDate() {
		return this.arrivalDate.get();
	}

	public Flight setArrivalDate(Long arrivalDate) {
		this.arrivalDate.set(arrivalDate);
		return this;
	}

	public LongProperty arrivalDateProperty() { // NO_UCD (unused code)
		return arrivalDate;
	}

	public String getPfPnf() {
		return pfPnf.get();
	}

	public Flight setPfPnf(String pfPnf) {
		this.pfPnf.set(pfPnf);
		return this;
	}

	public StringProperty pfPnfProperty() {
		return pfPnf;
	}

	public Airport getDepartureAirport() {
		return departureAirport;
	}

	public Flight setDepartureAirport(Airport departureAirport) {
		this.departureAirport = new Airport(departureAirport);
		return this;
	}

	public Airport getArrivalAirport() {
		return arrivalAirport;
	}

	public Flight setArrivalAirport(Airport arrivalAirport) {
		this.arrivalAirport = new Airport(arrivalAirport);
		return this;
	}

	public Aircraft getAircraft() {
		return aircraft;
	}

	public Flight setAircraft(Aircraft aircraft) {
		this.aircraft = new Aircraft(aircraft);
		setOtherTimes();
		return this;
	}

	public Crew getCrewPic() {
		return crewPic;
	}

	public Flight setCrewPic(Crew crewPic) {
		this.crewPic = new Crew(crewPic);
		return this;
	}

	public Crew getCrewSic() {
		return crewSic;
	}

	public Flight setCrewSic(Crew crewSic) {
		if (crewSic != null) {
			this.crewSic = new Crew(crewSic);
		} else {
			this.crewSic = new Crew();
		}
		return this;
	}

	public String getRemarks() {
		return remarks.get();
	}

	public Flight setRemarks(String remarks) {
		if (remarks != null) {
			this.remarks.set(remarks.substring(0, Math.min(remarks.length(), StrLen.FLIGHT_REMARKS)));
		}
		return this;
	}

	public StringProperty remarksProperty() {
		return remarks;
	}

	public String getPrivateNotes() {
		return privateNotes.get();
	}

	public Flight setPrivateNotes(String privateNotes) {
		if (privateNotes != null) {
			this.privateNotes
					.set(privateNotes.substring(0, Math.min(privateNotes.length(), StrLen.FLIGHT_PRIVATE_NOTES)));
		}
		return this;
	}

	public StringProperty privateNotesProperty() {
		return privateNotes;
	}

	public Integer getTakeOffDay() {
		return takeOffDay.get();
	}

	public Flight setTakeOffDay(Integer takeOffDay) {
		this.takeOffDay.set(takeOffDay);
		return this;
	}

	public IntegerProperty takeOffDayProperty() {
		return takeOffDay;
	}

	public Integer getTakeOffNight() {
		return takeOffNight.get();
	}

	public Flight setTakeOffNight(Integer takeOffNight) {
		this.takeOffNight.set(takeOffNight);
		return this;
	}

	public IntegerProperty takeOffNightProperty() {
		return takeOffNight;
	}

	public Integer getLandingDay() {
		return landingDay.get();
	}

	public Flight setLandingDay(Integer landingDay) {
		this.landingDay.set(landingDay);
		return this;
	}

	public IntegerProperty landingDayProperty() {
		return landingDay;
	}

	public Integer getLandingNight() {
		return landingNight.get();
	}

	public Flight setLandingNight(Integer landingNight) {
		this.landingNight.set(landingNight);
		return this;
	}

	public IntegerProperty landingNightProperty() {
		return landingNight;
	}

	public Integer getIfrTime() {
		return ifrTime.get();
	}

	public Flight setIfrTime(Integer ifrTime) {
		this.ifrTime.set(ifrTime);
		return this;
	}

	public IntegerProperty ifrTimeProperty() {
		return ifrTime;
	}

	public Integer getNightTime() {
		return nightTime.get();
	}

	public Flight setNightTime(Integer nightTime) {
		this.nightTime.set(nightTime);
		return this;
	}

	public IntegerProperty nightTimeProperty() {
		return nightTime;
	}

	public Integer getXcTime() {
		return xcTime.get();
	}

	public Flight setXcTime(Integer xcTime) {
		this.xcTime.set(xcTime);
		return this;
	}

	public IntegerProperty xcTimeProperty() {
		return xcTime;
	}

	public Integer getPicTime() {
		return picTime.get();
	}

	public Flight setPicTime(Integer picTime) {
		this.picTime.set(picTime);
		return this;
	}

	public IntegerProperty picTimeProperty() {
		return picTime;
	}

	public Integer getPicUsTime() {
		return picUsTime.get();
	}

	public Flight setPicUsTime(Integer picUsTime) {
		this.picUsTime.set(picUsTime);
		return this;
	}

	public IntegerProperty picUsTimeProperty() {
		return picUsTime;
	}

	public Integer getSicTime() {
		return sicTime.get();
	}

	public Flight setSicTime(Integer sicTime) {
		this.sicTime.set(sicTime);
		return this;
	}

	public IntegerProperty sicTimeProperty() {
		return sicTime;
	}

	public Integer getDualTime() {
		return dualTime.get();
	}

	public Flight setDualTime(Integer dualTime) {
		this.dualTime.set(dualTime);
		return this;
	}

	public IntegerProperty dualTimeProperty() {
		return dualTime;
	}

	public Integer getInstructorTime() {
		return instructorTime.get();
	}

	public Flight setInstructorTime(Integer instructorTime) {
		this.instructorTime.set(instructorTime);
		return this;
	}

	public IntegerProperty instructorTimeProperty() {
		return instructorTime;
	}

	public Integer getFstdTime() {
		return fstdTime.get();
	}

	public Flight setFstdTime(Integer fstdTime) {
		this.fstdTime.set(fstdTime);
		return this;
	}

	public IntegerProperty fstdTimeProperty() {
		return fstdTime;
	}

	public Integer getTotalTime() {
		return totalTime.get();
	}

	public Flight setTotalTime(Integer totalTime) {
		this.totalTime.set(totalTime);
		setOtherTimes();
		return this;
	}

	public IntegerProperty totalTimeProperty() {
		return totalTime;
	}

	public Integer getIfrMinutes(Integer total) {
		UserPreferences pref = new UserPreferences();
		int percent = Integer.parseInt(pref.getIfrPercentage());
		int deduction = Integer.parseInt(pref.getIfrDeduction());
		int minimum = Integer.parseInt(pref.getIfrMinimun());
		int ifr = (total * percent / 100) - deduction;
		if (ifr < minimum) {
			if (total < minimum) {
				ifr = total;
			} else {
				ifr = minimum;
			}
		}
		return ifr;
	}

	private void setOtherTimes() {
		if (aircraft != null && aircraft.getAircraftModel() != null) {
			setEngineTypeTimes();
			setMultiEngineTimes();
			setCategoryTimes();
		}
	}

	private void setEngineTypeTimes() {
		this.turbofan  = 0;
		this.turboprop = 0;
		this.piston    = 0;
		this.glider    = 0;
		if (aircraft != null && aircraft.getAircraftModel() != null) {
			switch (aircraft.getAircraftModel().getEngineType()) {

				case StrEng.ENGINE_TURBOPROP:
					this.turboprop = this.getTotalTime();
					break;

				case StrEng.ENGINE_PISTON:
					this.turboprop = this.getTotalTime();
					break;
				case StrEng.ENGINE_GLIDER:
					this.turboprop = this.getTotalTime();
					break;
				default:
					this.turbofan = this.getTotalTime();
					break;
			}
		}
	}

	private void setMultiEngineTimes() {
		this.sel = 0;
		this.mel = 0;
		this.ses = 0;
		this.mes = 0;
		if (aircraft != null && aircraft.getAircraftModel() != null) {
			// Add hours for SEL MEL SES MES
			if (!this.getAircraft().getAircraftModel().getSeaplane().booleanValue()) {
				if (!this.getAircraft().getAircraftModel().getMultiEngine().booleanValue()) {
					this.sel = this.getTotalTime();
				} else {
					this.mel = this.getTotalTime();
				}
			} else {
				if (!this.getAircraft().getAircraftModel().getMultiEngine().booleanValue()) {
					this.ses = this.getTotalTime();
				} else {
					this.mes = this.getTotalTime();
				}
			}
		}
	}

	private void setCategoryTimes() {
		this.efis       = 0;
		this.multiPilot = 0;
		
		if (aircraft != null && aircraft.getAircraftModel() != null) {
			if (this.getAircraft().getAircraftModel().getMultiPilot().booleanValue()) {
				this.multiPilot = this.getTotalTime();
			}
			if (this.getAircraft().getAircraftModel().getEfis().booleanValue()) {
				this.efis = this.getTotalTime();
			}
		}

	}
}
