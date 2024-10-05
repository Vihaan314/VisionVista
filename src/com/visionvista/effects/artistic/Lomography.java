package com.visionvista.effects.artistic;

import com.visionvista.effects.EffectDescription;
import com.visionvista.utils.ColorManipulator;

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
        int newRed = ColorManipulator.clamp((int) (red + (red * 0.2) - (green * 0.1)));
        int newGreen = ColorManipulator.clamp((int) (green + (green * 0.1) + (blue * 0.1)));
        int newBlue = ColorManipulator.clamp((int) (blue + (blue * 0.2) - (red * 0.1)));

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override public String toString() {
        return "Applied lomography";
    }

    public static Lomography getRandomInstance() {
        return new Lomography();
    }
}
