package com.visionvista.components;

import com.visionvista.ImageHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LandingWindow {
    JFrame landingFrame;

    public LandingWindow() {
        //Create landing frame and set properties
        landingFrame = new JFrame("Image Editor");
        landingFrame.setSize(1200, 800);
        landingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        landingFrame.setLocationRelativeTo(null);

        JPanel landingPanel = new JPanel();

//        OutlineText openingText = new OutlineText("Vision Vista", Color.BLUE, Color.YELLOW, new Font("Arial", Font.PLAIN, 24));
        JLabel openingText = new JLabel("Vision Vista");
        openingText.setFont(new Font("Aria", Font.BOLD, 20));

        JButton newImageButton = new JButton("New Blank Image");

        //Add padding and buttons to center them
        JLabel padding1 = new JLabel("");
        JLabel padding2 = new JLabel("");
        JLabel padding3 = new JLabel("");
        JLabel padding4 = new JLabel("");
        JLabel padding5 = new JLabel("");
        JLabel padding6 = new JLabel("");
        JLabel padding7 = new JLabel("");
        padding1.setBackground(new Color(238, 238, 238));
        padding2.setBackground(new Color(238, 238, 238));
        padding3.setBackground(new Color(238, 238, 238));
        padding4.setBackground(new Color(238, 238, 238));
        padding5.setBackground(new Color(238, 238, 238));
        padding6.setBackground(new Color(238, 238, 238));
        padding7.setBackground(new Color(238, 238, 238));

        JButton urlButton = new JButton("Load URL");
        JButton openButton = new JButton("Open Image");
        JButton recentButton = new JButton("Open recent");

        Dimension buttonDimension = new Dimension(250, 250);

        newImageButton.setPreferredSize(buttonDimension);
        padding1.setPreferredSize(buttonDimension);
        padding2.setPreferredSize(buttonDimension);
        padding3.setPreferredSize(buttonDimension);
        padding4.setPreferredSize(buttonDimension);
        padding5.setPreferredSize(buttonDimension);
        padding6.setPreferredSize(buttonDimension);
        padding7.setPreferredSize(buttonDimension);

        urlButton.setPreferredSize(buttonDimension);
        openButton.setPreferredSize(buttonDimension);
        recentButton.setPreferredSize(buttonDimension);

        landingPanel.add(padding5);
        landingPanel.add(padding6);
        landingPanel.add(openingText);
        landingPanel.add(padding4);
        landingPanel.add(padding2);
        landingPanel.add(padding1);
        landingPanel.add(newImageButton);
        landingPanel.add(openButton);
        landingPanel.add(padding7);
        landingPanel.add(urlButton);
        landingPanel.add(recentButton);

        ImageHandler imageHandler = new ImageHandler();
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                imageHandler.openImage();
                landingFrame.dispose();
            }
        });

        urlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                imageHandler.openImageFromUrl();
                landingFrame.dispose();
            }
        });

        newImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                imageHandler.createNewImage();
                landingFrame.dispose();
            }
        });

        recentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                imageHandler.openRecentProject();
                landingFrame.dispose();
            }
        });

        landingFrame.add(landingPanel);

    }

    public void show() {
        landingFrame.setVisible(true);
    }
}
