//
//  AircraftModel.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/23/24.
//

import Foundation
import SwiftData

@Model
class AircraftModel: Identifiable {
    
    var registration: String
    var aircraftMtow: Int
    var isSimulator: Bool
    var aircraftType: TypesModel?
    
    // List all the flights with this Aircraft
    var flightsWithThisAircraft: [FlightModel]?
    
    init(
        registration: String = "",
        aircraftMtow: Int = 0,
        isSimulator: Bool = false
    ) {
        self.registration = registration
        self.aircraftMtow = aircraftMtow
        self.isSimulator = isSimulator
    }
}

// Unwraping model variables
extension AircraftModel {
    var hasFlights: Bool {
        if let flights = self.flightsWithThisAircraft {
            return flights.count > 0
        }
        return false
    }
}
