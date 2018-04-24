package com.saucecode.filtr.core;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public abstract class MultiThreadedFilter implements Filter {

	protected SimpleDoubleProperty progress;

	protected int threadCount;

	@Override
	public void setProgress(SimpleDoubleProperty progress) {
		this.progress = progress;
	}

	@Override
	public Image filter(Image image) {
		progress.set(0.0);
		final PixelReader pr = image.getPixelReader();
		final WritableImage wi = new WritableImage((int) image.getWidth(), (int) image.getHeight());
		final PixelWriter pw = wi.getPixelWriter();
		if (Thread.currentThread().isInterrupted()) {
			return image;
		}
		final CountDownLatch latch = new CountDownLatch(threadCount);
		final ArrayList<Thread> threads = new ArrayList<>(threadCount);
		for (int p = 0; p < threadCount; p++) {
			final int current = (int) (image.getHeight() * p / threadCount);
			final int next = (int) (image.getHeight() * (p + 1) / threadCount);
			final int width = (int) image.getWidth();
			final int height = (int) image.getHeight();
			threads.add(new Thread(() -> {
				System.out.println("filter child " + Thread.currentThread().getName() + " started");
				for (int y = (current == 0 ? 1 : current); y < next - 1; y++) {
					if (Thread.currentThread().isInterrupted()) {
						break;
					}
					for (int x = 1; x < width - 1; x++) {
						computePixel(x, y, pr, pw);
					}
					progress.set(progress.doubleValue() + 1.0 / height);

				}
				System.out.println("filter child " + Thread.currentThread().getName()
						+ (Thread.currentThread().isInterrupted() ? " interrupted" : " ended"));
				latch.countDown();
			}));
		}
		threads.forEach(Thread::start);
		try {
			latch.await();
		} catch (final InterruptedException e) {
			System.out.println("filter parent " + Thread.currentThread().getName() + " interrupted");
			threads.forEach(Thread::interrupt);
			return image;
		}
		progress.set(1.0);
		return wi;
	}

	protected abstract void computePixel(int x, int y, PixelReader pr, PixelWriter pw);

	@Override
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

}
