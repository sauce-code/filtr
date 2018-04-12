package com.saucecode.filtr.gui;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;

/**
 * A about info alert box.
 * 
 * @author Torben Kr&uuml;ger
 *
 */
public class AboutAlert extends Alert {

	/**
	 * Creates a new About Alert.
	 * 
	 * @param logo
	 *            the icon, which shall be used
	 */
	public AboutAlert(Image logo) {
		super(AlertType.INFORMATION);
		ImageView image = new ImageView(logo);
		image.setFitWidth(80.0);
		image.setFitHeight(80.0);
		setGraphic(image);
		initStyle(StageStyle.UTILITY);
		setTitle("About");
		setHeaderText(MetaInfo.TITLE + " " + MetaInfo.VERSION);
		setContentText("Written by " + MetaInfo.AUTHOR + '\n' + "Logo by " + MetaInfo.LOGO_ARTIST + '\n' + "Email: "
				+ MetaInfo.EMAIL + '\n' + "Repository: " + MetaInfo.REPOSITORY + '\n'
				+ "Icons designed by Freepik from Flaticon.com");
	}

}
