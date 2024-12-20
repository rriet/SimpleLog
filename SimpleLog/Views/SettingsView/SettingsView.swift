//
//  SettingsView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI
import SwiftData

struct SettingsView: View {
    @State private var showAlert = false
    
    var body: some View {
        VStack {
            Text("Settings")
        }
        .navigationTitle("Settings")
        .toolbar {
            ToolbarItem(placement: .primaryAction) {
                Button(action: {
                }) {
                    Image(systemName: "plus")
                }
            }
        }
    }
}
