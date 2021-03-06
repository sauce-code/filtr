package com.saucecode.filtr.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
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

	private final BooleanBinding modificationDisabled = Bindings.or(image.isNull(), busy);

	private final SimpleDoubleProperty progress = new SimpleDoubleProperty(0.0);

	private final SimpleIntegerProperty threadCount = new SimpleIntegerProperty(MAX_THREAD_COUNT);

	private final SimpleBooleanProperty undoPossible = new SimpleBooleanProperty(false);

	private final SimpleBooleanProperty redoPossible = new SimpleBooleanProperty(false);

	private Thread thread;

	@Override
	public Image getImage() {
		return image.get();
	}

	@Override
	public ObjectProperty<Image> imageProperty() {
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
	public BooleanProperty busyProperty() {
		return busy;
	}

	@Override
	public void apply(Filter filter) {
		if (modificationDisabled.get()) {
			throw new IllegalStateException("modification currently disabled, can't apply filter " + filter.getName());
		}
		if (filter == null) {
			throw new IllegalArgumentException("filter must not be null");
		}
		busy.set(true);
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
	public DoubleProperty progressProperty() {
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
	public IntegerProperty threadCountProperty() {
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
	public boolean isModificationDisabled() {
		return modificationDisabled.get();
	}

	@Override
	public BooleanBinding modificationDisabledBinding() {
		return modificationDisabled;
	}

	@Override
	public boolean isUndoPossible() {
		return undoPossible.get();
	}

	@Override
	public BooleanProperty undoPossibleProperty() {
		return undoPossible;
	}

	@Override
	public void undo() {
		if (!undoPossible.get()) {
			throw new IllegalStateException("no undo possible right now");
		}
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isRedoPossible() {
		return redoPossible.get();
	}

	@Override
	public BooleanProperty redoPossibleProperty() {
		return redoPossible;
	}

	@Override
	public void redo() {
		if (!redoPossible.get()) {
			throw new IllegalStateException("no redo possible right now");
		}
		// TODO Auto-generated method stub
	}

	@Override
	public void read(File file) throws IOException, IllegalImageFormatException {
		if (file == null) {
			throw new IllegalArgumentException("file must not be null");
		}
		final BufferedImage bufferedImage = ImageIO.read(file);
		if (bufferedImage == null) {
			throw new IllegalImageFormatException("The selected file is no valid image!");
		} else {
			setImage(SwingFXUtils.toFXImage(bufferedImage, null));
		}
	}

	@Override
	public void save(File file, String formatName) throws IOException {
		if (file == null) {
			throw new IllegalArgumentException("file must not be null");
		}
		final BufferedImage temp = SwingFXUtils.fromFXImage(image.get(), null);
		ImageIO.write(temp, formatName, file);
	}

}
