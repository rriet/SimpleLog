//
//  ContentView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI

struct MainView: View {
    @State private var columnVisibility: NavigationSplitViewVisibility = .automatic
    @State private var selectedItem: String? = "Logbook" // Default selection
    
    // MARK: - Main View
    
    var body: some View {
        NavigationSplitView(columnVisibility: $columnVisibility) {
//            List{
            List (selection: $selectedItem){
                // Main Navigation Links
                ForEach(mainNavigationItems, id: \.label) { item in
                    NavigationLink(destination: item.destination) {
                        Label(item.label, systemImage: item.icon)
                    }
                }
                
                // Reports Section
                Section(header: Text("Reports")) {
                    ForEach(reportItems, id: \.label) { item in
                        NavigationLink(destination: item.destination) {
                            Label(item.label, systemImage: item.icon)
                        }
                    }
                }
                
                // System Section
                Section(header: Text("System")) {
                    ForEach(systemItems, id: \.label) { item in
                        NavigationLink(destination: item.destination) {
                            Label(item.label, systemImage: item.icon)
                        }
                    }
                }
            }
            .navigationTitle("SimpleLog")
            
        } detail: {
            LogbookView()
        }
        .listStyle(.sidebar)
        .onChange(of: selectedItem) {
//            self.selectedItem = selectedItem
#if os(iOS)

            // Close the sidebar in iPad on portrait mode
            if UIDevice.current.userInterfaceIdiom == .pad,
               UIScreen.main.bounds.size.width < UIScreen.main.bounds.size.height {
                columnVisibility = .detailOnly
            }
#endif
        }
        
    }
    
    // MARK: - Data Models

    struct NavigationItem {
        let label: String
        let icon: String
        let destination: AnyView
    }
    
    // MARK: - Navigation Data
    
    let mainNavigationItems = [
        NavigationItem(label: "Logbook", icon: "book.fill", destination: AnyView(LogbookView())),
        NavigationItem(label: "Aircrafts", icon: "airplane", destination: AnyView(AircraftsView())),
        NavigationItem(label: "Airports", icon: "mappin.circle", destination: AnyView(AirportsView())),
        NavigationItem(label: "Crew", icon: "person.crop.circle", destination: AnyView(CrewView()))
    ]

    let reportItems = [
        NavigationItem(label: "Summary", icon: "sum", destination: AnyView(SummaryView())),
        NavigationItem(label: "Reports", icon: "chart.dots.scatter", destination: AnyView(ReportsView()))
    ]

    let systemItems = [
        NavigationItem(label: "Settings", icon: "gear", destination: AnyView(SettingsView())),
        NavigationItem(label: "About", icon: "info.circle", destination: AnyView(AboutView()))
    ]
}

#Preview("Light") {
    MainView()
        .modelContainer(for: Item.self, inMemory: true)
}

#Preview("Dark") {
    MainView()
        .modelContainer(for: Item.self, inMemory: true)
        .environment(\.colorScheme, .dark)
}
