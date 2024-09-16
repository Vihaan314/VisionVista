package com.visionvista.effects.filters;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.blending.ColorDodge;
import com.visionvista.effects.EffectParameter;
import com.visionvista.effects.EffectType;
import com.visionvista.effects.blur.GaussianBlur;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.image.BufferedImage;
import java.io.Serial;

@EffectParameter(parameters = "0, 10")
public class Glow extends Filter {
    @Serial
    private static final long serialVersionUID = 3829537176814306081L;

    private double intensity;

    public Glow(@JsonProperty("value") double intensity) {
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
            BufferedImage blurImg = new GaussianBlur(quadraticSigma).run(image);
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
