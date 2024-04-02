package com.visionvista.effects.filters;

import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

public class CrossProcess extends Filter {
    @Serial
    private static final long serialVersionUID = -3731413825978193805L;

    public CrossProcess() {
        super();
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Cross-processing conversion formula
        int newRed = ColorManipulator.truncate((int) (red + (red * 0.3) - (green * 0.3)));
        int newGreen = ColorManipulator.truncate((int) (green + (green * 0.2) - (blue * 0.15)));
        int newBlue = ColorManipulator.truncate((int) (blue + (red * 0.15) + (green * 0.1)));

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override public String toString() {
        return "Applied cross-processing";
    }

    public static CrossProcess getRandomInstance() {
        return new CrossProcess();
    }
}
