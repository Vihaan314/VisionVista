package com.visionvista.effects.filters;

import com.visionvista.effects.EffectType;
import com.visionvista.effects.blur.GaussBlur;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ColorSplash extends Filter {

    private final int numberOfSplashes;

    private final float opacity = 0.6f;

    public ColorSplash(double numberOfSplashes) {
        this.numberOfSplashes = (int) numberOfSplashes;
    }

    @Override
    public Object getParameter() {
        return numberOfSplashes;
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = result.createGraphics();

        g2d.drawImage(image, 0, 0, null);

        Random random = new Random();
        for (int i = 0; i < numberOfSplashes; i++) {
            //Generate random color and position for the splash
            Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256), (int)(255 * opacity));
            int x = random.nextInt(image.getWidth());
            int y = random.nextInt(image.getHeight());
            int radius = 50 + random.nextInt(150); //Random radius for splash size

            //Draw the splash
            g2d.setColor(color);
            g2d.fillOval(x - radius / 2, y - radius / 2, radius, radius);
        }

        g2d.dispose();
        return result;
    }

    @Override
    public String toString() {
        return "Applied Color Splash. Count: " + numberOfSplashes;
    }

    public static ColorSplash getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.COLOR_SPLASH.getSliderBounds();
        return new ColorSplash(ImageHelper.getRandomParameter(bounds));
    }
}
