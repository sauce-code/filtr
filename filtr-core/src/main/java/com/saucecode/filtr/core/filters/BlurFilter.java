package com.saucecode.filtr.core.filters;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class BlurFilter implements Filter {
	
	public static final String NAME = "Blur";
	
	private SimpleDoubleProperty progress;

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Image filter(Image image) {
		progress.set(0.0);
		PixelReader pr = image.getPixelReader();
		WritableImage wi = new WritableImage((int) image.getWidth(),
				(int) image.getHeight());
		PixelWriter pw = wi.getPixelWriter();
		for (int x = 1; x < image.getWidth() - 1; x++) {
			if (Thread.currentThread().isInterrupted()) {
				  return image;
			}
			progress.set((double) x / image.getWidth()); 
			for (int y = 1; y < image.getHeight() - 1; y++) {
				double red = pr.getColor(x, y).getRed() * 0.2
						+ pr.getColor(x - 1, y).getRed() * 0.2
						+ pr.getColor(x, y - 1).getRed() * 0.2
						+ pr.getColor(x + 1, y).getRed() * 0.2
						+ pr.getColor(x, y + 1).getRed() * 0.2;
				double green = pr.getColor(x, y).getGreen() * 0.2
						+ pr.getColor(x - 1, y).getGreen() * 0.2
						+ pr.getColor(x, y - 1).getGreen() * 0.2
						+ pr.getColor(x + 1, y).getGreen() * 0.2
						+ pr.getColor(x, y + 1).getGreen() * 0.2;
				double blue = pr.getColor(x, y).getBlue() * 0.2
						+ pr.getColor(x - 1, y).getBlue() * 0.2
						+ pr.getColor(x, y - 1).getBlue() * 0.2
						+ pr.getColor(x + 1, y).getBlue() * 0.2
						+ pr.getColor(x, y + 1).getBlue() * 0.2;
				double opacity = pr.getColor(x, y).getOpacity() * 0.2
						+ pr.getColor(x - 1, y).getOpacity() * 0.2
						+ pr.getColor(x, y - 1).getOpacity() * 0.2
						+ pr.getColor(x + 1, y).getOpacity() * 0.2
						+ pr.getColor(x, y + 1).getOpacity() * 0.2;
				pw.setColor(x, y, new Color(red, green, blue, opacity));
			}
		}
		progress.set(1.0);
		return wi;
	}

	@Override
	public void setProgress(SimpleDoubleProperty progress) {
		this.progress = progress;
	}

}
