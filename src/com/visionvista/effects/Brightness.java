package com.visionvista.effects;

import com.visionvista.utils.Pair;
import com.visionvista.utils.Helper;
import com.visionvista.utils.ImageHelper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Brightness extends Effect implements Serializable {
    private double intensity;

    public Brightness(double intensity) {
        this.intensity = intensity;
    }

    @Override public String toString() {
        return "Applied Brightness. Intensity: " + this.intensity;
    }

    @Override public BufferedImage run(BufferedImage image) {
        System.out.println("Changing brightness");
        BufferedImage brightened_img = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color rgb = new Color(image.getRGB(x, y));
                int red = rgb.getRed();
                int green =  rgb.getGreen();
                int blue = rgb.getBlue();
                red = Helper.truncate((int) (red + intensity));
                green = Helper.truncate((int) (green + intensity));
                blue = Helper.truncate((int) (blue + intensity));
                Color new_rgb = new Color(red, green, blue);
                brightened_img.setRGB(x, y, new_rgb.getRGB());
            }
        }

        return brightened_img;
    }

    public static Brightness getRandomInstance(BufferedImage image) {
        Pair<Integer, Integer> bounds = EffectType.BRIGHTNESS.getSliderBounds();
        return new Brightness(ImageHelper.getRandomParameter(bounds));
    }

    public String encodeAsString() {
        return "Brightness" + Double.toString(this.intensity);
    }
}
