//
//  CrewView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI
import SwiftData

struct CrewView: View {
    @State private var showAddEdit = false
    
    var body: some View {
        ZStack {
            VStack{
                Button("Open") {
                    showAddEdit.toggle()
                }
                
                Text("Crew")
            }
            VStack {
                HStack {
                    Button(action: {
                        showAddEdit.toggle()
                    }) {
                        Image(systemName: "plus")
                            .font(.title)
                            .fontWeight(.bold)
                    }
                    .padding(.all, 15)
                    .background(Circle().fill(Color.accentColor))
                    .foregroundColor(.white)
                    .shadow(radius: 4)
                }
                .padding(.horizontal, 20)
                .frame(
                    maxWidth: .infinity,
                    maxHeight: .infinity,
                    alignment: .bottomTrailing
                )
            }
            .padding()
        }
        
        .navigationTitle("Crew")
#if os(iOS)
        .navigationBarTitleDisplayMode(.inline)
        .fullScreenCover(isPresented: $showAddEdit, content: {
            CrewEditView()
        })
#else
        // sheet works on all systems, but is dismissible on IOS, not dismissible on MacOS
        .sheet(isPresented: $showAddEdit) {
            CrewEditView()
        }
#endif
    }
}

#Preview("Light") {
    CrewView()
}
