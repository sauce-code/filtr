package com.saucecode.filtr.gui;

import com.saucecode.filtr.core.Logic;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressStage extends Stage {
	
	public ProgressStage(Logic logic, Image logo) {
		
		super(StageStyle.DECORATED);
		getIcons().add(logo);
		initModality(Modality.APPLICATION_MODAL);
		setResizable(false);
		setOnCloseRequest(e -> logic.interrupt());
		
		final ProgressBar progress = new ProgressBar();
		progress.setMinWidth(300.0);
		progress.setMinHeight(30.0);
		logic.getProgress().addListener(e -> Platform.runLater(() -> {
			final double currentProgress = logic.getProgress().get();
			progress.setProgress(currentProgress);
			setTitle((int) (currentProgress * 100) + "%");
		}));

		final Button cancel = new Button("Cancel");
		cancel.setOnAction(e -> {
			logic.interrupt();
		});
		
		final VBox vBox = new VBox(progress, cancel);
		vBox.setAlignment(Pos.CENTER);
		
		setScene(new Scene(vBox));
		
		logic.isBusy().addListener(e -> {
			if (logic.isBusy().get()) {
				Platform.runLater(() -> show());
			} else {
				Platform.runLater(() -> close());
			}
		});
		
	}

}
