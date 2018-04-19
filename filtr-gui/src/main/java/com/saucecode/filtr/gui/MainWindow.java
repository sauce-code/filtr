package com.saucecode.filtr.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import org.controlsfx.control.StatusBar;

import com.saucecode.filtr.core.Imgur;
import com.saucecode.filtr.core.filters.BlurFilterMulti;
import com.saucecode.filtr.core.filters.BlurFilterSingle;

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

	private final Imgur imgur = new Imgur();

	private MenuItem undo;

	private MenuItem redo;

	private Stage primaryStage;

	private ImageView imageView;

	private StatusBar statusBar;

	private MenuItem saveAs;

	private MenuItem copy;

	private MenuItem paste;

	private final Clipboard clipboard = Clipboard.getSystemClipboard();

	private final List<MenuItem> taskMenuItems = new LinkedList<MenuItem>();

	private Menu initMenuHelp() {
		final MenuItem about = new MenuItem("A_bout");
		about.setOnAction(e -> new AboutAlert(logo).showAndWait());

		return new Menu("_Help", null, about);
	}

	private Menu initSettingsMenu() {
		final MenuItem threads = new MenuItem("Threads...");
		threads.setOnAction(e -> {
			final ThreadDialog dialog = new ThreadDialog(imgur, logo);
			final Integer ret = dialog.showAndWait().get();
			if (ret != -1) {
				imgur.setThreadCount(ret);
			}
		});
		return new Menu("_Settings", null, threads);
	}

	private Menu initMenuFilter() {
		final FilterMenuItem blurMulti = new FilterMenuItem(new BlurFilterMulti(), imgur);
		final FilterMenuItem blurSingle = new FilterMenuItem(new BlurFilterSingle(), imgur);

		return new Menu("Fi_lter", null, blurMulti, blurSingle);
	}

	private Menu initMenuEdit() {
		undo = new MenuItem("_Undo", new ImageView(iconUndo));
		undo.setAccelerator(KeyCombination.keyCombination("Ctrl + Z"));
		undo.setOnAction(e -> {
			// TODO
		});
		undo.setDisable(true);
		taskMenuItems.add(undo);

		redo = new MenuItem("_Redo", new ImageView(iconRedo));
		redo.setAccelerator(KeyCombination.keyCombination("Ctrl + Y"));
		redo.setOnAction(e -> {
			// TODO
		});
		redo.setDisable(true);
		taskMenuItems.add(redo);

		copy = new MenuItem("_Copy", new ImageView(iconCopy));
		copy.setAccelerator(KeyCombination.keyCombination("Ctrl + C"));
		copy.setOnAction(e -> {
			final ClipboardContent content = new ClipboardContent();
			content.putImage(imgur.getImage().get());
			clipboard.setContent(content);
			final Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Image has been copied to clipboard.");
			alert.showAndWait();
		});
		taskMenuItems.add(copy);

		paste = new MenuItem("_Paste", new ImageView(iconPaste));
		paste.setAccelerator(KeyCombination.keyCombination("Ctrl + V"));
		paste.setOnAction(e -> {
			if (clipboard.hasImage()) {
				imgur.setImage(clipboard.getImage());
			}
		});
		taskMenuItems.add(paste);

		return new Menu("_Edit", null, undo, redo, new SeparatorMenuItem(), copy, paste);
	}

	private Menu initMenuFile() {
		final MenuItem open = new MenuItem("_Open...");
		open.setAccelerator(KeyCombination.keyCombination("Ctrl + O"));
		open.setOnAction(e -> {
			final FileChooser fc = new FileChooser();
			fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
					new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("GIF", "*.gif"),
					new FileChooser.ExtensionFilter("BMP", "*.bmp"), new FileChooser.ExtensionFilter("PNG", "*.png"));
			// fc.setInitialDirectory(new File(System.getProperty("user.dir")));
			try {
				final File input = fc.showOpenDialog(primaryStage);
				if (input != null) {
					final BufferedImage temp = ImageIO.read(input);
					if (temp == null) {
						new ErrorAlert("The selected file is no valid image!").showAndWait();
					} else {
						imgur.setImage(SwingFXUtils.toFXImage(temp, null));
					}
				}
			} catch (final IOException ex) {
				new ErrorAlert(ex.getMessage()).showAndWait();
			}
		});
		taskMenuItems.add(open);

		saveAs = new MenuItem("Save _As...", new ImageView(iconSave));
		saveAs.setOnAction(e -> {
			final FileChooser fc = new FileChooser();
			fc.setInitialFileName("*.png");
			fc.getExtensionFilters().add(new ExtensionFilter("PNG", "*.png"));
			final File outputfile = fc.showSaveDialog(primaryStage);
			if (outputfile != null) {
				try {
					final BufferedImage temp = SwingFXUtils.fromFXImage(imgur.getImage().get(), null);
					ImageIO.write(temp, "png", outputfile);
				} catch (final IOException ex) {
					new ErrorAlert(ex.getMessage()).showAndWait();
				}
			}
		});
		taskMenuItems.add(saveAs);

		final MenuItem exit = new MenuItem("E_xit");
		exit.setAccelerator(KeyCombination.keyCombination("Alt + F4"));
		exit.setOnAction(e -> Platform.exit());

		return new Menu("_File", null, open, saveAs, new SeparatorMenuItem(), exit);
	}

	/**
	 * Initializes the menu bar and returns it.
	 *
	 * @return the initialized menubar
	 */
	private MenuBar initMenuBar() {
		final MenuBar menuBar = new MenuBar(initMenuFile(), initMenuEdit(), initMenuFilter(), initSettingsMenu(),
				initMenuHelp());
		menuBar.setUseSystemMenuBar(true);
		menuBar.useSystemMenuBarProperty().set(true);
		return menuBar;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		new ProgressStage(imgur, logo);
		
		imgur.getImage().addListener(e -> Platform.runLater(() -> {
			imageView.setImage(imgur.getImage().get());
		}));

		this.primaryStage = primaryStage;

		imageView = new ImageView();
		final ScrollPane scrollPane = new ScrollPane(imageView);
		scrollPane.setPannable(true);

		statusBar = new StatusBar();
		imgur.getProgress().addListener(e -> {
			Platform.runLater(() -> statusBar.setProgress(imgur.getProgress().get()));
		});

		final BorderPane border = new BorderPane(scrollPane);
		border.setTop(initMenuBar());
		border.setBottom(statusBar);

		final Scene scene = new Scene(border);
		scene.getStylesheets().add(Paths.CSS);
		primaryStage.setTitle(MetaInfo.TITLE);
		primaryStage.setScene(scene);
		primaryStage.setWidth(1200.0);
		primaryStage.setHeight(800.0);
		primaryStage.getIcons().add(logo);

		new com.sun.glass.ui.ClipboardAssistance(com.sun.glass.ui.Clipboard.SYSTEM) {
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
