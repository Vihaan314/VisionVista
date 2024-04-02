package com.visionvista.effects.filters;

import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

public class Grayscale extends Filter {
    @Serial
    private static final long serialVersionUID = -918140131794156582L;

    public Grayscale() {
        super();
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        int gray = (int) ColorManipulator.toGray(red, green, blue);
        return (gray << 16 | gray << 8 | gray);
    }

    @Override public String toString() {
        return "Applied grayscaling";
    }

    public static Grayscale getRandomInstance() {
        return new Grayscale();
    }
}
