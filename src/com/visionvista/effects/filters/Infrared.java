package com.visionvista.effects.filters;

import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Infrared extends Filter {
    public Infrared() {
        super();
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Infrared conversion formula
        int newRed = ColorManipulator.truncate(red + green + blue) / 3;
        int newGreen = green / 2;
        int newBlue = blue / 2;

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override public String toString() {
        return "Applied infrared";
    }

    public static Infrared getRandomInstance() {
        return new Infrared();
    }
}
