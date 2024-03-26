package com.visionvista.effects.distort;

import com.visionvista.effects.Effect;

import java.awt.image.BufferedImage;

public abstract class Distort extends Effect {
    public Distort() {
        super();
    }

    protected int applyEffect(int red, int green, int blue, BufferedImage image, int x, int y) {
        return (red << 16 | green << 8 | blue);
    }

    protected int applyEffectAtPixel(int rgb, BufferedImage image, int x, int y) {
        int alpha = (rgb >>> 24);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        int newRGB = applyEffect(red, green, blue, image, x, y);
        return (alpha << 24) | newRGB;
    }

    @Override public BufferedImage run(BufferedImage image) {
        BufferedImage result = getEmptyImage(image);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int newRGB = applyEffectAtPixel(image.getRGB(x, y), image, x, y);
                result.setRGB(x, y, newRGB);
            }
        }
        return result;
    }

    @Override
    public Object getParameter() {
        return null;
    }
}
