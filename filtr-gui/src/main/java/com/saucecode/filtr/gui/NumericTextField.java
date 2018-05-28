package com.saucecode.filtr.gui;

import javafx.scene.control.TextField;

public class NumericTextField extends TextField {

	public NumericTextField() {
		this(0);
	}
	
	public NumericTextField(int n) {
		super(Integer.toString(n));
		textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				setText(newValue.replaceAll("[^\\d]", ""));
			}
			if (newValue.length() > 2) {
				setText(oldValue);
			}
		});
	}
	
	public int getInt() {
		try {
			return Integer.parseInt(getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
}
