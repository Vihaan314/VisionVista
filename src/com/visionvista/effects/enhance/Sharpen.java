package com.visionvista.effects.enhance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.Serial;


public class Sharpen extends Enhance {
    @Serial
    private static final long serialVersionUID = -8974053428062907406L;

    private double amount;

    public Sharpen(@JsonProperty("value") double amount) {
        super();
        this.amount = amount;
    }

    @Override public String toString() {
        return "Applied Sharpen. Amount: " + this.amount;
    }

    @Override
    public Object getParameter() {
        return amount;
    }

    @Override public BufferedImage run(BufferedImage image) {
        BufferedImage sharpenedImg = getEmptyImage(image);

        float[] SHARPEN3x3 = {
                0.0f, -1.0f, 0.0f,
                -1.0f,  5.0f, -1.0f,
                0.0f, -1.0f, 0.0f
        };
        for (int i = 0; i < SHARPEN3x3.length; i++) {
            SHARPEN3x3[i] *= (float) amount;
        }
        SHARPEN3x3[4] = 1.0f + Math.abs(SHARPEN3x3[4]);

        ConvolveOp convolve = new ConvolveOp(new Kernel(3, 3, SHARPEN3x3), ConvolveOp.EDGE_NO_OP, null);
        convolve.filter(image, sharpenedImg);
        return sharpenedImg;
    }

    public static Sharpen getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.SHARPEN.getSliderBounds();
        return new Sharpen(ImageHelper.getRandomParameter(bounds));
    }
}



