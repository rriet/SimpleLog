//
//  SelectedMenuView.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/22/24.
//

import SwiftUI

struct SelectedMenuView: View {
    
    @Binding var selectedView: MenuLink
    
    var body: some View {
        switch selectedView {
            case .LogbookView:
                LogbookView()
            case .AircraftsView:
                AircraftsView()
            case .AirportsView:
                AirportsView()
            case .CrewView:
                CrewView()
            case .SummaryView:
                SummaryView()
            case .ReportsView:
                ReportsView()
            case .SettingsView:
                SettingsView()
            case .AboutView:
                AboutView()
        }
    }
}
