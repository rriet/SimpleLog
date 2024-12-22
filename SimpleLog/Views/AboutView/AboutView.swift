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
                .frame(
                    maxWidth: .infinity,
                    maxHeight: .infinity
                )
        }
        .navigationTitle("About")
#if os(iOS)
        .navigationBarTitleDisplayMode(.inline)
#endif
        .floatingButton(
            buttonContent: AnyView(
                Image(systemName: "plus")
                    .foregroundColor(.white)
                    .font(.title)
            ),
            action: {
                showAddEdit.toggle()
                print("Floating button pressed!")
            }
        )
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



extension View {
    func floatingButton(
        buttonContent: AnyView,
        action: @escaping () -> Void
    ) -> some View {
        self.modifier(FloatingButtonModifier(buttonContent: buttonContent, action: action))
    }
}


struct FloatingButtonModifier: ViewModifier {
    var buttonContent: AnyView
    var action: () -> Void
    
    func body(content: Content) -> some View {
        ZStack {
            content // The original view being modified
            
            VStack {
                Spacer() // Push to the bottom
                HStack {
                    Spacer() // Push to the right
                    Button(action: {
                        action()
                    }) {
                        buttonContent
                    }
                    .padding()
                    .background(Circle().fill(Color.blue))
                    .foregroundColor(.white)
                    .shadow(radius: 4)
                }
            }
            .padding()
        }
    }
}






#Preview("Light") {
    AboutView()
}
