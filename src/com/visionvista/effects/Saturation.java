package com.visionvista.effects;

import com.visionvista.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Saturation extends Effect{
    private double amount;

    public Saturation(BufferedImage image, double amount) {
        super(image);
        this.amount = amount;
    }

    @Override public String toString() {
        return "Applied Saturation. Amount: " + this.amount;
    }

    @Override public BufferedImage run() {
        System.out.println("Changing saturation");
        BufferedImage sat_img = getEmptyImage(image);

        amount = 1+ amount / 100;
        for (int x = 0; x < Math.floor(image.getWidth()); x++) {
            for (int y = 0; y < Math.floor(image.getHeight()); y++) {
                Color originalColor = new Color(image.getRGB(x, y), true);
                float[] hsb = Color.RGBtoHSB(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), null);
                float hue = hsb[0];
                float saturation = hsb[1];
                float brightness = hsb[2];

                saturation += amount;
                saturation = Math.max(0.0f, Math.min(1.0f, saturation));

                int newRGB = Color.HSBtoRGB(hue, saturation, brightness);
                Color newColor = new Color(newRGB, true);
                sat_img.setRGB(x, y, newColor.getRGB());
            }
        }
        return sat_img;
    }


    public static Saturation getRandomInstance(BufferedImage image) {
        Pair<Integer, Integer> bounds = EffectType.SATURATION.getSliderBounds();
        return new Saturation(image, ImageHelper.getRandomParameter(bounds));
    }
}
