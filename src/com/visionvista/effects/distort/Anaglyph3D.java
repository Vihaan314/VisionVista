package com.visionvista.effects.distort;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.effects.EffectDescription;
import com.visionvista.effects.EffectParameter;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.image.BufferedImage;
import java.io.Serial;

@EffectParameter(parameters = "0, 30")
public class Anaglyph3D extends Distort {

    @Serial
    private static final long serialVersionUID = -2365211697672637508L;

    private int offset;

    public Anaglyph3D(@JsonProperty("value") double offset) {
        super();
        this.offset = (int) offset;
    }

    @Override protected int applyEffect(int red, int green, int blue, BufferedImage image, int x, int y) {
        //Anaglyph conversion formula - offset pixels based on x coordinate to give 3D look
        int shiftX = x + offset < image.getWidth() ? x + offset : x;
        int shiftedRGB = image.getRGB(shiftX, y);
        int shiftedGreen = (shiftedRGB >> 8) & 0xFF;
        int shiftedBlue = shiftedRGB & 0xFF;
        return (red << 16 | shiftedGreen << 8 | shiftedBlue);
    }

    @Override
    public Object getParameter() {
        return offset;
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
