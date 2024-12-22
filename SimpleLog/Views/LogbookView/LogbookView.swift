//
//  LogbookView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI
import SwiftData

struct LogbookView: View {
    @Environment(\.modelContext) private var modelContext
    @StateObject private var viewModel = LogbookViewModel()
    
    @Query(sort: \FlightModel.startTime, order: .forward) var flights: [FlightModel]
    @Query(sort: \DutyPeriodModel.startTime, order: .forward) var dutyPeriods: [DutyPeriodModel]
    
    var body: some View {
        VStack{
            Button("Add Random Data") {
                viewModel.addSampleData(context: modelContext)
            }
            List {
                // Combine and sort flights and duty periods lazily
                ForEach(mergeAndSortEvents(), id: \.id) { event in
                    Text(event.title)
                }
                .onDelete { offsets in
                    viewModel.deleteItems(at: offsets, flights: flights, dutyPeriods: dutyPeriods, context: modelContext)
                }
            }
        }
        
    }
    
    private func mergeAndSortEvents() -> [SchedulableEvent] {
        var events: [SchedulableEvent] = []
        
        // Map flights to SchedulableEvents
        events.append(contentsOf: flights.map { flight in
            SchedulableEvent(
                id: flight.id,
                startTime: flight.startTime,
                title: "Flight: \(flight.startTime) to \(flight.departurePlace.name)"
            )
        })
        
        // Map duty periods to SchedulableEvents
        events.append(contentsOf: dutyPeriods.flatMap { duty in
            [
                SchedulableEvent(
                    id: UUID(), // Unique ID for Duty Start
                    startTime: duty.startTime,
                    title: "Duty Start"
                ),
                SchedulableEvent(
                    id: UUID(), // Unique ID for Duty End
                    startTime: duty.endTime,
                    title: "Duty End"
                )
            ]
        })
        
        // Sort events by startTime
        events.sort(by: { $0.startTime < $1.startTime })
        
        return events
    }
}



//import SwiftUI
//import SwiftData
//
//struct LogbookView: View {
//    @Environment(\.modelContext) private var modelContext
//    
//    // Use @Query for fetching data dynamically
//    @Query private var flights: [FlightModel]
//    @Query private var dutyPeriods: [DutyPeriodModel]
//    
//    @StateObject private var viewModel = LogbookViewModel()
//    
//    var allEvents: [SchedulableEvent] {
//        var events: [SchedulableEvent] = []
//        
//        // Map flights into events
//        events.append(contentsOf: flights.map {
//            SchedulableEvent(
//                id: $0.id,
//                startTime: $0.startTime,
//                title: "Flight: \(String($0.startTime)) to \(String($0.departurePlace.name))"
//            )
//        })
//        
//        // Map duty periods into separate start and end events
//        for duty in dutyPeriods {
//            events.append(SchedulableEvent(
//                id: UUID(),
//                startTime: duty.startTime,
//                title: "Duty Start"
//            ))
//            events.append(SchedulableEvent(
//                id: UUID(),
//                startTime: duty.endTime,
//                title: "Duty End"
//            ))
//        }
//        
//        // Sort all events by start time
//        events.sort(by: { $0.startTime < $1.startTime })
//        return events
//    }
//    
//    var body: some View {
//        VStack {
//            Button("Add Random Data") {
//                viewModel.addSampleData(context: modelContext)
//            }
//            
//            List {
//                ForEach(allEvents) { event in
//                    Text(event.title)
//                }
//                .onDelete { offsets in
//                    viewModel.deleteItems(at: offsets, flights: flights, dutyPeriods: dutyPeriods, context: modelContext)
//                }
//            }
//        }
//    }
//}
