package com.visionvista.effects.distort;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.effects.EffectParameter;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.image.BufferedImage;
import java.io.Serial;


@EffectParameter(parameters = "0, 50")
public class Pixelate extends Distort {
    @Serial
    private static final long serialVersionUID = 4450958191542400512L;

    private int pixelSize;

    public Pixelate(@JsonProperty("value") double pixelSize) {
        super();
        this.pixelSize = (int) pixelSize;
    }

    @Override
    public Object getParameter() {
        return pixelSize;
    }

    @Override public String toString() {
        return "Applied pixelating. Amount: " + pixelSize;
    }

    private static int averageColor(BufferedImage image, int x, int y, int pixelSize) {
        int red = 0;
        int green = 0;
        int blue = 0;
        int count = 0;

        for (int x2 = x; x2 < x + pixelSize && x2 < image.getWidth(); x2++) {
            for (int y2 = y; y2 < y + pixelSize && y2 < image.getHeight(); y2++) {
                int rgb = image.getRGB(x2, y2);
                red += (rgb >> 16) & 0xFF;
                green += (rgb >> 8) & 0xFF;
                blue += rgb & 0xFF;
                count++;
            }
        }
        int newRed = red / count;
        int newGreen = green / count;
        int newBlue = blue / count;
        int alpha = 0xFF << 24;
        return alpha | (newRed << 16) | (newGreen << 8) | newBlue;
    }

    private static void fillPixelBlock(BufferedImage image, int x, int y, int pixelSize, int color) {
        for (int x2 = x; x2 < x + pixelSize && x2 < image.getWidth(); x2++) {
            for (int y2 = y; y2 < y + pixelSize && y2 < image.getHeight(); y2++) {
                image.setRGB(x2, y2, color);
            }
        }
    }

    @Override public BufferedImage run(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage pixelatedImage = getEmptyImage(image);

        int adjustedPixelSize = pixelSize * 3/4;
        for (int y = 0; y < height; y += adjustedPixelSize) {
            for (int x = 0; x < width; x += adjustedPixelSize) {
                //Get the average color of the current pixel block
                int avgColor = averageColor(image, x, y, adjustedPixelSize);
                fillPixelBlock(pixelatedImage, x, y, adjustedPixelSize, avgColor);
            }
        }

        return pixelatedImage;
    }

    public static Pixelate getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.PIXELATE.getSliderBounds();
        return new Pixelate(ImageHelper.getRandomParameter(bounds));
    }
}
