package com.visionvista.effects.distort;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

public class ChromaticAberration extends Distort {

    @Serial
    private static final long serialVersionUID = 4828993536339714850L;

    private int offset; //The pixel offset for the color channels

    public ChromaticAberration(@JsonProperty("value") double offset) {
        super();
        this.offset = (int) offset;
    }

    @Override protected int applyEffect(int red, int green, int blue, BufferedImage image, int x, int y) {
        //Chromatic aberration formula - offset pixels to give more of a glitched look
        int px = Math.min(x + offset, image.getWidth() - 1);
        int nx = Math.max(x - offset, 0);

        int newRed = new Color(image.getRGB(px, y)).getRed();
        int newGreen = new Color(image.getRGB(x, y)).getGreen();
        int newBlue = new Color(image.getRGB(nx, y)).getBlue();

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override
    public Object getParameter() {
        return offset;
    }

    @Override public String toString() {
        return "Applied Chromatic Aberration. Offset: " + this.offset;
    }

    public static ChromaticAberration getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.CHROMATIC_ABERRATION.getSliderBounds();
        return new ChromaticAberration(ImageHelper.getRandomParameter(bounds));
    }
}
