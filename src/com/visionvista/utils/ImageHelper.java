package com.visionvista.utils;

import com.visionvista.effects.transformation.Resize;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageHelper {
    public static BufferedImage createBlankImage() {
        return new BufferedImage(900, 600, BufferedImage.TYPE_INT_ARGB);
    }

    public static int getRandomParameter(Pair<Integer, Integer> bounds){
        int min = bounds.left();
        int max = bounds.right();
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

    public static Dimension getRandomDimension() {
        Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int maxWidth = screenSize.width;
        int maxHeight = screenSize.height;

        int randomWidth = (int) (Math.random() * maxWidth);

        double aspectRatio = 16.0 / 9.0;
        int newHeight = (int) (randomWidth / aspectRatio);

        if (newHeight > maxHeight) {
            newHeight = maxHeight;
            randomWidth = (int) (newHeight * aspectRatio);
        }

        return new Dimension(randomWidth, newHeight);
    }

    public static Pair<Integer, Integer> getColorBounds() {
        return new Pair<>(1, 256);
    }

    public static BufferedImage fitImageToWindow(BufferedImage image) {
        //Resize image if too large
        Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int maxScreenWidth = screenSize.width;
        int maxScreenHeight = screenSize.height;

        if (image.getWidth() > maxScreenWidth || image.getHeight() > maxScreenHeight) {
            double widthRatio = (double) maxScreenWidth / image.getWidth();
            double heightRatio = (double) maxScreenHeight / image.getHeight();
            double resizeRatio = Math.min(widthRatio, heightRatio);

            int newWidth = (int) (image.getWidth() * resizeRatio);
            int newHeight = (int) (image.getHeight() * resizeRatio);

            return new Resize(newWidth, newHeight).run(image);
        }
        return image;
    }

    public static float[] flatten2D(float[][] matrix) {
        float[] flatArr = new float[matrix.length*matrix[0].length];
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                flatArr[i*matrix[0].length+j] = matrix[i][j];
            }
        }
        return flatArr;
    }

    public static boolean isBlankImage(BufferedImage image) {
        BufferedImage blankImage = createBlankImage();

        if (image.getWidth() == blankImage.getWidth() && image.getHeight() == blankImage.getHeight()) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    if (image.getRGB(x, y) != blankImage.getRGB(x, y))
                        return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
