//
//  SettingsView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI
import SwiftData

struct SettingsView: View {
    @State private var showAlert = false
    
    var body: some View {
        ViewThatFits {
            HStack {
                CrewView()
                AboutView()
            }
            .frame(minWidth: 800)
            VStack {
                Text("Smal Display")
                AboutView()
            }
            
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color.red)
    }
}


#Preview("Light") {
    SettingsView()
}
