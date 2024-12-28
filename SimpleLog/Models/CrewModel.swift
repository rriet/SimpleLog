//
//  CrewModel.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/23/24.
//

import Foundation
import SwiftData

@Model
class CrewModel: Identifiable {

    var name: String
    var email: String
    var phone: String
    var coments: String
    
    var flightsRelationship: [FlightModel]?
    
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

// Unwraping model variables
extension CrewModel {
    var departureFlights: [FlightModel] {
        flightsRelationship ?? []
    }
}
