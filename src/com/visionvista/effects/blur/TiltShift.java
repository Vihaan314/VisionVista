package com.visionvista.effects.blur;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.effects.EffectDescription;
import com.visionvista.effects.EffectParameter;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.FocusAreaDetector;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

@EffectDescription(description = "Soft blur to edges of image")
@EffectParameter(parameters = "0, 10")
public class TiltShift extends Blur {

    @Serial
    private static final long serialVersionUID = 2155531383455254436L;

    private double intensity;

    public TiltShift(@JsonProperty("value") double intensity) {
        this.intensity = intensity;
    }

    @Override
    public Object getParameter() {
        return intensity;
    }

    @Override
    public String toString() {
        return "Applied Tilt Shift. Blur strength: " + intensity;
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        //Estimate the focus area of the image (will not be blurred)
        int focusAreaStart = image.getHeight() / 3;
        int focusAreaEnd = 2 * image.getHeight() / 3;
        //TODO
//        FocusAreaDetector focusAreaDetector = new FocusAreaDetector();
//        Rectangle focusArea = focusAreaDetector.getFocusRegion(image);

        BufferedImage blurredImage = new GaussianBlur(intensity).run(image);
        BufferedImage result = getEmptyImage(image);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color originalColor = new Color(image.getRGB(x, y));
                Color blurredColor = new Color(blurredImage.getRGB(x, y));

                double blendFactor;
                if (y < focusAreaStart) {
                    blendFactor = (double)(focusAreaStart - y) / focusAreaStart;
                } else if (y > focusAreaEnd) {
                    blendFactor = (double)(y - focusAreaEnd) / (image.getHeight() - focusAreaEnd);
                } else {
                    blendFactor = 0;
                }

                int red = (int)(originalColor.getRed() * (1 - blendFactor) + blurredColor.getRed() * blendFactor);
                int green = (int)(originalColor.getGreen() * (1 - blendFactor) + blurredColor.getGreen() * blendFactor);
                int blue = (int)(originalColor.getBlue() * (1 - blendFactor) + blurredColor.getBlue() * blendFactor);

                Color blendedColor = new Color(red, green, blue);
                result.setRGB(x, y, blendedColor.getRGB());
            }
        }

        return result;
    }

    public static TiltShift getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.TILT_SHIFT.getSliderBounds();
        return new TiltShift(ImageHelper.getRandomParameter(bounds));
    }
}
