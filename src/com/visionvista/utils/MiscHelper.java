package com.visionvista.utils;

import com.visionvista.components.PlaceholderTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Base64;

public class MiscHelper {
    public static String[] createZerosArray(int length) {
        String[] dummy = new String[length];
        Arrays.fill(dummy, "0");
        return dummy;
    }

    public static void addProportionalChangeListener(BufferedImage image, PlaceholderTextField sourceField, PlaceholderTextField targetField, boolean isWidthSource) {
        sourceField.getDocument().addDocumentListener(new DocumentListener() {
            public void update() {
                if (sourceField.hasFocus()) {
                    try {
                        double sourceValue = Double.parseDouble(sourceField.getText());
                        double ratio = isWidthSource ? sourceValue / image.getWidth() : sourceValue / image.getHeight();
                        double targetValue = isWidthSource ? ratio * image.getHeight() : ratio * image.getWidth();
                        targetField.setText(String.format("%.2f", targetValue));
                    } catch (NumberFormatException ex) {
                        System.out.println("Invalid number");
                    }
                }
            }

            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }
        });
    }


    public static String imageToBase64(BufferedImage image) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    public static String formatEffectName(String effectName) {
        //Insert a space before any uppercase letter or digit that follows a lowercase letter
        return effectName
                .replaceAll("([a-z])([A-Z])", "$1 $2")  //lowercase then uppercase
                .replaceAll("([a-zA-Z])([0-9])", "$1 $2")  //letter then digit
                .replaceAll("([0-9])([A-Z])", "$1 $2");   //digit then uppercase letter
    }

    public static boolean isString(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        String numericRegex = "[-+]?\\d*\\.?\\d+";
        return !str.matches(numericRegex);
    }
}
