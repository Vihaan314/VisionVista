package com.visionvista.effects.distort;

import com.visionvista.effects.EffectType;
import com.visionvista.effects.filters.Filter;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ChromaticAberration extends Distort {

    private int offset; //The pixel offset for the color channels

    public ChromaticAberration(double offset) {
        super();
        this.offset = (int) offset;
    }

    @Override
    public Object getParameter() {
        return offset;
    }

    @Override public String toString() {
        return "Applied Chromatic Aberration. Offset: " + this.offset;
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        BufferedImage result = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                //Offset pixel values
                int px = Math.min(x + offset, image.getWidth() - 1);
                int nx = Math.max(x - offset, 0);

                int r = new Color(image.getRGB(px, y)).getRed();
                int g = new Color(image.getRGB(x, y)).getGreen();
                int b = new Color(image.getRGB(nx, y)).getBlue();

                Color newColor = new Color(r, g, b);
                result.setRGB(x, y, newColor.getRGB());
            }
        }

        return result;
    }

    public static ChromaticAberration getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.CHROMATIC_ABERRATION.getSliderBounds();
        return new ChromaticAberration(ImageHelper.getRandomParameter(bounds));
    }
}
