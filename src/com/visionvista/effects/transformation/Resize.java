package com.visionvista.effects.transformation;

import effects.Scalr;

import java.awt.image.BufferedImage;
import java.io.Serial;

public class Resize extends Transformation {
    @Serial
    private static final long serialVersionUID = 8058268054301129550L;

    private int targetWidth;
    private int targetHeight;

    public Resize (int targetWidth, int targetHeight) {
        super();
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
    }

    @Override public String toString() {
        return "Resized - {Width: " + this.targetWidth + " Height: " + this.targetHeight + "}";
    }

    @Override
    public Object getParameter() {
        return new int[]{targetWidth, targetHeight};
    }

    @Override public BufferedImage run(BufferedImage image) {
        System.out.println("Resizing image");
        BufferedImage resized_img;
        resized_img = Scalr.resize(image, this.targetWidth, this.targetHeight);

        return resized_img;
    }
}
