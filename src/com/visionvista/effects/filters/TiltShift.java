package com.visionvista.effects.filters;

import java.awt.image.BufferedImage;

public class TiltShift extends Filter {
    public TiltShift(BufferedImage image) {
        super(image);
    }

    @Override public String toString() {
        return "Applied tilt-shift";
    }

    @Override public BufferedImage run() {
        int width = image.getWidth();
        int height = image.getHeight();
        float center = height / 2.0f;
        float focusHeight = height * 0.2f;  // adjust for desired focus height

        BufferedImage output = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            float blurAmount = Math.abs(y - center) / focusHeight;
            if (blurAmount < 1.0f) continue;

            for (int x = 0; x < width; x++) {
                // Here, a simple box blur is applied. For a more advanced approach, consider using a Gaussian blur.
                int avgR = 0, avgG = 0, avgB = 0;
                int count = 0;
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        if (x + kx < 0 || x + kx >= width || y + ky < 0 || y + ky >= height) continue;
                        int pixel = image.getRGB(x + kx, y + ky);
                        avgR += (pixel >> 16) & 0xFF;
                        avgG += (pixel >> 8) & 0xFF;
                        avgB += pixel & 0xFF;
                        count++;
                    }
                }
                int newPixel = ((avgR/count) << 16) | ((avgG/count) << 8) | (avgB/count);
                output.setRGB(x, y, newPixel);
            }
        }
        return output;
    }
}
