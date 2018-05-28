package com.saucecode.filtr.gui;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Represents a simple error alert.
 *
 * @author Torben Kr&uuml;ger
 *
 */
public class ErrorAlert extends Alert {

	/**
	 * Creates a new short error alert.
	 *
	 * @param message
	 *            message to be shown
	 */
	public ErrorAlert(String message) {
		super(AlertType.ERROR);
		setTitle("Error");
		setHeaderText(message);
		setContentText(null);
	}

	/**
	 * Creates a new detailed error alert with expandable stack trace.
	 * 
	 * @param exception
	 *            exception to be shown
	 */
	public ErrorAlert(Exception exception) {
		super(AlertType.ERROR);
		setTitle("Error");
		setHeaderText(exception.getMessage());
		setContentText(null);

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		getDialogPane().setExpandableContent(expContent);
	}

}
