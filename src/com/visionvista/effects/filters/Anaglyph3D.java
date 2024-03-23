package com.visionvista.effects.filters;

import com.visionvista.effects.EffectType;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Anaglyph3D extends Filter {

    private int offset;

    public Anaglyph3D(double offset) {
        super();
        this.offset = (int) offset;
    }

    @Override
    public Object getParameter() {
        return offset;
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);

                //Compute shifted color
                int shiftX = x + offset < image.getWidth() ? x + offset : x;
                int shiftedRGB = image.getRGB(shiftX, y);
                Color shiftedColor = new Color(shiftedRGB);

                Color anaglyphColor = new Color(color.getRed(), shiftedColor.getGreen(), shiftedColor.getBlue());
                result.setRGB(x, y, anaglyphColor.getRGB());
            }
        }

        return result;
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
