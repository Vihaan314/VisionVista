package com.visionvista;

import javax.swing.*;

public class ImageEditor {
    private final JFrame editorFrame;
    private final JPanel editorPanel;
    private final ImageDisplay imageDisplay;

    private final String[] fileNameBroken;

    private boolean isBlankImage = false;

    private final MenuPanel menuPanel;

    public ImageEditor(String title, String[] fileNameBroken) {
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
        //Set file name details
        this.fileNameBroken = fileNameBroken;
        //Create panel for components
        editorPanel = new JPanel(); //do gridlayout to add compoemnets onto editor
        //Add image viewer component and add to frame
        //TODO potentially fix does it make sense for component of panel to take in panel
        imageDisplay = new ImageDisplay(editorPanel);
        imageDisplay.setFileDetails(fileNameBroken);
        editorFrame.add(editorPanel);
        //Create menu panel and make menu panel part of editor
        menuPanel = new MenuPanel(imageDisplay);
        menuPanel.setupMenuPanel();
        editorFrame.setJMenuBar(menuPanel.getMenuBar());
    }

    public void show() {
        editorFrame.pack();
        editorFrame.setVisible(true);
    }
}
