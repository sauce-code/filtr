package com.saucecode.filtr.core.filters;

import com.saucecode.filtr.core.MultiThreadedFilter;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class BlurFilterMulti extends MultiThreadedFilter {
	
	@Override
	public String getName() {
		return "Blur Multi Threaded";
	}

	@Override
	protected void computePixel(int x, int y, PixelReader pr, PixelWriter pw) {
		double red = pr.getColor(x, y).getRed() * 0.2
				+ pr.getColor(x - 1, y).getRed() * 0.2
				+ pr.getColor(x, y - 1).getRed() * 0.2
				+ pr.getColor(x + 1, y).getRed() * 0.2
				+ pr.getColor(x, y + 1).getRed() * 0.2;
		double green = pr.getColor(x, y).getGreen() * 0.2
				+ pr.getColor(x - 1, y).getGreen() * 0.2
				+ pr.getColor(x, y - 1).getGreen() * 0.2
				+ pr.getColor(x + 1, y).getGreen() * 0.2
				+ pr.getColor(x, y + 1).getGreen() * 0.2;
		double blue = pr.getColor(x, y).getBlue() * 0.2
				+ pr.getColor(x - 1, y).getBlue() * 0.2
				+ pr.getColor(x, y - 1).getBlue() * 0.2
				+ pr.getColor(x + 1, y).getBlue() * 0.2
				+ pr.getColor(x, y + 1).getBlue() * 0.2;
		double opacity = pr.getColor(x, y).getOpacity() * 0.2
				+ pr.getColor(x - 1, y).getOpacity() * 0.2
				+ pr.getColor(x, y - 1).getOpacity() * 0.2
				+ pr.getColor(x + 1, y).getOpacity() * 0.2
				+ pr.getColor(x, y + 1).getOpacity() * 0.2;
		pw.setColor(x, y, new Color(red, green, blue, opacity));
	}

}
