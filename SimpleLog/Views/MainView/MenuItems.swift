//
//  MenuItems.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/20/24.
//

import Foundation
import SwiftUICore

struct NavigationItem: Identifiable {
    let id = UUID()
    let sectionName: String
    let label: String
    let icon: String
    let destination: AnyView
}

func MenuItems() -> [(String, [NavigationItem])] {
    let mainMenu:[NavigationItem] = [
        NavigationItem(sectionName: "", label: "Logbook", icon: "📒", destination: AnyView(LogbookView())),
        NavigationItem(sectionName: "",label: "Aircrafts", icon: "✈️", destination: AnyView(AircraftsView())),
        NavigationItem(sectionName: "",label: "Airports", icon: "📍", destination: AnyView(AirportsView())),
        NavigationItem(sectionName: "",label: "Crew", icon: "👨🏼‍✈️", destination: AnyView(CrewView())),
        NavigationItem(sectionName: "Reports",label: "Summary", icon: "📄", destination: AnyView(SummaryView())),
        NavigationItem(sectionName: "Reports",label: "Reports", icon: "🖨️", destination: AnyView(ReportsView())),
        NavigationItem(sectionName: "System",label: "Settings", icon: "⚙️", destination: AnyView(SettingsView())),
        NavigationItem(sectionName: "System",label: "About", icon: "💡", destination: AnyView(AboutView()))
    ]
    
    let grouped = Dictionary(grouping: mainMenu, by: { $0.sectionName })
    return grouped.sorted(by: { $0.key < $1.key })
}
