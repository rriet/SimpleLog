//
//  AircraftModel.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/23/24.
//

import Foundation
import SwiftData

/// Represents an Aircraft
@Model
class AircraftModel: Identifiable {
    // MARK: - Properties
    
    /// The registration number of the aircraft (e.g., tail number).
    var registration: String
    
    /// The Maximum Takeoff Weight (MTOW) of the aircraft in kilograms.
    /// Defaults to the MTOW of the associated type if not explicitly provided.
    var aircraftMtow: Int
    
    /// Indicates whether this aircraft is a simulator.
    var isSimulator: Bool
    
    /// Relationship to an aircraft type (optional).
    var typeRelationship: TypesModel?
    
    /// List of flights associated with this aircraft (optional).
    var flightsRelationship: [FlightModel]?
    
    // MARK: - Initializer
    
    /// Initializes a new `AircraftModel` instance.
    /// - Parameters:
    ///   - registration: The registration of the aircraft. Defaults to an empty string.
    ///   - aircraftMtow: The MTOW of the aircraft. If nil, defaults to the MTOW of the `type` (if provided) or 0.
    ///   - isSimulator: Whether this aircraft is a simulator. Defaults to `false`.
    ///   - type: The associated type of the aircraft. Defaults to `nil`.
    init(
        registration: String = "",
        aircraftMtow: Int? = nil,
        isSimulator: Bool = false,
        type: TypesModel? = nil
    ) {
        self.registration = registration
        // If no MTOW is provided, fall back to the type's MTOW or default to 0.
        self.aircraftMtow = aircraftMtow ?? type?.mtow ?? 0
        self.isSimulator = isSimulator
        self.typeRelationship = type
    }
}

// MARK: - Computed Properties

extension AircraftModel {
    /// Indicates whether the aircraft has any associated flights.
    /// - Returns: `true` if the aircraft has one or more flights; otherwise, `false`.
    var hasFlights: Bool {
        !flights.isEmpty
    }
    
    /// Provides a non-optional list of flights associated with this aircraft.
    /// - Returns: An array of `FlightModel` objects, or an empty array if `flightsRelationship` is `nil`.
    var flights: [FlightModel] {
        flightsRelationship ?? []
    }
    
    /// Provides a non-optional type for the aircraft.
    /// - Returns: The associated `TypesModel` object, or a default `TypesModel` instance if `typeRelationship` is `nil`.
    var type: TypesModel {
        typeRelationship ?? TypesModel()
    }
}
