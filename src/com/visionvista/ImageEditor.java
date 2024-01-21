package com.visionvista;

import com.visionvista.components.PlaceholderTextField;
import com.visionvista.effects.Effect;
import com.visionvista.effects.ImageHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ImageEditor {
    private JFrame editorFrame;
    private JPanel editorPanel;
    private JLabel imageLabel;
    private BufferedImage currentImg;
    private String title;

    private JLabel effectSequenceLabel;
    private PlaceholderTextField effectSequenceField;
    private JButton submitSequence;

    private boolean isBlankImage = false;

    private ButtonPanel buttonPanel;
    private MenuPanel menuPanel = new MenuPanel();

    public ImageEditor(String title, BufferedImage img, MenuPanel menuPanel) {
        this.currentImg = img;
        editorFrame = new JFrame();
        editorFrame.setTitle(title);
        editorPanel = new JPanel(); //do gridlayout to add compoemnets onto editor
        this.title = title;
        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        editorFrame.setJMenuBar(menuPanel.getMenuBar());
        imageLabel = new JLabel(new ImageIcon(img));
        if (ImageHelper.isBlankImage(img)) {
            this.isBlankImage = true;
        }
        System.out.println(isBlankImage );
    }

    public String getSequenceInput() {
        return effectSequenceField.getText();
    }

    public void openSequenceInput(ActionListener sequenceListener) {
        effectSequenceLabel = new JLabel("Enter sequence:");
        effectSequenceLabel.setFont(new Font("Aria", Font.BOLD, 20));
        effectSequenceField = new PlaceholderTextField();
        effectSequenceField.setPlaceholder("Effect sequence");
        effectSequenceField.setPreferredSize(new Dimension(370, 30));
        submitSequence = new JButton("Apply sequence");
        System.out.println("hi");
        submitSequence.addActionListener(sequenceListener);


        editorPanel.add(effectSequenceField);
        editorPanel.add(effectSequenceLabel);
        editorPanel.add(submitSequence);
    }

//    public void updateEditor(BufferedImage newImg, String title) {
//        this.updateMenuPanel(menuPanel.setupMenuPanel(newImg));
//        this.updateImage(newImg, title);
////        printEffectSequence();
//    }

    public void updateEditor(BufferedImage newImg, String title, EffectHistory effectHistory) {
        System.out.println("HI");
        this.menuPanel.setMenuParameters(this, effectHistory);
        System.out.println("HI");
        this.updateMenuPanel(menuPanel.setupMenuPanel(newImg));
        this.updateImage(newImg, title);
    }

    public void addButtonPanel(ButtonPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
        JPanel panelButton = buttonPanel.getButtonPanel();
        editorPanel.add(panelButton);
    }
    public void updateMenuPanel(MenuPanel menuPanel) {
        editorFrame.setJMenuBar(menuPanel.getMenuBar());
    }

    public void updateButtonPanel(ButtonPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    public BufferedImage getCurrentImage() {
        return currentImg;
    }

    public String getTitle() {
        return this.title;
    }

    public void updateImage(BufferedImage img, String title) {
        this.title = title;
        editorFrame.setTitle(title);
        imageLabel.setIcon(new ImageIcon(img));
        editorFrame.pack();
    }

    public void show() {

        editorPanel.add(imageLabel);
        editorFrame.add(editorPanel);
        editorFrame.pack();


        editorFrame.setVisible(true);
    }

    public JFrame getEditorFrame() {
        return this.editorFrame;
    }

}
