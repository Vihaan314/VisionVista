package com.visionvista.effects.filters;

import com.visionvista.Pair;
import com.visionvista.effects.Brightness;
import com.visionvista.effects.EffectType;
import com.visionvista.effects.Helper;
import com.visionvista.effects.ImageHelper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sepia extends Filter {
    private double intensity;

    public Sepia(double intensity) {
        super();
        this.intensity = intensity;
    }

    @Override public String toString() {
        return "Applied sepia";
    }

     @Override public BufferedImage run(BufferedImage image) {
        System.out.println("Adding sepia");
        BufferedImage sepiaImg = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color rgb = new Color(image.getRGB(x, y));
                int R = rgb.getRed();
                int G = rgb.getGreen();
                int B = rgb.getBlue();

                int newRed = Helper.truncate((int) (0.393*R + 0.769*G + 0.189*B));
                int newGreen = Helper.truncate((int) (0.349*R + 0.686*G + 0.168*B));
                int newBlue = Helper.truncate((int) (0.272*R + 0.534*G + 0.131*B));
                Color new_rgb = new Color(newRed, newGreen, newBlue);
                sepiaImg.setRGB(x, y, new_rgb.getRGB());
            }
        }
        BufferedImage sepiaCorrected = new Brightness(-intensity).run(sepiaImg);

        return sepiaCorrected;
    }


    public static Sepia getRandomInstance(BufferedImage image) {
        Pair<Integer, Integer> bounds = EffectType.SEPIA.getSliderBounds();
        return new Sepia(ImageHelper.getRandomParameter(bounds));
    }
}
