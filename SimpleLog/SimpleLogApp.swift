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
    
#if os(macOS)
    // Remove the "View" "Tab" menu
    fileprivate func disallowTabbingMode() {
        NSWindow.allowsAutomaticWindowTabbing = false
    }
    
    init() {
        disallowTabbingMode()
    }
#endif
    
    // SwiftData initialization
    var sharedModelContainer: ModelContainer = {
        let schema:Schema = Schema([
            AirportModel.self,
            DutyPeriodModel.self,
            FlightModel.self,
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
        // Modify menus on MacOS
        .commands {
            CommandGroup(replacing: CommandGroupPlacement.newItem) {}
            CommandGroup(replacing: CommandGroupPlacement.toolbar) {}
            
        }
#endif
    }
}
