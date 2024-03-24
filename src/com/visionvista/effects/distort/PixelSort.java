package com.visionvista.effects.distort;

import com.visionvista.effects.EffectType;
import com.visionvista.effects.filters.Filter;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.image.BufferedImage;

public class PixelSort extends Distort {

    private int threshold;  //Single parameter to control both black and white thresholds

    public PixelSort(double threshold) {
        this.threshold = (int) threshold;
    }

    @Override
    public Object getParameter() {
        return threshold;
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        BufferedImage sortedImage = getEmptyImage(image);

        int blackThreshold = calculateBlackThreshold(threshold);
        int whiteThreshold = calculateWhiteThreshold(threshold);

        for (int y = 0; y < image.getHeight(); y++) {
            int[] row = new int[image.getWidth()];
            for (int x = 0; x < image.getWidth(); x++) {
                row[x] = image.getRGB(x, y);
            }

            int startIndex = 0;
            boolean sorting = false;

            for (int x = 0; x < row.length; x++) {
                int pixel = row[x];
                int brightness = getBrightness(pixel);

                if (!sorting && brightness < blackThreshold) {
                    sorting = true;
                    startIndex = x;
                } else if (sorting && brightness > whiteThreshold) {
                    java.util.Arrays.sort(row, startIndex, x);
                    sorting = false;
                }
            }

            if (sorting) {
                java.util.Arrays.sort(row, startIndex, row.length);
            }

            for (int x = 0; x < row.length; x++) {
                sortedImage.setRGB(x, y, row[x]);
            }
        }

        return sortedImage;
    }

    private int calculateBlackThreshold(int threshold) {
        return Math.max(0, 255 - threshold);
    }

    private int calculateWhiteThreshold(int threshold) {
        return Math.min(255, threshold);
    }

    private int getBrightness(int color) {
        return (color >> 16) & 0xFF;
    }

    @Override
    public String toString() {
        return "Applied ASDF Pixel Sort. Threshold: " + threshold;
    }

    public static PixelSort getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.PIXEL_SORT.getSliderBounds();
        return new PixelSort(ImageHelper.getRandomParameter(bounds));
    }
}
