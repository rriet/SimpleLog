//
//  AboutView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI
import SwiftData

struct AboutView: View {
    @State private var showAddEdit = false
    
    var body: some View {
        VStack {
            Text("About")
        }
        .navigationTitle("About")
        
        // This doesn't exist in MacOS
#if os(iOS)
        .navigationBarTitleDisplayMode(.inline)
#endif
        .toolbar {
        // "Add new" icon - Only display if there is a view passed to this Struct
            ToolbarItem(placement: .primaryAction) {
                
                Button(action: {
                    showAddEdit.toggle()
                }) {
                    Image(systemName: "plus.app")
                        .font(.title2)
                        .fontWeight(.bold)
                        .padding(.all)
                }
                
                // fullScreenCover only works on IOS
#if os(iOS)
                .fullScreenCover(isPresented: $showAddEdit, content: {
                    FlightEditView()
                })
#else
                // sheet works on all systems, but is dismissible on IOS, not dismissible on MacOS
                .sheet(isPresented: $showAddEdit) {
                    FlightEditView()
                }
#endif
            }
        }
    }
}

#Preview("Light") {
    AboutView()
}
