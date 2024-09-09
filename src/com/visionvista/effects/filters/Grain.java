package com.visionvista.effects.filters;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ColorManipulator;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.image.BufferedImage;
import java.io.Serial;
import java.util.Random;

public class Grain extends Filter {
    @Serial
    private static final long serialVersionUID = 5105217091907693112L;

    private double intensity;

    public Grain(@JsonProperty("value") double intensity) {
        super();
        this.intensity = intensity;
    }

    @Override
    protected int applyEffect(int red, int green, int blue) {
        Random random = new Random();
        int noise = (int) (random.nextGaussian() * intensity/4);

        int newRed = ColorManipulator.truncate(red + noise);
        int newGreen = ColorManipulator.truncate(green + noise);
        int newBlue = ColorManipulator.truncate(blue + noise);

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override
    public Object getParameter() {
        return this.intensity;
    }

    @Override
    public String toString() {
        return "Applied Grain. Intensity: " + this.intensity;
    }

    public static Grain getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.GRAIN.getSliderBounds();
        return new Grain(ImageHelper.getRandomParameter(bounds));
    }
}
