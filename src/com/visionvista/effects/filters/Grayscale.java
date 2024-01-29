package com.visionvista.effects.filters;

import com.visionvista.utils.Helper;

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
                Color rgb = new Color(image.getRGB(x, y));
                int gray = Helper.getNewRGB(rgb, Helper.toGray(rgb));
                grayscaleImg.setRGB(x, y, gray);
            }
        }
        return grayscaleImg;
    }

    public static Grayscale getRandomInstance(BufferedImage image) {
        return new Grayscale();
    }
}
