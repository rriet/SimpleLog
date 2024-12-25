//
//  FlightModelClass.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/21/24.
//

import Foundation
import SwiftData

@Model
class FlightModel {

    @Relationship(deleteRule: .cascade, inverse: \TimeModel.flightsStartingHere)
    var startTime: TimeModel?
    
    // End DateTime Epoch
    var endTime: Int
    
    // Departure Airport
    var departurePlace: AirportModel?
    // Arrival Airport
    var arrivalPlace: AirportModel?
    
//     Aircraft (Deny deletting aircraft)
    @Relationship(deleteRule: .deny, inverse: \AircraftModel.flightsWithThisAircraft)
    var aircraft: AircraftModel
    
    var flightTime: Int
    
    init(
        startTime: TimeModel = TimeModel(),
        endTime: Int = Int(Date().timeIntervalSince1970 / 60),
        departurePlace: AirportModel = AirportModel(),
        arrivalPlace: AirportModel = AirportModel(),
        aircraft: AircraftModel = AircraftModel(),
        flightTime: Int = 0
    ) {
        self.startTime = startTime
        self.endTime = endTime
        self.departurePlace = departurePlace
        self.arrivalPlace = arrivalPlace
        self.aircraft = aircraft
        self.flightTime = flightTime
    }
}

// Unwraping model variables
extension FlightModel {
    var startTimeInt: Int {
        return startTime?.timestamp ?? 0
    }
}
