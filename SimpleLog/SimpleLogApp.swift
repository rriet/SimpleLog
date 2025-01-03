//
//  SimpleLogApp.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI
import SwiftData

@main
struct SimpleLogApp: App {
    
    
    
    
    // SwiftData initialization
    var sharedModelContainer: ModelContainer = {
        let schema:Schema = Schema([
            AircraftModel.self,
            AirportModel.self,
            CrewModel.self,
            DutyPeriodModel.self,
            FlightModel.self,
            TimelineModel.self,
            TypesModel.self
        ])
        let modelConfiguration = ModelConfiguration(schema: schema, isStoredInMemoryOnly: false)

        do {
            return try ModelContainer(for: schema, configurations: [modelConfiguration])
        } catch {
            fatalError("Could not create ModelContainer: \(error)")
        }
    }()
    
    var body: some Scene {
        WindowGroup {
            MainView()
        }
        .modelContainer(sharedModelContainer)
#if os(macOS)
        // Modify System menus on top bar of MacOS
        .commands {
            // Remove New Menu
            CommandGroup(replacing: CommandGroupPlacement.newItem) {}
        }
#endif
    }
    
    
#if os(macOS)
    // Modify System menus on top bar of MacOS
    
    // Remove the "View" "Tab" menu
    fileprivate func disallowTabbingMode() {
        NSWindow.allowsAutomaticWindowTabbing = false
    }
    
    init() {
        disallowTabbingMode()
    }
#else
    init() {
        print(URL.applicationSupportDirectory.path(percentEncoded: false))
    }
#endif
    
}
