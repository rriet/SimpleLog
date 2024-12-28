//
//  FlightModelClass.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/21/24.
//

import Foundation
import SwiftData

@Model
class FlightModel: Identifiable {

    @Relationship(deleteRule: .cascade, inverse: \TimelineModel.flightStartReletionship)
    var startTimeRelationship: TimelineModel?
    
    // End DateTime Epoch
    var endTime: Int
    
    // Departure Airport
    var departurePlaceReletionship: AirportModel?
    // Arrival Airport
    var arrivalPlaceReletionship: AirportModel?
    
    var aircraftReletionship: AircraftModel?
    
    // crew
    var crewReletionship: [CrewModel]?
    var crewPositions: [CrewPositions]?
    
    var flightTime: Int
    
    init(
        startTime: Int = Int(Date().timeIntervalSince1970 / 60),
        endTime: Int = Int(Date().timeIntervalSince1970 / 60),
        departurePlace: AirportModel? = nil,
        arrivalPlace: AirportModel? = nil,
        aircraft: AircraftModel? = nil,
        crew: [CrewModel]? = nil,
        crewPositions: [CrewPositions]? = nil,
        flightTime: Int = 0
    ) {
        self.startTimeRelationship = TimelineModel(timestamp: startTime)
        self.endTime = endTime
        self.departurePlaceReletionship = departurePlace
        self.arrivalPlaceReletionship = arrivalPlace
        self.aircraftReletionship = aircraft
        self.crewReletionship = crew
        self.crewPositions = crewPositions
        self.flightTime = flightTime
    }
}

// Unwraping model variables
extension FlightModel {
    var startTime: Int {
        return startTimeRelationship?.timestamp ?? 0
    }
    
    var departurePlace: AirportModel {
        departurePlaceReletionship ?? AirportModel()
    }
    
    var arrivalPlace: AirportModel {
        arrivalPlaceReletionship ?? AirportModel()
    }
    
    var aircraft: AircraftModel {
        aircraftReletionship ?? AircraftModel()
    }
}


enum CrewPositions: String, Codable {
    case pic = "PIC"
    case sic = "SIC"
    case fe = "Flight Engineer"
    case obs = "Observer"
    case inst = "Instructor"
    case check = "Check pilot"
    case relief = "Relief"
    case reliefCp = "Relief Captain"
    case reliefFo = "Relief First-Officer"
    case cabinSeniour = "Cabin Seniour"
    case cabinCrew = "Cabin Crew"
}
