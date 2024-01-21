package com.visionvista.effects;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.util.Objects;

public class Helper {
    public static void debug_pix(int x, int y)  {
        System.out.println("X:" + x + ",Y:" + y);
    }

    public static double unGamma(double value) {
        return Math.pow(value / 255.0, 2.2) * 255.0; // Inverse gamma correction (assuming gamma = 2.2)
    }

    public static double Gamma(double value) {
        return Math.pow(value / 255.0, 1.0 / 2.2) * 255.0; // Gamma correction (assuming gamma = 2.2)
    }

    public static double toGray(double r, double g, double b) {
        return 0.299 * r + 0.587 * g + 0.114 * b; // Standard formula for grayscale conversion.
    }

    public static int truncate(int value) {
        if (value > 255) return 255;
        if (value < 0) return 0;
        return value;
    }

    public static int get_rgb(BufferedImage image, String color, int x, int y) {
        int val = 0;
        Color image_cols = new Color(image.getRGB(x, y));
        if (color.equals("r")) {
            val = image_cols.getRed();
        }
        else if (color.equals("g")) {
            val = image_cols.getGreen();
        }
        else if (color.equals("b")) {
            val = image_cols.getGreen();
        }
        return val;
    }

    public static BufferedImage create_blank_image(BufferedImage image) {
        BufferedImage blank_image = image;
        for (int x= 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color rgb = new Color(255, 255, 255);
                blank_image.setRGB(x, y, rgb.getRGB());
            }
        }
        return blank_image;
    }

    public static void addChangeListener(JTextComponent text, ChangeListener changeListener) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(changeListener);
        DocumentListener dl = new DocumentListener() {
            private int lastChange = 0, lastNotifiedChange = 0;

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lastChange++;
                SwingUtilities.invokeLater(() -> {
                    if (lastNotifiedChange != lastChange) {
                        lastNotifiedChange = lastChange;
                        changeListener.stateChanged(new ChangeEvent(text));
                    }
                });
            }
        };
        text.addPropertyChangeListener("document", (PropertyChangeEvent e) -> {
            Document d1 = (Document)e.getOldValue();
            Document d2 = (Document)e.getNewValue();
            if (d1 != null) d1.removeDocumentListener(dl);
            if (d2 != null) d2.addDocumentListener(dl);
            dl.changedUpdate(null);
        });
        Document d = text.getDocument();
        if (d != null) d.addDocumentListener(dl);
    }

    public static float[] scalerMultiply( double scaler, float[] kernal) {
        for (int i = 0; i < kernal.length; i++) {
            kernal[i] *= scaler;
        }
        return kernal;
    };

    public static float kernalSum(float[] kernal) {
        float sum = 0f;
        for (int i = 0; i < kernal.length; i++) {
            sum += kernal[i];
        }
        return sum;
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

    public static float[][] normalizeKernal(float[][] kernal) {
        float normFactor = kernal[0][0];
        float[][] normKernal = new float[kernal.length][kernal.length];
        for (int i = 0; i < kernal.length; i++) {
            for (int j = 0; j < kernal.length; j++) {
                normKernal[i][j] = kernal[i][j] / normFactor;
            }
        }
        return normKernal;
    }

    private static double computeLuminance(Color color) {
        return 0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue();
    }

    public static Color normalizeLuminance(Color original, Color blended) {
        double originalLuminance = computeLuminance(original);
        double blendedLuminance = computeLuminance(blended);
        double luminanceRatio = originalLuminance / blendedLuminance;

        int adjustedRed = clamp((int)(blended.getRed() * luminanceRatio), 0, 255);
        int adjustedGreen = clamp((int)(blended.getGreen() * luminanceRatio), 0, 255);
        int adjustedBlue = clamp((int)(blended.getBlue() * luminanceRatio), 0, 255);

        return new Color(adjustedRed, adjustedGreen, adjustedBlue);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
