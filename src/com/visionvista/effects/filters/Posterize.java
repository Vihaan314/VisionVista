package com.visionvista.effects.filters;

import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Posterize extends Filter {
    public Posterize() {
        super();
    }

    @Override public String toString() {
        return "Applied posterizing";
    }

    @Override public BufferedImage run(BufferedImage image) {
        BufferedImage image_posterize = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int R = ColorManipulator.get_rgb(image, "r", x, y);
                int G = ColorManipulator.get_rgb(image, "g", x, y);
                int B = ColorManipulator.get_rgb(image, "b", x, y);
                int newRed = ColorManipulator.truncate(Math.round(R / 85) * 85);
                int newGreen = ColorManipulator.truncate(Math.round(G / 85) * 85);
                int newBlue = ColorManipulator.truncate(Math.round(B / 85) * 85);
                Color newRGB = new Color(newRed, newGreen, newBlue);
                image_posterize.setRGB(x, y, newRGB.getRGB());
            }
        }
        return image_posterize;
    }

    public static Posterize getRandomInstance(BufferedImage image) {
        return new Posterize();
    }
}
