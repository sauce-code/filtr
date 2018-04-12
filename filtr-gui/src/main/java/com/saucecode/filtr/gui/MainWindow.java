package com.saucecode.filtr.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainWindow extends Application {

	/**
	 * The icon used for this program.
	 */
	private final Image logo = new Image(Paths.LOGO);

	/**
	 * The save icon.
	 */
	private final Image iconSave = new Image(Paths.ICON_SAVE);

	/**
	 * The undo icon.
	 */
	private final Image iconUndo = new Image(Paths.ICON_UNDO);

	/**
	 * The redo icon.
	 */
	private final Image iconRedo = new Image(Paths.ICON_REDO);

	/**
	 * The copy icon.
	 */
	private final Image iconCopy = new Image(Paths.ICON_COPY);

	/**
	 * The paste icon.
	 */
	private final Image iconPaste = new Image(Paths.ICON_PASTE);

	private MenuItem undo;

	private MenuItem redo;

	private Image image;

	private Stage primaryStage;

	private ImageView imageView;

	private MenuItem saveAs;

	private MenuItem copy;

	private MenuItem paste;

	private Clipboard clipboard = Clipboard.getSystemClipboard();

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
		undo.setAccelerator(KeyCombination.keyCombination("Ctrl + Z"));
		undo.setOnAction(e -> {
			// TODO
		});
		undo.setDisable(true);

		redo = new MenuItem("_Redo", new ImageView(iconRedo));
		redo.setAccelerator(KeyCombination.keyCombination("Ctrl + Y"));
		redo.setOnAction(e -> {
			// TODO
		});
		redo.setDisable(true);

		copy = new MenuItem("_Copy", new ImageView(iconCopy));
		copy.setAccelerator(KeyCombination.keyCombination("Ctrl + C"));
		copy.setOnAction(e -> {
			ClipboardContent content = new ClipboardContent();
			content.putImage(image);
			clipboard.setContent(content);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Image has been copied to clipboard.");
			alert.showAndWait();
		});
		copy.setDisable(true);

		paste = new MenuItem("_Paste", new ImageView(iconPaste));
		paste.setAccelerator(KeyCombination.keyCombination("Ctrl + V"));
		paste.setOnAction(e -> {
			if (clipboard.hasImage()) {
				image = clipboard.getImage();
				imageView.setImage(image);
				copy.setDisable(false);
			}
		});
		paste.setDisable(!clipboard.hasImage());

		Menu menuEdit = new Menu("_Edit", null, undo, redo,
				new SeparatorMenuItem(), copy, paste);

		// =========================================================================================
		// ======= FILE MENU
		// =========================================================================================

		MenuItem open = new MenuItem("_Open...");
		open.setAccelerator(KeyCombination.keyCombination("Ctrl + O"));
		open.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("All Images", "*.*"),
					new FileChooser.ExtensionFilter("JPG", "*.jpg"),
					new FileChooser.ExtensionFilter("GIF", "*.gif"),
					new FileChooser.ExtensionFilter("BMP", "*.bmp"),
					new FileChooser.ExtensionFilter("PNG", "*.png"));
			// fc.setInitialDirectory(new File(System.getProperty("user.dir")));
			try {
				File input = fc.showOpenDialog(primaryStage);
				if (input != null) {
					BufferedImage temp = ImageIO.read(input);
					if (temp == null) {
						new ErrorAlert("The selected file is no valid image!")
								.showAndWait();
					} else {
						image = SwingFXUtils.toFXImage(temp, null);
						imageView.setImage(image);
						saveAs.setDisable(false);
						copy.setDisable(false);
					}
				}
			} catch (IOException ex) {
				new ErrorAlert(ex.getMessage()).showAndWait();
			}
		});

		saveAs = new MenuItem("Save _As...", new ImageView(iconSave));
		saveAs.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			fc.setInitialFileName("*.png");
			fc.getExtensionFilters().add(new ExtensionFilter("PNG", "*.png"));
			File outputfile = fc.showSaveDialog(primaryStage);
			if (outputfile != null) {
				try {
					BufferedImage temp = SwingFXUtils.fromFXImage(image, null);
					ImageIO.write(temp, "png", outputfile);
				} catch (IOException ex) {
					new ErrorAlert(ex.getMessage()).showAndWait();
				}
			}
		});
		saveAs.setDisable(true);

		MenuItem exit = new MenuItem("E_xit");
		exit.setAccelerator(KeyCombination.keyCombination("Alt + F4"));
		exit.setOnAction(e -> Platform.exit());

		Menu menuFile = new Menu("_File", null, open, saveAs,
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

		new com.sun.glass.ui.ClipboardAssistance(
				com.sun.glass.ui.Clipboard.SYSTEM) {
			@Override
			public void contentChanged() {
				paste.setDisable(!clipboard.hasImage());
			}
		};

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
