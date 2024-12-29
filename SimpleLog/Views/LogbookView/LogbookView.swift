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
    @Query var timeLine: [TimelineModel]
    
    var body: some View {
        VStack {
            Button("Add Random Flights") {
                LogbookViewModel(modelContext: modelContext).addSampleData(context: modelContext)
            }
            
            List {
                ForEach(timeLine) { event in
                    Section(header: Text("Time: \(event.timestamp)")) {
                        // Check if there's an associated flight or duty
                        if event.hasDutieStart {
                            let duty = event.dutieStart
                            VStack(alignment: .leading) {
                                Text("Duty Start Time: \(duty.startTime)")
                                Text("Duty End Time: \(duty.endTime)")
                                Text("Total Duty Time: \(duty.totalDutyTimeFormatted) minutes")
                                Text("Notes: \(duty.notes)")
                            }
                        } else if event.hasDutieEnd {
                            let duty = event.dutieEnd
                            VStack(alignment: .leading) {
                                Text("Duty End Time: \(duty.endTime)")
                                Text("Associated Duty: \(duty.notes)")
                            }
                        } else if event.hasFlightStart {
                            let flight = event.flightStart
                            VStack(alignment: .leading) {
                                Text("Flight Start Time: \(flight.startTime)")
                                Text("Flight End Time: \(flight.endTime)")
                                Text("Departure: \(flight.departurePlace.name) → Arrival: \(flight.arrivalPlace.name)")
                                Text("Aircraft: \(flight.aircraft.registration)")
                            }
                            .swipeActions(allowsFullSwipe: false) {
                                Button(
                                    "Delete",
                                    systemImage: "trash",
                                    role: .destructive
                                ){
                                    deleteFlight(flightToDelete: flight)
                                }
                                Button("Edit", systemImage: "pencil") {
                                    //                            deleteAirc(aircraft: aircraft)
                                }
                                .tint(.green)
                            }
                        }
                    }
                }
                .onDelete(perform: deleteTimeModels)
            }
        }
    }
    
    private func deleteFlight(flightToDelete: FlightModel) {
        if LogbookViewModel(modelContext: modelContext).deleteFlight(flightToDelete: flightToDelete) {
            print("Deleted - LogbookView")
        } else {
            print("Error - LogbookView")
        }
    }
    
    private func deleteTimeModels(at offsets: IndexSet) {
        for index in offsets {
            // get model at the index
            let timeModelToDelete = timeLine[index]
                
            // If timeModel is associated with a flight, delete flight -> cascade delete time
            if let flight = timeModelToDelete.flightStartReletionship {
                modelContext.delete(flight)
            }
            
            // Same logic, but will cascade delete start and end times
            if let dutyStart = timeModelToDelete.dutieStartRelationship {
                if let endTimeToDelete = dutyStart.endTimeRelationship {
                    modelContext.delete(endTimeToDelete)
                }
                modelContext.delete(dutyStart)
            }
            
            // Same logic here.
            if let dutyEnd = timeModelToDelete.dutieEndRelationship {
                if let startTimeToDelete = dutyEnd.startTimeRelationship {
                    modelContext.delete(startTimeToDelete)
                }
                modelContext.delete(dutyEnd)
            }
            
            // Save changes to the context
            do {
                try modelContext.save()
            } catch {
                print("Error saving context after deletion: \(error)")
            }
        }
    }
}
