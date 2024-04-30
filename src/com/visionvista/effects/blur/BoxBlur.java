package com.visionvista.effects.blur;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.effects.Effect;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.Pair;
import com.visionvista.utils.ImageHelper;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

public class BoxBlur extends Blur {
    @Serial
    private static final long serialVersionUID = -4888871537170881156L;

    private int intensity;

    public BoxBlur(@JsonProperty("value") double intensity) {
        this.intensity = (int) intensity;
    }

    @Override public String toString() {
        return "Applied Blur. Intensity: " + this.intensity;
    }

    @Override
    public Object getParameter() {
        return intensity;
    }

    public BufferedImage run(BufferedImage image) {
        System.out.println("Blurring image");

        BufferedImage blurredImg;

        int size = intensity * 2 + 1;
        float weight = 1.0f / (size * size);
        float[] elements = new float[size * size];

        Arrays.fill(elements, weight);

        Kernel kernel = new Kernel(size, size, elements);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        blurredImg = op.filter(image, null);

        return blurredImg;
    }

    public static BoxBlur getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.BOX_BLUR.getSliderBounds();
        return new BoxBlur(ImageHelper.getRandomParameter(bounds));
    }
}
