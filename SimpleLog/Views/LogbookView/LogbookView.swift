//
//  LogbookView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI
import SwiftData

struct LogbookView: View {
    
    private var addEditView: AnyView? = AnyView(Text("teste"))
    
    @State private var teste = "Teste!!!!"
    
    var body: some View {
        SelectedView(
            title: "Logbook",
            addEditView: AnyView(FlightEditView(teste: $teste)),
            displayAddButton: true,
            mainView: AnyView(MainLogbookView(teste: $teste)))
    }
}

struct MainLogbookView: View {
    
    // TODO: Delete
    @Binding var teste:String
    
    @Environment(\.modelContext) private var modelContext
    
    @Query var flights: [FlightModel]
    @Query var dutyPeriods: [DutyPeriodModel]
    
    var allItems: [any Schedulable] {
        // Combine all items into a single array
        let all = (flights as [any Schedulable]) +
        (dutyPeriods as [any Schedulable])
        
        // Sort by start time
        return all.sorted(by: { $0.startTime < $1.startTime })
    }
    
    
    var body: some View {
        VStack {
            Button("Add Random Data") {
                addSampleData()  // Call the function to add data
            }
            
            Button("Read Data") {
                fetchDataAndPrintCount()  // Call the function to add data
            }
            
            List {
                ForEach(allItems, id: \.id) { item in
                    
                    if let duty = item as? DutyPeriodModel {
                        Text("Duty Start: \(duty.startTime)")
                        Text("Duty End: \(duty.endTime)")
                    } else if let flight = item as? FlightModel {
                        Text(
                            "Flight: \(flight.startTime) to \(flight.departurePlace.name)"
                        )
                    }
                }
            }
        }
    }
    
    func fetchDataAndPrintCount() {
        do {
            // Create a FetchDescriptor for FlightModel
            let flightFetchDescriptor = FetchDescriptor<FlightModel>()
            let flights = try modelContext.fetch(flightFetchDescriptor)
            print("Number of Flights: \(flights.count)")
            
            // Create a FetchDescriptor for DutyPeriodModel
            let dutyPeriodFetchDescriptor = FetchDescriptor<DutyPeriodModel>()
            let dutyPeriods = try modelContext.fetch(dutyPeriodFetchDescriptor)
            print("Number of Duty Periods: \(dutyPeriods.count)")
        } catch {
            print("Error fetching data: \(error)")
        }
    }
    
    func addSampleData() {
        // Create random airports
        let airport1 = AirportModel(
            ICAO: "JFK",
            name: "John F. Kennedy International Airport",
            latitude: 40.6413,
            longitude: -73.7781
        )
        
        let airport2 = AirportModel(
            ICAO: "LAX",
            name: "Los Angeles International Airport",
            latitude: 33.9416,
            longitude: -118.4085
        )
        
        // Add airports to the context
        modelContext.insert(airport1)
        modelContext.insert(airport2)
        
        // Create random flights
        let flight1 = FlightModel(
            startTime: Int(Date().timeIntervalSince1970 / 60),
            endTime: Int(Date().timeIntervalSince1970 / 60) + 120,
            departurePlace: airport1,
            arrivalPlace: airport2,
            flightTime: 120
        )
        
        let flight2 = FlightModel(
            startTime: Int(Date().timeIntervalSince1970 / 60) + 180,
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
            startTime: Int(Date().timeIntervalSince1970 / 60),
            endTime: Int(Date().timeIntervalSince1970 / 60) + 480,
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
