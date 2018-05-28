package com.saucecode.filtr.gui;

import com.saucecode.filtr.core.Filter;
import com.saucecode.filtr.core.Logic;

import javafx.scene.control.MenuItem;

public class FilterMenuItem extends MenuItem {

	public FilterMenuItem(Filter filter, Logic logic) {
		super(filter.getName());
		disableProperty().bind(logic.modificationDisabledBinding());
		setOnAction(e -> logic.apply(filter));
	}

}
