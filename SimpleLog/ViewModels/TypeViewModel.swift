//
//  TypeViewModel.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/28/24.
//

import SwiftUICore
import SwiftData

class TypeViewModel {
    private var modelContext:ModelContext
    
    init(modelContext: ModelContext) {
        self.modelContext = modelContext
    }
    
    func deleteType (typeToDelete: TypesModel) throws {
        
        if typeToDelete.hasAircraft {
            throw OperationError.notEmpty
        } else {
            modelContext.delete(typeToDelete)
            
            // Save changes to the context
            // Propagate the error
            try modelContext.save()
        }
    }
}
