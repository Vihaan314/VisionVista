package com.visionvista.effects;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.visionvista.Pair;


public class Contrast extends Effect{
    private double amount;

    public Contrast(BufferedImage image, double amount) {
        super(image);
        this.amount = amount;
    }

    @Override public String toString() {
        return "Applied Contrast. Amount: " + this.amount;
    }

    @Override public BufferedImage run() {
        System.out.println("Changing contrast");
        BufferedImage contrast_img = getEmptyImage(image);

        double scale = 1.0 + amount / 100.0;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color originalColor = new Color(image.getRGB(x, y));
                int rgb;
                int alpha = originalColor.getAlpha();
                int red = originalColor.getRed();
                int green = originalColor.getGreen();
                int blue = originalColor.getBlue();
                red = Helper.truncate((int) (128 + (red - 128) * scale));
                green = Helper.truncate((int) (128 + (green - 128) * scale));
                blue = Helper.truncate((int) (128 + (blue - 128) * scale));

                rgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
                contrast_img.setRGB(x, y, rgb);
            }
        }
        return contrast_img;
    }

    public static Contrast getRandomInstance(BufferedImage image) {
        Pair<Integer, Integer> bounds = EffectType.CONTRAST.getSliderBounds();
        return new Contrast(image, ImageHelper.getRandomParameter(bounds));
    }
}
