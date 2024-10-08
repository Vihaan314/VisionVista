package com.visionvista.effects.filters;

import com.visionvista.effects.EffectDescription;
import com.visionvista.utils.ColorManipulator;

import java.io.Serial;

@EffectDescription(description = "Simulates infrared photography")
public class Infrared extends Filter {
    @Serial
    private static final long serialVersionUID = 8221869721211155321L;

    public Infrared() {
        super();
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Infrared conversion formula
        int newRed = ColorManipulator.clamp(red + green + blue) / 3;
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
