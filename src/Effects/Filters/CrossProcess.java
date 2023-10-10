package Effects.Filters;

import Effects.Brightness;
import Effects.Effect;
import Effects.Helper;
import Effects.ImageHelper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CrossProcess extends Filter {
    public CrossProcess(BufferedImage image) {
        super(image);
    }

    @Override public String toString() {
        return "Applied cross-processing";
    }

    @Override public BufferedImage run() {
        BufferedImage cross_image = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int R = Helper.get_rgb(image, "r", x, y);
                int G = Helper.get_rgb(image, "g", x, y);
                int B = Helper.get_rgb(image, "b", x, y);
                int newRed = Helper.truncate((int) (R + (R * 0.3) - (G * 0.3)));
                int newGreen = Helper.truncate((int) (G + (G * 0.2) - (B * 0.15)));
                int newBlue = Helper.truncate((int) (B + (R * 0.15) + (G * 0.1)));
                Color newRGB = new Color(newRed, newGreen, newBlue);
                cross_image.setRGB(x, y, newRGB.getRGB());
            }
        }
        return cross_image;
    }

    public static CrossProcess getRandomInstance(BufferedImage image) {
        return new CrossProcess(image);
    }
}
