package com.visionvista;

import javax.swing.*;

public class ImageEditor {
    //For main frame
    private final JFrame editorFrame;

    private boolean isBlankImage = false;

    //NOTE: Procedure for applying effect:
    /*
    Effect effect = new Effect();
    BufferedImage currentImage = EditorState.getInstance().getImage();
    currentImage = effect.run(currentImage);
    EditorState.getInstance().getEffectHistory().add((effect, currentImage);
    EditorState.getInstance().setImage(currentImage);
    imageDisplay.updateImageFromState();
    imageTimeline.refreshTimeline();
     */

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
        //Panel components
        JPanel editorPanel = new JPanel(); //do gridlayout to add compoemnets onto editor

        //Initialize image display component
        ImageDisplay imageDisplay = new ImageDisplay();
        imageDisplay.setFileDetails(fileNameBroken);

        editorPanel.add(imageDisplay.getImageLabel());

        //TODO POtentially move to tools panel speparate of editor
        ImageTimeline imageTimeline = new ImageTimeline(imageDisplay);

        editorFrame.add(editorPanel);

        EffectControls effectControls = new EffectControls(imageDisplay, imageTimeline);

        //Create menu panel and make menu panel part of editor
        MenuPanel menuPanel = new MenuPanel(imageDisplay, imageTimeline, effectControls);
        menuPanel.setupMenuPanel();
        editorFrame.setJMenuBar(menuPanel.getMenuBar());

        //Tools panel
        ToolsPanel toolsPanel = new ToolsPanel(imageDisplay, imageTimeline);
        //Add pages
        toolsPanel.addPage(effectControls);
        //Setup panel
        toolsPanel.setupTabPanels();
        toolsPanel.show();

        //Add state based UI components to the group
        StateBasedUIComponentGroup stateBasedUIComponentGroup = new StateBasedUIComponentGroup();
        stateBasedUIComponentGroup.addUIComponent(imageDisplay);
        stateBasedUIComponentGroup.addUIComponent(imageTimeline);
        stateBasedUIComponentGroup.addUIComponent(effectControls);
    }

    public void show() {
        editorFrame.pack();
        editorFrame.setVisible(true);
    }
}
