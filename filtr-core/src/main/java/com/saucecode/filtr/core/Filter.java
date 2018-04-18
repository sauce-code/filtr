package com.saucecode.filtr.core;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;

/**
 * Declares the functionality af any filter.
 *
 * @author Torben Kr&uuml;ger
 * @since 1.0.0
 */
public interface Filter {

	/**
	 * Returns the simple name of this filter.
	 *
	 * @return name of this filter
	 * @since 1.0.0
	 */
	public String getName();

	/**
	 * Filters a given image.
	 *
	 * @param image
	 *            the image, which shall be filtered
	 * @since 1.0.0
	 */
	public Image filter(Image image);

	public void setProgress(SimpleDoubleProperty progress);

	public void setThreadCount(int threadCount);

}
