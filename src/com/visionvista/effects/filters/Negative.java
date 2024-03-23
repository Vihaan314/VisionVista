package com.visionvista.effects.filters;

import com.visionvista.utils.ColorManipulator;

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
                Color color = new Color(image.getRGB(x, y));
                //Negative of all color values
                int rn = 255 - color.getRed();
                int gn = 255 - color.getGreen();
                int bn = 255 - color.getBlue();

                Color colorN = new Color(rn, gn, bn);
                image_negative.setRGB(x, y, colorN.getRGB());
            }
        }
        return image_negative;
    }

    public static Negative getRandomInstance() {
        return new Negative();
    }
}
