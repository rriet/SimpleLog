package com.rietcorrea.simplelog;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rietcorrea.constants.AppSettings;
import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.csv.ImporterAirport;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class AirportsDownloadController implements Initializable {

	@FXML
	private AnchorPane anchorPane;
	@FXML
    private Hyperlink hyperlink;
	@FXML
	private CheckBox   chkOverride;

	@Override
    public void initialize(URL url, ResourceBundle rb) {
		hyperlink.setText(AppSettings.AIRPORT_URL);
	}

	@FXML
	void hyperlinkAction(ActionEvent event) {
		try {
			Desktop.getDesktop().browse(new URL(AppSettings.AIRPORT_URL).toURI());
		} catch (IOException | URISyntaxException e) {
			// ignore error
		}
	}

	@FXML
	void btnImportAction(ActionEvent event) {
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

			ImporterAirport importerAirport = new ImporterAirport(chkOverride.isSelected());

			Task<Void> importTask = importerAirport.downloadImport();

			controller.progressBar.progressProperty().unbind();
			controller.progressBar.progressProperty().bind(importTask.progressProperty());

			importTask.messageProperty().addListener((ObservableValue<? extends String> observable, String oldValue,
					String newValue) -> controller.addText(newValue));

			importTask.setOnSucceeded(f -> {
				Stage thiStage = (Stage) anchorPane.getScene().getWindow();
				thiStage.close();
			});
			
			new Thread(importTask).start();

		} catch (IOException ex) {
			Logger.getLogger(AirportController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@FXML
	private void btnCancelAction(ActionEvent event) {
		Stage stage = (Stage) anchorPane.getScene().getWindow();
		stage.close();
	}

}
