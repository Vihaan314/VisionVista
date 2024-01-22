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
    private final JFrame editorFrame;
    private final JPanel editorPanel;
    private final ImageDisplay imageDisplay;

    private boolean isBlankImage = false;

    private ButtonPanel buttonPanel;
    private final MenuPanel menuPanel;

    public ImageEditor(String title) {
        /*
            Image Editor
             /\      /\
         MenuPanel EditorPanel
                   /\       /\
            Image display Other components (e.g. slider)
         */
        //Initialize frame
        editorFrame = new JFrame();
        editorFrame.setTitle(title);
        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Create panel for components
        editorPanel = new JPanel(); //do gridlayout to add compoemnets onto editor
        //Add image viewer component and add to frame
        imageDisplay = new ImageDisplay(editorPanel);
        editorPanel.add(imageDisplay.getImage());
        editorFrame.add(editorPanel);
        //Create menu panel and make menu panel part of editor
        menuPanel = new MenuPanel(imageDisplay);
        menuPanel.setupMenuPanel();
        editorFrame.setJMenuBar(menuPanel.getMenuBar());
    }

    public void show() {
//        editorFrame.add(editorPanel);
        editorFrame.pack();
        editorFrame.setVisible(true);
    }
}
