package com.visionvista.effects.filters;

import com.visionvista.utils.Pair;
import com.visionvista.effects.Contrast;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ImageHelper;
import com.visionvista.effects.Saturation;

import java.awt.image.BufferedImage;
import java.io.Serial;

public class Temperature extends Filter {
    @Serial
    private static final long serialVersionUID = -775480314575417954L;

    private double amount;

    public Temperature(double amount) {
        super();
        this.amount = amount;
    }

    @Override public BufferedImage run(BufferedImage image) {
        BufferedImage saturatedImg = new Saturation(amount*0.5).run(image);
        return new Contrast(amount*0.5).run(saturatedImg);
    }

    @Override public Object getParameter() {
        return amount;
    }

    @Override public String toString() {
        return "Applied Temperature. Amount: " + this.amount;
    }

    public static Temperature getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.TEMPERATURE.getSliderBounds();
        return new Temperature(ImageHelper.getRandomParameter(bounds));
    }
}
