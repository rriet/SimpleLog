//
//  AirportModelClass.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/21/24.
//

import Foundation
import SwiftData

/// Represents an airport
@Model
class AirportModel: Identifiable {
    
    // MARK: - Properties
    
    /// The ICAO code of the airport (e.g., "KATL").
    var icao: String
    
    /// The IATA code of the airport (e.g., "ATL").
    var iata: String
    
    /// The full name of the airport (e.g., "Hartsfield-Jackson Atlanta International Airport").
    var name: String
    
    /// The city where the airport is located (e.g., "Atlanta").
    var city: String
    
    /// The country where the airport is located (e.g., "United States" ou "US").
    var country: String
    
    /// The geographic latitude of the airport.
    var latitude: Double
    
    /// The geographic longitude of the airport.
    var longitude: Double
    
    /// Relationship to flights that depart from this airport
    var departureFlightsRelationship: [FlightModel]?
    
    /// Relationship to flights that arrive at this airport
    var arrivalFlightsRelationship: [FlightModel]?
    
    // MARK: - Initializer
    
    /// Initializes a new `AirportModel` instance.
    /// - Parameters:
    ///   - icao: The ICAO code of the airport. Defaults to an empty string.
    ///   - iata: The IATA code of the airport. Defaults to an empty string.
    ///   - name: The name of the airport. Defaults to an empty string.
    ///   - city: The city where the airport is located. Defaults to an empty string.
    ///   - country: The country where the airport is located. Defaults to an empty string.
    ///   - latitude: The latitude of the airport. Defaults to `0.00`.
    ///   - longitude: The longitude of the airport. Defaults to `0.00`.
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

// MARK: - Computed Properties

extension AirportModel {
    /// Provides a non-optional list of flights departing from this airport.
    /// - Returns: An array of `FlightModel` objects, or an empty array if `departureFlightsRelationship` is `nil`.
    var departureFlights: [FlightModel] {
        departureFlightsRelationship ?? []
    }
    
    /// Provides a non-optional list of flights arriving at this airport.
    /// - Returns: An array of `FlightModel` objects, or an empty array if `arrivalFlightsRelationship` is `nil`.
    var arrivalFlights: [FlightModel] {
        arrivalFlightsRelationship ?? []
    }
    
    /// Validates if the latitude and longitude are within acceptable ranges.
    var isValidCoordinates: Bool {
        (-90...90).contains(latitude) && (-180...180).contains(longitude)
    }
}
