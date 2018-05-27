package com.saucecode.filtr.core;

import java.io.File;
import java.io.IOException;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.image.Image;

public interface Logic {

	public static final int MIN_THREAD_COUNT = 1;

	public static final int MAX_THREAD_COUNT = Runtime.getRuntime().availableProcessors() + 1;

	public static final int MAX_UNDO_COUNT = 10;

	public static final int MAX_REDO_COUNT = 10;

	public Image getImage();
	
	public ObjectProperty<Image> imageProperty();
	
	public void setImage(Image image);
	
	public boolean isBusy();

	public BooleanProperty busyProperty();

	public void apply(Filter filter);
	
	public double getProgress();

	public DoubleProperty progressProperty();
	
	public int getThreadCount();
	
	public IntegerProperty threadCountProperty();

	public void setThreadCount(Integer threadCount);

	public void interrupt();

	public boolean isModificationDisabled();
	
	public BooleanBinding modificationDisabledBinding();

	public boolean isUndoPossible();
	
	public BooleanProperty undoPossibleProperty();

	public void undo();
	
	public boolean isRedoPossible();

	public BooleanProperty redoPossibleProperty();

	public void redo();

	public void read(File file) throws IOException, IllegalImageFormatException;
	
	public void save(File file, String formatName) throws IOException;

}
