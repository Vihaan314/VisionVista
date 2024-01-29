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
        //Create panel for components
        editorPanel = new JPanel(); //do gridlayout to add compoemnets onto editor

        //Initialize image display component
        imageDisplay = new ImageDisplay();
        imageDisplay.setFileDetails(fileNameBroken);

        editorPanel.add(imageDisplay.getImageLabel());

        //TODO POtentially move to tools panel speparate of editor
        imageTimeline = new ImageTimeline(imageDisplay);

        editorFrame.add(editorPanel);

        //Create menu panel and make menu panel part of editor
        menuPanel = new MenuPanel(imageDisplay, imageTimeline);
        menuPanel.setupMenuPanel();
        editorFrame.setJMenuBar(menuPanel.getMenuBar());

        ToolsPanel toolsPanel = new ToolsPanel();
        toolsPanel.show();
    }

    public void show() {
        editorFrame.pack();
        editorFrame.setVisible(true);
    }
}
