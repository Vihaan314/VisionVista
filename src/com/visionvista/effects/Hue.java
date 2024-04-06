package com.visionvista.effects;

import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

public class Hue extends Effect{
    @Serial
    private static final long serialVersionUID = -5079533650035834989L;

    private Color color;

    public Hue(Color color) {
        super();
        this.color = color;
    }

    @Override public String toString() {
        return "Applied Hue. Color: " + "#"+Integer.toHexString(this.color.getRGB()).substring(2).toUpperCase();
    }

    @Override
    public Object getParameter() {
        return color;
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Adding hue
        int newRed = (red + this.color.getRed()) / 2;
        int newGreen = (green + this.color.getGreen()) / 2;
        int newBlue = (blue + this.color.getBlue()) / 2;

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    public static Hue getRandomInstance() {
        return new Hue(new Color(ImageHelper.getRandomParameter(ImageHelper.getColorBounds()),ImageHelper.getRandomParameter(ImageHelper.getColorBounds()),ImageHelper.getRandomParameter(ImageHelper.getColorBounds())));
    }
}
