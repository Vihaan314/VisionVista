package com.visionvista.effects.filters;

import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;


public class Negative extends Filter {
    @Serial
    private static final long serialVersionUID = 1824964793972213463L;

    public Negative() {
        super();
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Negative conversion formula
        int newRed = 255 - red;
        int newGreen = 255 - green;
        int newBlue = 255 - blue;

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override public String toString() {
        return "Applied negative";
    }

    public static Negative getRandomInstance() {
        return new Negative();
    }
}
