/*
 * Copyright (c) 2024. Vihaan Mathur
 * All rights reserved.
 */

package com.visionvista;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.visionvista.components.LandingWindow;

import javax.swing.*;

public class Main {
    private static void initializeLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            System.err.println("Failed to initialize LaF");
        }
    }

    public static void main(String[] args) throws Exception {
        initializeLookAndFeel();

        LandingWindow landingWindow = new LandingWindow();
        landingWindow.show();
    }
}
