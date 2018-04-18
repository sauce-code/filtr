package com.saucecode.filtr.gui;

import com.saucecode.filtr.core.Logic;
import com.saucecode.filtr.core.filters.Filter;

import javafx.scene.control.MenuItem;

public class FilterMenuItem extends MenuItem {

	public FilterMenuItem(Filter filter, Logic logic) {
		super(filter.getName());
		setDisable(true);
		setOnAction(e -> logic.apply(filter));
		logic.isBusy().addListener(e ->  updateDisable(logic));
		logic.getImage().addListener(e ->  updateDisable(logic));
	}
	
	private void updateDisable(Logic logic) {
		setDisable(logic.isBusy().get() || logic.getImage().get() == null);
	}

}
