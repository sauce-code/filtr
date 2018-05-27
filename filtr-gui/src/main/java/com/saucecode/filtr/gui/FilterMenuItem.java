package com.saucecode.filtr.gui;

import com.saucecode.filtr.core.Filter;
import com.saucecode.filtr.core.Logic;

import javafx.application.Platform;
import javafx.scene.control.MenuItem;

public class FilterMenuItem extends MenuItem {

	public FilterMenuItem(Filter filter, Logic logic) {
		super(filter.getName());
		setDisable(true);
		setOnAction(e -> logic.apply(filter));
		logic.busyProperty().addListener(e -> Platform.runLater(() -> updateDisable(logic)));
		logic.imageProperty().addListener(e -> Platform.runLater(() -> updateDisable(logic)));
	}

	private void updateDisable(Logic logic) {
		setDisable(logic.busyProperty().get() || logic.imageProperty().get() == null);
	}

}
