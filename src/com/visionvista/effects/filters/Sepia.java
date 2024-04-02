package com.visionvista.effects.filters;

import com.visionvista.effects.Brightness;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ColorManipulator;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.io.Serial;
import java.io.Serializable;

public class Sepia extends Filter {
    @Serial
    private static final long serialVersionUID = -1859549826646856909L;

    private double intensity;

    public Sepia(double intensity) {
        this.intensity = intensity;
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Sepia conversion formula - apply formula and blend with original
        int newRed = ColorManipulator.truncate( (red * 0.393) + (green * 0.769) + (blue * 0.189));
        int newGreen = ColorManipulator.truncate((red * 0.349) + (green * 0.686) + (blue * 0.168));
        int newBlue = ColorManipulator.truncate((red * 0.272) + (green * 0.534) + (blue * 0.131));

        newRed = (int) ((newRed * intensity / 10) + (red * (10 - intensity) / 10));
        newGreen = (int) ((newGreen * intensity / 10) + (green * (10 - intensity) / 10));
        newBlue = (int) ((newBlue * intensity / 10) + (blue * (10 - intensity) / 10));

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override public String toString() {
        return "Applied Sepia. Intensity: " + this.intensity;
    }

    @Override
    public Object getParameter() {
        return intensity;
    }

    public static Sepia getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.SEPIA.getSliderBounds();
        return new Sepia(ImageHelper.getRandomParameter(bounds));
    }
}
