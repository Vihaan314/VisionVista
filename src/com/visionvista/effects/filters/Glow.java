package com.visionvista.effects.filters;

import com.visionvista.effects.blur.GaussianBlur2;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.blending.AdditiveBlending;
import com.visionvista.blending.Blender;
import com.visionvista.effects.EffectParameter;

import java.awt.image.BufferedImage;
import java.io.Serial;

@EffectParameter(parameters = "0, 10")
public class Glow extends Filter {
    @Serial
    private static final long serialVersionUID = 3829537176814306081L;

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
        BufferedImage blurredImage = new GaussianBlur2(intensity).run(image);
        Blender blend = new AdditiveBlending(image, blurredImage, this.intensity);
        return blend.blend();
    }
}
