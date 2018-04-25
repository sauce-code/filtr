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
		final double red = computeColor(x, y, pr, Colors.RED);
		final double green = computeColor(x, y, pr, Colors.GREEN);
		final double blue = computeColor(x, y, pr, Colors.BLUE);
		final double opacity = computeColor(x, y, pr, Colors.OPACITY);
		pw.setColor(x, y, new Color(red, green, blue, opacity));
	}

	private double computeColor(int x, int y, PixelReader pr, Colors color) {
		return getColor(x, y, pr, color) * 0.2 + getColor(x - 1, y, pr, color) * 0.2
				+ getColor(x, y - 1, pr, color) * 0.2 + getColor(x + 1, y, pr, color) * 0.2
				+ getColor(x, y + 1, pr, color) * 0.2;
	}

	protected double getColor(int x, int y, PixelReader pr, Colors color) {
		switch (color) {
		case RED:
			return pr.getColor(x, y).getRed();
		case GREEN:
			return pr.getColor(x, y).getGreen();
		case BLUE:
			return pr.getColor(x, y).getBlue();
		case OPACITY:
			return pr.getColor(x, y).getOpacity();
		default:
			throw new InternalError();
		}
	}

	protected enum Colors {
		RED, GREEN, BLUE, OPACITY;
	};

}
