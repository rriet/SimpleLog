//
//  LogbookView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/19/24.
//

import SwiftUI
import SwiftData

struct LogbookView: View {
    
    private var addEditView: AnyView? = AnyView(Text("teste"))
    
    @State private var teste = "Teste!!!!"
    
    var body: some View {
        SelectedView(
            title: "Logbook",
            addEditView: AnyView(FlightEditView(teste: $teste)),
            displayAddButton: true,
            mainView: AnyView(MainLogbookView(teste: $teste)))
    }
}

struct MainLogbookView: View {
    
    @Binding var teste:String
    
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        Text("teste - \(teste)")
            .padding(.all)
            .background(Color.gray)
            .onDisappear(perform: {
//                if presentationMode.wrappedValue.isPresented {
//                        print("View was hidden")
//                    } else {
//                        print("View unloaded")
//                    }
            })
    }
    
}
