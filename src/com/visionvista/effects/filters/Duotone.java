package com.visionvista.effects.filters;

import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Duotone extends Filter {

    private Color color1;
    private Color color2;

    public Duotone(Color color1, Color color2) {
        super();
        this.color1 = color1;
        this.color2 = color2;
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        int gray = (int) ColorManipulator.toGray(red, green, blue);
        int newRed = (color1.getRed() * gray + color2.getRed() * (255 - gray)) / 255;
        int newGreen = (color1.getGreen() * gray + color2.getGreen() * (255 - gray)) / 255;
        int newBlue = (color1.getBlue() * gray + color2.getBlue() * (255 - gray)) / 255;

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override
    public String toString() {
        return "Applied Duotone";
    }
}
