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
		progress.progressProperty().bind(logic.progressProperty());
		logic.progressProperty().addListener(e -> Platform.runLater(() -> {
			setTitle((int) (logic.progressProperty().get() * 100) + "%");
		}));

		final Button cancel = new Button("Cancel");
		cancel.setOnAction(e -> {
			logic.interrupt();
		});

		final VBox vBox = new VBox(progress, cancel);
		vBox.setAlignment(Pos.CENTER);

		setScene(new Scene(vBox));

		logic.busyProperty().addListener(e -> {
			if (logic.busyProperty().get()) {
				Platform.runLater(() -> show());
			} else {
				Platform.runLater(() -> close());
			}
		});

	}

}
