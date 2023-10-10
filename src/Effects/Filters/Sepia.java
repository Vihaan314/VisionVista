package Effects.Filters;

import Effects.Brightness;
import Effects.Effect;
import Effects.Helper;
import Effects.ImageHelper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sepia extends Filter {
    private double intensity;

    public Sepia(BufferedImage image, double intensity) {
        super(image);
        this.intensity = intensity;
    }

    @Override public String toString() {
        return "Applied sepia";
    }

     @Override public BufferedImage run() {
        System.out.println("Adding sepia");
        BufferedImage sepia_img;
        BufferedImage sepia_1 = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color rgb = new Color(image.getRGB(x, y));
                int R = rgb.getRed();
                int G = rgb.getGreen();
                int B = rgb.getBlue();

                int newRed = Helper.truncate((int) (0.393*R + 0.769*G + 0.189*B));
                int newGreen = Helper.truncate((int) (0.349*R + 0.686*G + 0.168*B));
                int newBlue = Helper.truncate((int) (0.272*R + 0.534*G + 0.131*B));
                Color new_rgb = new Color(newRed, newGreen, newBlue);
                sepia_1.setRGB(x, y, new_rgb.getRGB());
            }
        }
        sepia_img = new Brightness(sepia_1, -intensity).run();

        return sepia_img;
    }

    public static Sepia getRandomInstance(BufferedImage image) {
        return new Sepia(image, ImageHelper.getRandomParameter(1, 100));
    }
}
