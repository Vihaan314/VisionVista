package com.visionvista.effects.filters;

import com.visionvista.effects.*;
import com.visionvista.blending.ColorDodge;

import java.awt.image.BufferedImage;

public class PencilSketch extends Filter {
    public PencilSketch(BufferedImage image) {
        super(image);
    }

    @Override public String toString() {
        return "Applied pencil-sketch";
    }

    @Override public BufferedImage run() {
        BufferedImage grayscaleImg = new Grayscale(image).run();
        BufferedImage invertImg = new Negative(grayscaleImg).run();
        BufferedImage blurImg = new GaussBlur(invertImg, 5).run();
        BufferedImage blendImage = new ColorDodge(grayscaleImg, blurImg).blend();
        BufferedImage correctedBlend = new Brightness(blendImage, -20).run();
        return correctedBlend;
    }

    public static PencilSketch getRandomInstance(BufferedImage image) {
        return new PencilSketch(image);
    }
}
