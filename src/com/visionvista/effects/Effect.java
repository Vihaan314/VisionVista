package com.visionvista.effects;

import com.visionvista.Pair;

import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;

public abstract class Effect implements Serializable
{
    public Effect() {
    }

    public abstract BufferedImage run(BufferedImage image);

    public static BufferedImage getEmptyImage(BufferedImage image) {
        return new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    }

    public Pair<Integer, Integer> getSliderBounds() {
        try {
            String className = this.getClass().getSimpleName().toUpperCase();
            Field field = EffectType.class.getField(className);
            EffectType effectType = (EffectType) field.get(null);
            return effectType.getSliderBounds();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Slider bounds not defined for " + this.getClass().getSimpleName());
        }
    }
}
