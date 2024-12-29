//
//  AircraftAndTypesView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/28/24.
//

import SwiftUI
import SwiftData

struct AircraftAndTypesView: View {
    
    @State var selectedView: String = "Types"
    
    
    var body: some View {
        ViewThatFits {
            HStack {
                TypesView()
                    .frame(minWidth: 300)
                AircraftsView()
                    .frame(minWidth: 300)
            }
            VStack {
                VStack {
                    // Picker for selecting the view
                    Picker(
                        selection: $selectedView, // Bind to the selectedView state
                        label: Text("Select View") // Label for the Picker
                    ) {
                        Text("Types").tag("Types") // Option for Types
                        Text("Aircrafts").tag("Aircrafts") // Option for Aircrafts
                    }
                    .pickerStyle(SegmentedPickerStyle()) // Optionally, you can change the style
                    
                    // Conditionally render views based on the selectedView
                    if selectedView == "Types" {
                        // Render the Types view
                        TypesView()
                            .frame(maxHeight: .infinity)
                    } else if selectedView == "Aircrafts" {
                        // Render the Aircrafts view
                        AircraftsView()
                            .frame(maxHeight: .infinity)
                    }
                }
                .padding()
            }
            
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color(UIColor.secondarySystemBackground))
    }
}
