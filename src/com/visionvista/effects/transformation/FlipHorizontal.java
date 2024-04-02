package com.visionvista.effects.transformation;

import java.awt.image.BufferedImage;
import java.io.Serial;

public class FlipHorizontal extends Transformation {
    @Serial
    private static final long serialVersionUID = 388948030919476539L;

    public FlipHorizontal () {
        super();
    }

    @Override public String toString() {
        return "Flipped horizontally";
    }

    @Override public BufferedImage run(BufferedImage image) {
        System.out.println("Flipping horizontally");
        BufferedImage horizontal_img;
        horizontal_img = image;
        for (int x = 0; x < Math.floor((double) image.getWidth() /2); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int i_pix_rgb = image.getRGB(x, y);
                int n_pix_rgb = image.getRGB(image.getWidth()-1-x, y);
                horizontal_img.setRGB(x, y, n_pix_rgb);
                horizontal_img.setRGB(image.getWidth()-1-x, y, i_pix_rgb);
            }
        }
        return horizontal_img;
    }
}
