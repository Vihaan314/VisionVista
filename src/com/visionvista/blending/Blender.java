package com.visionvista.blending;

import java.awt.image.BufferedImage;

public abstract class Blender {
    protected BufferedImage baseImage;
    protected BufferedImage blendImage;

    public Blender(BufferedImage baseImage, BufferedImage blendImage) {
        this.baseImage = baseImage;
        this.blendImage = blendImage;
    }

    public abstract BufferedImage blend();

    public BufferedImage getEmptyImage(BufferedImage baseImage) {
        return new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), baseImage.getType());
    }
}
