package com.visionvista.effects.filters;

import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Solarize extends Filter {
    public Solarize() {
        super();
    }

    @Override public String toString() {
        return "Applied solarizing";
    }

    @Override public BufferedImage run(BufferedImage image) {
        BufferedImage image_solarized = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int R = ColorManipulator.get_rgb(image, "r", x, y);
                int G = ColorManipulator.get_rgb(image, "g", x, y);
                int B = ColorManipulator.get_rgb(image, "b", x, y);
                int newRed, newGreen, newBlue;
                if (R > 128) newRed = 255 - R;
                else newRed = R;

                if (G > 128) newGreen = 255 - G;
                else newGreen = G;

                if (B > 128) newBlue = 255 - B;
                else newBlue = B;

                Color newRGB = new Color(newRed, newGreen, newBlue);
                image_solarized.setRGB(x, y, newRGB.getRGB());
            }
        }
        return image_solarized;
    }

    public static Solarize getRandomInstance(BufferedImage image) {
        return new Solarize();
    }
}
