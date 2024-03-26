package com.visionvista.effects.transformation;

import com.visionvista.utils.MathHelper;
import effects.Scalr;

import java.awt.image.BufferedImage;

public class Rotate extends Transformation {
    private double angle;

    public Rotate(double angle) {
        super();
        this.angle = angle;
    }

    @Override public String toString() {
        return "Rotated " + angle + " degrees";
    }

    @Override
    public Object getParameter() {
        return angle;
    }

    @Override public BufferedImage run(BufferedImage image) {
        System.out.println("Resizing image");
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int newWidth = (int) Math.ceil(Math.abs(imageWidth*Math.cos(angle))+Math.abs(imageHeight*Math.sin(angle)));
        int newHeight = (int) Math.ceil(Math.abs(imageHeight*Math.cos(angle))+Math.abs(imageWidth*Math.sin(angle)));

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        int centerX = imageWidth / 2;
        int centerY = imageHeight / 2;

        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                int[][] convertedPoint = new int[][] {{}, {}};
                double[][] rotatedPoint = MathHelper.rotatePoint(convertedPoint, angle);
                //Convert rotated point back to normal
                rotatedImage.setRGB((int) rotatedPoint[0][0], (int) rotatedPoint[1][0], image.getRGB(x, y));
            }
        }

        return rotatedImage;
    }
}
