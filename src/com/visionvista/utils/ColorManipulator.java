package com.visionvista.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorManipulator {
    //Color correction
    public static double unGamma(double value) {
        return Math.pow(value / 255.0, 2.2) * 255.0;
    }

    public static double Gamma(double value) {
        return Math.pow(value / 255.0, 1.0 / 2.2) * 255.0;
    }

    public static double toGray(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        return 0.299 * r + 0.587 * g + 0.114 * b;
    }

    public static double toGray(double r, double g, double b) {
        return 0.299 * r + 0.587 * g + 0.114 * b;
    }

    private static double computeLuminance(Color color) {
        return 0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue();
    }

    public static int truncate(int value) {
        if (value > 255) return 255;
        if (value < 0) return 0;
        return value;
    }

    public static int truncate(double value) {
        int newVal = (int) value;
        if (newVal > 255) return 255;
        if (newVal < 0) return 0;
        return newVal;
    }

    //For blending
    public static Color normalizeLuminance(Color original, Color blended) {
        double originalLuminance = computeLuminance(original);
        double blendedLuminance = computeLuminance(blended);
        double luminanceRatio = originalLuminance / blendedLuminance;

        int adjustedRed = clamp((int)(blended.getRed() * luminanceRatio), 0, 255);
        int adjustedGreen = clamp((int)(blended.getGreen() * luminanceRatio), 0, 255);
        int adjustedBlue = clamp((int)(blended.getBlue() * luminanceRatio), 0, 255);

        return new Color(adjustedRed, adjustedGreen, adjustedBlue);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static int getNewRGB(Color originalColor, double scale) {
        int rgb;
        int alpha = originalColor.getAlpha();
        int red = originalColor.getRed();
        int green = originalColor.getGreen();
        int blue = originalColor.getBlue();
        red = ColorManipulator.truncate((int) (128 + (red - 128) * scale));
        green = ColorManipulator.truncate((int) (128 + (green - 128) * scale));
        blue = ColorManipulator.truncate((int) (128 + (blue - 128) * scale));

        rgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
        return rgb;
    }
}
