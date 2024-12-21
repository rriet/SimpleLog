//
//  ContentView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI

struct MainView: View {
    
    @State private var columnVisibility: NavigationSplitViewVisibility = .automatic
    @State var selected:String?
    
    // Get menu items
    let menu = MenuItems()
    
    // MARK: - Main View
    
    var body: some View {
        
        NavigationSplitView(columnVisibility: $columnVisibility) {
            List (selection: $selected){
                ForEach(menu.SectionItems) { section in
                     Section(header: Text(section.sectionName)) {
                         ForEach(section.items) { item in
                             NavigationLink(destination: item.destination) {
                                 Label(item.label, systemImage: item.icon)
                             }
                         }
                     }
                 }
             }
                .navigationTitle("SimpleLog")
        
        } detail: {
            LogbookView()
        }
    }
    
    // MARK: Auxiliar Functions

    /// Closes the iPad menu if the tablet is in Portrait
    func closeIpadMenu() {
#if os(iOS)
        // Close the sidebar in iPad on portrait mode
        if UIDevice.current.userInterfaceIdiom == .pad,
           UIScreen.main.bounds.size.width < UIScreen.main.bounds.size.height {
            columnVisibility = .detailOnly
    }
#endif
    }
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
