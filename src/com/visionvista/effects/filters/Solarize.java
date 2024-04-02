package com.visionvista.effects.filters;

import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

public class Solarize extends Filter {
    @Serial
    private static final long serialVersionUID = -9223227824919724810L;

    public Solarize() {
        super();
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Solarize conversion procedure
        int newRed, newGreen, newBlue;
        if (red > 128) newRed = 255 - red;
        else newRed = red;

        if (green > 128) newGreen = 255 - green;
        else newGreen = green;

        if (blue > 128) newBlue = 255 - blue;
        else newBlue = blue;

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override public String toString() {
        return "Applied solarizing";
    }

    public static Solarize getRandomInstance() {
        return new Solarize();
    }
}
