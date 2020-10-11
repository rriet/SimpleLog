/*
 * Copyright (C) 2019 Ricardo Riet Correa - rietcorrea.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.rietcorrea.simplelog;

import java.io.IOException;

import com.rietcorrea.constants.StrEng;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.csv.ImporterMcc;
import com.rietcorrea.simplelog.csv.ImporterQatar;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 *
 * @author riet
 */
public class ImportDialog {

	Stage  stage = new Stage(StageStyle.DECORATED);
	String selectedFile;

	public ImportDialog(String selectedFile, String fileTypeString) {

		this.selectedFile = selectedFile;

		switch (fileTypeString) {
			case StrEng.SIMPLELOG:
				ImportSimpleLogController simpleLogController = (ImportSimpleLogController) loadWindow(
						"/fxml/ImportSimpleLog.fxml", MyTranslate.text("ImportSimpleLog"));
				simpleLogController.readFile(selectedFile);
				break;

			case StrEng.FLIGHTS:
				ImportFlightController flightController = (ImportFlightController) loadWindow("/fxml/ImportFlight.fxml",
						MyTranslate.text("ImportFlightList"));
				flightController.readFile(selectedFile);
				break;
				
			case StrEng.QATAR_AIRWAYS_XLSX:
				ImportActionController qrController = (ImportActionController) loadWindow("/fxml/ImportAction.fxml",
						MyTranslate.text("ImportQatarAirways"));

				try {
					ImporterQatar importerQatar = new ImporterQatar(selectedFile);

					Task<Object> importQrTask = importerQatar.importFile();

					qrController.progressBar.progressProperty().unbind();
					qrController.progressBar.progressProperty().bind(importQrTask.progressProperty());

					importQrTask.messageProperty().addListener((ObservableValue<? extends String> observable,
							String oldValue, String newValue) -> qrController.addText(newValue));

					new Thread(importQrTask).start();
				} catch (Exception ex) {
					qrController.addText(MyTranslate.text("QrImportInvalidFileFormat"));
				}

				break;
				
			case StrEng.MCC_PILOT_LOG:
				ImportActionController mccController = (ImportActionController) loadWindow("/fxml/ImportAction.fxml",
						MyTranslate.text("ImportMccPilotLog"));

				mccController.progressBar.setProgress(0);

				ImporterMcc importerMcc = new ImporterMcc(selectedFile, mccController);
				importerMcc.importFile();
				break;

			default:
				LogException.getMessage(new RuntimeException("File type string didn't match any option on ImportDialog.java"));
				break;
		}
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}

	private Object loadWindow(String resourceString, String windowTitleString) {

		stage = new Stage(StageStyle.DECORATED);

		FXMLLoader simpleLogLoader = new FXMLLoader(getClass().getResource(resourceString));

		// Set locale for fxml translation
		simpleLogLoader.setResources(MyTranslate.getResourceBundle());

		stage.setTitle(windowTitleString);
		try {
			stage.setScene(new Scene((Pane) simpleLogLoader.load()));
		} catch (IOException ex) {
			LogException.getMessage(ex);
		}
		return simpleLogLoader.getController();

	}
}
