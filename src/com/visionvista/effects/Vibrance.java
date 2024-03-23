package com.visionvista.effects;

import com.visionvista.utils.Pair;
import com.visionvista.utils.ColorManipulator;
import com.visionvista.utils.ImageHelper;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.visionvista.utils.ColorManipulator.*;

public class Vibrance extends Effect{
    private double amount;

    public Vibrance(double amount) {
        super();
        this.amount = amount;
    }

    public static Color vibranceChange(Color color, double input) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        double x = Math.max(Math.max(r, g), b);
        double y = Math.min(Math.min(r, g), b);

        double gray = toGray(unGamma(r), unGamma(g), unGamma(b));
        double scale = input;

        if (x == r) {
            double t = (x == y) ? 0 : Math.min(1, Math.abs((g - b) / (x - y)));
            scale = scale * (1 + t) * 0.5;
        }

        double a = (x - y) / 255.0;
        double scale1 = scale * (2 - a);
        double scale2 = 1 + scale1 * (1 - a);
        double sub = y * scale1;

        r = (int) Math.round(unGamma(r * scale2 - sub));
        g = (int) Math.round(unGamma(g * scale2 - sub));
        b = (int) Math.round(unGamma(b * scale2 - sub));

        double gray2 = toGray(r, g, b);

        r = (int) Math.round(r * gray / gray2);
        g = (int) Math.round(g * gray / gray2);
        b = (int) Math.round(b * gray / gray2);

        double m = Math.max(Math.max(r, g), b);

        if (Gamma(m) > 255) {
            double scaleNew = (unGamma(255) - gray2) / (m - gray2);
            r = (int) Math.round((r - gray2) * scaleNew + gray2);
            g = (int) Math.round((g - gray2) * scaleNew + gray2);
            b = (int) Math.round((b - gray2) * scaleNew + gray2);
        }

        r = (int) Math.round(Gamma(r));
        g = (int) Math.round(Gamma(g));
        b = (int) Math.round(Gamma(b));

        return new Color(r, g, b);
    }

    @Override public String toString() {
        return "Applied Vibrance. Intensity: " + this.amount;
    }

    @Override
    public Object getParameter() {
        return amount;
    }

    @Override public BufferedImage run(BufferedImage image) {
        System.out.println("Changing vibrance");
        BufferedImage img_vibrance = getEmptyImage(image);

        for (int x = 0; x < (double) image.getWidth(); x++) {
            for (int y = 0; y < (double) image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                Color vibranceRGB = vibranceChange(color, amount);
                img_vibrance.setRGB(x, y, vibranceRGB.getRGB());
            }
        }
        return img_vibrance;
    }

    public static Vibrance getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.VIBRANCE.getSliderBounds();
        return new Vibrance(ImageHelper.getRandomParameter(bounds));
    }
}
