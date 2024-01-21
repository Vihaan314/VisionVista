package com.visionvista.effects.filters;

import com.visionvista.Pair;
import com.visionvista.effects.Contrast;
import com.visionvista.effects.EffectType;
import com.visionvista.effects.ImageHelper;
import com.visionvista.effects.Saturation;

import java.awt.image.BufferedImage;

public class Temperature extends Filter {
    private double amount;

    public Temperature(BufferedImage image, double amount) {
        super(image);
        this.amount = amount;
    }

    @Override public String toString() {
        return "Applied Temperature. Amount: " + this.amount;
    }

    @Override public BufferedImage run() {
        System.out.println("Changing Temperature");
        BufferedImage sat_img = new Saturation(image, amount*0.5).run();
        BufferedImage con_img = new Contrast(sat_img, amount*0.5).run();

        return con_img;
    }

    public static Temperature getRandomInstance(BufferedImage image) {
        Pair<Integer, Integer> bounds = EffectType.TEMPERATURE.getSliderBounds();
        return new Temperature(image, ImageHelper.getRandomParameter(bounds));
    }
}
