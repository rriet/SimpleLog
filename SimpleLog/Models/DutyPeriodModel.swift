//
//  DutyModelClass.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/21/24.
//

import Foundation
import SwiftData

/// Represents a duty period
@Model
class DutyPeriodModel {
    
    // MARK: - Relationships
    
    /// Relationship to the timeline representing the start time of the duty period.
    /// When the duty period is deleted, the related start time is also deleted from Timeline (`cascade` delete rule).
    @Relationship(deleteRule: .cascade, inverse: \TimelineModel.dutieStartRelationship)
    var startTimeRelationship: TimelineModel?
    
    /// Relationship to the timeline representing the end time of the duty period.
    /// When the duty period is deleted, the related end time is also deleted from Timeline (`cascade` delete rule).
    @Relationship(deleteRule: .cascade, inverse: \TimelineModel.dutieEndRelationship)
    var endTimeRelationship: TimelineModel?
    
    // MARK: - Properties
    
    /// The optional factored duty time in minutes. If `nil`, it defaults to the total duty time.
    var factoredDutyTimeOpt: Int?
    
    /// Additional notes or comments about the duty period.
    var notes: String
    
    // MARK: - Initializer
    
    /// Initializes a new `DutyPeriodModel` instance.
    /// - Parameters:
    ///   - startTime: The start time of the duty period in minutes since 1970-01-01 00:00:00 UTC. Defaults to the current time.
    ///   - endTime: The end time of the duty period in minutes since 1970-01-01 00:00:00 UTC. Defaults to the current time.
    ///   - factoredDutyTime: The optional factored duty time in minutes. Defaults to `nil`.
    ///   - notes: Additional notes or comments about the duty period. Defaults to an empty string.
    init(
        startTime: Int = Int(Date().timeIntervalSince1970 / 60),
        endTime: Int = Int(Date().timeIntervalSince1970 / 60),
        factoredDutyTime: Int? = nil,
        notes: String = ""
    ) {        
        // Initialize the relationships and properties.
        self.startTimeRelationship = TimelineModel(timestamp: startTime)
        self.endTimeRelationship = TimelineModel(timestamp: endTime)
        self.factoredDutyTimeOpt = factoredDutyTime
        self.notes = notes
    }
}

// MARK: - Computed Properties

/// Extension to provide simplified access to model variables and derived properties.
extension DutyPeriodModel {
    
    /// The start time of the duty period in minutes since 1970-01-01 00:00:00 UTC.
    /// Defaults to 0 if `startTimeRelationship` is `nil`.
    var startTime: Int {
        return startTimeRelationship?.timestamp ?? 0
    }
    
    /// The end time of the duty period in minutes since 1970-01-01 00:00:00 UTC.
    /// Defaults to 0 if `endTimeRelationship` is `nil`.
    var endTime: Int {
        return endTimeRelationship?.timestamp ?? 0
    }
    
    /// The total duty time in minutes, calculated as the difference between `endTime` and `startTime`.
    var totalDutyTime: Int {
        return endTime - startTime
    }
    
    /// A computed property that formats `totalDutyTime` (in minutes) into a readable `HH:MM` string format.
    var totalDutyTimeFormatted: String {
        String(format: "%02d:%02d", totalDutyTime / 60, totalDutyTime % 60)
    }
    
    /// The factored duty time in minutes. If `factoredDutyTimeOpt` is `nil`, it defaults to `totalDutyTime`.
    var factoredDutyTime: Int {
        return factoredDutyTimeOpt ?? totalDutyTime
    }
    
    /// A computed property that formats `factoredDutyTime` (in minutes) into a readable `HH:MM` string format.
    var factoredDutyTimeFormatted: String {
        String(format: "%02d:%02d", factoredDutyTime / 60, factoredDutyTime % 60)
    }
}
