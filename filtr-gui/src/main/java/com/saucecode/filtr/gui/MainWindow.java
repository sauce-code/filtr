package com.saucecode.filtr.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow extends Application {

	/**
	 * The icon used for this program.
	 */
	private final Image logo = new Image(Paths.LOGO);

	/**
	 * The undo icon.
	 */
	private final Image iconUndo = new Image(Paths.ICON_UNDO);

	/**
	 * The redo icon.
	 */
	private final Image iconRedo = new Image(Paths.ICON_REDO);

	private MenuItem undo;

	private MenuItem redo;

	/**
	 * Initializes the menu bar and returns it.
	 * 
	 * @return the initialized menubar
	 */
	private MenuBar initMenuBar() {

		// =========================================================================================
		// ==== HELP MENU
		// =========================================================================================

		MenuItem about = new MenuItem("A_bout");
		about.setOnAction(e -> new AboutAlert(logo).showAndWait());

		Menu menuHelp = new Menu("_Help", null, new SeparatorMenuItem(), about);

		// =========================================================================================
		// ====== EDIT MENU
		// =========================================================================================

		undo = new MenuItem("_Undo", new ImageView(iconUndo));
		undo.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
		undo.setOnAction(e -> {

		});

		redo = new MenuItem("_Redo", new ImageView(iconRedo));
		redo.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));
		redo.setOnAction(e -> {

		});

		Menu menuEdit = new Menu("_Edit", null, undo, redo);

		// =========================================================================================
		// ======= FILE MENU
		// =========================================================================================

		MenuItem restart = new MenuItem("_New Game");
		restart.setAccelerator(KeyCombination.keyCombination("F2"));
		restart.setOnAction(e -> {

		});

		MenuItem exit = new MenuItem("E_xit");
		exit.setAccelerator(KeyCombination.keyCombination("Alt+F4"));
		exit.setOnAction(e -> Platform.exit());

		Menu menuFile = new Menu("_File", null, restart,
				new SeparatorMenuItem(), exit);

		// =========================================================================================
		// ======= BUILDING MENUBAR
		// =========================================================================================

		MenuBar menuBar = new MenuBar(menuFile, menuEdit, menuHelp);
		menuBar.setUseSystemMenuBar(true);
		menuBar.useSystemMenuBarProperty().set(true);
		return menuBar;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		VBox vBox = new VBox();

		BorderPane border = new BorderPane(vBox);
		border.setTop(initMenuBar());

		Scene scene = new Scene(border);
		scene.getStylesheets().add(Paths.CSS);
		primaryStage.setTitle(MetaInfo.TITLE);
		primaryStage.setScene(scene);
		// primaryStage.setResizable(false);
		primaryStage.getIcons().add(logo);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
