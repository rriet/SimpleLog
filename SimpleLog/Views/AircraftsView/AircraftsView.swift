//
//  AircraftsView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI
import SwiftData

struct AircraftsView: View {
    @Environment(\.modelContext) private var modelContext
    @Query var aircrafts: [AircraftModel]
      
    var body: some View {
        VStack {
            Button("Add Random Aircraft") {
//                addSampleData()   Call the function to add data
            }
            
            List {
                // Display Duty Periods first
                ForEach(aircrafts) { aircraft in
                    VStack(alignment: .leading) {
                        Text("Aircraft: \(aircraft.registration)")
                        .foregroundColor(.gray)
                        
                        ForEach(aircraft.flightsWithThisAircraft) { flt in
                            Text("Flight: \(flt.departurePlace?.icao ?? "Nil")")
                        }
                        
                    }
                    .padding()
                    .swipeActions {
                        Button("Delete", systemImage: "trash", role: .destructive) {
                            deleteAirc(aircraft: aircraft)
                        }
                    }
                }
            }
        }
    }
    
    private func deleteAirc (aircraft: AircraftModel){
        modelContext.delete(aircraft)
        // Save changes to the context
        do {
            try modelContext.save()
        } catch {
            print("Error saving context after deletion: \(error)")
        }
    }
}
