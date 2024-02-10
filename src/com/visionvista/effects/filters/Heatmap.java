package com.visionvista.effects.filters;

import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Heatmap extends Filter {
    public Heatmap() {
        super();
    }

    @Override public String toString() {
        return "Applied heatmap";
    }

    @Override public BufferedImage run(BufferedImage image) {
        BufferedImage image_map = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int R = ColorManipulator.get_rgb(image, "r", x, y);
                int G = ColorManipulator.get_rgb(image, "g", x, y);
                int B = ColorManipulator.get_rgb(image, "b", x, y);
                int newRed = 0, newGreen = 0, newBlue = 0;

                int average = (R + G + B) / 3;
                newRed = ColorManipulator.truncate(average + 50);
                newGreen = ColorManipulator.truncate((int) (average * 0.7));
                newBlue = ColorManipulator.truncate((int) (average * 0.4));

                Color newRGB = new Color(newRed, newGreen, newBlue);
                image_map.setRGB(x, y, newRGB.getRGB());
            }
        }
        return image_map;
    }

    public static Heatmap getRandomInstance(BufferedImage image) {
        return new Heatmap();
    }
}
