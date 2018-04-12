package com.saucecode.filtr.core.filters;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javafx.scene.image.Image;

public class BlurFilter implements Filter {

	@Override
	public String getName() {
		return "Blur";
	}

	@Override
	public BufferedImage filter(Image image) {
//		image.getPixelReader().getColor(1, 2).get
//		Image filtered = new Image);
//		image.
		for (int x = 1; x < image.getWidth() - 1; x++) {
			for (int y = 1; y < image.getHeight() - 1; y++) {
				
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

}
