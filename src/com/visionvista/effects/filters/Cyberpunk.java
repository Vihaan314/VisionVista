package com.visionvista.effects.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Cyberpunk extends Filter {

    public Cyberpunk() {
        super();
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                red = (int) (Math.min(255, red * 1.2 + 30));
                green = (int) (Math.min(255, green * 1.2 + 30));
                blue = (int) (Math.min(255, blue * 1.2 + 30));

                blue = Math.min(255, blue + 50);
                if (green > 100) {
                    green = Math.min(255, green + 20);
                }

                Color newColor = new Color(red, green, blue);
                result.setRGB(x, y, newColor.getRGB());
            }
        }

//        BufferedImage glow = new Glow(1).run(result);
//        return glow;
        return result;
    }

    @Override
    public String toString() {
        return "Applied Cyberpunk";
    }

    public static Cyberpunk getRandomInstance() {
        return new Cyberpunk();
    }
}
