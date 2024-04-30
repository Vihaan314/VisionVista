package com.visionvista.effects.artistic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

public class OilPainting extends Artistic {

    @Serial
    private static final long serialVersionUID = -1191187314685833264L;

    private int radius = 5;
    private int intensity;

    public OilPainting(@JsonProperty("value") double intensity) {
        super();
//        this.radius = radius;
        this.intensity = (int) intensity;
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Map<Color, Integer> colorCount = new HashMap<>();

                for (int i = -radius; i <= radius; i++) {
                    for (int j = -radius; j <= radius; j++) {
                        if (x + i >= 0 && x + i < image.getWidth() && y + j >= 0 && y + j < image.getHeight()) {
                            Color color = new Color(image.getRGB(x + i, y + j));
                            //Quantize the color
                            Color quantizedColor = new Color(
                                    (color.getRed() / intensity) * intensity,
                                    (color.getGreen() / intensity) * intensity,
                                    (color.getBlue() / intensity) * intensity);
                            colorCount.merge(quantizedColor, 1, Integer::sum);
                        }
                    }
                }

                Color mostCommonColor = colorCount.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(Color.BLACK);

                result.setRGB(x, y, mostCommonColor.getRGB());
            }
        }
        return result;
    }


    @Override
    public Object getParameter() {
        return intensity;
    }

    @Override
    public String toString() {
        return "Applied Oil Painting. Intensity: " + intensity;
    }

    public static OilPainting getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.OIL_PAINTING.getSliderBounds();
        return new OilPainting(ImageHelper.getRandomParameter(bounds));
    }
}
