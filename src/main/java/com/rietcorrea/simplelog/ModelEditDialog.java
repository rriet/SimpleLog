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

import com.rietcorrea.simplelog.auxiliaray.MyTranslate;
import com.rietcorrea.simplelog.objects.Model;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ModelEditDialog {

    public ModelEditDialog(Model selectedModel) {
        createDialog(selectedModel);
    }

    public ModelEditDialog() {
        createDialog(null);
    }

    private void createDialog(Model selectedModel) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModelEdit.fxml"));

            // Set locale for fxml translation
        	loader.setResources(MyTranslate.getResourceBundle());
        	
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene((Pane) loader.load()));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            ModelEditController controller = loader.<ModelEditController>getController();

            // If airport is null then create new, else edit selected
            if (selectedModel == null) {
                stage.setTitle(MyTranslate.text("NewModel"));
                controller.newModel();
            } else {
                stage.setTitle(MyTranslate.text("EditModel"));
                controller.editModel(selectedModel);
            }
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(AirportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
