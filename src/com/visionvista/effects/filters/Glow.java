package com.visionvista.effects.filters;

import com.visionvista.effects.blur.GaussBlur;
import com.visionvista.utils.Pair;
import com.visionvista.blending.ColorDodge;
import com.visionvista.effects.*;
import com.visionvista.utils.ImageHelper;

import java.awt.image.BufferedImage;

public class Glow extends Filter {
    private double intensity;

    public Glow(double intensity) {
        super();
        this.intensity = intensity;
    }

    @Override
    public Object getParameter() {
        return intensity;
    }

    @Override
    public String toString() {
        return "Applied Glow. Intensity: " + this.intensity;
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        BufferedImage blurImg = new GaussBlur(intensity).run(image);
        BufferedImage blendImg = new ColorDodge(image, blurImg).blend();
        BufferedImage correctedBlend = new Brightness(-10*intensity).run(blendImg);
        return correctedBlend;
    }


    public static Glow getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.GLOW.getSliderBounds();
        return new Glow(ImageHelper.getRandomParameter(bounds));
    }
}
