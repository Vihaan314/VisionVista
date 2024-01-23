package com.visionvista.effects.filters;

import com.visionvista.utils.Pair;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ImageHelper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pixelate extends Filter {
    private int pixelSize;

    public Pixelate(double pixelSize) {
        super();
        this.pixelSize = (int) pixelSize;
    }

    @Override public String toString() {
        return "Applied pixelating";
    }

    private static Color averageColor(BufferedImage image, int x, int y, int pixelSize) {
        int red = 0;
        int green = 0;
        int blue = 0;
        int count = 0;

        for (int x2 = x; x2 < x + pixelSize && x2 < image.getWidth(); x2++) {
            for (int y2 = y; y2 < y + pixelSize && y2 < image.getHeight(); y2++) {
                int rgb = image.getRGB(x2, y2);
                red += (rgb >> 16) & 0xFF;
                green += (rgb >> 8) & 0xFF;
                blue += rgb & 0xFF;
                count++;
            }
        }

        return new Color(red / count, green / count, blue / count);
    }

    private static void fillPixelBlock(BufferedImage image, int x, int y, int pixelSize, Color color) {
        for (int x2 = x; x2 < x + pixelSize && x2 < image.getWidth(); x2++) {
            for (int y2 = y; y2 < y + pixelSize && y2 < image.getHeight(); y2++) {
                image.setRGB(x2, y2, color.getRGB());
            }
        }
    }

    @Override public BufferedImage run(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage pixelatedImage = getEmptyImage(image);

        for (int y = 0; y < height; y += pixelSize) {
            for (int x = 0; x < width; x += pixelSize) {
                // Get the average color of the current pixel block
                Color avgColor = averageColor(image, x, y, pixelSize);
                fillPixelBlock(pixelatedImage, x, y, pixelSize, avgColor);
            }
        }

        return pixelatedImage;
    }

    public static Pixelate getRandomInstance(BufferedImage image) {
        Pair<Integer, Integer> bounds = EffectType.PIXELATE.getSliderBounds();
        return new Pixelate(ImageHelper.getRandomParameter(bounds));
    }
}
