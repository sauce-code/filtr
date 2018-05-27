package com.saucecode.filtr.core;

import java.io.File;
import java.io.IOException;

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

	public Image getImage();
	
	public SimpleObjectProperty<Image> imageProperty();
	
	public void setImage(Image image);
	
	public boolean isBusy();

	public SimpleBooleanProperty busyProperty();

	public void apply(Filter filter);
	
	public double getProgress();

	public SimpleDoubleProperty progressProperty();
	
	public int getThreadCount();
	
	public SimpleIntegerProperty threadCountProperty();

	public void setThreadCount(Integer threadCount);

	public void interrupt();

	public boolean isFilterApplyable();
	
	public SimpleBooleanProperty FilterApplyableProperty();

	public boolean isUndoPossible();
	
	public SimpleBooleanProperty undoPossibleProperty();

	public void undo();
	
	public boolean isRedoPossible();

	public SimpleBooleanProperty redoPossibleProperty();

	public void redo();

	public void read(File file) throws IOException, IllegalImageFormatException;
	
	public void save(File file, String formatName) throws IOException;

}
