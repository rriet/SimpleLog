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
    @Query var timeModels: [TimeModel]
    
    var body: some View {
        VStack {
            Button("Add Random Flights") {
                LogbookViewModel(modelContext: modelContext).addSampleData(context: modelContext)
            }
            
            List {
                ForEach(timeModels) { timeModel in
                    Section(header: Text("Time: \(timeModel.timestamp)")) {
                        // Check if there's an associated flight or duty
                        if let flight = timeModel.flightsStartingHere {
                            VStack(alignment: .leading) {
                                Text("Flight Start Time: \(flight.startTimeInt)")
                                Text("Flight End Time: \(flight.endTime)")
                                Text("Departure: \(flight.departurePlace?.name ?? "") → Arrival: \(flight.arrivalPlace?.name ?? "")")
                                Text("Aircraft: \(flight.aircraft?.registration ?? "Not Found")")
//                                Text("Aircraft: \(flight.aircraft?.registration ?? "Not Found")")
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
                        else if let duty = timeModel.dutiesStartingHere {
                            VStack(alignment: .leading) {
                                Text("Duty Start Time: \(duty.startTime)")
                                Text("Duty End Time: \(duty.endTime)")
                                Text("Total Duty Time: \(duty.totalDutyTime) minutes")
                                Text("Notes: \(duty.notes)")
                            }
                        } else if let dutyEnd = timeModel.dutiesEndingHere {
                            // Handle Duty End times separately if necessary
                            VStack(alignment: .leading) {
                                Text("Duty End Time: \(dutyEnd.endTime)")
                                Text("Associated Duty: \(dutyEnd.notes)")
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
            let timeModelToDelete = timeModels[index]
                
            // If timeModel is associated with a flight, delete flight -> cascade delete time
            if let flight = timeModelToDelete.flightsStartingHere {
                modelContext.delete(flight)
            }
            
            // Same logic, but will cascade delete start and end times
            if let dutyStart = timeModelToDelete.dutiesStartingHere {
                if let endTimeToDelete = dutyStart.endTimeRelationship {
                    modelContext.delete(endTimeToDelete)
                }
                modelContext.delete(dutyStart)
            }
            
            // Same logic here.
            if let dutyEnd = timeModelToDelete.dutiesEndingHere {
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
