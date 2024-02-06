package com.visionvista.effects;

import com.visionvista.utils.Pair;
import com.visionvista.utils.ImageHelper;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.Serializable;

public class Blur extends Effect implements Serializable {
    private int intensity;

    public Blur(double intensity) {
        this.intensity = (int) intensity;
    }

    @Override public String toString() {
        return "Applied Blur. Intensity: " + this.intensity;
    }

    @Override
    public Object getParameter() {
        return intensity;
    }

    public BufferedImage run(BufferedImage image) {
        System.out.println("Blurring image");

        BufferedImage blurredImg;

        int size = intensity * 2 + 1;
        float weight = 1.0f / (size * size);
        float[] elements = new float[size * size];

        for (int i = 0; i < elements.length; i++) {
            elements[i] = weight;
        }

        Kernel kernel = new Kernel(size, size, elements);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        blurredImg = op.filter(image, null);

        return blurredImg;
    }

    public static Blur getRandomInstance(BufferedImage image) {
        Pair<Integer, Integer> bounds = EffectType.BLUR.getSliderBounds();
        return new Blur(ImageHelper.getRandomParameter(bounds));
    }
}
