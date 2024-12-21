//
//  MenuItems.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/20/24.
//

import Foundation
import SwiftUICore

struct NavigationItem: Identifiable, Hashable {
    static func == (lhs: NavigationItem, rhs: NavigationItem) -> Bool {
        lhs.label == rhs.label
    }
    
    func hash(into hasher: inout Hasher) {
        hasher.combine(id)
    }
    
    
    let id = UUID()
    let label: String
    let icon: String
    let destination: AnyView
}

struct SectionItem: Identifiable {
    let id = UUID()
    let sectionName: String
    let items: [NavigationItem]
}

struct Menu {
    var SectionItems: [SectionItem]
}

func MenuItems() -> Menu {
    let mainMenu:[NavigationItem] = [
        NavigationItem(label: "Logbook", icon: "📒", destination: AnyView(LogbookView())),
        NavigationItem(label: "Aircrafts", icon: "✈️", destination: AnyView(AircraftsView())),
        NavigationItem(label: "Airports", icon: "📍", destination: AnyView(AirportsView())),
        NavigationItem(label: "Crew", icon: "👨🏼‍✈️", destination: AnyView(CrewView()))
    ]
    
    let reportMenu:[NavigationItem] = [
        NavigationItem(label: "Summary", icon: "📄", destination: AnyView(SummaryView())),
        NavigationItem(label: "Reports", icon: "🖨️", destination: AnyView(ReportsView()))
    ]
    
    let systemMenu:[NavigationItem] = [
        NavigationItem(label: "Settings", icon: "⚙️", destination: AnyView(SettingsView())),
        NavigationItem(label: "About", icon: "💡", destination: AnyView(AboutView()))
    ]
    
    let menu = Menu(
        SectionItems:[
            SectionItem(sectionName:"",
                items: mainMenu),
            SectionItem(sectionName:"Reports",
                items:reportMenu),
            SectionItem(sectionName:"System",
                items:systemMenu)
        ]
    )
    
    return menu
}
