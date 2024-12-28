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

    var icao: String
    var iata: String
    var name: String
    var city: String
    var country: String
    var latitude: Double
    var longitude: Double
    
    var departureFlightsRelationship: [FlightModel]?
    var arrivalFlightsRelationship: [FlightModel]?
    
    init(
        icao: String = "",
        iata: String = "",
        name: String = "",
        city: String = "",
        country: String = "",
        latitude: Double = 0.00,
        longitude: Double = 0.00
    ) {
        self.icao = icao
        self.iata = iata
        self.name = name
        self.city = city
        self.country = country
        self.latitude = latitude
        self.longitude = longitude
    }
}

// Unwraping model variables
extension AirportModel {
    var departureFlights: [FlightModel] {
        departureFlightsRelationship ?? []
    }
    
    var arrivalFlights: [FlightModel] {
        arrivalFlightsRelationship ?? []
    }
}
