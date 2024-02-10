package com.visionvista.effects.filters;

import com.visionvista.utils.Pair;
import com.visionvista.effects.Brightness;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ColorManipulator;
import com.visionvista.utils.ImageHelper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sepia extends Filter {
    private double intensity;

    public Sepia(double intensity) {
        super();
        this.intensity = intensity;
    }

    @Override
    public Object getParameter() {
        return intensity;
    }

    @Override public String toString() {
        return "Applied sepia";
    }

     @Override public BufferedImage run(BufferedImage image) {
         return applyEffect(image, (x, y, rgbColor) -> {
             int alpha = (rgbColor >> 24) & 0xff;
             int red = (rgbColor >> 16) & 0xff;
             int green = (rgbColor >> 8) & 0xff;
             int blue = rgbColor & 0xff;

             int gray = (int)(0.299 * red + 0.587 * green + 0.114 * blue);
             red = ColorManipulator.truncate((int)(gray * 0.9));
             green = ColorManipulator.truncate((int)(gray * 0.7));
             blue = ColorManipulator.truncate((int)(gray * 0.5));

             return (alpha << 24) | (red << 16) | (green << 8) | blue;
         });
//        BufferedImage sepiaCorrected = new Brightness(-intensity).run(sepiaImg);
//        return sepiaCorrected;
    }


    public static Sepia getRandomInstance(BufferedImage image) {
        Pair<Integer, Integer> bounds = EffectType.SEPIA.getSliderBounds();
        return new Sepia(ImageHelper.getRandomParameter(bounds));
    }
}
