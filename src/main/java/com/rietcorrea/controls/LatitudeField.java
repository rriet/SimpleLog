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

import javafx.scene.control.IndexRange;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;

/**
 *
 * @author riet
 */
public class LatitudeField extends TextField {

    public LatitudeField() {
        resetValue();

        setOnMouseEvents();

        setOnKeyEvents();
    }

    private void resetValue() {
        this.setText("N 00° 00.0");
    }

    private void setOnMouseEvents() {
        this.setOnMouseClicked(action -> {
            if ("".equals(this.getSelectedText())) {
                if (this.getCaretPosition() < 2) {
                    this.selectRange(0, 1);
                } else if (this.getCaretPosition() < 5) {
                    this.selectRange(2, 4);
                } else {
                    this.selectRange(6, 10);
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
        this.setOnKeyTyped(action -> action.consume());

        this.setOnKeyReleased(action -> {
            if ("LEFT".equals(action.getCode().toString())) {
                if (this.getCaretPosition() < 5) {
                    this.selectRange(0, 1);
                } else {
                    this.selectRange(2, 4);
                }
            }
            if ("RIGHT".equals(action.getCode().toString())) {
                if (this.getCaretPosition() < 2) {
                    this.selectRange(2, 4);
                } else {
                    this.selectRange(6, 10);
                }
            }

            if ("".equals(this.getSelectedText()) || this.getSelectedText().equals(this.getText())) {
                this.selectRange(0, 1);
            }
            action.consume();
        });

        this.setOnKeyPressed(action -> {
            if ("UP".equals(action.getCode().toString())) {
                increaseValue();
            }

            if ("DOWN".equals(action.getCode().toString())) {
                decreaseValue();
            }

            if (!"TAB".equals(action.getCode().toString())) {
                action.consume();
            }
        });
    }

    private void increaseValue() {
        String cardinal = this.getText().substring(0, 1);
        Integer degrees = Integer.valueOf(this.getText().substring(2, 4));
        Double minutes = Double.valueOf(this.getText().substring(6));
        int caret = this.getCaretPosition();
        if (caret < 3) {
            if (cardinal.equals("N")) {
                cardinal = "S";
            } else {
                cardinal = "N";
            }
        } else if (caret < 5) {
            if (degrees < 89) {
                degrees = degrees + 1;
            } else {
                degrees = 0;
            }
        } else {
            if (minutes < 59.9) {
                minutes = minutes + 0.1;
            } else {
                minutes = 0.0;
            }
        }
        this.setText(cardinal + " " + String.format("%02d", degrees) + "° " + String.format("%04.1f", minutes));

        if (caret < 2) {
            this.selectRange(0, 1);
        } else if (caret < 5) {
            this.selectRange(2, 4);
        } else {
            this.selectRange(6, 10);
        }
    }

    private void decreaseValue() {
        String cardinal = this.getText().substring(0, 1);
        Integer degrees = Integer.valueOf(this.getText().substring(2, 4));
        Double minutes = Double.valueOf(this.getText().substring(6));
        int caret = this.getCaretPosition();
        if (caret < 3) {
            if (cardinal.equals("N")) {
                cardinal = "S";
            } else {
                cardinal = "N";
            }
        } else if (caret < 5) {
            if (degrees > 0) {
                degrees = degrees - 1;
            } else {
                degrees = 89;
            }
        } else {
            if (minutes > 0) {
                minutes = minutes - 0.1;
            } else {
                minutes = 59.9;
            }
        }
        this.setText(cardinal + " " + String.format("%02d", degrees) + "° " + String.format("%04.1f", minutes));

        if (caret < 2) {
            this.selectRange(0, 1);
        } else if (caret < 5) {
            this.selectRange(2, 4);
        } else {
            this.selectRange(6, 10);
        }
    }
    
    
    @Override
    public void replaceSelection(String replacement) {
        // Avoid pasting text
    }
    @Override
    public void deleteText(IndexRange range) {
        // Avoid delete or cut
    }
    @Override
    public void deleteText(int begin, int end) {
        // Avoid delete or cut
    }

}
