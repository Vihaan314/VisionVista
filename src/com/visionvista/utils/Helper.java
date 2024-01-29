package com.visionvista.utils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class Helper {
    //Color correction
    public static double unGamma(double value) {
        return Math.pow(value / 255.0, 2.2) * 255.0; // Inverse gamma correction (assuming gamma = 2.2)
    }

    public static double Gamma(double value) {
        return Math.pow(value / 255.0, 1.0 / 2.2) * 255.0; // Gamma correction (assuming gamma = 2.2)
    }

    public static double toGray(java.awt.Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        return 0.299 * r + 0.587 * g + 0.114 * b; // Standard formula for grayscale conversion.
    }

    public static double toGray(double r, double g, double b) {
        return 0.299 * r + 0.587 * g + 0.114 * b; // Standard formula for grayscale conversion.
    }

    private static double computeLuminance(java.awt.Color color) {
        return 0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue();
    }

    public static int truncate(int value) {
        if (value > 255) return 255;
        if (value < 0) return 0;
        return value;
    }

    public static int truncate(double value) {
        int newVal = (int) value;
        if (newVal > 255) return 255;
        if (newVal < 0) return 0;
        return newVal;
    }

    public static int get_rgb(BufferedImage image, String color, int x, int y) {
        int val = 0;
        java.awt.Color image_cols = new java.awt.Color(image.getRGB(x, y));
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


    public static int getNewRGB(java.awt.Color originalColor, double scale) {
        int rgb;
        int alpha = originalColor.getAlpha();
        int red = originalColor.getRed();
        int green = originalColor.getGreen();
        int blue = originalColor.getBlue();
        red = Helper.truncate((int) (128 + (red - 128) * scale));
        green = Helper.truncate((int) (128 + (green - 128) * scale));
        blue = Helper.truncate((int) (128 + (blue - 128) * scale));

        rgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
        return rgb;
    }

    public static BufferedImage create_blank_image(BufferedImage image) {
        BufferedImage blank_image = image;
        for (int x= 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                java.awt.Color rgb = new java.awt.Color(255, 255, 255);
                blank_image.setRGB(x, y, rgb.getRGB());
            }
        }
        return blank_image;
    }

    private static int getPower(int counter) {
        if (counter == 0 || counter == 1) {
            return 0;
        }
        return (int) Math.floor(Math.log10(counter))+1;
    }

    public static File getEditedFile(String directory, String file_name, String type, String customExtension) {
        int buffer = customExtension.length()-1 + 4;
        String directoryConstant = directory + File.separator;

        File editedFile = new File(directoryConstant +
                file_name.split("[.]")[0] + ("-VV" + customExtension + "." + type));
        int counter = 0;
        while (editedFile.exists()) {
            counter += 1;
            editedFile = new File(directoryConstant + editedFile.getName().split("[.]")[0].substring(
                    0, editedFile.getName().split("[.]")[0].length()-(buffer+2*getPower(counter))) + "-" + counter + "-VV" + customExtension + "." + type);
        }
        if (type.isEmpty()) {
            editedFile = new File(directoryConstant + editedFile.getName().split("[.]")[0]);
        }
        return editedFile;
    }

    public static String chooseDirectory() {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);

        String directory = String.valueOf(f.getSelectedFile());
        return directory;
    }

    public static String chooseFile(String extension) {
        JFileChooser f = new JFileChooser();
        f.showSaveDialog(null);
        FileFilter fileFilter = new SerializedFileFilter(extension, "Serialized Vision Vista edits");
        f.addChoosableFileFilter(fileFilter);

        String directory = f.getSelectedFile().getAbsolutePath();
        return directory;
    }

    public static String[] createZerosArray(int length) {
        String[] dummy = new String[length];
        Arrays.fill(dummy, "0");
        return dummy;
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

    //For blending
    public static java.awt.Color normalizeLuminance(java.awt.Color original, java.awt.Color blended) {
        double originalLuminance = computeLuminance(original);
        double blendedLuminance = computeLuminance(blended);
        double luminanceRatio = originalLuminance / blendedLuminance;

        int adjustedRed = clamp((int)(blended.getRed() * luminanceRatio), 0, 255);
        int adjustedGreen = clamp((int)(blended.getGreen() * luminanceRatio), 0, 255);
        int adjustedBlue = clamp((int)(blended.getBlue() * luminanceRatio), 0, 255);

        return new java.awt.Color(adjustedRed, adjustedGreen, adjustedBlue);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
