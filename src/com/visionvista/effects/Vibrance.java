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

    @Override protected int applyEffect(int red, int green, int blue) {
        double unGammaR = unGamma(red);
        double unGammaG = unGamma(green);
        double unGammaB = unGamma(blue);

        double gray = toGray(unGammaR, unGammaG, unGammaB);

        double max = Math.max(Math.max(unGammaR, unGammaG), unGammaB);
        double min = Math.min(Math.min(unGammaR, unGammaG), unGammaB);

        double scale = amount;
        if (max == unGammaR) {
            double t = (max == min) ? 0 : (unGammaG - unGammaB) / (max - min);
            scale *= 1 + Math.abs(t) * 0.5;
        }

        double a = (max - min) / 255.0;
        double scale1 = scale * (2 - a);
        double scale2 = 1 + scale1 * (1 - a);
        double sub = min * scale1;

        unGammaR = unGammaR * scale2 - sub;
        unGammaG = unGammaG * scale2 - sub;
        unGammaB = unGammaB * scale2 - sub;

        double gray2 = toGray(unGammaR, unGammaG, unGammaB);
        if (gray2 != 0) {
            double grayRatio = gray / gray2;
            unGammaR *= grayRatio;
            unGammaG *= grayRatio;
            unGammaB *= grayRatio;
        }

        int newRed = truncate((int) Gamma(unGammaR));
        int newGreen = truncate((int) Gamma(unGammaG));
        int newBlue = truncate((int) Gamma(unGammaB));

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override public String toString() {
        return "Applied Vibrance. Intensity: " + this.amount;
    }

    @Override
    public Object getParameter() {
        return amount;
    }

    public static Vibrance getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.VIBRANCE.getSliderBounds();
        return new Vibrance(ImageHelper.getRandomParameter(bounds));
    }
}
