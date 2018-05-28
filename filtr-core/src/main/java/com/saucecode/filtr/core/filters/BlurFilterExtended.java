package com.saucecode.filtr.core.filters;

import com.saucecode.filtr.core.Filter;
import com.saucecode.filtr.core.Logic;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class BlurFilterExtended extends Filter {
	
	public static final String NAME = "Blur extended";
	
	private final Settings settings;
	
	public BlurFilterExtended(Logic logic, Settings settings) {
		super(NAME, logic.progressProperty(), logic.getThreadCount());
		this.settings = settings;
	}
	
	@Override
	protected void computePixel(int x, int y, PixelReader pr, PixelWriter pw) {
		for (int i = 0; i < settings.iterations; i++) {
			
		}
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

	public static class Settings {

		private final int iterations;

		private final int radius;

		public Settings(int iterations, int radius) {
			this.iterations = iterations;
			this.radius = radius;
		}

	}

}
