package com.visionvista.effects.filters;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.blending.ColorDodge;
import com.visionvista.effects.Brightness;
import com.visionvista.effects.EffectParameter;
import com.visionvista.effects.EffectType;
import com.visionvista.effects.blur.GaussianBlur2;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.image.BufferedImage;

@EffectParameter(parameters = "0, 10")
public class Glow extends Filter {
    private double intensity;

    public Glow(@JsonProperty("intensity") double intensity) {
        super();
        this.intensity = intensity;
    }

    @Override
    public String toString() {
        return "Applied Glow. Intensity: " + this.intensity;
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        BufferedImage blurImg = new GaussianBlur2(intensity).run(image);
        BufferedImage blendImg = new ColorDodge(image, blurImg).blend();
        BufferedImage correctedBlend = new Brightness(-5*intensity).run(blendImg);
        return correctedBlend;
    }


    public static Glow getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.GLOW.getSliderBounds();
        return new Glow(ImageHelper.getRandomParameter(bounds));
    }
}