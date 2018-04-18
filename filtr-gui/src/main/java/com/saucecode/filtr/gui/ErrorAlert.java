package com.saucecode.filtr.gui;

import javafx.scene.control.Alert;

/**
 * Represents a simple error alert.
 *
 * @author Torben Kr&uuml;ger
 *
 */
public class ErrorAlert extends Alert {

	/**
	 * Creates a new error alert.
	 *
	 * @param message
	 *            messgae to be shown
	 */
	public ErrorAlert(String message) {
		super(AlertType.ERROR);
		setTitle("Error");
		setHeaderText(null);
		setContentText(message);
	}

}
