package com.visionvista.effects.filters;

import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

public class SplitTone extends Filter {
    @Serial
    private static final long serialVersionUID = 1957385647116092992L;

    public SplitTone() {
        super();
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Spit tone conversion formula
        int newRed = 0, newGreen = 0, newBlue = 0;
        if (red + green + blue > 382) {
            newRed = ColorManipulator.truncate(red + 40);
            newGreen = ColorManipulator.truncate(green + 40);
        } else {
            newBlue = ColorManipulator.truncate(blue + 50);
        }

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override public String toString() {
        return "Applied split-tone";
    }

    public static SplitTone getRandomInstance() {
        return new SplitTone();
    }
}
