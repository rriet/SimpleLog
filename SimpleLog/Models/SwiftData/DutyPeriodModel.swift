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
//    @Relationship(deleteRule: .noAction)
    var startTime: TimeModel?
//    @Relationship(deleteRule: .noAction)
    var endTime: TimeModel?
    var totalDutyTime: Int
    var notes: String
    
    init(startTime: TimeModel = TimeModel(),
         endTime: TimeModel = TimeModel(timestamp: Int(Date().timeIntervalSince1970 / 60 + 120)),
         totalDutyTime: Int = 0,
         notes: String = ""
    ) {
        self.startTime = startTime
        self.endTime = endTime
        self.totalDutyTime = totalDutyTime
        self.notes = notes
    }
}

//@Model
//class DutyPeriodModel: Schedulable {
//    var id: UUID
//    var startTime: Int
//    var endTime: Int
//    var totalDutyTime: Int
//    
//    init(startTime: Int, endTime: Int, totalDutyTime: Int, notes: String) {
//        self.id = UUID()
//        self.startTime = startTime
//        self.endTime = endTime
//        self.totalDutyTime = totalDutyTime
//    }
//}
