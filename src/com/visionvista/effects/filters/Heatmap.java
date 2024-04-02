package com.visionvista.effects.filters;

import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

public class Heatmap extends Filter {
    @Serial
    private static final long serialVersionUID = -348520464103120836L;

    public Heatmap() {
        super();
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Heatmap conversion formula
        int average = (red + green + blue) / 3;
        int newRed = ColorManipulator.truncate(average + 50);
        int newGreen = ColorManipulator.truncate((int) (average * 0.7));
        int newBlue = ColorManipulator.truncate((int) (average * 0.4));

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override public String toString() {
        return "Applied heatmap";
    }

    public static Heatmap getRandomInstance() {
        return new Heatmap();
    }
}
