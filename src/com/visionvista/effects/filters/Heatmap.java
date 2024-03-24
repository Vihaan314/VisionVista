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
                Color color = new Color(image.getRGB(x, y));
                int R = color.getRed();
                int G = color.getGreen();
                int B = color.getBlue();

                //Heatmap conversion formula
                int average = (R + G + B) / 3;
                int newRed = ColorManipulator.truncate(average + 50);
                int newGreen = ColorManipulator.truncate((int) (average * 0.7));
                int newBlue = ColorManipulator.truncate((int) (average * 0.4));

                Color newRGB = new Color(newRed, newGreen, newBlue);
                image_map.setRGB(x, y, newRGB.getRGB());
            }
        }
        return image_map;
    }

    public static Heatmap getRandomInstance() {
        return new Heatmap();
    }
}
