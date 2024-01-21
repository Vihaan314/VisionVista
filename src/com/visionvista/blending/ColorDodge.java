package com.visionvista.blending;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorDodge extends Blender {
    private BufferedImage baseImage;
    private BufferedImage blendImage;


    public ColorDodge(BufferedImage baseImage, BufferedImage blendImage) {
        super(baseImage, blendImage);
        this.baseImage = baseImage;
        this.blendImage = blendImage;
    }

    private static int colorDodge(int baseColor, int blendColor) {
        if (blendColor == 255) {
            return 255;
        } else {
            return Math.min(255, (baseColor * 255) / (255 - blendColor));
        }
    }

    private static Color applyColorDodge(Color baseColor, Color blendColor) {
        int resultRed = colorDodge(baseColor.getRed(), blendColor.getRed());
        int resultGreen = colorDodge(baseColor.getGreen(), blendColor.getGreen());
        int resultBlue = colorDodge(baseColor.getBlue(), blendColor.getBlue());

        return new Color(resultRed, resultGreen, resultBlue);
    }

    @Override public BufferedImage blend() {
        if (baseImage.getWidth() != blendImage.getWidth() || baseImage.getHeight() != blendImage.getHeight()) {
            throw new IllegalArgumentException("Both images should have the same dimensions");
        }

        BufferedImage resultImage = getEmptyImage(baseImage);

        for (int x = 0; x < baseImage.getWidth(); x++) {
            for (int y = 0; y < baseImage.getHeight(); y++) {
                Color basePixelColor = new Color(baseImage.getRGB(x, y));
                Color blendPixelColor = new Color(blendImage.getRGB(x, y));
                Color resultPixelColor = applyColorDodge(basePixelColor, blendPixelColor);
//                Color normalizedPixelColor = Helper.normalizeLuminance(basePixelColor, resultPixelColor);
                resultImage.setRGB(x, y, resultPixelColor.getRGB());
            }
        }

        return resultImage;
    }
}
