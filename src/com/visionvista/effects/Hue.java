package com.visionvista.effects;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.visionvista.Pair;


public class Hue extends Effect{
    private Color color;

    public Hue(Color color) {
        super();
        this.color = color;
    }

    @Override public String toString() {
        return "Applied Hue. Color: " + "#"+Integer.toHexString(this.color.getRGB()).substring(2).toUpperCase();
    }

    @Override public BufferedImage run(BufferedImage image) {
        System.out.println("Adding hue");
        BufferedImage hue_img = getEmptyImage(image);

        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color originalColor = new Color(image.getRGB(x, y), true);
                int red = (originalColor.getRed() + r) / 2;
                int green = (originalColor.getGreen() + g) / 2;
                int blue = (originalColor.getBlue() + b) / 2;
                int alpha = originalColor.getAlpha();
                Color newColor = new Color(red, green, blue, alpha);
                hue_img.setRGB(x, y, newColor.getRGB());
            }
        }
        return hue_img;
    }

    public static Pair<Integer, Integer> getColorBounds() {
        return new Pair<>(1, 256);
    }

    public static Hue getRandomInstance(BufferedImage image) {
        return new Hue(new Color(ImageHelper.getRandomParameter(getColorBounds()),ImageHelper.getRandomParameter(getColorBounds()),ImageHelper.getRandomParameter(getColorBounds())));
    }
}
