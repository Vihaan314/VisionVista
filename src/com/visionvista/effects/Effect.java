package com.visionvista.effects;

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

    public abstract BufferedImage run(BufferedImage image);

    public static BufferedImage getEmptyImage(BufferedImage image) {
        return new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    }

    protected BufferedImage applyEffect(BufferedImage sourceImage, ColorTransformer transformer) {
        BufferedImage outputImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < sourceImage.getWidth(); x++) {
            for (int y = 0; y < sourceImage.getHeight(); y++) {
                int rgbColor = sourceImage.getRGB(x, y);
                int transformedColor = transformer.apply(x, y, rgbColor);
                outputImage.setRGB(x, y, transformedColor);
            }
        }
        return outputImage;
    }

    public String toString() {
        return "Effect";
    }

//    public Pair<Integer, Integer> getSliderBounds() {
//        try {
//            String className = this.getClass().getSimpleName().toUpperCase();
//            Field field = EffectType.class.getField(className);
//            EffectType effectType = (EffectType) field.get(null);
//            return effectType.getSliderBounds();
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new RuntimeException("Slider bounds not defined for " + this.getClass().getSimpleName());
//        }
//    }
}
