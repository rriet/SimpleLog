//
//  FlightModelClass.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/21/24.
//

import Foundation
import SwiftData


@Model
class FlightModel {
    @Relationship(deleteRule: .cascade) var startTime: TimeModel?
    var endTime: Int
    var departurePlace: AirportModel?
    var arrivalPlace: AirportModel?
    var flightTime: Int
    
    init(
        startTime: TimeModel = TimeModel(),
        endTime: Int = Int(Date().timeIntervalSince1970 / 60),
        departurePlace: AirportModel = AirportModel(),
        arrivalPlace: AirportModel = AirportModel(),
        flightTime: Int = 0
    ) {
        self.startTime = startTime
        self.endTime = endTime
        self.departurePlace = departurePlace
        self.arrivalPlace = arrivalPlace
        self.flightTime = flightTime
    }
}

extension FlightModel {
    var startTimeInt: Int {
        return startTime?.timestamp ?? 0
    }
}
