//
//  ContentView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI

struct MainView: View {
    
    // MARK: Declarations
    
    @State private var columnVisibility: NavigationSplitViewVisibility = .automatic
    @State private var preferredColumn = NavigationSplitViewColumn.detail
    @State var selected:String?
    
    @State var test:String = "A"
    
    // Get menu items
    let menu = MenuItems()
    
    // MARK: Main View
    
    var body: some View {
        
        NavigationSplitView(columnVisibility: $columnVisibility, preferredCompactColumn: $preferredColumn) {
            
            VStack {
                Text("SimpleLog  \(test)")
                    .font(.title)
                    .fontWeight(.bold)
                    .padding(.top)
                List {
                    ForEach(menu.SectionItems) { section in
                        Section(header: Text(section.sectionName)) {
                            ForEach(section.items) { item in
//                                NavigationLink(destination: item.destination) {
//                                    HStack {
//                                        Text(item.icon)
//                                            .font(.title2)
//                                            .padding(.horizontal)
//                                        Text(item.label)
//                                            .fontWeight(.bold)
//                                            .font(.title3)
//                                    }
//                                }
                                NavigationLink(value: item) {
                                    HStack {
                                        Text(item.icon)
                                            .font(.title2)
                                            .padding(.horizontal)
                                        Text(item.label)
                                            .fontWeight(.bold)
                                            .font(.title3)
                                    }
                                }
                            }
                        }
                    }
                }
                .navigationDestination(for: NavigationItem.self) { navigationItem in
                    navigationItem.destination
                }
                    .onAppear(perform: {
                        print("Teste")
                    })
                    .listStyle(.inset)
            }
                .background(.background)
#if os(macOS)
                .navigationSplitViewColumnWidth(min: 200, ideal: 200, max: 200)
#else
                .navigationTitle("Menu")
                .navigationBarHidden(true)
#endif
        
        } detail: {
            LogbookView()
        }
#if os(iOS)
//        .onChange(of: selected) {
//            test = "Teste"
//            closeIpadMenu()
//        }
#endif
    }
    
    // MARK: Auxiliar Functions

#if os(iOS)
    /// Closes the iPad menu if the tablet is in Portrait
    func closeIpadMenu() {
        test = "Teste"
        // Close the sidebar in iPad on portrait mode
        if UIDevice.current.userInterfaceIdiom == .pad,
           UIScreen.main.bounds.size.width < UIScreen.main.bounds.size.height {
            columnVisibility = .detailOnly
        }
    }
#endif
    
} // End of struct

#Preview("Light") {
    MainView()
        .modelContainer(for: Item.self, inMemory: true)
}

#Preview("Dark") {
    MainView()
        .modelContainer(for: Item.self, inMemory: true)
        .environment(\.colorScheme, .dark)
}
