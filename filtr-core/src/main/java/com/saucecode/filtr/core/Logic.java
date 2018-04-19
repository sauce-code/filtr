package com.saucecode.filtr.core;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

public interface Logic {

	public static final int MIN_THREAD_COUNT = 1;

	public static final int MAX_THREAD_COUNT = Runtime.getRuntime().availableProcessors() + 1;
	
	public static final int MAX_UNDO_COUNT = 10;
	
	public static final int MAX_REDO_COUNT = 10;
	
	public SimpleObjectProperty<Image> getImage();

	public void setImage(Image image);

	public SimpleBooleanProperty isBusy();

	public void apply(Filter filter);

	public SimpleDoubleProperty getProgress();

	public void interrupt();

	public SimpleIntegerProperty getThreadCount();

	public void setThreadCount(Integer threadCount);

	public boolean isFilterApplyable();
	
	public SimpleBooleanProperty isUndoPossible();
	
	public void undo();
	
	public SimpleBooleanProperty isRedoPossible();
	
	public void redo();

}
