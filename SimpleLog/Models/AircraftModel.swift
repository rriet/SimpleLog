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
    var typeRelationship: TypesModel?
    
    // List all the flights with this Aircraft
    var flightsRelationship: [FlightModel]?
    
    init(
        registration: String = "",
        aircraftMtow: Int = 0,
        isSimulator: Bool = false,
        typeRelationship: TypesModel? = nil
    ) {
        self.registration = registration
        self.aircraftMtow = aircraftMtow
        self.isSimulator = isSimulator
        self.typeRelationship = typeRelationship
    }
}

// Unwraping model variables
extension AircraftModel {
    var hasFlights: Bool {
        return self.flights.count > 0
    }
    
    var flights: [FlightModel] {
        flightsRelationship ?? []
    }
    
    var type: TypesModel {
        typeRelationship ?? TypesModel()
    }
}
