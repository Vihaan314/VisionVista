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

    private boolean isBlankImage = false;

    private ButtonPanel buttonPanel;
    private MenuPanel menuPanel;

    public ImageEditor(String title, MenuPanel menuPanel) {
        this.currentImg = EditorState.getInstance().getImage();
        editorFrame = new JFrame();
        editorFrame.setTitle(title);
        editorPanel = new JPanel(); //do gridlayout to add compoemnets onto editor
        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Make menu panel part of editor
        menuPanel = new MenuPanel().setupMenuPanel();
        editorFrame.setJMenuBar(menuPanel.getMenuBar());
        imageLabel = new JLabel(new ImageIcon(currentImg));
        if (ImageHelper.isBlankImage(currentImg)) {
            this.isBlankImage = true;
        }
        System.out.println(isBlankImage);
    }

    public void updateEditor(BufferedImage newImg, String title) {
        //Update image
        //Wait it doesn't need to take newImg just read from the editor state get image
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

    public void updateImage(BufferedImage img, String title) {
        //Process new title
        this.title = title;
        editorFrame.setTitle(title);
        //Set currently displayed image to new image
        imageLabel.setIcon(new ImageIcon(img));
        //Set as current image
        EditorState.getInstance().setImage(img);
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
