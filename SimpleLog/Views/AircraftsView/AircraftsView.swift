//
//  AircraftsView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI
import SwiftData

struct AircraftsView: View {
    @Environment(\.modelContext) private var modelContext
    @Query var aircrafts: [AircraftModel]
    
    @State private var showAddEdit = false
    @State private var alertType: MyAlerts? = nil
    @State private var selectedAircraft: AircraftModel?
    
    enum MyAlerts: Identifiable {
        case notEmpty
        case deleteConfirmation
        case error
        
        var id: Int { hashValue }
    }
    
    var body: some View {
        VStack {
            List {
                ForEach(aircrafts) { aircraft in
                    VStack(alignment: .leading) {
                        Text("Aircraft: \(aircraft.registration)")
                            .foregroundColor(.gray)
                        
                        if aircraft.hasFlights {
                            ForEach(aircraft.flights) { flight in
                                Text("Flight: \(flight.departurePlaceReletionship?.icao ?? "Nil")")
                            }
                        }
                    }
                    .padding()
                    .swipeActions(allowsFullSwipe: false) {
                        Button("Delete", systemImage: aircraft.hasFlights ? "trash.slash" : "trash") {
                            if aircraft.hasFlights {
                                alertType = .notEmpty
                            } else {
                                selectedAircraft = aircraft
                                alertType = .deleteConfirmation
                            }
                        }
                        .tint(aircraft.hasFlights ? .gray : .red)
                        Button("Edit", systemImage: "pencil") {
                            // Edit action
                        }
                        .tint(.green)
                    }
                }
            }
            .alert(item: $alertType) { alertType in
                myAlert()
            }
        }
        .floatingButton(
            buttonContent: AnyView(
                Image(systemName: "plus")
                    .foregroundColor(.white)
                    .font(.title)
            ),
            action: {
                showAddEdit.toggle()
            }
        )
        
        // Edit Screen
#if os(iOS)
        .fullScreenCover(isPresented: $showAddEdit, content: {
            // TODO:
        })
#else
        // sheet works on all systems, but is dismissible on IOS, not dismissible on MacOS
        .sheet(isPresented: $showAddEdit) {
            // TODO:
        }
#endif
    }
    
    private func deleteAircraft() {
        
        guard let aircraftToDelete = selectedAircraft else {
            return
        }
        
        do {
            try AircraftViewModel(modelContext: modelContext)
                .deleteAircraft(aircraftToDelete: aircraftToDelete)
        } catch {
            alertType = .error
        }
        selectedAircraft = nil
    }
    
    private func myAlert() -> Alert {
        switch alertType {
            case .notEmpty:
                return Alert(
                    title: Text("Error"),
                    message: Text("The selected aircraft cannot be deleted because it is associated with one or more flights."),
                    dismissButton: .default(Text("OK")) {
                        alertType = nil
                    }
                )
            case .deleteConfirmation:
                return Alert(
                    title: Text("Delete Aircraft"),
                    message: Text("Are you sure you want to delete \(selectedAircraft?.registration ?? "this aircraft")?"),
                    primaryButton: .destructive(Text("Delete")) {
                        deleteAircraft()
                    },
                    secondaryButton: .cancel {
                        alertType = nil
                    }
                )
            case .error, .none:
                return Alert(
                    title: Text("Error"),
                    message: Text("An error occurred while deleting the aircraft."),
                    dismissButton: .default(Text("OK")) {
                        alertType = nil
                    }
                )
        }
    }
}
