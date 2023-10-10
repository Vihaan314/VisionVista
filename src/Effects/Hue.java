package Effects;

import Effects.Filters.Temperature;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Hue extends Effect{
    private Color color;

    public Hue(BufferedImage image, Color color) {
        super(image);
        this.color = color;
    }

    @Override public String toString() {
        return "Applied Hue. Color: " + "#"+Integer.toHexString(this.color.getRGB()).substring(2).toUpperCase();
    }

    @Override public BufferedImage run() {
        System.out.println("Adding hue");
        BufferedImage hue_img = getEmptyImage(image);

        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color originalColor = new Color(image.getRGB(x, y), true);
                int red = (originalColor.getRed() + r) / 2;
                int green = (originalColor.getGreen() + g) / 2;
                int blue = (originalColor.getBlue() + b) / 2;
                int alpha = originalColor.getAlpha();
                Color newColor = new Color(red, green, blue, alpha);
                hue_img.setRGB(x, y, newColor.getRGB());
            }
        }
        return hue_img;
    }

    public static Hue getRandomInstance(BufferedImage image) {
        return new Hue(image, new Color(ImageHelper.getRandomParameter(1, 256),ImageHelper.getRandomParameter(1, 256),ImageHelper.getRandomParameter(1, 256)));
    }
}
