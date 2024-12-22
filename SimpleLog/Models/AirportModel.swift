//
//  AirportModelClass.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/21/24.
//

import Foundation
import SwiftData

@Model
class AirportModel: Identifiable {
    var id: UUID
    var ICAO: String
    var name: String
    var latitude: Double
    var longitude: Double
    
    init(ICAO: String = "", name: String = "", latitude: Double = 0.00, longitude: Double = 0.00) {
        self.id = UUID()
        self.ICAO = ICAO
        self.name = name
        self.latitude = latitude
        self.longitude = longitude
    }
}
