package com.visionvista.effects.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Duotone extends Filter {

    private Color color1;
    private Color color2;

    public Duotone(Color color1, Color color2) {
        super();
        this.color1 = color1;
        this.color2 = color2;
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        BufferedImage result = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                int gray = (int)(0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());
                Color blendColor = new Color(
                        (color1.getRed() * gray + color2.getRed() * (255 - gray)) / 255,
                        (color1.getGreen() * gray + color2.getGreen() * (255 - gray)) / 255,
                        (color1.getBlue() * gray + color2.getBlue() * (255 - gray)) / 255);
                result.setRGB(x, y, blendColor.getRGB());
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return "Applied Duotone";
    }
}
