package com.saucecode.filtr.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Imgur implements Logic {

	private final SimpleObjectProperty<Image> image = new SimpleObjectProperty<>();

	private final SimpleBooleanProperty busy = new SimpleBooleanProperty(false);

	private final SimpleDoubleProperty progress = new SimpleDoubleProperty(0.0);

	private final SimpleIntegerProperty threadCount = new SimpleIntegerProperty(MAX_THREAD_COUNT);

	private Thread thread;

	@Override
	public Image getImage() {
		return image.get();
	}

	@Override
	public SimpleObjectProperty<Image> imageProperty() {
		return image;
	}

	@Override
	public void setImage(Image image) {
		this.image.set(image);
	}

	@Override
	public boolean isBusy() {
		return busy.get();
	}

	@Override
	public SimpleBooleanProperty busyProperty() {
		return busy;
	}

	@Override
	public void apply(Filter filter) {
		if (image == null) {
			throw new IllegalStateException("There is no image, can't apply filter " + filter.getName());
		}
		if (busy.get()) {
			throw new IllegalStateException("Busy right now, can't apply filter " + filter.getName());
		}
		busy.set(true);
		filter.setProgress(progress);
		filter.setThreadCount(threadCount.get());
		final Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				System.out.println("filter parent " + Thread.currentThread().getName() + " started");
				image.set(filter.filter(image.get()));
				end();
				System.out.println("filter parent " + Thread.currentThread().getName() + " ended");
				return null;
			}
		};
		thread = new Thread(task);
		thread.start();
	}

	@Override
	public double getProgress() {
		return progress.get();
	}

	@Override
	public SimpleDoubleProperty progressProperty() {
		return progress;
	}

	@Override
	public void interrupt() {
		thread.interrupt();
	}

	private void end() {
		busy.set(false);
		progress.set(0.0);
	}

	@Override
	public int getThreadCount() {
		return threadCount.get();
	}

	@Override
	public SimpleIntegerProperty threadCountProperty() {
		return threadCount;
	}

	@Override
	public void setThreadCount(Integer threadCount) {
		if (threadCount < MIN_THREAD_COUNT) {
			throw new IllegalArgumentException("threadCount must not be smaller than " + MIN_THREAD_COUNT);
		}
		if (threadCount > MAX_THREAD_COUNT) {
			throw new IllegalArgumentException("threadCount must not be greater than " + MAX_THREAD_COUNT);
		}
		this.threadCount.set(threadCount);
	}

	@Override
	public boolean isFilterApplyable() {
		return (image != null) && !busy.get();
	}

	@Override
	public SimpleBooleanProperty FilterApplyableProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUndoPossible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SimpleBooleanProperty undoPossibleProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRedoPossible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SimpleBooleanProperty redoPossibleProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void read(File file) throws IOException, IllegalImageFormatException {
			final BufferedImage bufferedImage = ImageIO.read(file);
			if (bufferedImage == null) {
				throw new IllegalImageFormatException("The selected file is no valid image!");
			} else {
				setImage(SwingFXUtils.toFXImage(bufferedImage, null));
			}
	}

	@Override
	public void save(File file, String formatName) throws IOException {
		final BufferedImage temp = SwingFXUtils.fromFXImage(image.get(), null);
		ImageIO.write(temp, formatName, file);
	}

}
