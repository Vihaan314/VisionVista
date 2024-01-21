package com.visionvista.effects;

import com.visionvista.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.visionvista.effects.Helper.*;

public class Vibrance extends Effect{
    private double intensity;

    public Vibrance(BufferedImage image, double amount) {
        super(image);
        this.intensity = intensity;
    }

    public static int[] vibranceChange(int r, int g, int b, double input) {
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

        return new int[] {r, g, b};
    }

    @Override public String toString() {
        return "Applied Vibrance. Intensity: " + this.intensity;
    }


    @Override public BufferedImage run() {
        System.out.println("Changing vibrance");
        BufferedImage img_vibrance = getEmptyImage(image);

        for (int x = 0; x < Math.floor(image.getWidth()); x++) {
            for (int y = 0; y < Math.floor(image.getHeight()); y++) {
                int[] rgb_arr = vibranceChange(Helper.get_rgb(image, "r", x, y), Helper.get_rgb(image, "g", x, y), Helper.get_rgb(image, "b", x, y), intensity);
                Color rgb_col = new Color(rgb_arr[0], rgb_arr[1], rgb_arr[2]);
                img_vibrance.setRGB(x, y, rgb_col.getRGB());
            }
        }
        return img_vibrance;
    }

    public static Vibrance getRandomInstance(BufferedImage image) {
        Pair<Integer, Integer> bounds = EffectType.VIBRANCE.getSliderBounds();
        return new Vibrance(image, ImageHelper.getRandomParameter(bounds));
    }
}
