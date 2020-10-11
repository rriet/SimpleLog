/*
*    Simple Log - Pilot logbook software
*    Copyright (C) 2018  Ricardo Brito Riet Correa
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.rietcorrea.simplelog;

import java.util.logging.Logger;

/**
 *
 * @author riet
 */
public class LogException {

	private LogException() {
		throw new IllegalStateException("Utility class");
	}

	public static void getMessage(Exception exception) {
		String exceptionMessage = exception.getMessage() + "\n\n";

		StackTraceElement[] stktrace = exception.getStackTrace();

		StringBuilder bld = new StringBuilder();
		for (int i = 0; i < stktrace.length; i++) {
			bld.append(stktrace[i].toString() + "\n");
		}
		exceptionMessage += bld.toString();
		System.out.println("LogException:");
		// todo: fix
System.out.println(exceptionMessage);
		Logger.getLogger(exceptionMessage);
	}

}
