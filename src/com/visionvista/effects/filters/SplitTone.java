package com.visionvista.effects.filters;

import com.visionvista.effects.EffectDescription;
import com.visionvista.utils.ColorManipulator;

import java.io.Serial;

@EffectDescription(description = "Applies an intense cool blue filter")
public class SplitTone extends Filter {
    @Serial
    private static final long serialVersionUID = 1957385647116092992L;

    public SplitTone() {
        super();
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Spit tone conversion formula
        int newRed = ColorManipulator.clamp((int) (red * 0.5));
        int newBlue = ColorManipulator.clamp((int) (blue * 1.5));

        return (newRed << 16 | green << 8 | newBlue);
    }

    @Override public String toString() {
        return "Applied split-tone";
    }

    public static SplitTone getRandomInstance() {
        return new SplitTone();
    }
}
