//
//  SchedulableProtocol.swift
//  SimpleLog
//
//  Created by Ricardo Brito Riet Correa on 12/21/24.
//

import Foundation
import SwiftData

protocol Schedulable: Identifiable {
    var id: UUID { get }
    var startTime: Int { get }
    var endTime: Int { get }
}
