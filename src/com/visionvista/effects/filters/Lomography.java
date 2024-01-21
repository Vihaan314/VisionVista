package com.visionvista.effects.filters;

import com.visionvista.effects.Helper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lomography extends Filter {
    public Lomography(BufferedImage image) {
        super(image);
    }

    @Override public String toString() {
        return "Applied lomography";
    }

    @Override public BufferedImage run() {
        BufferedImage lomography_image = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int R = Helper.get_rgb(image, "r", x, y);
                int G = Helper.get_rgb(image, "g", x, y);
                int B = Helper.get_rgb(image, "b", x, y);
                int newRed = Helper.truncate((int) (R + (R * 0.2) - (G * 0.1)));
                int newGreen = Helper.truncate((int) (G + (G * 0.1) + (B * 0.1)));
                int newBlue = Helper.truncate((int) (B + (B * 0.2) - (R * 0.1)));
                Color newRGB = new Color(newRed, newGreen, newBlue);
                lomography_image.setRGB(x, y, newRGB.getRGB());
            }
        }
        return lomography_image;
    }

    public static Lomography getRandomInstance(BufferedImage image) {
        return new Lomography(image);
    }
}
