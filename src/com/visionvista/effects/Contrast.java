package com.visionvista.effects;

import com.visionvista.utils.ColorManipulator;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

public class Contrast extends Effect{
    @Serial
    private static final long serialVersionUID = 7444703618395475853L;

    private double amount;
    private double scale;

    public Contrast(double amount) {
        super();
        this.amount = amount;
        this.scale = 1+amount/100;
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Contrast conversion formula
        int newRed = ColorManipulator.truncate((int) (128 + (red - 128) * scale));
        int newGreen = ColorManipulator.truncate((int) (128 + (green - 128) * scale));
        int newBlue = ColorManipulator.truncate((int) (128 + (blue - 128) * scale));

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override public String toString() {
        return "Applied Contrast. Amount: " + this.amount;
    }

    @Override
    public Object getParameter() {
        return this.amount;
    }

    public static Contrast getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.CONTRAST.getSliderBounds();
        return new Contrast(ImageHelper.getRandomParameter(bounds));
    }
}
