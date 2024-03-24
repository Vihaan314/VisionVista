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
                Color originalColor = new Color(image.getRGB(x, y));
                Color newColor = applyEffect(originalColor);
                result.setRGB(x, y, newColor.getRGB());
            }
        }
        return result;
    }

    protected Color applyEffect(Color color) {
        return color;
    }

    protected BufferedImage getEmptyImage(BufferedImage reference) {
        return new BufferedImage(reference.getWidth(), reference.getHeight(), BufferedImage.TYPE_INT_ARGB);
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
