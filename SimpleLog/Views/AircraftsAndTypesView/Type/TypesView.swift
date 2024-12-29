//
//  TypesView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/28/24.
//

import SwiftUI
import SwiftData

struct TypesView: View {
    @Environment(\.modelContext) private var modelContext
    @Query var typeList: [TypesModel]
    
    @State private var showAddEdit = false
    @State private var alertType: MyAlerts? = nil
    @State private var selectedType: TypesModel?
    
    enum MyAlerts: Identifiable {
        case notEmpty
        case deleteConfirmation
        case error
        
        var id: Int { hashValue }
    }
    
    var body: some View {
        VStack {
            Text("Types")
                .font(.largeTitle)
                .padding(.top, 0)
            List {
                ForEach(typeList) { type in
                    VStack(alignment: .leading) {
                        Text("Type: \(type.designator)")
                            .foregroundColor(.gray)
                        
                        if type.hasAircraft {
                            ForEach(type.aircrafts) { aircraft in
                                Text("Aircraft: \(aircraft.registration)")
                            }
                        }
                    }
                    .padding()
                    .swipeActions(allowsFullSwipe: false) {
                        Button("Delete", systemImage: type.hasAircraft ? "trash.slash" : "trash") {
                            if type.hasAircraft {
                                alertType = .notEmpty
                            } else {
                                selectedType = type
                                alertType = .deleteConfirmation
                            }
                        }
                        .tint(type.hasAircraft ? .gray : .red)
                        Button("Edit", systemImage: "pencil") {
                            // Edit action
                        }
                        .tint(.green)
                    }
                }
            }
        }
        .alert(item: $alertType) { alertType in
            myAlert()
        }
        .background(Color(UIColor.secondarySystemBackground))
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
        
        guard let typeToDelete = selectedType else {
            return
        }
        
        do {
            try TypeViewModel(modelContext: modelContext)
                .deleteType(typeToDelete: typeToDelete)
        } catch {
            alertType = .error
        }
        selectedType = nil
    }
    
    private func myAlert() -> Alert {
        switch alertType {
            case .notEmpty:
                return Alert(
                    title: Text("Error"),
                    message: Text("The selected Type cannot be deleted because it is associated with one or more Aircrafts."),
                    dismissButton: .default(Text("OK")) {
                        alertType = nil
                    }
                )
            case .deleteConfirmation:
                return Alert(
                    title: Text("Delete Aircraft"),
                    message: Text("Are you sure you want to delete this Type"),
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
                    message: Text("An error occurred while deleting the type."),
                    dismissButton: .default(Text("OK")) {
                        alertType = nil
                    }
                )
        }
    }
}

