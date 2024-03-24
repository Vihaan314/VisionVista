package com.visionvista.effects;

import com.visionvista.utils.ColorManipulator;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Contrast extends Effect{
    private double amount;

    public Contrast(double amount) {
        super();
        this.amount = amount;
    }

    @Override protected Color applyEffect(Color color) {
        int red = color.getRed();
        int green =  color.getGreen();
        int blue = color.getBlue();
        double scale = 1+amount/100;

        //Contrast conversion formula
        int newRed = ColorManipulator.truncate((int) (128 + (red - 128) * scale));
        int newGreen = ColorManipulator.truncate((int) (128 + (green - 128) * scale));
        int newBlue = ColorManipulator.truncate((int) (128 + (blue - 128) * scale));

        return new Color(newRed, newGreen, newBlue);
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
