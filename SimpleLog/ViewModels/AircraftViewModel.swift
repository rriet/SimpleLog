//
//  AircraftViewModel.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/25/24.
//

import SwiftUICore
import SwiftData

class AircraftViewModel {
    private var modelContext:ModelContext
    
    init(modelContext: ModelContext) {
        self.modelContext = modelContext
    }
    
    func deleteAircraft(aircraftToDelete: AircraftModel) throws {
        
        if aircraftToDelete.hasFlights {
            throw OperationError.notEmpty
        } else {
            modelContext.delete(aircraftToDelete)
            
            // Save changes to the context
            // Propagate the error
            try modelContext.save()
        }
    }
}

enum OperationError: Error {
    case notEmpty
}
