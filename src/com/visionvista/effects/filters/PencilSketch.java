package com.visionvista.effects.filters;

import com.visionvista.effects.*;
import com.visionvista.blending.ColorDodge;

import java.awt.image.BufferedImage;

public class PencilSketch extends Filter {
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

    public static PencilSketch getRandomInstance(BufferedImage image) {
        return new PencilSketch();
    }
}
