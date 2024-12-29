//
//  TypeModel.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/23/24.
//

import Foundation
import SwiftData

/// A model representing aircraft types
/// - This model defines various characteristics that describe different types of aircraft,
///   such as their designator, engine type, weight category, and performance characteristics.
/// - Aircraft types are imported from CSV and will include information such as `designator`,
///   `modelName`, `modelGroup`, and others. Some properties, such as `mtow`, will be optional.

@Model
class TypesModel: Identifiable {
    
    // The designator (ID) for the aircraft type (e.g., "A320", "B737").
    var designator: String
    
    // The name of the model (e.g., "Airbus A320").
    var modelName: String
    
    // A name of the aircrafts with the same type (e.g. A320, A319 and A321 can be grouped on "A320" type)
    var modelGroup: String
    
    // The manufacturer of the aircraft type, optional as it might not be available in all cases.
    var manufacturer: String?
    
    // The class of the aircraft, categorized by its structure or intended use (e.g., landplane, helicopter).
    var aircraftClass: AircraftClasses
    
    // Boolean indicating if the aircraft is multi-engine (true/false).
    var multiEngine: Bool
    
    // The type of engine used by the aircraft (e.g., Jet, Piston).
    var engineType: EngineTypes
    
    // The weight category of the aircraft as per ICAO classification (Heavy, Medium, Light).
    var icaoWeights: IcaoWeights
    
    // Maximum Takeoff Weight (MTOW), optional, may be missing in some cases.
    var mtow: Int?
    
    // Boolean indicating if the aircraft requires two pilots (multi-pilot).
    var multiPilot: Bool
    
    // Boolean indicating if the aircraft is equipped with EFIS (Electronic Flight Instrument System).
    var efis: Bool
    
    // Boolean indicating if the aircraft is considered "Complex" (e.g., having advanced systems).
    var Complex: Bool
    
    // Boolean indicating if the aircraft is "High Performance" (e.g., fast or advanced capabilities).
    var highPerformance: Bool
    
    // A relationship to all `AircraftModel` instances associated with this aircraft type.
    var aircraftsRelationship: [AircraftModel]?
    
    /// Initializes the `TypesModel` with default or provided values.
    ///
    /// - Parameters:
    ///   - designator: The unique identifier for the aircraft type (defaults to an empty string).
    ///   - modelName: The name of the model (defaults to an empty string).
    ///   - modelGroup: The group the model belongs to (defaults to an empty string).
    ///   - manufacturer: The manufacturer of the aircraft (optional, defaults to nil).
    ///   - aircraftClass: The class of the aircraft (defaults to `.Landplane`).
    ///   - multiEngine: Whether the aircraft is multi-engine (defaults to `true`).
    ///   - engineType: The type of engine used by the aircraft (defaults to `.Jet`).
    ///   - icaoWeights: The ICAO weight category (defaults to `.H` for Heavy).
    ///   - mtow: The maximum takeoff weight (optional, defaults to nil).
    ///   - multiPilot: Whether the aircraft requires multiple pilots (defaults to `true`).
    ///   - efis: Whether the aircraft has EFIS (defaults to `true`).
    ///   - Complex: Whether the aircraft is considered complex (defaults to `true`).
    ///   - highPerformance: Whether the aircraft is high performance (defaults to `true`).
    init(
        designator: String = "",
        modelName: String = "",
        modelGroup: String = "",
        manufacturer: String? = nil,
        aircraftClass: AircraftClasses = .Landplane,
        multiEngine: Bool = true,
        engineType: EngineTypes = .Jet,
        icaoWeights: IcaoWeights = .H,
        mtow: Int? = nil,
        multiPilot: Bool = true,
        efis: Bool = true,
        Complex: Bool = true,
        highPerformance: Bool = true
    ) {
        self.designator = designator
        self.modelName = modelName
        self.modelGroup = modelGroup
        self.manufacturer = manufacturer
        self.aircraftClass = aircraftClass
        self.multiEngine = multiEngine
        self.engineType = engineType
        self.icaoWeights = icaoWeights
        self.mtow = mtow
        self.multiPilot = multiPilot
        self.efis = efis
        self.Complex = Complex
        self.highPerformance = highPerformance
    }
}

// MARK: - Unwrapping Model Variables (Computed Properties)

extension TypesModel {
    
    /// Returns a list of `AircraftModel` instances associated with this aircraft type.
    /// - If no relationships are set, returns an empty array.
    var aircrafts: [AircraftModel] {
        aircraftsRelationship ?? []
    }
}

/// Enum representing different aircraft classes (e.g., amphibian, landplane, helicopter).
/// Each case corresponds to a specific type of aircraft with distinct characteristics.
enum AircraftClasses: String, Codable {
    case Amphibian = "Amphibian"
    case Gyrocopter = "Gyrocopter"
    case Helicopter = "Helicopter"
    case Landplane = "Landplane"
    case Seaplane = "Seaplane"
    case Tiltwing = "Tilt-wing"
}

/// Enum representing various engine types used in aircraft (e.g., Jet, Piston, Turboprop).
/// These engine types help categorize the aircraft's propulsion system.
enum EngineTypes: String, Codable {
    case Rocket = "Rocket"
    case Piston = "Piston"
    case Turboprop = "Turboprop/Turboshaft"
    case Jet = "Jet"
    case Electric = "Electric"
    case UltraLightAircraft = "Ultra-light Aircraft"
    case Drone = "Drone"
    case Helicopter = "Helicopter"
    case UltraLightGyrocopter = "Micro-/Ultra-light Gyrocopter"
    case UltraLightHelicopter = "Ultra-light Helicopter"
    case Glider = "Glider/Sailplane"
    case Airship = "Airship"
    case Balloon = "Balloon"
    case ZZZZ = "Aircraft type not yet assigned"
    case Paraplane = "Powered parachute/Paraplane."
}

/// Enum representing ICAO weight categories (Heavy, Medium, Light).
/// This classification is used for determining aircraft size and performance constraints.
enum IcaoWeights: String, Codable {
    case H = "Heavy"
    case M = "Medium"
    case L = "Light"
}
