package com.visionvista.effects.transformation;

import com.visionvista.effects.Contrast;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.MathHelper;
import com.visionvista.utils.Pair;

import java.awt.image.BufferedImage;
import java.io.Serial;
import java.util.Arrays;

public class Rotate extends Transformation {
    @Serial
    private static final long serialVersionUID = 2979754438467683733L;

    private double angle;

    public Rotate(double angle) {
        super();
        this.angle = angle;
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        angle = Math.toRadians(angle);

        //Computing the new image dimensions
        int newWidth = (int) Math.ceil(Math.abs(imageWidth * Math.cos(angle)) + Math.abs(imageHeight * Math.sin(angle)));
        int newHeight = (int) Math.ceil(Math.abs(imageHeight * Math.cos(angle)) + Math.abs(imageWidth * Math.sin(angle)));

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        //To simplify the rotation calculations, take the center of the image as reference for the operations
        int centerX = imageWidth / 2;
        int centerY = imageHeight / 2;
        int newCenterX = newWidth / 2;
        int newCenterY = newHeight / 2;

        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
                //Rotation
                int srcX = (int) ((x - newCenterX) * Math.cos(-angle) - (y - newCenterY) * Math.sin(-angle) + centerX);
                int srcY = (int) ((x - newCenterX) * Math.sin(-angle) + (y - newCenterY) * Math.cos(-angle) + centerY);

                if (srcX >= 0 && srcX < imageWidth && srcY >= 0 && srcY < imageHeight) {
                    rotatedImage.setRGB(x, y, image.getRGB(srcX, srcY));
                }
            }
        }

        return rotatedImage;
    }

    @Override public String toString() {
        return "Rotated " + angle + " degrees";
    }

    @Override
    public Object getParameter() {
        return Math.toDegrees(angle);
    }

    public static Rotate getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.ROTATE.getSliderBounds();
        return new Rotate(ImageHelper.getRandomParameter(bounds));
    }
}
