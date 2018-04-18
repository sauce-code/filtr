package com.saucecode.filtr.core.filters;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;

public abstract class SingleThreadedFilter implements Filter {

	@Override
	public Image filter(Image image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProgress(SimpleDoubleProperty progress) {
		// TODO Auto-generated method stub

	}

}
