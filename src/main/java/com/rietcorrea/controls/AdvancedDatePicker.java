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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.util.StringConverter;

/**
 *
 * @author riet
 */
public class AdvancedDatePicker extends DatePicker {

    public AdvancedDatePicker() {
        this.setValue(LocalDate.now());

        this.getEditor().focusedProperty().addListener(observable -> this.getEditor().selectRange(0, 2));

        setOnMouseEvents();

        setOnKeyEvents();
        
        String pattern = "dd/MM/yyyy";
        this.setConverter(new StringConverter<LocalDate>() {
             DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

             @Override 
             public String toString(LocalDate date) {
                 if (date != null) {
                     return dateFormatter.format(date);
                 } else {
                     return "";
                 }
             }

             @Override 
             public LocalDate fromString(String string) {
                 if (string != null && !string.isEmpty()) {
                     return LocalDate.parse(string, dateFormatter);
                 } else {
                     return null;
                 }
             }
         });
    }

    private void setOnMouseEvents() {
        this.getEditor().setOnMouseClicked(action -> {
            if ("".equals(this.getEditor().getSelectedText())) {
                if (this.getEditor().getCaretPosition() < 3) {
                    this.getEditor().selectRange(0, 2);
                } else if (this.getEditor().getCaretPosition() < 6) {
                    this.getEditor().selectRange(3, 5);
                } else {
                    this.getEditor().selectRange(6, 10);
                }
            }
        });

        this.setOnScroll((ScrollEvent action) -> {
            if (action.getDeltaY() < 0) {
                increaseValue();
            } else {
                decreaseValue();
            }
        });
    }

    private void setOnKeyEvents() {
        this.getEditor().setOnKeyTyped(action -> action.consume());

        this.getEditor().setOnKeyReleased((KeyEvent action) -> {
        	switch (action.getCode().toString()) {
        		case "LEFT":
        			if (this.getEditor().getCaretPosition() < 6) {
                        this.getEditor().selectRange(0, 2);
                    } else if (this.getEditor().getCaretPosition() >= 6) {
                        this.getEditor().selectRange(3, 5);
                    }
					break;
        		case "RIGHT":
        			if (this.getEditor().getCaretPosition() < 3) {
                        this.getEditor().selectRange(3, 5);
                    } else if (this.getEditor().getCaretPosition() < 6) {
                        this.getEditor().selectRange(6, 10);
                    }
					break;
        		case "UP":
                    increaseValue();
					break;
					
				case "DOWN":
	                decreaseValue();
					break;
				case "":
	                this.getEditor().selectRange(0, 2);
					break;
				default:
					action.consume();
					break;
			}        });

        this.getEditor().setOnKeyPressed(action -> {
            if (!"TAB".equals(action.getCode().toString())) {
                action.consume();
            }
        });
    }

    private void increaseValue() {
        LocalDate dateValue = this.getValue();
        if (this.getEditor().getCaretPosition() < 3) {
            dateValue = dateValue.plusDays(1);
            this.setValue(dateValue);
            this.getEditor().selectRange(0, 2);
        } else if (this.getEditor().getCaretPosition() < 6) {
            dateValue = dateValue.plusMonths(1);
            this.setValue(dateValue);
            this.getEditor().selectRange(3, 5);
        } else {
            dateValue = dateValue.plusYears(1);
            this.setValue(dateValue);
            this.getEditor().selectRange(6, 10);
        }
    }

    private void decreaseValue() {
        LocalDate dateValue = this.getValue();
        if (this.getEditor().getCaretPosition() < 3) {
            dateValue = dateValue.minusDays(1);
            this.setValue(dateValue);
            this.getEditor().selectRange(0, 2);
        } else if (this.getEditor().getCaretPosition() < 6) {
            dateValue = dateValue.minusMonths(1);
            this.setValue(dateValue);
            this.getEditor().selectRange(3, 5);
        } else {
            dateValue = dateValue.minusYears(1);
            this.setValue(dateValue);
            this.getEditor().selectRange(6, 10);
        }
    }
    
    public Long getEpoch() {
		return this.getValue().toEpochDay()*24*60*60;
	}
    
    public Long getEpoch(int minutes) {
		return (this.getValue().toEpochDay()*24*60*60)+(minutes*60);
	}
    
}
