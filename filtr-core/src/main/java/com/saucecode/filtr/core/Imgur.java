package com.saucecode.filtr.core;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.scene.image.Image;

public class Imgur implements Logic {
	
	private final SimpleObjectProperty<Image> image = new SimpleObjectProperty<>();

	private final SimpleBooleanProperty busy = new SimpleBooleanProperty(false);
	
	private final SimpleDoubleProperty progress = new SimpleDoubleProperty(0.0);
	
	private final SimpleIntegerProperty threadCount = new SimpleIntegerProperty(MAX_THREAD_COUNT);

	private Thread thread;

	@Override
	public SimpleObjectProperty<Image> getImage() {
		return image;
	}

	@Override
	public void setImage(Image image) {
		this.image.set(image);
	}

	@Override
	public SimpleBooleanProperty isBusy() {
		return busy;
	}
	
	@Override
	public void apply(Filter filter) {
		if (image == null) {
			throw new IllegalStateException(
					"There is no image, can't apply filter " + filter.getName());
		}
		if (busy.get()) {
			throw new IllegalStateException(
					"Busy right now, can't apply filter " + filter.getName());
		}
		busy.set(true);
		filter.setProgress(progress);
		filter.setThreadCount(threadCount.get());
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				image.set(filter.filter(image.get()));
				end();
				return null;
			}
		};
		thread = new Thread(task);
		thread.start();
	}

	@Override
	public SimpleDoubleProperty getProgress() {
		return progress;
	}

	@Override
	public void stop() {
		thread.interrupt();
	}
	
	private void end() {
		busy.set(false);
		progress.set(0.0);
	}

	@Override
	public SimpleIntegerProperty getThreadCount() {
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

}
