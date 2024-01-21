package com.visionvista.effects.filters;

import com.visionvista.effects.Helper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Infrared extends Filter {
    public Infrared(BufferedImage image) {
        super(image);
    }

    @Override public String toString() {
        return "Applied infrared";
    }

    @Override public BufferedImage run() {
        BufferedImage image_infrared = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int R = Helper.get_rgb(image, "r", x, y);
                int G = Helper.get_rgb(image, "g", x, y);
                int B = Helper.get_rgb(image, "b", x, y);
                int newRed = 0, newGreen = 0, newBlue = 0;

                int average = (R + G + B) / 3;
                newRed = G;
                newGreen = B;
                newBlue = R;

                Color newRGB = new Color(newRed, newGreen, newBlue);
                image_infrared.setRGB(x, y, newRGB.getRGB());
            }
        }
        return image_infrared;
    }

    public static Infrared getRandomInstance(BufferedImage image) {
        return new Infrared(image);
    }
}
