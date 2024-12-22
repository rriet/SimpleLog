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
            startTime: Int(Date().timeIntervalSince1970 / 60),
            endTime: Int(Date().timeIntervalSince1970 / 60 + 120),
            departurePlace: sampleAirport,
            arrivalPlace: sampleAirport,
            flightTime: 120
        )
        let dutyPeriod = DutyPeriodModel(
            startTime: Int(Date().timeIntervalSince1970 / 60),
            endTime: Int(Date().timeIntervalSince1970 / 60 + 480),
            totalDutyTime: 480,
            notes: "Example Duty Period"
        )
        
        context.insert(flight)
        context.insert(dutyPeriod)
        
        do {
            try context.save()
        } catch {
            print("Error adding data: \(error)")
        }
    }
    
    func deleteItems(at offsets: IndexSet, flights: [FlightModel], dutyPeriods: [DutyPeriodModel], context: ModelContext) {
        for index in offsets {
            // Combine all events and map them into SchedulableEvent
            let eventToDelete = (flights.map { flight in
                SchedulableEvent(id: flight.id, startTime: flight.startTime, title: "Flight: \(flight.startTime) to \(flight.departurePlace.name)")
            } + dutyPeriods.flatMap { duty in
                [
                    SchedulableEvent(id: UUID(), startTime: duty.startTime, title: "Duty Start"),
                    SchedulableEvent(id: UUID(), startTime: duty.endTime, title: "Duty End")
                ]
            })[index]
            
            // Delete the actual object from context based on the event ID
            if let flight = flights.first(where: { $0.id == eventToDelete.id }) {
                context.delete(flight)
            } else if let duty = dutyPeriods.first(where: { $0.startTime == eventToDelete.startTime }) {
                context.delete(duty)
            }
        }
        
        // Save the context after deletion
        do {
            try context.save()
        } catch {
            print("Error deleting items: \(error)")
        }
    }


}
