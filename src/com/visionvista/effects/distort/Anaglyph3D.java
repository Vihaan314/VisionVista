package com.visionvista.effects.distort;

import com.visionvista.effects.EffectType;
import com.visionvista.effects.filters.Filter;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Anaglyph3D extends Distort {

    private int offset;

    public Anaglyph3D(double offset) {
        super();
        this.offset = (int) offset;
    }

    @Override protected int applyEffect(int red, int green, int blue, BufferedImage image, int x, int y) {
        //Anaglyph conversion formula - offset pixels to give 3D look
        int shiftX = x + offset < image.getWidth() ? x + offset : x;
        int shiftedRGB = image.getRGB(shiftX, y);
        int shiftedGreen = (shiftedRGB >> 8) & 0xFF;
        int shiftedBlue = shiftedRGB & 0xFF;
        return (red << 16 | shiftedGreen << 8 | shiftedBlue);
    }

    @Override
    public Object getParameter() {
        return offset;
    }

    @Override
    public String toString() {
        return "Applied Anaglyph 3D. Offset: " + offset;
    }

    public static Anaglyph3D getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.ANAGLYPH.getSliderBounds();
        return new Anaglyph3D(ImageHelper.getRandomParameter(bounds));
    }
}
