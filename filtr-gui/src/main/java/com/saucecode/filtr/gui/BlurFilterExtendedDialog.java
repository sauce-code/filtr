package com.saucecode.filtr.gui;

import com.saucecode.filtr.core.Logic;
import com.saucecode.filtr.core.filters.BlurFilterExtended;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BlurFilterExtendedDialog extends Dialog<BlurFilterExtended.Settings> {

	public BlurFilterExtendedDialog(Logic logic, Image logo) {

		super();

		setTitle(null);
		setHeaderText(null);
		getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		final Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(logo);
		
		Insets insets = new Insets(0.0, 10.0, 0.0, 0.0);

		Label txt1 = new Label("Iterations:");
		txt1.setPadding(insets);
		NumericTextField field1 = new NumericTextField(1);
		field1.setMaxWidth(40.0);

		Label txt2 = new Label("Radius:");
		txt2.setPadding(insets);
		NumericTextField field2 = new NumericTextField(1);
		field2.setMaxWidth(40.0);

		GridPane grid = new GridPane();
		grid.add(txt1, 0, 0);
		grid.add(field1, 1, 0);
		grid.add(txt2, 0, 1);
		grid.add(field2, 1, 1);

		getDialogPane().setContent(grid);

		setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				return new BlurFilterExtended.Settings(field1.getInt(), field2.getInt());
			}
			return null;
		});
	}

}
