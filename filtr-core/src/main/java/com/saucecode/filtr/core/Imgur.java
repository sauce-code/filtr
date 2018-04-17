package com.saucecode.filtr.core;

import com.saucecode.filtr.core.filters.Filter;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

public class Imgur implements Logic {

	private final SimpleObjectProperty<Image> image = new SimpleObjectProperty<>();

	private final SimpleBooleanProperty busy = new SimpleBooleanProperty(false);
	
	private final SimpleDoubleProperty progress = new SimpleDoubleProperty(0.0);

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
		if (busy.get()) {
			throw new IllegalStateException(
					"Busy right now, can't apply filter " + filter.getName());
		}
		busy.set(true);
		filter.setProgress(progress);
		image.set(filter.filter(image.get()));
		busy.set(false);
	}

	@Override
	public SimpleDoubleProperty getProgress() {
		return progress;
	}

}
