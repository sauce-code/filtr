package com.saucecode.filtr.core.filters;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public abstract class MultiThreadedFilter implements Filter {

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
		if (Thread.currentThread().isInterrupted()) {
			return image;
		}
		int processors = Runtime.getRuntime().availableProcessors() + 1;
		final CountDownLatch latch = new CountDownLatch(processors);
		final ArrayList<Thread> threads = new ArrayList<>(processors);
		for (int p = 0; p < processors; p++) {
			final int current = (int) (image.getHeight() * p / processors);
			final int next = (int) (image.getHeight() * (p + 1) / processors);
			final int width = (int) image.getWidth();
			final int height = (int) image.getHeight();
			threads.add(new Thread(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("Thread "
							+ Thread.currentThread().getName() + " started");
					for (int y = (current == 0 ? 1 : current); y < next
							- 1; y++) {
						if (Thread.currentThread().isInterrupted()) {
							break;
						}
						for (int x = 1; x < width - 1; x++) {
							computePixel(x, y, pr, pw);
						}
						progress.set(progress.doubleValue() + 1.0 / height);

					}
					System.out.println("Thread "
							+ Thread.currentThread().getName() + " ended");
					latch.countDown();
				}

			}));
		}
		threads.forEach(Thread::start);
		try {
			latch.await();
		} catch (InterruptedException e) {
			System.out.println("main thread interrupted");
			threads.forEach(Thread::interrupt);
			return image;
		}
		progress.set(1.0);
		return wi;
	}
	
	protected abstract void computePixel(int x, int y, PixelReader pr, PixelWriter pw);
	
}
