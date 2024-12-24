//
//  DutyModelClass.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/21/24.
//

import Foundation
import SwiftData

@Model
class DutyPeriodModel {
    
    @Relationship(deleteRule: .cascade) var startTime: TimeModel?
    @Relationship(deleteRule: .cascade) var endTime: TimeModel?
    var totalDutyTime: Int
    var factoredDutyTime: Int
    var notes: String
    
    init(
        startTime: TimeModel = TimeModel(),
        endTime: TimeModel = TimeModel(timestamp: Int(Date().timeIntervalSince1970 / 60 + 120)),
        totalDutyTime: Int = 0,
        factoredDutyTime: Int = 0,
        notes: String = ""
    ) {
        self.startTime = startTime
        self.endTime = endTime
        self.totalDutyTime = totalDutyTime
        self.factoredDutyTime = factoredDutyTime
        self.notes = notes
    }
}

extension DutyPeriodModel {
    var startTimeInt: Int {
        return startTime?.timestamp ?? 0
    }
    
    var endTimeInt: Int {
        return endTime?.timestamp ?? 0
    }
}
