package com.visionvista.effects.filters;

import com.visionvista.effects.EffectType;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Halftone extends Filter {

    public Halftone() {
        super();
    }

    @Override
    public String toString() {
        return "Applied Halftone";
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        BufferedImage result = getEmptyImage(image);

        int radius = 2; //Size of the dots
        for (int y = 0; y < image.getHeight(); y += radius * 2) {
            for (int x = 0; x < image.getWidth(); x += radius * 2) {
                int areaAvg = getAverageColor(image, x, y, radius);
                int diameter = (int) (radius * 2 * areaAvg / 255.0);
                Graphics2D g2d = result.createGraphics();
                g2d.setColor(Color.BLACK);
                g2d.fillOval(x, y, diameter, diameter);
                g2d.dispose();
            }
        }

        return result;
    }

    private int getAverageColor(BufferedImage image, int x, int y, int radius) {
        int sum = 0;
        int count = 0;

        for (int dy = -radius; dy <= radius; dy++) {
            for (int dx = -radius; dx <= radius; dx++) {
                if (x + dx >= 0 && x + dx < image.getWidth() && y + dy >= 0 && y + dy < image.getHeight()) {
                    Color color = new Color(image.getRGB(x + dx, y + dy));
                    int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                    sum += gray;
                    count++;
                }
            }
        }
        return count > 0 ? sum / count : 0;
    }

    public static Halftone getRandomInstance() {
        return new Halftone();
    }
}
