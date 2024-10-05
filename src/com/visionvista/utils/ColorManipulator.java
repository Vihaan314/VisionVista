package com.visionvista.utils;

public class ColorManipulator {
    //Color correction
    public static double unGamma(double value) {
        return Math.pow(value / 255.0, 2.2) * 255.0;
    }

    public static double Gamma(double value) {
        return Math.pow(value / 255.0, 1.0 / 2.2) * 255.0;
    }

    public static double toGray(double r, double g, double b) {
        return 0.299 * r + 0.587 * g + 0.114 * b;
    }

    //Helper
    public static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    public static int clamp(double value) {
        int newVal = (int) value;
        return Math.max(0, Math.min(255, newVal));
    }

    public static int clampCoordinate(int value, int max) {
        return Math.max(0, Math.min(max - 1, value));
    }

    public static int getBrightness(int color) {
        return (color >> 16) & 0xFF;
    }

}
