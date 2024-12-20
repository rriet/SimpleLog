//
//  SummaryView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI
import SwiftData

struct SummaryView: View {
    @State private var showAlert = false
    
    var body: some View {
        VStack {
            Text("Summary")
        }
        .navigationTitle("Summary")
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
