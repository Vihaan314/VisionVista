package com.visionvista.components;

import com.visionvista.ImageHandler;

import javax.swing.*;
import java.awt.*;

public class LandingWindow {
    private JFrame landingFrame;
    private ImageHandler imageHandler;
    private JPanel landingPanel;

    private JButton openButton;
    private JButton generateImageButton;
    private JButton urlButton;
    private JButton recentButton;

    private void setupGridItems() {
        //Create landing frame and set properties
        landingFrame = new JFrame("Vision Vista");
        landingFrame.setSize(1200, 800);
        landingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        landingFrame.setLocationRelativeTo(null);

        landingPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //Layout constraints for the top panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(100, 0, 0, 0);  //Top padding

        JLabel openingText = new JLabel("Vision Vista");
        openingText.setFont(new Font("Arial", Font.BOLD, 24));
        landingPanel.add(openingText, gbc);

        //Button grid setup
        gbc.weighty = 0.8;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.anchor = GridBagConstraints.SOUTH;

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        openButton = new JButton("Open Image");
        generateImageButton = new JButton("Generate Image");
        urlButton = new JButton("Load URL");
        recentButton = new JButton("Open Recent");

        Font buttonFont = new Font("Arial", Font.BOLD, 15);
        openButton.setFont(buttonFont);
        generateImageButton.setFont(buttonFont);
        urlButton.setFont(buttonFont);
        recentButton.setFont(buttonFont);

        Dimension buttonDimension = new Dimension(250, 250);
        openButton.setPreferredSize(buttonDimension);
        generateImageButton.setPreferredSize(buttonDimension);
        urlButton.setPreferredSize(buttonDimension);
        recentButton.setPreferredSize(buttonDimension);

        buttonPanel.add(openButton);
        buttonPanel.add(generateImageButton);
        buttonPanel.add(urlButton);
        buttonPanel.add(recentButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        landingPanel.add(buttonPanel, gbc);
    }

    public void setupButtonActions() {
        imageHandler = new ImageHandler();
        openButton.addActionListener(e -> executeCommand(() -> imageHandler.openImage()));
        generateImageButton.addActionListener(e -> executeCommand(() -> {
            try {
                imageHandler.generateImageFromPrompt(landingFrame);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }));
        urlButton.addActionListener(e -> executeCommand(() -> imageHandler.openImageFromUrl()));
        recentButton.addActionListener(e -> executeCommand(() -> imageHandler.openRecentProject()));

        landingFrame.add(landingPanel);
    }

    public LandingWindow() throws Exception {
        setupGridItems();
        setupButtonActions();
    }

    private void executeCommand(Runnable action) {
        action.run();
        landingFrame.dispose();
    }

    public void show() {
        landingFrame.setVisible(true);
    }
}
