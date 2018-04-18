package com.saucecode.filtr.core;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public abstract class SingleThreadedFilter implements Filter {
	
	protected SimpleDoubleProperty progress;

	@Override
	public void setProgress(SimpleDoubleProperty progress) {
		this.progress = progress;
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
				computePixel(x, y, pr, pw);
			}
		}
		progress.set(1.0);
		return wi;
	}

	protected abstract void computePixel(int x, int y, PixelReader pr, PixelWriter pw);

}
