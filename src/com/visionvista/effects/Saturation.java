package com.visionvista.effects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.utils.Pair;
import com.visionvista.utils.ImageHelper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

@EffectParameter(parameters = "0, 100")
public class Saturation extends Effect{
    @Serial
    private static final long serialVersionUID = 1219410747444288209L;

    private double amount;

    public Saturation(@JsonProperty("value") double amount) {
        super();
        this.amount = amount;
    }

    @Override public String toString() {
        return "Applied Saturation. Amount: " + this.amount;
    }

    @Override
    public Object getParameter() {
        return amount;
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        //Extract HSB values
        float[] hsb = Color.RGBtoHSB(red, green, blue, null);
        float hue = hsb[0];
        float saturation = hsb[1];
        float brightness = hsb[2];

        //Adjust saturation by amount
        saturation *= (float) (1 + amount/20);
        saturation = Math.max(0.0f, Math.min(1.0f, saturation));

        return Color.HSBtoRGB(hue, saturation, brightness);
    }

    @Override public BufferedImage run(BufferedImage image) {
        System.out.println("Changing saturation");
        BufferedImage sat_img = getEmptyImage(image);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color originalColor = new Color(image.getRGB(x, y), true);
                //Extract HSB values
                float[] hsb = Color.RGBtoHSB(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), null);
                float hue = hsb[0];
                float saturation = hsb[1];
                float brightness = hsb[2];

                //Adjust saturation by amount
                saturation *= (float) (1 + amount/20);
                saturation = Math.max(0.0f, Math.min(1.0f, saturation));

                int newRGB = Color.HSBtoRGB(hue, saturation, brightness);
                sat_img.setRGB(x, y, newRGB);
            }
        }
        return sat_img;
    }


    public static Saturation getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.SATURATION.getSliderBounds();
        return new Saturation(ImageHelper.getRandomParameter(bounds));
    }
}
