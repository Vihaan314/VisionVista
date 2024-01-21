package com.visionvista.effects.transformation;

import java.awt.image.BufferedImage;

public class FlipHorizontal extends Transformation {
    public FlipHorizontal (BufferedImage image) {
        super(image);
    }

    @Override public String toString() {
        return "Flipped horizontally";
    }

    @Override public BufferedImage run() {
        System.out.println("Flipping horizontally");
        BufferedImage horizontal_img;
        horizontal_img = image;
        for (int x = 0; x < Math.floor(image.getWidth()/2); x++) {
            for (int y = 0; y < Math.floor(image.getHeight()); y++) {
                int i_pix_rgb = image.getRGB(x, y);
                int n_pix_rgb = image.getRGB(image.getWidth()-1-x, y);
                horizontal_img.setRGB(x, y, n_pix_rgb);
                horizontal_img.setRGB(image.getWidth()-1-x, y, i_pix_rgb);
            }
        }
        return horizontal_img;
    }
}
