package com.visionvista.effects.artistic;

import com.visionvista.effects.EffectDescription;
import com.visionvista.effects.filters.Filter;
import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

@EffectDescription(description = "Brighter green tone")
public class Lomography extends Artistic {
    @Serial
    private static final long serialVersionUID = -612951579289507412L;

    public Lomography() {
        super();
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Lomography conversion formula
        int newRed = ColorManipulator.truncate((int) (red + (red * 0.2) - (green * 0.1)));
        int newGreen = ColorManipulator.truncate((int) (green + (green * 0.1) + (blue * 0.1)));
        int newBlue = ColorManipulator.truncate((int) (blue + (blue * 0.2) - (red * 0.1)));

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override public String toString() {
        return "Applied lomography";
    }

    public static Lomography getRandomInstance() {
        return new Lomography();
    }
}
