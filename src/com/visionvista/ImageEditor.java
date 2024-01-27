package com.visionvista;

import javax.swing.*;

public class ImageEditor {
    //For main frame
    private final JFrame editorFrame;
    private final MenuPanel menuPanel;

    //Panel components
    private final JPanel editorPanel;
    private final ImageDisplay imageDisplay;
    private final ImageTimeline imageTimeline;

    //Other
    private final String[] fileNameBroken;
    private boolean isBlankImage = false;

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
        //Initialize image display component
        imageDisplay = new ImageDisplay(editorPanel);
        imageDisplay.setFileDetails(fileNameBroken);

        imageTimeline = new ImageTimeline(imageDisplay);

        editorFrame.add(editorPanel);
        //Create menu panel and make menu panel part of editor
        menuPanel = new MenuPanel(imageDisplay, imageTimeline);
        menuPanel.setupMenuPanel();
        editorFrame.setJMenuBar(menuPanel.getMenuBar());
    }

    public void show() {
        editorFrame.pack();
        editorFrame.setVisible(true);
    }
}
