package com.visionvista.effects.distort;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.effects.EffectDescription;
import com.visionvista.effects.EffectParameter;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ColorManipulator;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.image.BufferedImage;
import java.io.Serial;

@EffectDescription(description = "Creates epic glitched effect, most effect around 150")
@EffectParameter(parameters = "0, 255")
public class PixelSort extends Distort {

    @Serial
    private static final long serialVersionUID = 5498665501888385430L;

    private int threshold;  //Single parameter to control both black and white thresholds

    public PixelSort(@JsonProperty("value") double threshold) {
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
                int brightness = ColorManipulator.getBrightness(pixel);

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

    @Override
    public String toString() {
        return "Applied ASDF Pixel Sort. Threshold: " + threshold;
    }

    public static PixelSort getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.PIXEL_SORT.getSliderBounds();
        return new PixelSort(ImageHelper.getRandomParameter(bounds));
    }
}
