package com.visionvista.effects.transformation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.utils.ImageHelper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

public class Resize extends Transformation {
    @Serial
    private static final long serialVersionUID = 8058268054301129550L;

    private int targetWidth;
    private int targetHeight;

    public Resize (int targetWidth, int targetHeight) {
        super();
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
    }

    @Override public String toString() {
        return "Resized - {Width: " + this.targetWidth + " Height: " + this.targetHeight + "}";
    }

    @Override
    public Object getParameter() {
        return new int[]{targetWidth, targetHeight};
    }

    @Override public BufferedImage run(BufferedImage image) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();

        //Bilinear interpolation
        for (int y = 0; y < targetHeight; y++) {
            for (int x = 0; x < targetWidth; x++) {
                //Ratios for resizing
                double xRatio = ((double) x / (targetWidth - 1)) * (originalWidth - 1);
                double yRatio = ((double) y / (targetHeight - 1)) * (originalHeight - 1);

                //Get top left pixel and make sure it is in bounds
                int xFloor = (int) Math.floor(xRatio);
                int yFloor = (int) Math.floor(yRatio);
                int xCeil = Math.min(xFloor + 1, originalWidth - 1);
                int yCeil = Math.min(yFloor + 1, originalHeight - 1);

                //Interpolate pixels
                double xLerp = xRatio - xFloor;
                double yLerp = yRatio - yFloor;

                int topLeft = image.getRGB(xFloor, yFloor);
                int topRight = image.getRGB(xCeil, yFloor);
                int bottomLeft = image.getRGB(xFloor, yCeil);
                int bottomRight = image.getRGB(xCeil, yCeil);

                int rgb = interpolateColor(topLeft, topRight, bottomLeft, bottomRight, xLerp, yLerp);
                resizedImage.setRGB(x, y, rgb);
            }
        }

        return resizedImage;
    }

    private static int interpolateColor(int topLeft, int topRight, int bottomLeft, int bottomRight, double xLerp, double yLerp) {
        int topInterpolated = interpolate(topLeft, topRight, xLerp);
        int bottomInterpolated = interpolate(bottomLeft, bottomRight, xLerp);
        return interpolate(topInterpolated, bottomInterpolated, yLerp);
    }

    private static int interpolate(int start, int end, double ratio) {
        int alphaStart = (start >> 24) & 0xff;
        int redStart = (start >> 16) & 0xff;
        int greenStart = (start >> 8) & 0xff;
        int blueStart = start & 0xff;

        int alphaEnd = (end >> 24) & 0xff;
        int redEnd = (end >> 16) & 0xff;
        int greenEnd = (end >> 8) & 0xff;
        int blueEnd = end & 0xff;

        int alpha = (int) (alphaStart + (alphaEnd - alphaStart) * ratio);
        int red = (int) (redStart + (redEnd - redStart) * ratio);
        int green = (int) (greenStart + (greenEnd - greenStart) * ratio);
        int blue = (int) (blueStart + (blueEnd - blueStart) * ratio);

        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    public static Resize getRandomInstance() {
        Dimension randomDimension = ImageHelper.getRandomDimension();
        return new Resize((int) randomDimension.getWidth(), (int) randomDimension.getHeight());
    }
}
