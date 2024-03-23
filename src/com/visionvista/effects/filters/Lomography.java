package com.visionvista.effects.filters;

import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lomography extends Filter {
    public Lomography() {
        super();
    }

    @Override public String toString() {
        return "Applied lomography";
    }

    @Override public BufferedImage run(BufferedImage image) {
        BufferedImage lomography_image = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                int R = color.getRed();
                int G = color.getGreen();
                int B = color.getBlue();

                //Lomography conversion formula
                int newRed = ColorManipulator.truncate((int) (R + (R * 0.2) - (G * 0.1)));
                int newGreen = ColorManipulator.truncate((int) (G + (G * 0.1) + (B * 0.1)));
                int newBlue = ColorManipulator.truncate((int) (B + (B * 0.2) - (R * 0.1)));

                Color newRGB = new Color(newRed, newGreen, newBlue);
                lomography_image.setRGB(x, y, newRGB.getRGB());
            }
        }
        return lomography_image;
    }

    public static Lomography getRandomInstance() {
        return new Lomography();
    }
}
