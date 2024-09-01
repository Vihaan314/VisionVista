package com.visionvista.effects.filters;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ColorManipulator;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

public class Vignette extends Filter {
    @Serial
    private static final long serialVersionUID = 3842634112664032347L;

    private double intensity;

    public Vignette (@JsonProperty("value") double intensity) {
        super();
        this.intensity = intensity;
    }

    @Override public BufferedImage run(BufferedImage image) {
        BufferedImage vignetteImage = getEmptyImage(image);
        double adjustedIntensity = Math.sqrt(intensity/25);

        int width = image.getWidth();
        int height = image.getHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        double maxDistance = Math.sqrt(Math.pow(centerX, 2) + Math.pow(centerY, 2));

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);

                double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
                double factor = adjustedIntensity * Math.max(0, (distance - 0) / (maxDistance - 0));

                int red = ColorManipulator.truncate(((rgb >> 16) & 0xFF) * (1 - factor));
                int green = ColorManipulator.truncate(((rgb >> 8) & 0xFF) * (1 - factor));
                int blue = ColorManipulator.truncate((rgb & 0xFF) * (1 - factor));

                vignetteImage.setRGB(x, y, new Color(red, green, blue).getRGB());
            }
        }

        return vignetteImage;
    }

    @Override
    public Object getParameter() {
        return intensity;
    }

    @Override public String toString() {
        return "Applied Vignette. Intensity: " + this.intensity;
    }

    public static Vignette getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.VIGNETTE.getSliderBounds();
        return new Vignette(ImageHelper.getRandomParameter(bounds));
    }
}
