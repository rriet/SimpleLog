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
    let title: String
    
    @State private var showAddEdit = false
    @State var addEditView: AnyView?
    
    @State var mainView: AnyView?
    
    var body: some View {
        ScrollView {
            mainView
        }
#if os(iOS)
        .navigationBarTitleDisplayMode(.inline)
#endif
        .navigationTitle(title)
        .toolbar {
            if let newView = addEditView {
                ToolbarItem(placement: .primaryAction) {
                
                    Button(action: {
                        showAddEdit.toggle()
                    }) {
                        Image(systemName: "plus.app")
                            .font(.title2)
                            .fontWeight(.bold)
                            .padding(.all)
                    }
#if os(iOS)
                        .fullScreenCover(isPresented: $showAddEdit, content: {
                            newView
                        })
#else
                        .sheet(isPresented: $showAddEdit) {
                            newView
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


