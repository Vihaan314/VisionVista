package Effects.Filters;

import Effects.Brightness;
import Effects.Effect;
import Effects.Helper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SplitTone extends Filter {
    public SplitTone(BufferedImage image) {
        super(image);
    }

    @Override public String toString() {
        return "Applied split-tone";
    }

    @Override public BufferedImage run() {
        BufferedImage image_split = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int R = Helper.get_rgb(image, "r", x, y);
                int G = Helper.get_rgb(image, "g", x, y);
                int B = Helper.get_rgb(image, "b", x, y);
                int newRed = 0, newGreen = 0, newBlue = 0;
                if (R + G + B > 382) {
                    newRed = Helper.truncate(R + 40);
                    newGreen = Helper.truncate(G + 40);
                } else {
                    newBlue = Helper.truncate(B + 50);
                }

                Color newRGB = new Color(newRed, newGreen, newBlue);
                image_split.setRGB(x, y, newRGB.getRGB());
            }
        }
        return image_split;
    }
}
