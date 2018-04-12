package com.saucecode.filtr.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
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

	private BufferedImage image;

	private Stage primaryStage;

	private ImageView imageView;

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

		Menu menuHelp = new Menu("_Help", null, about);

		// =========================================================================================
		// ====== EDIT MENU
		// =========================================================================================

		undo = new MenuItem("_Undo", new ImageView(iconUndo));
		undo.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
		undo.setOnAction(e -> {
			// TODO
		});
		undo.setDisable(true);

		redo = new MenuItem("_Redo", new ImageView(iconRedo));
		redo.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));
		redo.setOnAction(e -> {
			// TODO
		});
		redo.setDisable(true);

		Menu menuEdit = new Menu("_Edit", null, undo, redo);

		// =========================================================================================
		// ======= FILE MENU
		// =========================================================================================

		MenuItem open = new MenuItem("_Open...");
		open.setAccelerator(KeyCombination.keyCombination("Ctrl + O"));
		open.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			// fc.setInitialDirectory(new File(System.getProperty("user.dir")));
			try {
				File input = fc.showOpenDialog(primaryStage);
				if (input != null) {
					image = ImageIO.read(input);
					if (image == null) {
						new ErrorAlert("The selected file is no valid image!")
								.showAndWait();
					} else {
						imageView.setImage(SwingFXUtils.toFXImage(image, null));
					}
				}
			} catch (IOException ex) {
				new ErrorAlert(ex.getMessage()).showAndWait();
			}
		});

		MenuItem exit = new MenuItem("E_xit");
		exit.setAccelerator(KeyCombination.keyCombination("Alt + F4"));
		exit.setOnAction(e -> Platform.exit());

		Menu menuFile = new Menu("_File", null, open, new SeparatorMenuItem(),
				exit);

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

		this.primaryStage = primaryStage;

		imageView = new ImageView();
		ScrollPane scrollpane = new ScrollPane(imageView);

		BorderPane border = new BorderPane(scrollpane);
		border.setTop(initMenuBar());

		Scene scene = new Scene(border);
		scene.getStylesheets().add(Paths.CSS);
		primaryStage.setTitle(MetaInfo.TITLE);
		primaryStage.setScene(scene);
		primaryStage.setWidth(1200.0);
		primaryStage.setHeight(800.0);
		// primaryStage.setResizable(false);
		primaryStage.getIcons().add(logo);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
