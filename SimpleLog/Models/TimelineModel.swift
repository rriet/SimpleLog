//
//  TimeModel.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/22/24.
//

import Foundation
import SwiftData

/// A model representing the timeline of all events (e.g., Flight, Simulator, Duty, Positioning, etc.).
/// - Important: Instances of `TimelineModel` should never be created or deleted directly.
///   - When creating the corresponding event, the `TimelineModel` will be created automatically.
///   - When deleting the corresponding event, the `TimelineModel` will be cascade-deleted due to relationships.

@Model
class TimelineModel: Identifiable {
    
    /// The timestamp representing the event time in minutes since the EPOCH (Unix time).
    var timestamp: Int
    
    /// Relationship to the `DutyPeriodModel` marking the start of a duty period.
    var dutieStartRelationship: DutyPeriodModel?
    
    /// Relationship to the `DutyPeriodModel` marking the end of a duty period.
    var dutieEndRelationship: DutyPeriodModel?
    
    /// Relationship to the `FlightModel` marking the start of a flight event.
    var flightStartReletionship: FlightModel?
    
    /// Initializes the `TimelineModel` with the current EPOCH timestamp by default.
    /// - Parameter timestamp: The time of the event in minutes since EPOCH. Defaults to the current time.
    init(
        timestamp: Int = Int(Date().timeIntervalSince1970 / 60)
    ) {
        self.timestamp = timestamp
    }
}

// MARK: - Computed Properties (Extensions)

extension TimelineModel {
    
    /// Checks if there is a relationship for the start of a duty period.
    var hasDutieStart: Bool {
        dutieStartRelationship != nil
    }
    
    /// Returns the `DutyPeriodModel` for the start of a duty period.
    /// - If no relationship exists, it returns a default instance of `DutyPeriodModel`.
    var dutieStart: DutyPeriodModel {
        dutieStartRelationship ?? DutyPeriodModel()
    }
    
    /// Checks if there is a relationship for the end of a duty period.
    var hasDutieEnd: Bool {
        dutieEndRelationship != nil
    }
    
    /// Returns the `DutyPeriodModel` for the end of a duty period.
    /// - If no relationship exists, it returns a default instance of `DutyPeriodModel`.
    var dutieEnd: DutyPeriodModel {
        dutieEndRelationship ?? DutyPeriodModel()
    }
    
    /// Checks if there is a relationship for the start of a flight event.
    var hasFlightStart: Bool {
        flightStartReletionship != nil
    }
    
    /// Returns the `FlightModel` for the start of a flight event.
    /// - If no relationship exists, it returns a default instance of `FlightModel`.
    var flightStart: FlightModel {
        flightStartReletionship ?? FlightModel()
    }
}
