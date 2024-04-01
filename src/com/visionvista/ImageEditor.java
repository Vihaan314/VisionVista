package com.visionvista;

import javax.swing.*;

public class ImageEditor {
    //For main frame
    private final JFrame editorFrame;

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
        editorFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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


        //Add state based UI components to the group
        StateBasedUIComponentGroup stateBasedUIComponentGroup = new StateBasedUIComponentGroup();
        stateBasedUIComponentGroup.addUIComponent(imageDisplay);
        stateBasedUIComponentGroup.addUIComponent(imageTimeline);
        EffectControls effectControls = new EffectControls(stateBasedUIComponentGroup);

        stateBasedUIComponentGroup.addUIComponent(effectControls);

        //Create menu panel and make menu panel part of editor
        MenuPanel menuPanel = new MenuPanel(stateBasedUIComponentGroup);
        menuPanel.setupMenuPanel();
        editorFrame.setJMenuBar(menuPanel.getMenuBar());

        //Tools panel
        ToolsPanel toolsPanel = new ToolsPanel(stateBasedUIComponentGroup);
        stateBasedUIComponentGroup.addUIComponent(toolsPanel);
        //Add pages
        toolsPanel.addPage(effectControls);
        //Setup panel
//        toolsPanel.setupTabPanels();
//        toolsPanel.show();
    }

    public void show() {
        editorFrame.pack();
        editorFrame.setVisible(true);
    }
}
