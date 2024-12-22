//
//  FlightModelClass.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/21/24.
//

import Foundation
import SwiftData


@Model
class FlightModel: Schedulable {
    var id: UUID
    var startTime: Int
    var endTime: Int
    var departurePlace: AirportModel
    var arrivalPlace: AirportModel
    var flightTime: Int
    
    init(
        startTime: Int = Int(Date().timeIntervalSince1970 / 60),
        endTime: Int = Int(Date().timeIntervalSince1970 / 60),
        departurePlace: AirportModel = AirportModel(),
        arrivalPlace: AirportModel = AirportModel(),
        flightTime: Int) {
        
        self.id = UUID()
        self.startTime = startTime
        self.endTime = endTime
        self.departurePlace = departurePlace
        self.arrivalPlace = arrivalPlace
        self.flightTime = flightTime
    }
}
