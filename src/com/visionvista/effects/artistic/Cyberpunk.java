package com.visionvista.effects.artistic;

import com.visionvista.effects.EffectDescription;
import com.visionvista.effects.filters.Filter;
import com.visionvista.utils.ColorManipulator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

@EffectDescription(description = "Accentuates futuristic blue tones to the image")
public class Cyberpunk extends Artistic {

    @Serial
    private static final long serialVersionUID = 1748721184831132510L;

    public Cyberpunk() {
        super();
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Cyberpunk conversion formula
        int newRed = (int) (Math.min(255, red * 1.2 + 30));
        int newGreen = (int) (Math.min(255, green * 1.2 + 30));
        int newBlue = (int) (Math.min(255, blue * 1.2 + 30));

        newBlue = Math.min(255, newBlue + 50);
        if (newGreen > 100) {
            newGreen = Math.min(255, newGreen + 20);
        }
        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override
    public String toString() {
        return "Applied Cyberpunk";
    }

    public static Cyberpunk getRandomInstance() {
        return new Cyberpunk();
    }
}
