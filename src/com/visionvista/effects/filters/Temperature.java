package com.visionvista.effects.filters;

import com.visionvista.utils.Pair;
import com.visionvista.effects.Contrast;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ImageHelper;
import com.visionvista.effects.Saturation;

import java.awt.image.BufferedImage;

public class Temperature extends Filter {
    private double amount;

    public Temperature(double amount) {
        super();
        this.amount = amount;
    }

    @Override public String toString() {
        return "Applied Temperature. Amount: " + this.amount;
    }

    @Override public BufferedImage run(BufferedImage image) {
        System.out.println("Changing Temperature");
        BufferedImage saturatedImg = new Saturation(amount*0.5).run(image);
        BufferedImage contrastedImg = new Contrast(amount*0.5).run(saturatedImg);

        return contrastedImg;
    }

    public static Temperature getRandomInstance(BufferedImage image) {
        Pair<Integer, Integer> bounds = EffectType.TEMPERATURE.getSliderBounds();
        return new Temperature(ImageHelper.getRandomParameter(bounds));
    }
}
