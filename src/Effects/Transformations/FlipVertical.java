package Effects.Transformations;

import Effects.Helper;

import java.awt.image.BufferedImage;

public class FlipVertical extends Transformation {
    public FlipVertical(BufferedImage image) {
        super(image);
    }

    @Override public String toString() {
        return "Flipped vertically";
    }

    @Override public BufferedImage run() {
        System.out.println("Flipping vertically");

        BufferedImage vertical_img = getEmptyImage(image);
        for (int x = 0; x < Math.floor(image.getWidth()); x++) {
            for (int y = 0; y < Math.floor(image.getHeight()/2)+1; y++) {
                int i_pix_rgb = image.getRGB(x, y);
                int n_pix_rgb = image.getRGB(x, image.getHeight()-1-y);
                vertical_img.setRGB(x, y, n_pix_rgb);
                vertical_img.setRGB(x, image.getHeight()-1-y, i_pix_rgb);
            }
        }
        return vertical_img;
    }
}
