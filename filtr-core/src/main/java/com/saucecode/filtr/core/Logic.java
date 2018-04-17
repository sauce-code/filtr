package com.saucecode.filtr.core;

import com.saucecode.filtr.core.filters.Filter;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

public interface Logic {

	public SimpleObjectProperty<Image> getImage();
	
	public void setImage(Image image);
	
	public SimpleBooleanProperty isBusy();
	
	public void apply(Filter filter);
	
	public SimpleDoubleProperty getProgress();
	
}
