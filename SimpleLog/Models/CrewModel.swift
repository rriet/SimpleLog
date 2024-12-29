//
//  CrewModel.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/23/24.
//

import Foundation
import SwiftData

/// Represents a crew member
@Model
class CrewModel: Identifiable {
    
    // MARK: - Properties
    
    /// The name of the crew member.
    var name: String
    
    /// The email address of the crew member.
    var email: String
    
    /// The phone number of the crew member.
    var phone: String
    
    /// Additional comments about the crew member.
    var coments: String
    
    /// List of flights associated with this crew member.
    /// This is a relationship to the `FlightModel` instances.
    var flightsRelationship: [FlightModel]?
    
    // MARK: - Initializer
    
    /// Initializes a new `CrewModel` instance.
    /// - Parameters:
    ///   - name: The name of the crew member. Defaults to an empty string.
    ///   - email: The email address of the crew member. Defaults to an empty string.
    ///   - phone: The phone number of the crew member. Defaults to an empty string.
    ///   - coments: Additional comments about the crew member. Defaults to an empty string.
    init(
        name: String = "",
        email: String = "",
        phone: String = "",
        coments: String = ""
    ) {
        self.name = name
        self.email = email
        self.phone = phone
        self.coments = coments
    }
}

// MARK: - Computed Properties

/// Extensions for unwrapping optional relationships.
extension CrewModel {
    /// A computed property that returns all flights associated with this crew member.
    /// If `flightsRelationship` is `nil`, it returns an empty array.
    var departureFlights: [FlightModel] {
        flightsRelationship ?? []
    }
}

