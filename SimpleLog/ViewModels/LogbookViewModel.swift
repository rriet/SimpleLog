//
//  LogbookVM.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/22/24.
//

import SwiftUICore
import SwiftData

class LogbookViewModel: ObservableObject {
    private var modelContext:ModelContext
    
    init(modelContext: ModelContext) {
        self.modelContext = modelContext
    }
    
    func deleteFlight(flightToDelete: FlightModel) -> Bool {
        
        modelContext.delete(flightToDelete)
        
        // Save changes to the context
        do {
            try modelContext.save()
        } catch {
            return false
        }
        return true
    }
    
    func addSampleData(context: ModelContext) {
        let sampleAirport = AirportModel(icao: "KATL", name: "Atlanta Airport", latitude: 33.6407, longitude: -84.4277)
        let sampleAirport2 = AirportModel(icao: "KMIA", name: "Miami Airport", latitude: 33.6407, longitude: -84.4277)
        
        let type1 = TypesModel(
            designator: "C175",
            modelName: "Cesna 175",
            modelGroup: "Cesna",
            manufacturer: "Cesna Inc",
            aircraftClass: .Landplane,
            multiEngine: false,
            engineType: .Piston,
            icaoWeights: .L,
            mtow: 22555,
            multiPilot: false,
            efis: false,
            Complex: false,
            highPerformance: false
        )
        
        let type2 = TypesModel(
            designator: "A320",
            modelName: "Airbus 320",
            modelGroup: "Airbus 320",
            manufacturer: "Airbus",
            aircraftClass: .Landplane,
            multiEngine: true,
            engineType: .Jet,
            icaoWeights: .H,
            mtow: 77000,
            multiPilot: true,
            efis: true,
            Complex: true,
            highPerformance: true
        )
        
        let type3 = TypesModel(
            designator: "A321",
            modelName: "Airbus 321",
            modelGroup: "Airbus 320",
            manufacturer: "Airbus",
            aircraftClass: .Landplane,
            multiEngine: true,
            engineType: .Jet,
            icaoWeights: .H,
            mtow: 77000,
            multiPilot: true,
            efis: true,
            Complex: true,
            highPerformance: true
        )
        
        let bird1 = AircraftModel(registration: "PT-PTU", type: type1)
        let bird2 = AircraftModel(registration: "A7-BPP", type: type2)
        let bird3 = AircraftModel(registration: "A7-BTT", type: type2)
        let bird4 = AircraftModel(registration: "A7-BPY", type: type3)
        
        let flight = FlightModel(
            startTime: Int(Date().timeIntervalSince1970 / 60 - 1000),
            endTime: Int(Date().timeIntervalSince1970 / 60 - 950 ),
            departurePlace: sampleAirport,
            arrivalPlace: sampleAirport2,
            aircraft: bird1,
            flightTime: 50
        )
        
        let flight2 = FlightModel(
            startTime: Int(Date().timeIntervalSince1970 / 60 - 200),
            endTime: Int(Date().timeIntervalSince1970 / 60 - 180),
            departurePlace: sampleAirport2,
            arrivalPlace: sampleAirport,
            aircraft: bird2,
            flightTime: 20
        )
        
        let dutyPeriod = DutyPeriodModel(
            startTime: Int(Date().timeIntervalSince1970 / 60 + 200 ),
            endTime: Int(Date().timeIntervalSince1970 / 60 + 450),
            notes: "Example Duty Period"
        )
        
        context.insert(bird3)
        context.insert(bird4)
        context.insert(flight)
        context.insert(flight2)
        context.insert(dutyPeriod)
        
        do {
            try context.save()
        } catch {
            print("Error adding data: \(error)")
        }
    }
    
    func deleteItems(at offsets: IndexSet, flights: [FlightModel], dutyPeriods: [DutyPeriodModel], context: ModelContext) {
    }


}
