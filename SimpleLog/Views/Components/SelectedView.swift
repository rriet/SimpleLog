//
//  SelectedView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/20/24.
//

import SwiftUI

struct SelectedView: View {
    @State private var showAlert = false
    @Environment(\.dismiss) private var dismiss
    @State private var showFlight = false
    let title: String
    
    
    var body: some View {
        ScrollView {
            Text(title)
        }
#if os(iOS)
        .navigationBarBackButtonHidden(UIDevice.current.userInterfaceIdiom == .phone)
        .navigationBarTitleDisplayMode(.inline)
#endif
        .navigationTitle(title)
        .toolbar {
#if os(iOS)
            if UIDevice.current.userInterfaceIdiom == .phone {
                ToolbarItem(placement: .topBarLeading) {
                    Button(action: {
                        dismiss()
                    }) {
                        Image(systemName: "house")
                            .fontWeight(.bold)
                            .padding(.all)
                    }
                }
            }
#endif
            
            ToolbarItem(placement: .primaryAction) {
                HStack{
                    Button(action: {
                        showFlight.toggle()
                    }) {
                        Image(systemName: "plus.app")
                            .font(.title2)
                            .fontWeight(.bold)
                            .padding(.all)
                    }
#if os(iOS)
                        .fullScreenCover(isPresented: $showFlight, content: {
                            FlightEditView()
                        })
#else
                        .sheet(isPresented: $showFlight) {
                            FlightEditView()
                        }
#endif
                }
                
            }
        }
    }
}

#Preview {
    SelectedView(title: "Title")
}
