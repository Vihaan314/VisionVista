package com.visionvista.utils;

import java.awt.image.BufferedImage;

public class ImageHelper {
//    @Override
//    protected void paintComponent(Graphics g) {
//        BufferedImage blankImage = createBlankImageBing();
//        super.paintComponent(g);
//        Graphics2D graphics = blankImage.createGraphics();
//        graphics.setPaint(new Color(255, 255, 255));
//        graphics.fillRect(0, 0, blankImage.getWidth(), blankImage.getHeight());
//    }


    public static BufferedImage createBlankImage() {
        return new BufferedImage(900, 600, BufferedImage.TYPE_INT_ARGB);
    }

    public static int getRandomParameter(Pair<Integer, Integer> bounds){
        int min = bounds.getLeft();
        int max = bounds.getRight();
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
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
