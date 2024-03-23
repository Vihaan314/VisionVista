package com.visionvista.effects.filters;

import com.visionvista.effects.EffectType;
import com.visionvista.effects.blur.GaussBlur;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TiltShift extends Filter {

    private double blurStrength;

    public TiltShift(double blurStrength) {
        this.blurStrength = blurStrength;
    }

    @Override
    public Object getParameter() {
        return blurStrength;
    }

    @Override
    public String toString() {
        return "Applied Tilt Shift. Blur strength: " + blurStrength;
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        int focusAreaStart = image.getHeight() / 3;
        int focusAreaEnd = 2 * image.getHeight() / 3;
        BufferedImage blurredImage = new GaussBlur(blurStrength).run(image);
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color originalColor = new Color(image.getRGB(x, y));
                Color blurredColor = new Color(blurredImage.getRGB(x, y));

                double blendFactor;
                if (y < focusAreaStart) {
                    blendFactor = (double)(focusAreaStart - y) / focusAreaStart;
                } else if (y > focusAreaEnd) {
                    blendFactor = (double)(y - focusAreaEnd) / (image.getHeight() - focusAreaEnd);
                } else {
                    blendFactor = 0;
                }

                int red = (int)(originalColor.getRed() * (1 - blendFactor) + blurredColor.getRed() * blendFactor);
                int green = (int)(originalColor.getGreen() * (1 - blendFactor) + blurredColor.getGreen() * blendFactor);
                int blue = (int)(originalColor.getBlue() * (1 - blendFactor) + blurredColor.getBlue() * blendFactor);

                Color blendedColor = new Color(red, green, blue);
                result.setRGB(x, y, blendedColor.getRGB());
            }
        }

        return result;
    }

    public static TiltShift getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.TILT_SHIFT.getSliderBounds();
        return new TiltShift(ImageHelper.getRandomParameter(bounds));
    }
}
