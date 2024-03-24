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

    public static double toGray(double r, double g, double b) {
        return 0.299 * r + 0.587 * g + 0.114 * b;
    }

    //Helper
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
}
