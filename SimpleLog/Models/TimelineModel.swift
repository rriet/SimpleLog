//
//  TimeModel.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/22/24.
//

import Foundation
import SwiftData

// Timeline of all events (Flight, Simulator, Duty, Positioning, etc...)
// Never create or delete directly. When creating the corresponding event, it will be created
// When the corresponding event is deleted, it will be cascade deleted.

@Model
class TimelineModel: Identifiable {
    
    var timestamp: Int
    
    var dutieStartRelationship: DutyPeriodModel?
    var dutieEndRelationship: DutyPeriodModel?
    var flightStartReletionship: FlightModel?
    
    
    // initializes the time with current EPOCH
    init(
        timestamp: Int = Int(Date().timeIntervalSince1970 / 60)
    ) {
        self.timestamp = timestamp
    }
}

// Unwraping model variables
extension TimelineModel {
    
    var hasDutieStart: Bool {
        dutieStartRelationship != nil
    }
    
    var dutieStart: DutyPeriodModel {
        dutieStartRelationship ?? DutyPeriodModel()
    }
    
    var hasDutieEnd: Bool {
        dutieEndRelationship != nil
    }
    
    var dutieEnd: DutyPeriodModel {
        dutieEndRelationship ?? DutyPeriodModel()
    }
    
    var hasFlightStart: Bool {
        flightStartReletionship != nil
    }
    
    var flightStart: FlightModel {
        flightStartReletionship ?? FlightModel()
    }
}
