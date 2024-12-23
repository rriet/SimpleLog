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
    var id: UUID
    var timestamp: Int
    
    @Relationship(deleteRule: .noAction, inverse: \FlightModel.startTime)
    var flightsStartingHere: [FlightModel] = []
    
    @Relationship(deleteRule: .noAction, inverse: \DutyPeriodModel.startTime)
    var dutiesStartingHere: [DutyPeriodModel] = []
    
    @Relationship(deleteRule: .noAction, inverse: \DutyPeriodModel.endTime)
    var dutiesEndingHere: [DutyPeriodModel] = []
    
    init(
        id: UUID = UUID(),
        timestamp: Int = Int(Date().timeIntervalSince1970 / 60)
    ) {
        self.id = id
        self.timestamp = timestamp
    }
}
