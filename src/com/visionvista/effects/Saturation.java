package com.visionvista.effects;

import com.visionvista.utils.Pair;
import com.visionvista.utils.ImageHelper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Saturation extends Effect{
    private double amount;

    public Saturation(double amount) {
        super();
        this.amount = amount;
    }

    @Override public String toString() {
        return "Applied Saturation. Amount: " + this.amount;
    }

    @Override
    public Object getParameter() {
        return amount;
    }

    @Override public BufferedImage run(BufferedImage image) {
        System.out.println("Changing saturation");
        BufferedImage sat_img = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color originalColor = new Color(image.getRGB(x, y), true);
                //Extract HSB values
                float[] hsb = Color.RGBtoHSB(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), null);
                float hue = hsb[0];
                float saturation = hsb[1];
                float brightness = hsb[2];

                //Adjust saturation by amount
                saturation *= (float) (1 + amount/20);
                saturation = Math.max(0.0f, Math.min(1.0f, saturation));

                int newRGB = Color.HSBtoRGB(hue, saturation, brightness);
                sat_img.setRGB(x, y, newRGB);
            }
        }
        return sat_img;
    }


    public static Saturation getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.SATURATION.getSliderBounds();
        return new Saturation(ImageHelper.getRandomParameter(bounds));
    }
}
