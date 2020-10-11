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
package com.rietcorrea.controls;

import javafx.scene.control.TextField;

/**
 *
 * @author riet
 */
public class IntegerTextField extends TextField {

    public IntegerTextField() {
        setOnKeyEvents();
        
    }
    
    private void setOnKeyEvents() {
        this.setOnKeyTyped(action -> {
            if (!action.getCharacter().matches("[0-9]") || this.getText().length() > 7) {
                action.consume();
            }
        });
        
        this.setOnKeyReleased(action -> {
            int position = this.getCaretPosition();
            if (action.getText().matches("[0-9]") && position > 1) {
                position++;
                String previousValue = this.getText().replace(",", "");
                this.setText(String.format("%,d", Integer.valueOf(previousValue)));
            }
            this.selectRange(position, position);
        });
    }
    
    @Override
    public void replaceSelection(String replacement) {
        // Avoid pasting text
    }
    
    public Integer getInteger() {
        String numericValue = this.getText().replace(",", "");
        return Integer.valueOf(numericValue);
    }
    
    public void setInteger(Integer inputValue) {
        this.setText(String.format("%,d", inputValue));
    }
}
