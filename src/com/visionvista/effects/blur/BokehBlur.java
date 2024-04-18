package com.visionvista.effects.blur;

import com.visionvista.effects.EffectType;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

public class BokehBlur extends Blur {

    @Serial
    private static final long serialVersionUID = -6189304943051171986L;

    private int intensity;

    public BokehBlur(double intensity) {
        super();
        this.intensity = (int) intensity;
    }

    @Override
    public Object getParameter() {
        return intensity;
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        BufferedImage blurredImage = new GaussianBlur(intensity).run(image);
        BufferedImage result = getEmptyImage(image);

        //Enhance bokeh effect by merging with original based on luminance
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color originalColor = new Color(image.getRGB(x, y));
                Color blurredColor = new Color(blurredImage.getRGB(x, y));

                //Mix based on brightness
                Color finalColor = getFinalColor(originalColor, blurredColor);
                result.setRGB(x, y, finalColor.getRGB());
            }
        }

        return result;
    }

    private Color getFinalColor(Color originalColor, Color blurredColor) {
        double luminance = 0.299 * originalColor.getRed() + 0.587 * originalColor.getGreen() + 0.114 * originalColor.getBlue();
        double blendFactor = Math.min(1, luminance / 255.0);

        int red = (int)(blendFactor * originalColor.getRed() + (1 - blendFactor) * blurredColor.getRed());
        int green = (int)(blendFactor * originalColor.getGreen() + (1 - blendFactor) * blurredColor.getGreen());
        int blue = (int)(blendFactor * originalColor.getBlue() + (1 - blendFactor) * blurredColor.getBlue());

        return new Color(red, green, blue);
    }

    @Override
    public String toString() {
        return "Applied Bokeh Blur. Intensity: " + intensity;
    }

    public static BokehBlur getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.BOKEH_BLUR.getSliderBounds();
        return new BokehBlur(ImageHelper.getRandomParameter(bounds));
    }
}
