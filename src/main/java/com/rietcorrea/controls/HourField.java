/*
 * Copyright (C) 2018 Ricardo Riet Correa - rietcorrea.com
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
package com.rietcorrea.controls;

import java.util.regex.Pattern;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author riet
 */
// This time field holds a maximum of 24 hours
public class HourField extends TextField {

    private IntegerProperty minutes = new SimpleIntegerProperty();
    private final Pattern timePattern;

    public HourField() {
        this("00:00");
        Bindings.bindBidirectional(this.textProperty(), this.minutes, new TimeIntegerConverter());

        // this filter limits the number of characters to 5 and numbers only
        this.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent inputevent) -> {
            int c = HourField.this.getCaretPosition();
            // Only takes numbers and less than 5 characteres
            if (!"1234567890".contains(inputevent.getCharacter().toLowerCase()) || c > 5) {
                inputevent.consume();
            }
        });

        // This filter jumps the : char when caret position is 2
        this.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent inputevent) -> {
            // If carret position is 2, then "jump" the ":" (ignores left arrow and backspace
            int c = HourField.this.getCaretPosition();
            if ((c == 2) && (inputevent.getCode() != KeyCode.LEFT && inputevent.getCode() != KeyCode.BACK_SPACE)) {
                HourField.this.forward();
                inputevent.consume();
            }
        });
    }

    private HourField(String time) {
        super(time);
        timePattern = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
        if (!validate(time)) {
            throw new IllegalArgumentException("Invalid time: " + time);
        }

    }

    public Integer getMinutes() {
        return this.minutes.get();
    }

    public void setMinutes(Integer minutes) {
        this.minutes.set(minutes);
    }

    public IntegerProperty minutesProperty() {
        return this.minutes;
    }

    @Override
    public void appendText(String text) {
        // Ignore this. Our text is always 5 characters long, we cannot
        // append anything
    }

    @Override
    public boolean deleteNextChar() {

        boolean success = false;

        // If there's a selection, delete it:
        final IndexRange selection = getSelection();
        if (selection.getLength() > 0) {
            int selectionEnd = selection.getEnd();
            this.deleteText(selection);
            this.positionCaret(selectionEnd);
            success = true;
        } else {
            // If the caret preceeds a digit, replace that digit with a zero
            // and move the caret forward. Else just move the caret forward.
            int caret = this.getCaretPosition();
            if (caret % 3 != 2) { // not preceeding a colon
                String currentText = this.getText();
                setText(currentText.substring(0, caret) + "0" + currentText.substring(caret + 1));
                success = true;
            }
            this.positionCaret(Math.min(caret + 1, this.getText().length()));
        }
        return success;
    }

    @Override
    public boolean deletePreviousChar() {

        boolean success = false;

        // If there's a selection, delete it:
        final IndexRange selection = getSelection();
        if (selection.getLength() > 0) {
            int selectionStart = selection.getStart();
            this.deleteText(selection);
            this.positionCaret(selectionStart);
            success = true;
        } else {
            // If the caret is after a digit, replace that digit with a zero
            // and move the caret backward. Else just move the caret back.
            int caret = this.getCaretPosition();
            if (caret % 3 != 0) { // not following a colon
                String currentText = this.getText();
                setText(currentText.substring(0, caret - 1) + "0" + currentText.substring(caret));
                success = true;
            }
            this.positionCaret(Math.max(caret - 1, 0));
        }
        if (this.getCaretPosition() == 3) {
            this.positionCaret(2);
        }
        return success;
    }

    @Override
    public void deleteText(IndexRange range) {
        this.deleteText(range.getStart(), range.getEnd());
    }

    @Override
    public void deleteText(int begin, int end) {
        // Replace all digits in the given range with zero:
        StringBuilder builder = new StringBuilder(this.getText());
        for (int c = begin; c < end; c++) {
            if (c % 3 != 2) { // Not at a colon:
                builder.replace(c, c + 1, "0");
            }
        }
        this.setText(builder.toString());
    }

    @Override
    public void insertText(int index, String text) {
        // Handle an insert by replacing the range from index to
        // index+text.length() with text, if that results in a valid string:
        StringBuilder builder = new StringBuilder(this.getText());
        builder.replace(index, index + text.length(), text);
        final String testText = builder.toString();
        if (validate(testText)) {
            this.setText(testText);
            this.positionCaret(index + text.length());
        }

    }

    @Override
    public void replaceSelection(String replacement) {
        // Avoid pasting text
    }

    @Override
    public void replaceText(int begin, int end, String text) {
        if (begin == end) {
            this.insertText(begin, text);
        } else {
            this.deleteText(begin, end);
            this.insertText(begin, text);
        }
    }

    private boolean validate(String time) {
        return timePattern.matcher(time).matches();
    }

    private class TimeIntegerConverter extends NumberStringConverter {

        @Override
        public Number fromString(String inputTime) {
            try {
                String[] hourMin = inputTime.split(":");
                int hour = Integer.parseInt(hourMin[0]);
                int mins = Integer.parseInt(hourMin[1]);
                return (hour * 60) + mins;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public String toString(Number inputMinutes) {
            int hoursInt = inputMinutes.intValue() / 60; //since both are ints, you get an int
            int minutesInt = inputMinutes.intValue() % 60;
            return String.format("%02d", hoursInt) + ":" + String.format("%02d", minutesInt);
        }
    }
}
