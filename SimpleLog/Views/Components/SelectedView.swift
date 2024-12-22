//
//  SelectedView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/20/24.
//

import SwiftUI

struct SelectedView: View {
    
    // The title to display in the toolbar
    let title: String
    
    // This variables controls the add/edit screen
    // addEditView - receives the view to dislay
    // showAddEdit - controls the state of the view (hidden/displayed)
    // displayAddButton - if true, displays the + button on the toolbar
    @State private var showAddEdit = false
    var addEditView: AnyView?
    var displayAddButton: Bool = false
    
    // this is the view that is inserted in the main view
    @State var mainView: AnyView?
    
    var body: some View {
//        mainView
        
        Text("This is the main view")
        
        .navigationTitle(title)
        
        // This doesn't exist in MacOS
#if os(iOS)
        .navigationBarTitleDisplayMode(.inline)
#endif
        .toolbar {
            // "Add new" icon - Only display if there is a view passed to this Struct
//            if let newView = addEditView {
            if displayAddButton, let addEditView {
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
                            addEditView
                        })
                    // sheet works on all systems, but is dismissible on IOS, not dismissible on MacOS
#else
                        .sheet(isPresented: $showAddEdit) {
                            addEditView
                        }
#endif
                }
            }
        }
    }
}
