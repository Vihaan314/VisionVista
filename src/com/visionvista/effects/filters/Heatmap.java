package com.visionvista.effects.filters;

import com.visionvista.effects.Helper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Heatmap extends Filter {
    public Heatmap(BufferedImage image) {
        super(image);
    }

    @Override public String toString() {
        return "Applied heatmap";
    }

    @Override public BufferedImage run() {
        BufferedImage image_map = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int R = Helper.get_rgb(image, "r", x, y);
                int G = Helper.get_rgb(image, "g", x, y);
                int B = Helper.get_rgb(image, "b", x, y);
                int newRed = 0, newGreen = 0, newBlue = 0;

                int average = (R + G + B) / 3;
                newRed = Helper.truncate(average + 50);
                newGreen = Helper.truncate((int) (average * 0.7));
                newBlue = Helper.truncate((int) (average * 0.4));

                Color newRGB = new Color(newRed, newGreen, newBlue);
                image_map.setRGB(x, y, newRGB.getRGB());
            }
        }
        return image_map;
    }

    public static Heatmap getRandomInstance(BufferedImage image) {
        return new Heatmap(image);
    }
}
