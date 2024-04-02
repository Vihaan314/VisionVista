package com.visionvista.blending;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorDodge extends Blender {
    public ColorDodge(BufferedImage baseImage, BufferedImage blendImage) {
        super(baseImage, blendImage);
    }

    @Override
    public BufferedImage blend() {
        int width = baseImage.getWidth();
        int height = baseImage.getHeight();
        BufferedImage result = getEmptyImage(baseImage);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int baseRGB = baseImage.getRGB(x, y);
                int blendRGB = blendImage.getRGB(x, y);

                Color baseColor = new Color(baseRGB, true);
                Color blendColor = new Color(blendRGB, true);

                int red = colorDodge(baseColor.getRed(), blendColor.getRed());
                int green = colorDodge(baseColor.getGreen(), blendColor.getGreen());
                int blue = colorDodge(baseColor.getBlue(), blendColor.getBlue());

                Color resultColor = new Color(red, green, blue, baseColor.getAlpha());
                result.setRGB(x, y, resultColor.getRGB());
            }
        }

        return result;
    }

    private int colorDodge(int base, int blend) {
        if (blend == 255) return 255;
        int result = (base << 8) / (255 - blend);
        return Math.min(255, result);
    }
}
