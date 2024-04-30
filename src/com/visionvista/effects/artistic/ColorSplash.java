package com.visionvista.effects.artistic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.effects.EffectType;
import com.visionvista.effects.filters.Filter;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.util.Random;

public class ColorSplash extends Artistic {

    @Serial
    private static final long serialVersionUID = -3519474521516384345L;

    private final int numberOfSplashes;
    private final float opacity = 0.6f;

    public ColorSplash(@JsonProperty("value") double numberOfSplashes) {
        this.numberOfSplashes = (int) numberOfSplashes;
    }

    @Override
    public Object getParameter() {
        return numberOfSplashes;
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        BufferedImage result = getEmptyImage(image);
        Graphics2D g2d = result.createGraphics();

        g2d.drawImage(image, 0, 0, null);

        Random random = new Random();
        for (int i = 0; i < numberOfSplashes; i++) {
            //Generate random color
            Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256), (int)(255 * opacity));
            int x = random.nextInt(image.getWidth());
            int y = random.nextInt(image.getHeight());
            int radius = 50 + random.nextInt(150); //Random radius for splash size

            //Simulate paint splatter
            for (int j = 0; j < 5; j++) {
                int ovalX = x + random.nextInt(20) - 10;
                int ovalY = y + random.nextInt(20) - 10;
                int ovalRadius = radius + random.nextInt(20) - 10;
                g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(color.getAlpha() * 0.2)));
                g2d.fillOval(ovalX - ovalRadius / 2, ovalY - ovalRadius / 2, ovalRadius, ovalRadius);
            }

            //Simulate paint spread
            int spreadRadius = radius + 20;
            g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(color.getAlpha() * 0.1)));
            g2d.fillOval(x - spreadRadius / 2, y - spreadRadius / 2, spreadRadius, spreadRadius);
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
