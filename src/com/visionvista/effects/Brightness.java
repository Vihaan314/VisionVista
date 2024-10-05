package com.visionvista.effects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.utils.ColorManipulator;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.io.Serial;

@EffectParameter(parameters = "-100, 100")
public class Brightness extends Effect {
    @Serial
    private static final long serialVersionUID = 2796306533054543227L;

    private double intensity;

    public Brightness(@JsonProperty("value") double intensity) {
        this.intensity = intensity;
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Brightness conversion formula - increase RGB values by intensity
        int newRed = ColorManipulator.clamp(red + intensity);
        int newGreen = ColorManipulator.clamp(green + intensity);
        int newBlue = ColorManipulator.clamp(blue + intensity);

        return (newRed << 16 | newGreen << 8 | newBlue);
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
