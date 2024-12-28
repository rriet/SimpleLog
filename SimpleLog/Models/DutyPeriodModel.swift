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
    
    @Relationship(deleteRule: .cascade, inverse: \TimelineModel.dutieStartRelationship)
    var startTimeRelationship: TimelineModel?
    @Relationship(deleteRule: .cascade, inverse: \TimelineModel.dutieEndRelationship)
    var endTimeRelationship: TimelineModel?
    
    var totalDutyTime: Int
    var factoredDutyTime: Int
    var notes: String
    
    init(
        startTime: Int = Int(Date().timeIntervalSince1970 / 60),
        endTime: Int = Int(Date().timeIntervalSince1970 / 60),
        totalDutyTime: Int = 0,
        factoredDutyTime: Int = 0,
        notes: String = ""
    ) {
        self.startTimeRelationship = TimelineModel(timestamp: startTime)
        self.endTimeRelationship = TimelineModel(timestamp: endTime)
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
