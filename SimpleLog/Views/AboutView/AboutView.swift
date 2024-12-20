//
//  AboutView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI
import SwiftData

struct AboutView: View {
    @State private var showAlert = false
    
    var body: some View {
        VStack {
            Text("About")
        }
        .navigationTitle("About")
        .toolbar {
            ToolbarItem(placement: .primaryAction) {
                Button(action: {
                    
                    print("Action button tapped!")
                    
                    
                }) {
                    Image(systemName: "plus")
                }
            }
        }
    }
}

#Preview("Light") {
    AboutView()
}
