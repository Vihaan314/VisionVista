package com.visionvista.effects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.io.Serializable;

public abstract class Effect implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    public Effect() {
    }

    public abstract Object getParameter();

    public BufferedImage run(BufferedImage image) {
        BufferedImage result = getEmptyImage(image);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int newRGB = applyEffectAtPixel(image.getRGB(x, y));
                result.setRGB(x, y, newRGB);
            }
        }
        return result;
    }

    protected int applyEffectAtPixel(int rgb) {
        int alpha = (rgb >>> 24);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        int newRGB = applyEffect(red, green, blue);
        return (alpha << 24) | newRGB;
    }

    protected int applyEffect(int red, int green, int blue) {
        return (red << 16 | green << 8 | blue);
    }

    protected BufferedImage getEmptyImage(BufferedImage reference) {
        return new BufferedImage(reference.getWidth(), reference.getHeight(), BufferedImage.TYPE_INT_ARGB);
    }


    public String toString() {
        return "Effect";
    }
}
