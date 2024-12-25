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
            viewiPhone()
        }
        
    }
    
    func close() {
        dismiss()
    }
}

struct viewiPhone: View {
    
    @Environment(\.modelContext) private var modelContext
    @Environment(\.dismiss) var dismiss
    
    var body: some View {
        VStack {
            Text("iPhone Edit")
            Button("Dismiss", action: {dismiss()})

        }
        .frame( width: CGFloat(200))
    }
}
