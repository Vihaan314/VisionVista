package com.visionvista.effects.filters;

import com.visionvista.effects.EffectType;
import com.visionvista.utils.ColorManipulator;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

public class Halftone extends Filter {
    @Serial
    private static final long serialVersionUID = -4159776900599531080L;

    private double dotSize;

    public Halftone(double dotSize) {
        super();
        this.dotSize = dotSize;
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage halftoneImage = getEmptyImage(image);

        Graphics2D graphics = halftoneImage.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        int adjustedDotSize = (dotSize == 0) ? 0 : ((int) dotSize+2);
        if (adjustedDotSize == 0) {
            return image;
        }
        for (int y = 0; y < height; y += adjustedDotSize) {
            for (int x = 0; x < width; x += adjustedDotSize) {
                Color avgColor = getAverageColor(image, x, y, adjustedDotSize);
                graphics.setColor(avgColor);
                graphics.fillOval(x, y, adjustedDotSize, adjustedDotSize);
            }
        }

        graphics.dispose();
        return halftoneImage;
    }

    private Color getAverageColor(BufferedImage image, int xStart, int yStart, int dotSize) {
        int redSum = 0, greenSum = 0, blueSum = 0, count = 0;

        for (int y = yStart; y < yStart + dotSize && y < image.getHeight(); y++) {
            for (int x = xStart; x < xStart + dotSize && x < image.getWidth(); x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                redSum += pixelColor.getRed();
                greenSum += pixelColor.getGreen();
                blueSum += pixelColor.getBlue();
                count++;
            }
        }

        return new Color(redSum / count, greenSum / count, blueSum / count);
    }

    @Override
    public String toString() {
        return "Applied Halftone";
    }

    public static Halftone getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.HALFTONE.getSliderBounds();
        return new Halftone(ImageHelper.getRandomParameter(bounds));
    }
}
