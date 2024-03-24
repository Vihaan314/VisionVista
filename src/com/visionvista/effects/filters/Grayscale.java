package com.visionvista.effects.filters;

import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Grayscale extends Filter {
    public Grayscale() {
        super();
    }

    @Override public String toString() {
        return "Applied grayscaling";
    }

    @Override public BufferedImage run(BufferedImage image) {
        System.out.println("Grayscaling");
        BufferedImage grayscaleImg = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                int gray = (int) ColorManipulator.toGray(red, green, blue);
                Color newColor = new Color(gray, gray, gray);

                grayscaleImg.setRGB(x, y, newColor.getRGB());
            }
        }
        return grayscaleImg;
    }

    public static Grayscale getRandomInstance() {
        return new Grayscale();
    }
}
