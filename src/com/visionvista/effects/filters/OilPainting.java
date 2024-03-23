package com.visionvista.effects.filters;

import com.visionvista.effects.EffectType;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class OilPainting extends Filter {

    private int radius = 3;
    private int intensityLevels;

    public OilPainting(double intensityLevels) {
        super();
//        this.radius = radius;
        this.intensityLevels = (int) intensityLevels;
    }

    @Override
    public Object getParameter() {
        return intensityLevels;
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Map<Color, Integer> colorMap = new HashMap<>();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                colorMap.clear();
                for (int ky = -radius; ky <= radius; ky++) {
                    int py = y + ky;
                    if (py < 0 || py >= image.getHeight()) continue;

                    for (int kx = -radius; kx <= radius; kx++) {
                        int px = x + kx;
                        if (px < 0 || px >= image.getWidth()) continue;

                        Color pixelColor = new Color(image.getRGB(px, py));
                        Color quantizedColor = quantizeColor(pixelColor, intensityLevels);
                        colorMap.merge(quantizedColor, 1, Integer::sum);
                    }
                }

                Color mostFrequent = colorMap.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(Color.BLACK);
                result.setRGB(x, y, mostFrequent.getRGB());
            }
        }

        return result;
    }

    private Color quantizeColor(Color color, int levels) {
        int r = (color.getRed() / levels) * levels;
        int g = (color.getGreen() / levels) * levels;
        int b = (color.getBlue() / levels) * levels;
        return new Color(r, g, b);
    }

    @Override
    public String toString() {
        return "Applied Oil Painting. Intensity: " + intensityLevels;
    }

    public static OilPainting getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.OIL_PAINTING.getSliderBounds();
        return new OilPainting(ImageHelper.getRandomParameter(bounds));
    }
}
