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
    
    @Binding var teste:String
    
    @Environment(\.dismiss) var dismiss
    
    var body: some View {
        ViewThatFits {
            viewiPad(teste: $teste)
            viewiPhone(teste: $teste)
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
            Text("iPad Edit - \(teste)")
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
    
    @Binding var teste:String
    
    @Environment(\.dismiss) var dismiss
    var body: some View {
        VStack {
            Text("iPhone Edit - \(teste)")
            Button("Dismiss", action: close)

        }
        .frame( width: CGFloat(200))
    }
    func close() {
        teste = "Funciona!!!"
        dismiss()
    }
}
