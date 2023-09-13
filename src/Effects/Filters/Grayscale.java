package Effects.Filters;

import Effects.Brightness;
import Effects.Effect;
import Effects.Helper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Grayscale extends Filter {
    public Grayscale(BufferedImage image) {
        super(image);
    }

    @Override public String toString() {
        return "Applied grayscaling";
    }

    @Override public BufferedImage run() {
        System.out.println("Grayscaling");
        BufferedImage grayscaleImg = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color rgb = new Color(image.getRGB(x, y));
                double red = Math.pow(rgb.getRed() / 255.0, 2.2);
                double green = Math.pow(rgb.getGreen() / 255.0, 2.2);
                double blue = Math.pow(rgb.getBlue() / 255.0, 2.2);

                float lum = (float) (0.2126 * red + 0.7152 * green + 0.0722 * blue);

                int grayLevel = (int) (255.0 * Math.pow(lum, 1.0 / 2.2));
                int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
                grayscaleImg.setRGB(x, y, gray);
            }
        }
        return grayscaleImg;
    }
}
