package com.visionvista.blending;

import com.visionvista.utils.ColorManipulator;
import java.awt.image.BufferedImage;
import static com.visionvista.utils.ColorManipulator.clamp;

public class AdditiveBlending extends Blender {
    private double intensity;

    public AdditiveBlending(BufferedImage baseImage, BufferedImage blendImage, double intensity) {
        super(baseImage, blendImage);
        this.intensity = intensity;
    }

    @Override
    public BufferedImage blend() {
        int width = baseImage.getWidth();
        int height = baseImage.getHeight();

        BufferedImage blendedImage = getEmptyImage(baseImage);

        int[] basePixels = new int[width * height];
        int[] blendPixels = new int[width * height];
        int[] resultPixels = new int[width * height];

        //Extract pixel data from both images into arrays
        baseImage.getRGB(0, 0, width, height, basePixels, 0, width);
        blendImage.getRGB(0, 0, width, height, blendPixels, 0, width);

        float blendFactor = (float) (intensity / 10.0);

        for (int i = 0; i < basePixels.length; i++) {
            int basePixel = basePixels[i];
            int blendPixel = blendPixels[i];

            //Get ARGB from base pixel - in base image
            int a1 = (basePixel >> 24) & 0xff;
            int r1 = (basePixel >> 16) & 0xff;
            int g1 = (basePixel >> 8) & 0xff;
            int b1 = basePixel & 0xff;

            //Get ARGB from blendPixel - in blend image
            int a2 = (blendPixel >> 24) & 0xff;
            int r2 = (blendPixel >> 16) & 0xff;
            int g2 = (blendPixel >> 8) & 0xff;
            int b2 = blendPixel & 0xff;

            //Additive blending
            int a = ColorManipulator.clamp(a1 + (int) (a2 * blendFactor));
            int r = ColorManipulator.clamp(r1 + (int) (r2 * blendFactor));
            int g = ColorManipulator.clamp(g1 + (int) (g2 * blendFactor));
            int b = ColorManipulator.clamp(b1 + (int) (b2 * blendFactor));

            resultPixels[i] = (a << 24) | (r << 16) | (g << 8) | b;
        }

        blendedImage.setRGB(0, 0, width, height, resultPixels, 0, width);
        return blendedImage;
    }
}
