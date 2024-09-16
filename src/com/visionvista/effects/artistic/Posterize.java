package com.visionvista.effects.artistic;

import com.visionvista.effects.EffectDescription;
import com.visionvista.effects.filters.Filter;
import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

@EffectDescription(description = "Turns darker, black / red tones")
public class Posterize extends Artistic {
    @Serial
    private static final long serialVersionUID = 4866353868092510145L;

    public Posterize() {
        super();
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Posterize conversion formula
        int newRed = ColorManipulator.truncate(Math.round((float) red / 85) * 85);
        int newGreen = ColorManipulator.truncate(Math.round((float) green / 85) * 85);
        int newBlue = ColorManipulator.truncate(Math.round((float) blue / 85) * 85);

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override public String toString() {
        return "Applied posterizing";
    }

    public static Posterize getRandomInstance() {
        return new Posterize();
    }
}
