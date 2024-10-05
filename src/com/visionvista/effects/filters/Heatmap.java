package com.visionvista.effects.filters;

import com.visionvista.effects.EffectDescription;
import com.visionvista.utils.ColorManipulator;

import java.io.Serial;

@EffectDescription(description = "Visualizes image as thermal colors")
public class Heatmap extends Filter {
    @Serial
    private static final long serialVersionUID = -348520464103120836L;

    public Heatmap() {
        super();
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Heatmap conversion formula
        int average = (red + green + blue) / 3;
        int newRed = ColorManipulator.clamp(average + 50);
        int newGreen = ColorManipulator.clamp((int) (average * 0.7));
        int newBlue = ColorManipulator.clamp((int) (average * 0.4));

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override public String toString() {
        return "Applied heatmap";
    }

    public static Heatmap getRandomInstance() {
        return new Heatmap();
    }
}
