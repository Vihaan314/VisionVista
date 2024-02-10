package com.visionvista.effects.filters;

import com.visionvista.utils.Pair;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ColorManipulator;
import com.visionvista.utils.ImageHelper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Vignette extends Filter {
    private double intensity;

    public Vignette (double intensity) {
        super();
        this.intensity = intensity;
    }

    @Override
    public Object getParameter() {
        return intensity;
    }

    @Override public String toString() {
        return "Applied Vignette. Intensity: " + this.intensity;
    }

    @Override public BufferedImage run(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage vignetteImage = new BufferedImage(width, height, image.getType());

        int centerX = width / 2;
        int centerY = height / 2;

        double maxDistance = Math.sqrt(Math.pow(centerX, 2) + Math.pow(centerY, 2));

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);

                double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
                double factor = intensity * Math.max(0, (distance - 0) / (maxDistance - 0));

                int red = ColorManipulator.truncate(color.getRed() * (1 - factor));
                int green = ColorManipulator.truncate(color.getGreen() * (1 - factor));
                int blue = ColorManipulator.truncate(color.getBlue() * (1 - factor));

                vignetteImage.setRGB(x, y, new Color(red, green, blue).getRGB());
            }
        }

        return vignetteImage;
    }

    public static Vignette getRandomInstance(BufferedImage image) {
        Pair<Integer, Integer> bounds = EffectType.VIGNETTE.getSliderBounds();
        return new Vignette(ImageHelper.getRandomParameter(bounds));
    }
}
