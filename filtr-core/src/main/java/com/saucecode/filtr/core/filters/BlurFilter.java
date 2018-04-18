package com.saucecode.filtr.core.filters;

import java.util.concurrent.CountDownLatch;

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
		if (Thread.currentThread().isInterrupted()) {
			return image;
		}
		int processors = Runtime.getRuntime().availableProcessors() * 2;
		final CountDownLatch latch = new CountDownLatch(processors);
		for (int p = 0; p < processors; p++) {
			final int current = (int) (image.getHeight() * p / processors);
			final int next = (int) (image.getHeight() * (p + 1) / processors);
			final int width = (int) image.getWidth();
			final int height = (int) image.getHeight();
			new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println("Thread "
							+ Thread.currentThread().getName() + " started");
					for (int y = (current == 0 ? 1 : current); y < next
							- 1; y++) {
						for (int x = 1; x < width - 1; x++) {
							doPixel(x, y, pr, pw);
						}
						progress.set(progress.doubleValue() + 1.0 / height);

					}
					latch.countDown();
				}

			}).start();

		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		progress.set(1.0);
		return wi;
	}

	private void doPixel(int x, int y, PixelReader pr, PixelWriter pw) {
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

	@Override
	public void setProgress(SimpleDoubleProperty progress) {
		this.progress = progress;
	}

}
