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
        NavigationItem(label: "Logbook", icon: "book.fill", destination: AnyView(LogbookView())),
        NavigationItem(label: "Aircrafts", icon: "airplane", destination: AnyView(AircraftsView())),
        NavigationItem(label: "Airports", icon: "mappin.circle", destination: AnyView(AirportsView())),
        NavigationItem(label: "Crew", icon: "person.crop.circle", destination: AnyView(CrewView()))
    ]
    
    let reportMenu:[NavigationItem] = [
        NavigationItem(label: "Summary", icon: "sum", destination: AnyView(SummaryView())),
        NavigationItem(label: "Reports", icon: "chart.dots.scatter", destination: AnyView(ReportsView()))
    ]
    
    let systemMenu:[NavigationItem] = [
        NavigationItem(label: "Settings", icon: "gear", destination: AnyView(SettingsView())),
        NavigationItem(label: "About", icon: "info.circle", destination: AnyView(AboutView()))
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
