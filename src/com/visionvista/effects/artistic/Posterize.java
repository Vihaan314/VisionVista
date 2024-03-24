package com.visionvista.effects.artistic;

import com.visionvista.effects.filters.Filter;
import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Posterize extends Artistic {
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
                Color color = new Color(image.getRGB(x, y));
int R = color.getRed();
                int G = color.getGreen();
                int B = color.getBlue();
                int newRed = ColorManipulator.truncate(Math.round(R / 85) * 85);
                int newGreen = ColorManipulator.truncate(Math.round(G / 85) * 85);
                int newBlue = ColorManipulator.truncate(Math.round(B / 85) * 85);
                Color newRGB = new Color(newRed, newGreen, newBlue);
                image_posterize.setRGB(x, y, newRGB.getRGB());
            }
        }
        return image_posterize;
    }

    public static Posterize getRandomInstance() {
        return new Posterize();
    }
}
