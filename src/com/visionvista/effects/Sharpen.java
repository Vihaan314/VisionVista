package com.visionvista.effects;

import com.visionvista.utils.Pair;
import com.visionvista.utils.ImageHelper;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Arrays;


public class Sharpen extends Effect{
    private double amount;

    public Sharpen(double amount) {
        super();
        this.amount = amount;
    }

    @Override public String toString() {
        return "Applied Sharpen. Amount: " + this.amount;
    }

    @Override public BufferedImage run(BufferedImage image) {
        System.out.println("Sharpening image");
        BufferedImage sharpenedImg = getEmptyImage(image);

        float[] SHARPEN3x3 = {
                0.0f, -1.0f, 0.0f,
                -1.0f,  5.0f, -1.0f,
                0.0f, -1.0f, 0.0f
        };
        for (int i = 0; i < SHARPEN3x3.length; i++) {
            SHARPEN3x3[i] *= amount;
        }

        SHARPEN3x3[4] = 1.0f + Math.abs(SHARPEN3x3[4]);
        System.out.println(Arrays.toString(SHARPEN3x3));

        ConvolveOp convolve = new ConvolveOp(new Kernel(3, 3, SHARPEN3x3), ConvolveOp.EDGE_NO_OP, null);
        convolve.filter(image, sharpenedImg);
        System.out.println(sharpenedImg.getRGB(50, 50));
        System.out.println(image.getRGB(50, 50));

        return sharpenedImg;
    }

    public static Sharpen getRandomInstance(BufferedImage image) {
        Pair<Integer, Integer> bounds = EffectType.SHARPEN.getSliderBounds();
        return new Sharpen(ImageHelper.getRandomParameter(bounds));
    }
}



