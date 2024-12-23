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
    
    @StateObject private var viewModel = LogbookViewModel()
    
    var body: some View {
        VStack {
            Button("Add Random Flights") {
                viewModel.addSampleData(context: modelContext)
            }
            
            List {
                ForEach(timeModels, id: \.id) { timeModel in
                    Section(header: Text("Time: \(timeModel.timestamp)")) {
                        // Check if there's an associated flight or duty
                        if let flight = timeModel.flightsStartingHere.first {
                            VStack(alignment: .leading) {
                                Text(
                                    "Flight Start Time: \(flight.startTime?.timestamp ?? 0)"
                                )
//                                Text("Flight End Time: \(flight.endTime)")
                                Text("Departure: \(flight.departurePlace.name) → Arrival: \(flight.arrivalPlace.name)")
                            }
                        } else if let duty = timeModel.dutiesStartingHere.first {
                            VStack(alignment: .leading) {
//                                Text("Duty Start Time: \(duty.startTime.timestamp)")
//                                Text("Duty End Time: \(duty.endTime.timestamp)")
                                Text("Total Duty Time: \(duty.totalDutyTime) minutes")
                                Text("Notes: \(duty.notes)")
                            }
                        } else if let dutyEnd = timeModel.dutiesEndingHere.first {
                            // Handle Duty End times separately if necessary
                            VStack(alignment: .leading) {
//                                Text("Duty End Time: \(dutyEnd.endTime.timestamp)")
                                Text("Associated Duty: \(dutyEnd.notes)")
                            }
                        }
                    }
                }
                .onDelete(perform: deleteTimeModels)
            }
        }
    }

    private func deleteTimeModels(at offsets: IndexSet) {
        for index in offsets {
            let timeModelToDelete = timeModels[index]
            
            // Check and delete associated flights
            if let flight = timeModelToDelete.flightsStartingHere.first {
                modelContext.delete(timeModelToDelete)
                modelContext.delete(flight)
            }
            
            // Check and delete associated duties starting here
            else if let dutyStart = timeModelToDelete.dutiesStartingHere.first {
                if let endTimeToDelete = dutyStart.endTime{
                    modelContext.delete(timeModelToDelete)
                    modelContext.delete(endTimeToDelete)
                    modelContext.delete(dutyStart)
                }
            }
            
            // Check and delete associated duties ending here
            else if let dutyEnd = timeModelToDelete.dutiesEndingHere.first {
                if let startTimeToDelete = dutyEnd.startTime {
                    modelContext.delete(startTimeToDelete)
                    modelContext.delete(timeModelToDelete)
                    modelContext.delete(dutyEnd)
                }
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
