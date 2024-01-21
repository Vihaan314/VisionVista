package com.visionvista.effects.transformation;

import effects.Scalr;

import java.awt.image.BufferedImage;

public class Resize extends Transformation {
    private int targetWidth;
    private int targetHeight;

    public Resize (int targetWidth, int targetHeight) {
        super();
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
    }

    @Override public String toString() {
        return "Resized - (Width: " + this.targetWidth + " Height: " + this.targetHeight;
    }

    @Override public BufferedImage run(BufferedImage image) {
        System.out.println("Resizing image");
        BufferedImage resized_img;
        resized_img = Scalr.resize(image, this.targetWidth, this.targetHeight);

        return resized_img;
    }
}
