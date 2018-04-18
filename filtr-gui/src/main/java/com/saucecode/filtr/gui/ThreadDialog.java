package com.saucecode.filtr.gui;

import com.saucecode.filtr.core.Logic;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ThreadDialog extends Dialog<Integer> {

	public ThreadDialog(Logic logic, Image logo) {
		setTitle("filtr Settings - Threads");
		setHeaderText("Maximum Number of Threads:");
		getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		final Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(logo);

		final Slider slider = new Slider();
		slider.setMin(Logic.MIN_THREAD_COUNT);
		slider.setMax(Logic.MAX_THREAD_COUNT);
		slider.setValue(logic.getThreadCount().get());
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(0);
		slider.valueProperty()
				.addListener((obs, oldval, newVal) -> slider.setValue((int) Math.round(newVal.doubleValue())));

		getDialogPane().setContent(slider);

		Platform.runLater(() -> slider.requestFocus());

		setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				return (int) Math.round(slider.valueProperty().get());
			}
			return -1;
		});
	}

}
