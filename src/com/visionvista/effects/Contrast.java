package com.visionvista.effects;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.visionvista.Pair;


public class Contrast extends Effect{
    private double amount;

    public Contrast(double amount) {
        super();
        this.amount = amount;
    }

    @Override public String toString() {
        return "Applied Contrast. Amount: " + this.amount;
    }

    @Override public BufferedImage run(BufferedImage image) {
        System.out.println("Changing contrast");
        BufferedImage contrast_img = getEmptyImage(image);

        double scale = 1.0 + amount / 100.0;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color originalColor = new Color(image.getRGB(x, y));
                int rgb = Helper.getNewRGB(originalColor, scale);
                contrast_img.setRGB(x, y, rgb);
            }
        }
        return contrast_img;
    }

    public static Contrast getRandomInstance(BufferedImage image) {
        Pair<Integer, Integer> bounds = EffectType.CONTRAST.getSliderBounds();
        return new Contrast(ImageHelper.getRandomParameter(bounds));
    }
}
