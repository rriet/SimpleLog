//
//  DutyModelClass.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/21/24.
//

import Foundation
import SwiftData

@Model
class DutyPeriodModel: Schedulable {
    var id: UUID
    var startTime: Int
    var endTime: Int
    var totalDutyTime: Int
    var notes: String
    
    init(startTime: Int, endTime: Int, totalDutyTime: Int, notes: String) {
        self.id = UUID()
        self.startTime = startTime
        self.endTime = endTime
        self.totalDutyTime = totalDutyTime
        self.notes = notes
    }
}
