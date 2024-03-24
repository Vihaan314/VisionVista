package com.visionvista.effects.artistic;

import com.visionvista.effects.*;
import com.visionvista.blending.ColorDodge;
import com.visionvista.effects.blur.GaussBlur;
import com.visionvista.effects.filters.Filter;
import com.visionvista.effects.filters.Grayscale;
import com.visionvista.effects.filters.Negative;

import java.awt.image.BufferedImage;

public class PencilSketch extends Artistic {
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
        BufferedImage correctedBlend = new Brightness(-20).run(blendImage);
        return correctedBlend;
    }

    public static PencilSketch getRandomInstance() {
        return new PencilSketch();
    }
}
