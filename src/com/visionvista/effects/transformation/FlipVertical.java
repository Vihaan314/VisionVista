package com.visionvista.effects.transformation;

import java.awt.image.BufferedImage;
import java.io.Serial;

public class FlipVertical extends Transformation {
    @Serial
    private static final long serialVersionUID = -2036457337507151015L;

    public FlipVertical() {
        super();
    }

    @Override public String toString() {
        return "Flipped vertically";
    }

    @Override public BufferedImage run(BufferedImage image) {
        BufferedImage vertical_img = getEmptyImage(image);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < Math.floor((double) image.getHeight() /2)+1; y++) {
                int i_pix_rgb = image.getRGB(x, y);
                int n_pix_rgb = image.getRGB(x, image.getHeight()-1-y);
                vertical_img.setRGB(x, y, n_pix_rgb);
                vertical_img.setRGB(x, image.getHeight()-1-y, i_pix_rgb);
            }
        }
        return vertical_img;
    }
}
