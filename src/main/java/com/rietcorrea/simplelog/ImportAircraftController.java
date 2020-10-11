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

import com.rietcorrea.simplelog.csv.ImporterAircraft;
import com.rietcorrea.simplelog.csv.ImporterCsvOpener;
import com.rietcorrea.simplelog.csv.ImporterModel;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author riet
 */
public class ImportAircraftController implements Initializable {

	@FXML
	private AnchorPane anchorPane;
	@FXML
	private ComboBox<String> cmbModel;
	@FXML
	private ComboBox<String> cmbGroup;
	@FXML
	private ComboBox<String> cmbModelMtow;
	@FXML
	private ComboBox<String> cmbEngineType;
	@FXML
	private ComboBox<String> cmbMultiPilot;
	@FXML
	private ComboBox<String> cmbMultiEngine;
	@FXML
	private ComboBox<String> cmbEfis;
	@FXML
	private ComboBox<String> cmbRegistration;
	@FXML
	private ComboBox<String> cmbSimulator;
	@FXML
	private ComboBox<String> cmbAircraftMtow;
	@FXML
	private ComboBox<String> cmbSeaplane;
	@FXML
	private CheckBox chkIgnoreRegistration;
	@FXML
	private CheckBox chkIgnoreAircraftMtow;
	@FXML
	private CheckBox chkIgnoreMakeModel;
	@FXML
	private CheckBox chkIgnoreGroup;
	@FXML
	private CheckBox chkIgnoreModelMtow;
	@FXML
	private CheckBox chkIgnoreEngine;
	@FXML
	private CheckBox chkOverrideAircraft;
	@FXML
	private CheckBox chkOverrideModel;

	Task<?> importTask;
	String file;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// not used
	}

	@FXML
	private void btnImportAction(ActionEvent event) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ImportAction.fxml"));
			
            // Set locale for fxml translation
        	loader.setResources(MyTranslate.getResourceBundle());

			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene((Pane) loader.load()));
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);

			ImportActionController controller = loader.<ImportActionController>getController();

			stage.show();

			controller.progressBar.setProgress(0);
			
			checkModels(controller);

		} catch (IOException ex) {
			Logger.getLogger(AirportController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void checkModels(ImportActionController controller) {
		ImporterModel importerModel = new ImporterModel()
				.setName(cmbModel.getSelectionModel().getSelectedIndex())
				.setIgnoreNameError(chkIgnoreMakeModel.isSelected())
				.setGroup(cmbGroup.getSelectionModel().getSelectedIndex())
				.setIgnoreGroupError(chkIgnoreGroup.isSelected())
				.setMtow(cmbModelMtow.getSelectionModel().getSelectedIndex())
				.setIgnoreMtowError(chkIgnoreModelMtow.isSelected())
				.setEngineType(cmbEngineType.getSelectionModel().getSelectedIndex())
				.setIgnoreEngineTypeError(chkIgnoreEngine.isSelected())
				.setMultiEngine(cmbMultiEngine.getSelectionModel().getSelectedIndex())
				.setMultiPilot(cmbMultiPilot.getSelectionModel().getSelectedIndex())
				.setEfis(cmbEfis.getSelectionModel().getSelectedIndex())
				.setSeaplane(cmbSeaplane.getSelectionModel().getSelectedIndex());
		

		importerModel.setOverride(chkOverrideModel.isSelected());

		importTask = importerModel.checkFile(file);

		bindController(controller);

		importTask.setOnSucceeded(e -> importModels(controller, importerModel));
		new Thread(importTask).start();
	}
	
	private void importModels(ImportActionController controller, ImporterModel importerModel) {
		importTask = importerModel.importFile(file);
		bindController(controller);

		importTask.setOnSucceeded(f -> checkAircrafts(controller, importerModel));
		new Thread(importTask).start();
	}
	
	private void checkAircrafts(ImportActionController controller, ImporterModel importerModel) {
		
		ImporterAircraft importerAircraft = new ImporterAircraft()
				
				.setRegistration(cmbRegistration.getSelectionModel().getSelectedIndex())
				.setIgnoreRegistrationError(chkIgnoreRegistration.isSelected())
				.setSimulator(cmbSimulator.getSelectionModel().getSelectedIndex())
				.setMtow(cmbAircraftMtow.getSelectionModel().getSelectedIndex())
				.setIgnoreMtowError(chkIgnoreAircraftMtow.isSelected())
				.setModel(cmbModel.getSelectionModel().getSelectedIndex())
				.setOverride(chkOverrideAircraft.isSelected());

		importTask = importerAircraft.checkFile(file);

		bindController(controller);

		importTask.setOnSucceeded(g -> {
			importTask = importerAircraft.importFile(file);
			bindController(controller);

			importTask.setOnSucceeded(h -> close());
			
			new Thread(importTask).start();
		});

		new Thread(importTask).start();
	}

	private void bindController(ImportActionController controller) {
		controller.progressBar.progressProperty().unbind();
		controller.progressBar.progressProperty().bind(importTask.progressProperty());

		importTask.messageProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> controller.addText(newValue));
	}

	@FXML
	private void btnCancelAction(ActionEvent event) {
		close();
	}

	public void close() {
		// Close window
		Stage stage = (Stage) anchorPane.getScene().getWindow();
		stage.close();
	}

	public void readFile(String selectedFile) {
		this.file = selectedFile;

		List<String[]> lineStrings = ImporterCsvOpener.open(file);

		ObservableList<String> options = FXCollections.observableArrayList(MyTranslate.text("DontImport"));
		options.addAll(lineStrings.get(0));

		cmbRegistration.setItems(options);
		cmbRegistration.setValue(MyTranslate.text("DontImport"));
		cmbSimulator.setItems(options);
		cmbSimulator.setValue(MyTranslate.text("DontImport"));
		cmbAircraftMtow.setItems(options);
		cmbAircraftMtow.setValue(MyTranslate.text("DontImport"));

		cmbModel.setItems(options);
		cmbModel.setValue(MyTranslate.text("DontImport"));
		cmbGroup.setItems(options);
		cmbGroup.setValue(MyTranslate.text("DontImport"));
		cmbModelMtow.setItems(options);
		cmbModelMtow.setValue(MyTranslate.text("DontImport"));
		cmbEngineType.setItems(options);
		cmbEngineType.setValue(MyTranslate.text("DontImport"));
		cmbMultiPilot.setItems(options);
		cmbMultiPilot.setValue(MyTranslate.text("DontImport"));
		cmbMultiEngine.setItems(options);
		cmbMultiEngine.setValue(MyTranslate.text("DontImport"));
		cmbEfis.setItems(options);
		cmbEfis.setValue(MyTranslate.text("DontImport"));
		cmbSeaplane.setItems(options);
		cmbSeaplane.setValue(MyTranslate.text("DontImport"));
	}
}
