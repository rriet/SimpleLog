//
//  CrewEditView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/22/24.
//

import SwiftUI
import SwiftData

struct CrewEditView: View {
    @Environment(\.dismiss) var dismiss
    
    var body: some View {
        VStack {
            Text("Dismiss Window")
            Button("Dismiss", action: {dismiss()})
        }
        .padding(.all)
        
    }
}
