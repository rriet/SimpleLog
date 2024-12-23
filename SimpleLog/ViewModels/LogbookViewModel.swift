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
        let sampleAirport = AirportModel(ICAO: "KATL", name: "Atlanta Airport", latitude: 33.6407, longitude: -84.4277)
        
        let flight = FlightModel(
//            startTime: Int(Date().timeIntervalSince1970 / 60),
            endTime: Int(Date().timeIntervalSince1970 / 60 + 120),
            departurePlace: sampleAirport,
            arrivalPlace: sampleAirport,
            flightTime: 120
        )
        let dutyPeriod = DutyPeriodModel(
//            startTime: Int(Date().timeIntervalSince1970 / 60),
//            endTime: Int(Date().timeIntervalSince1970 / 60 + 480),
            totalDutyTime: 480,
            notes: "Example Duty Period"
        )
        
        context.insert(flight)
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
