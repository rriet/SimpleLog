//
//  AircraftsView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI
import SwiftData

struct AircraftsView: View {
    
    var body: some View {
        SelectedView(title: "Aircrafts",
                     addEditView: AnyView(AircraftEditView()),
                     displayAddButton: true,
                     mainView: AnyView(MainAircraftView()))
    }
}

struct MainAircraftView: View {
    var body: some View {
        Text("Hi")
    }
}
