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
    
    @Relationship(deleteRule: .cascade, inverse: \TimeModel.dutiesStartingHere)
    var startTimeRelationship: TimeModel?
    @Relationship(deleteRule: .cascade, inverse: \TimeModel.dutiesEndingHere)
    var endTimeRelationship: TimeModel?
    
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
        self.startTimeRelationship = startTime
        self.endTimeRelationship = endTime
        self.totalDutyTime = totalDutyTime
        self.factoredDutyTime = factoredDutyTime
        self.notes = notes
    }
}

// Unwraping model variables
extension DutyPeriodModel {
    var startTime: Int {
        return startTimeRelationship?.timestamp ?? 0
    }
    
    var endTime: Int {
        return endTimeRelationship?.timestamp ?? 0
    }
}
