package com.visionvista.effects.artistic;

import com.visionvista.effects.filters.Filter;
import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Posterize extends Artistic {
    public Posterize() {
        super();
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Posterize conversion formula
        int newRed = ColorManipulator.truncate(Math.round(red / 85) * 85);
        int newGreen = ColorManipulator.truncate(Math.round(green / 85) * 85);
        int newBlue = ColorManipulator.truncate(Math.round(blue / 85) * 85);

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override public String toString() {
        return "Applied posterizing";
    }

    public static Posterize getRandomInstance() {
        return new Posterize();
    }
}
