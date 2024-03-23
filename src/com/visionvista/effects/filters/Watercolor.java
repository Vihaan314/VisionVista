package com.visionvista.effects.filters;

import com.visionvista.effects.enhance.EdgeEnhance;
import com.visionvista.effects.blur.GaussBlur;

import java.awt.image.BufferedImage;

public class Watercolor extends Filter {
    public Watercolor() {
        super();
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        BufferedImage blurredImage = new GaussBlur(2.5).run(image);
        BufferedImage edgeEnhancedImage = new EdgeEnhance().run(blurredImage);

        return edgeEnhancedImage;
    }

    @Override
    public String toString() {
        return "Applied Watercolor";
    }

    public static Watercolor getRandomInstance() {
        return new Watercolor();
    }
}
