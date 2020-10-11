package com.rietcorrea.simplelog;

import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.csv.ImporterSimpleLog;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ImportSimpleLogController implements Initializable {

    Task<Void> importTask;
    String file;
    @FXML
    private CheckBox chkReCalcToLdg;
    @FXML
    private CheckBox chkRecalcIfr;
    @FXML
    private CheckBox chkRecalcNight;
    @FXML
    private CheckBox chkRecalcXc;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ComboBox<String> cmbCrewNameFormat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> crewNameFormat = FXCollections.observableArrayList(
        		MyTranslate.text("PilotNameUnchanged"),
        		MyTranslate.text("PilotNameUpperFirst"),
        		MyTranslate.text("PilotNameUppercase"),
        		MyTranslate.text("PilotNameLowercase")
        );
        cmbCrewNameFormat.setItems(crewNameFormat);
        cmbCrewNameFormat.setValue(MyTranslate.text("PilotNameUnchanged"));
    }

    public void readFile(String selectedFile) {
        this.file = selectedFile;
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
            
            ImporterSimpleLog importer = new ImporterSimpleLog(file, controller)
            		.setRecalculateIfr(chkRecalcIfr.isSelected())
            		.setRecalculateNight(chkRecalcNight.isSelected())
            		.setRecalculateTakeoffLandings(chkReCalcToLdg.isSelected())
            		.setRecalculateXc(chkRecalcXc.isSelected());
            
            importer.setCrewNameFormat(cmbCrewNameFormat.getValue());
            
            importer.importFile();
            
        } catch (IOException ex) {
            Logger.getLogger(AirportController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}
