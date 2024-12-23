//
//  AircraftsView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI
import SwiftData

struct AircraftsView: View {
//    @Environment(\.modelContext) private var modelContext
    
//    // Using @Query to fetch FlightModel and DutyPeriodModel, sorted by startTime
//    @Query(sort: \FlightModel.startTime) private var flights: [FlightModel]
//    @Query(sort: \DutyPeriodModel.startTime) private var dutyPeriods: [DutyPeriodModel]
//    
    var body: some View {
        VStack {
            Button("Add Random Data") {
//                addSampleData()   Call the function to add data
            }
            
            Button("Print Data Count") {
//                fetchDataAndPrintCount() // Print the count of data in the context
            }
            
//            List {
//                // Display Duty Periods first
//                ForEach(dutyPeriods) { duty in
//                    VStack(alignment: .leading) {
//                        Text("Duty Start: \(duty.startTime)")
//                        Text("Duty End: \(duty.endTime)")
//                        Text("Total Duty Time: \(duty.totalDutyTime) minutes")
//                            .foregroundColor(.gray)
//                    }
//                    .padding()
//                }
//                
//                // Then display Flights
//                ForEach(flights) { flight in
//                    VStack(alignment: .leading) {
//                        Text("Flight from \(flight.departurePlace.name) to \(flight.arrivalPlace.name)")
//                        Text("Start Time: \(flight.startTime)")
//                        Text("End Time: \(flight.endTime)")
//                        Text("Flight Time: \(flight.flightTime) minutes")
//                            .foregroundColor(.gray)
//                    }
//                    .padding()
//                }
//            }
        }
    }
}
