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

import com.rietcorrea.simplelog.database.SearchDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.stage.Window;

/**
 *
 * @author riet
 */
public class SearchComboBox<T> extends ComboBox<T> {

	private String searchString;
	private SearchDAO<T> dao;
	private Boolean allowNull = false;

	public SearchComboBox() {
		this.searchString = "";
		this.setTooltip(new Tooltip());
		this.getTooltip().setOpacity(0.3);

		this.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.BACK_SPACE && searchString.length() > 0) {
				searchString = searchString.substring(0, searchString.length() - 1);
				showSearchText();
				refreshResults();
			} else if (e.getCode() == KeyCode.BACK_SPACE && searchString.length() == 0 && allowNull) {
				this.getSelectionModel().clearSelection();
			} else if (e.getText().matches("[a-zA-Z0-9\\-\\.\\,\\+\\(\\)\\[\\]\\{\\}]")) {
				searchString += e.getText();

				showSearchText();
				refreshResults();
			}
		});
		this.setOnKeyTyped(e -> {
			e.consume();
		});
		this.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.SPACE) {
				searchString += e.getText();

				showSearchText();
				refreshResults();
			}
			e.consume();
		});

		this.focusedProperty().addListener((action, hadFocus, hasFocus) -> {
			if (hasFocus) {
				this.show();
				showSearchText();
			} else {
				this.hide();
				this.getTooltip().hide();
			}
		});
	}

	public void setDao(SearchDAO<T> dao) {
		this.dao = dao;
		ObservableList<T> resultList = FXCollections.observableList(dao.search(""));
		this.setItems(resultList);
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
		refreshResults();
	}

	public void setAllowNull(Boolean allowNull) {
		this.allowNull = allowNull;
	}

	private void showSearchText() {
		this.getTooltip().setText(searchString);
		Window stage = this.getScene().getWindow();

		Point2D p = this.localToScene(0.0, 0.0);
		double posX = p.getX() + this.getScene().getX() + this.getScene().getWindow().getX();
		double posY = p.getY() + this.getScene().getY() + this.getScene().getWindow().getY() - 30;
		this.getTooltip().setMinWidth(this.getWidth());
		this.getTooltip().show(stage, posX, posY);
	}

	private void refreshResults() {
		ObservableList<T> resultList = FXCollections.observableList(dao.search(searchString));
		this.setItems(resultList);
		this.getSelectionModel().selectFirst();

		// Fix bug that only shows one line of results
		this.hide(); // before you set new visibleRowCount value
		this.setVisibleRowCount(10); // set new visibleRowCount value
		this.show(); // after you set new visibleRowCount value
	}

}
