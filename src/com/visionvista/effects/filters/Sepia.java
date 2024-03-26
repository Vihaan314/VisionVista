package com.visionvista.effects.filters;

import com.visionvista.utils.Pair;
import com.visionvista.effects.Brightness;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ColorManipulator;
import com.visionvista.utils.ImageHelper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sepia extends Filter {
    private double intensity;

    public Sepia(double intensity) {
        super();
        this.intensity = intensity;
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Sepia conversion formula
        int newRed = ColorManipulator.truncate((int) (0.393*red + 0.769*green + 0.189*blue));
        int newGreen = ColorManipulator.truncate((int) (0.349*red + 0.686*green + 0.168*blue));
        int newBlue = ColorManipulator.truncate((int) (0.272*red + 0.534*green + 0.131*blue));

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override
    public Object getParameter() {
        return intensity;
    }

    @Override public String toString() {
        return "Applied sepia";
    }

    public static Sepia getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.SEPIA.getSliderBounds();
        return new Sepia(ImageHelper.getRandomParameter(bounds));
    }
}
