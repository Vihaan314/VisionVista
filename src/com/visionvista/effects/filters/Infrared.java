package com.visionvista.effects.filters;

import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Infrared extends Filter {
    public Infrared() {
        super();
    }

    @Override public String toString() {
        return "Applied infrared";
    }

    @Override public BufferedImage run(BufferedImage image) {
        BufferedImage image_infrared = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                int R = color.getRed();
                int G = color.getGreen();
                int B = color.getBlue();

                int newRed = ColorManipulator.truncate(R + G + B) / 3;
                int newGreen = G / 2;
                int newBlue = B / 2;

                Color newRGB = new Color(newRed, newGreen, newBlue);
                image_infrared.setRGB(x, y, newRGB.getRGB());
            }
        }
        return image_infrared;
    }

    public static Infrared getRandomInstance() {
        return new Infrared();
    }
}
