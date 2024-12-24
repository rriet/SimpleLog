//
//  FlightEditView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/20/24.
//

import SwiftUI
import SwiftData

struct FlightEditView: View {
    @Environment(\.dismiss) var dismiss
    
    var body: some View {
        ViewThatFits {
//            viewiPad()
            viewiPhone()
        }
        
    }
    
    func close() {
        dismiss()
    }
}

struct viewiPad: View {
    
    
    
    @Binding var teste:String
    
    @Environment(\.dismiss) var dismiss
    var body: some View {
        VStack {
            Text("iPad Edit")
            Button("Dismiss", action: close)
        }
        .frame( width: CGFloat(500))
    }
    func close() {
        teste = "Funciona!!!"
        dismiss()
    }
}

struct viewiPhone: View {
    
    @Environment(\.modelContext) private var modelContext
    
    @Environment(\.dismiss) var dismiss
    var body: some View {
        VStack {
            Text("iPhone Edit")
            Button("Add Data", action: addSampleData)
            Button("Dismiss", action: close)

        }
        .frame( width: CGFloat(200))
    }
    func close() {
        dismiss()
    }
    
    func addSampleData() {
        // Create random airports
        let airport1 = AirportModel(
            icao: "JFK",
            name: "John F. Kennedy International Airport",
            latitude: 40.6413,
            longitude: -73.7781
        )
        
        let airport2 = AirportModel(
            icao: "LAX",
            name: "Los Angeles International Airport",
            latitude: 33.9416,
            longitude: -118.4085
        )
        
        // Add airports to the context
        modelContext.insert(airport1)
        modelContext.insert(airport2)
        
        // Create random flights
        let flight1 = FlightModel(
//            startTime: Int(Date().timeIntervalSince1970 / 60),
            endTime: Int(Date().timeIntervalSince1970 / 60) + 120,
            departurePlace: airport1,
            arrivalPlace: airport2,
            flightTime: 120
        )
        
        let flight2 = FlightModel(
//            startTime: Int(Date().timeIntervalSince1970 / 60) + 180,
            endTime: Int(Date().timeIntervalSince1970 / 60) + 300,
            departurePlace: airport2,
            arrivalPlace: airport1,
            flightTime: 120
        )
        
        // Add flights to the context
        modelContext.insert(flight1)
        modelContext.insert(flight2)
        
        // Create a random duty period
        let dutyPeriod = DutyPeriodModel(
//            startTime: Int(Date().timeIntervalSince1970 / 60),
//            endTime: Int(Date().timeIntervalSince1970 / 60) + 480,
            totalDutyTime: 480,
            notes: "Test Duty Period"
        )
        
        // Add duty period to the context
        modelContext.insert(dutyPeriod)
        
        // Save the context
        do {
            try modelContext.save()
            print("Sample data added!")
        } catch {
            print("Failed to save sample data: \(error)")
        }
    }
}
