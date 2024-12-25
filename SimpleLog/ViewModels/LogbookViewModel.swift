//
//  LogbookVM.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/22/24.
//

import Foundation
import SwiftData

struct SchedulableEvent: Identifiable {
    var id: UUID
    var startTime: Int
    var title: String
}

class LogbookViewModel: ObservableObject {
    func addSampleData(context: ModelContext) {
        let sampleAirport = AirportModel(icao: "KATL", name: "Atlanta Airport", latitude: 33.6407, longitude: -84.4277)
        let sampleAirport2 = AirportModel(icao: "KMIA", name: "Miami Airport", latitude: 33.6407, longitude: -84.4277)
        
        let bird1 = AircraftModel(registration: "PT-PTU")
        let bird2 = AircraftModel(registration: "A7-BPP")
        
        let flight = FlightModel(
            startTime: TimeModel(timestamp: Int(Date().timeIntervalSince1970 / 60)),
            endTime: Int(Date().timeIntervalSince1970 / 60 + 120),
            departurePlace: sampleAirport,
            arrivalPlace: sampleAirport2,
            aircraft: bird1,
            flightTime: 120
        )
        let flight2 = FlightModel(
            startTime: TimeModel(timestamp: Int(Date().timeIntervalSince1970 / 200)),
            endTime: Int(Date().timeIntervalSince1970 / 60 + 180),
            departurePlace: sampleAirport2,
            arrivalPlace: sampleAirport,
            aircraft: bird2,
            flightTime: 120
        )
        let dutyPeriod = DutyPeriodModel(
            startTime: TimeModel(timestamp: Int(Date().timeIntervalSince1970 / 80)),
            endTime: TimeModel(timestamp: Int(Date().timeIntervalSince1970 / 160)
            ),
            totalDutyTime: 480,
            notes: "Example Duty Period"
        )
        
        context.insert(flight)
        context.insert(flight2)
        context.insert(dutyPeriod)
        
        do {
            print("Saved")
            try context.save()
        } catch {
            print("Error adding data: \(error)")
        }
    }
    
    func deleteItems(at offsets: IndexSet, flights: [FlightModel], dutyPeriods: [DutyPeriodModel], context: ModelContext) {
    }


}
