package com.visionvista.effects.filters;

import com.visionvista.effects.Effect;

import java.awt.image.BufferedImage;

public abstract class Filter extends Effect {
    public Filter(BufferedImage image) {
        super(image);
    }
}
