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
    var flightsWithThisAircraft: [FlightModel] = []
    
    init(
        registration: String = "",
        aircraftMtow: Int = 0,
        isSimulator: Bool = false,
        aircraftType: TypesModel? = nil
    ) {
        self.registration = registration
        self.aircraftMtow = aircraftMtow
        self.isSimulator = isSimulator
        self.aircraftType = aircraftType
    }
}
