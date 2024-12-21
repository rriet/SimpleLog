//
//  FlightEditView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/20/24.
//

import SwiftUI
import SwiftData

struct FlightEditView: View {
    @State private var showAlert = false
    
    @Environment(\.dismiss) var dismiss
    
    var body: some View {
        ViewThatFits {
            viewiPad()
            viewiPhone()
        }
        
    }
    
    func close() {
        dismiss()
    }
}

struct viewiPad: View {
    @Environment(\.dismiss) var dismiss
    var body: some View {
        VStack {
            Text("iPad Edit")
            Button("Dismiss", action: close)
        }
        .frame( width: CGFloat(500))
    }
    func close() {
        dismiss()
    }
}

struct viewiPhone: View {
    @Environment(\.dismiss) var dismiss
    var body: some View {
        VStack {
            Text("iPhone Edit")
            Button("Dismiss", action: close)

        }
        .frame( width: CGFloat(200))
    }
    func close() {
        dismiss()
    }
}
