package com.visionvista.effects.filters;

import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SplitTone extends Filter {
    public SplitTone() {
        super();
    }

    @Override public String toString() {
        return "Applied split-tone";
    }

    @Override public BufferedImage run(BufferedImage image) {
        BufferedImage image_split = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                int R = color.getRed();
                int G = color.getGreen();
                int B = color.getBlue();

                //Split tone conversion formula
                int newRed = 0, newGreen = 0, newBlue = 0;
                if (R + G + B > 382) {
                    newRed = ColorManipulator.truncate(R + 40);
                    newGreen = ColorManipulator.truncate(G + 40);
                } else {
                    newBlue = ColorManipulator.truncate(B + 50);
                }

                Color newRGB = new Color(newRed, newGreen, newBlue);
                image_split.setRGB(x, y, newRGB.getRGB());
            }
        }
        return image_split;
    }

    public static SplitTone getRandomInstance() {
        return new SplitTone();
    }
}
