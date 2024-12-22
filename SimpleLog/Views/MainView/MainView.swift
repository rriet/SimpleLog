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
    
    @State var selectedMenuItem = "Logbook"
    @State var selectedView = AnyView(LogbookView())
    
    // Get menu items
    let menu = MenuItems()
    
    // MARK: Main View
    var body: some View {
        NavigationSplitView(columnVisibility: $columnVisibility, preferredCompactColumn: $preferredColumn) {
            VStack {
                Text("SimpleLog")
                    .font(.title)
                    .fontWeight(.bold)
                    .padding(.top)
//                List {
                    ForEach(menu, id: \.0) { (section, items) in
//                        Section(header: Text(section)) {
                        Text(section)
                            .fontWeight(.bold)
                            .padding(.top)
                            ForEach(items) { item in
                                Button {
                                    selectedMenuItem = item.label
                                    selectedView = item.destination
                                    preferredColumn = .detail
                                } label: {
                                    HStack {
                                        Text(item.icon)
                                            .font(.title2)
//                                            .padding(.horizontal)
                                            .padding(EdgeInsets(top: 3, leading: 10, bottom: 3, trailing: 10))
                                        Text(item.label)
                                            .fontWeight(selectedMenuItem == item.label ? .black : .light)
                                            .animation(.easeIn)
                                            .font(.title3)
                                            .frame(maxWidth: .infinity, alignment: .leading)
                                    }
                                }
                                .buttonStyle(.bordered)
                            }
                        
                    }
//                }
                .listStyle(.sidebar)
            }
            .padding(.all)
            .frame(maxHeight: .infinity, alignment: .top)
                .background(.background)
#if os(macOS)
                .navigationSplitViewColumnWidth(min: 200, ideal: 200, max: 200)
#else
                .navigationTitle("Menu")
                .navigationBarHidden(true)
#endif
        
        } detail: {
            selectedView
        }
#if os(iOS)
        /// Closes the iPad menu if the tablet is in Portrait
        .onChange(of: selectedMenuItem) {
            if UIDevice.current.userInterfaceIdiom == .pad,
               UIScreen.main.bounds.size.width < UIScreen.main.bounds.size.height {
                columnVisibility = .detailOnly
            }
        }
        .onChange(of: UIScreen.main.bounds.size.width) {
            if UIDevice.current.userInterfaceIdiom == .pad,
               UIScreen.main.bounds.size.width > UIScreen.main.bounds.size.height {
                columnVisibility = .automatic
            }
        }
#endif
    }
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
