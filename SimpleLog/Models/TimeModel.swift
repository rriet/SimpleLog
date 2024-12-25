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
    
    var flightsStartingHere: FlightModel?
    var dutiesStartingHere: DutyPeriodModel?
    var dutiesEndingHere: DutyPeriodModel?
    
    
    // initializes the time with current EPOCH
    init(
        timestamp: Int = Int(Date().timeIntervalSince1970 / 60)
    ) {
        self.timestamp = timestamp
    }
}
