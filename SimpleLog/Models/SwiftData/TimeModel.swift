//
//  TimeModel.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/22/24.
//

import Foundation
import SwiftData

@Model
class TimeModel {
    
    var timestamp: Int
    
    @Relationship(deleteRule: .noAction, inverse: \FlightModel.startTime)
    var flightsStartingHere: FlightModel? = nil
    
    @Relationship(deleteRule: .noAction, inverse: \DutyPeriodModel.startTime)
    var dutiesStartingHere: DutyPeriodModel? = nil
    
    @Relationship(deleteRule: .noAction, inverse: \DutyPeriodModel.endTime)
    var dutiesEndingHere: DutyPeriodModel? = nil
    
    // initializes the time with current EPOCH
    init(
        timestamp: Int = Int(Date().timeIntervalSince1970 / 60)
    ) {
        self.timestamp = timestamp
    }
}
