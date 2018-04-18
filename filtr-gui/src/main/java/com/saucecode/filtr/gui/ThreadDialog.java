package com.saucecode.filtr.gui;

import com.saucecode.filtr.core.Imgur;
import com.saucecode.filtr.core.Logic;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ThreadDialog extends Dialog<Integer> {

	public ThreadDialog(Logic logic, Image logo) {
		setTitle("filtr Threads");
		setHeaderText("Maximum Number of Threads:");
		getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(logo);

		Slider slider = new Slider();
		slider.setMin(Imgur.MIN_THREAD_COUNT);
		slider.setMax(Imgur.MAX_THREAD_COUNT);
		slider.setValue(logic.getThreadCount().get());
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(false);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(1);
		slider.valueProperty()
				.addListener((obs, oldval, newVal) -> slider.setValue((int) Math.round(newVal.doubleValue())));

		getDialogPane().setContent(slider);
		
		setResultConverter(dialogButton -> {
		    if (dialogButton == ButtonType.OK) {
		        return (int) Math.round(slider.valueProperty().get());
		    }
		    return -1;
		});
	}
	
}
