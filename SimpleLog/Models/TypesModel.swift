//
//  TypeModel.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/23/24.
//

import Foundation
import SwiftData

enum AircraftClasses: String, Codable {
    case Amphibian = "Amphibian"
    case Gyrocopter = "Gyrocopter"
    case Helicopter = "Helicopter"
    case Landplane = "Landplane"
    case Seaplane = "Seaplane"
    case Tiltwing = "Tilt-wing"
}

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

enum IcaoWeights: String, Codable {
    case H = "Heavy"
    case M = "Medium"
    case L = "Light"
}


@Model
class TypesModel: Identifiable {
    // This are included in the CSV file
    var designator: String
    var modelName: String
    var modelGroup: String
    var manufacturer: String?
    var aircraftClass: AircraftClasses
    var multiEngine: Bool
    var engineType: EngineTypes
    var icaoWeights: IcaoWeights
    // This will be imported with default values
    var mtow: Int?
    var multiPilot: Bool
    var efis: Bool
    var Complex: Bool
    var highPerformance: Bool
    
    var aircraftsRelationship: [AircraftModel]?
    
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

// Unwraping model variables
extension TypesModel {
    var aircrafts: [AircraftModel] {
        aircraftsRelationship ?? []
    }
}
