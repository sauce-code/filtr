package com.saucecode.filtr.gui;

import com.saucecode.filtr.core.Logic;
import com.saucecode.filtr.core.filters.Filter;

import javafx.concurrent.Task;
import javafx.scene.control.MenuItem;

public class FilterMenuItem extends MenuItem {

	public FilterMenuItem(Filter filter, Logic logic) {
		super(filter.getName());
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				logic.apply(filter);
				return null;
			}
		};
		new Thread(task).start();
	}

}
