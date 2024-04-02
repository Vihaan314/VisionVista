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
    public BufferedImage run(BufferedImage image) {
        if (intensity == 0) {
            return image;
        }
        else {
            double quadraticSigma = 1.0 + Math.pow(intensity / 10.0 * 3.0, 2);
            BufferedImage blurImg = new GaussBlur(quadraticSigma).run(image);
            ColorDodge colorDodge = new ColorDodge(image, blurImg);
            return colorDodge.blend();
        }
    }

    @Override
    public Object getParameter() {
        return intensity;
    }

    @Override
    public String toString() {
        return "Applied Glow. Intensity: " + this.intensity;
    }

    public static Glow getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.GLOW.getSliderBounds();
        return new Glow(ImageHelper.getRandomParameter(bounds));
    }
}
