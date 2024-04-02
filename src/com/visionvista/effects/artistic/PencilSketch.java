package com.visionvista.effects.artistic;

import com.visionvista.effects.*;
import com.visionvista.blending.ColorDodge;
import com.visionvista.effects.blur.GaussBlur;
import com.visionvista.effects.filters.Filter;
import com.visionvista.effects.filters.Grayscale;
import com.visionvista.effects.filters.Negative;

import java.awt.image.BufferedImage;
import java.io.Serial;

public class PencilSketch extends Artistic {
    @Serial
    private static final long serialVersionUID = -5949361901012287058L;

    public PencilSketch() {
        super();
    }

    @Override public String toString() {
        return "Applied pencil-sketch";
    }

    @Override public BufferedImage run(BufferedImage image) {
        BufferedImage grayscaleImg = new Grayscale().run(image);
        BufferedImage invertImg = new Negative().run(grayscaleImg);
        BufferedImage blurImg = new GaussBlur(5).run(invertImg);
        BufferedImage blendImage = new ColorDodge(grayscaleImg, blurImg).blend();
        return new Brightness(-20).run(blendImage);
    }

    public static PencilSketch getRandomInstance() {
        return new PencilSketch();
    }
}
