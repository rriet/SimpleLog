//
//  AircraftEditView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/21/24.
//

import SwiftUI
import SwiftData

struct AircraftEditView: View {
    
    @Environment(\.dismiss) var dismiss
    
    var body: some View {
        ViewThatFits {
            Button("Dismiss", action: {dismiss()})
        }
        
    }
}
