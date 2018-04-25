package com.saucecode.filtr.core.filters;

import com.saucecode.filtr.core.Filter;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class BlurFilterMulti extends Filter {

	@Override
	public String getName() {
		return "Blur Multi Threaded";
	}

	@Override
	protected void computePixel(int x, int y, PixelReader pr, PixelWriter pw) {
		final double red = computeColor(x, y, pr, Colors.RED);
		final double green = computeColor(x, y, pr, Colors.GREEN);
		final double blue = computeColor(x, y, pr, Colors.BLUE);
		final double opacity = computeColor(x, y, pr, Colors.OPACITY);
		pw.setColor(x, y, new Color(red, green, blue, opacity));
	}

	private double computeColor(int x, int y, PixelReader pr, Colors color) {
		// @formatter:off
		return getColor(x    , y    , pr, color) * 0.2
			 + getColor(x - 1, y    , pr, color) * 0.2
			 + getColor(x    , y - 1, pr, color) * 0.2 
			 + getColor(x + 1, y    , pr, color) * 0.2
			 + getColor(x    , y + 1, pr, color) * 0.2;
		// @formatter:on
	}

}
