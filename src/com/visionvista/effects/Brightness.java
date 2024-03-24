package com.visionvista.effects;

import com.visionvista.utils.Pair;
import com.visionvista.utils.ColorManipulator;
import com.visionvista.utils.ImageHelper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Brightness extends Effect implements Serializable {
    private double intensity;

    public Brightness(double intensity) {
        this.intensity = intensity;
    }

    @Override protected Color applyEffect(Color color) {
        int red = color.getRed();
        int green =  color.getGreen();
        int blue = color.getBlue();

        //Brightness conversion formula - increase RGB values
        red = ColorManipulator.truncate((int) (red + intensity));
        green = ColorManipulator.truncate((int) (green + intensity));
        blue = ColorManipulator.truncate((int) (blue + intensity));

        return new Color(red, green, blue);
    }

    @Override public String toString() {
        return "Applied Brightness. Intensity: " + this.intensity;
    }

    @Override
    public Object getParameter() {
        return intensity;
    }

    public static Brightness getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.BRIGHTNESS.getSliderBounds();
        return new Brightness(ImageHelper.getRandomParameter(bounds));
    }
}
