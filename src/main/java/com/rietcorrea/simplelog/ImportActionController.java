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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author riet
 */
public class ImportActionController implements Initializable {

    @FXML
    private TextArea txtResult;
    @FXML
    public ProgressBar progressBar;
    @FXML
    private AnchorPane anchorPane;
    
    private String oldText = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	// set progress to ZERO
    	progressBar.setProgress(0);
    }

    public void addText(String newText) {
        if (newText != null) {
        	txtResult.setText(oldText + newText);
        	oldText += newText;
        
        	// Time to update message...
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// do nothing
			}
			
	        // scroll to the bottom of the textarea
	        txtResult.setScrollTop(Double.MAX_VALUE);
        }
    }

    public void setProgress(Double progress) {
        progressBar.setProgress(progress);
    }

    @FXML
    private void btnCloseAction(ActionEvent event) {
        // Close window
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

}
