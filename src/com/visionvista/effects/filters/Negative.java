package com.visionvista.effects.filters;

import com.visionvista.effects.Helper;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Negative extends Filter {
    public Negative() {
        super();
    }

    @Override public String toString() {
        return "Applied negative";
    }

    @Override public BufferedImage run(BufferedImage image) {
        BufferedImage image_negative = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rn = 255 - Helper.get_rgb(image, "r", x, y);
                int gn = 255 - Helper.get_rgb(image, "g", x, y);
                int bn = 255 - Helper.get_rgb(image, "b", x, y);
                Color colorN = new Color(rn, gn, bn);
                image_negative.setRGB(x, y, colorN.getRGB());
            }
        }
        return image_negative;
    }

    public static Negative getRandomInstance(BufferedImage image) {
        return new Negative();
    }
}
