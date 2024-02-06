package com.visionvista;

import com.visionvista.components.DraggableTabbedPane;

import javax.swing.*;
import java.awt.*;

public class ToolsPanel {
    private ImageDisplay imageDisplay;
    private ImageTimeline imageTimeline;
    private EffectControls effectControls;

    private JFrame toolsFrame;
    private DraggableTabbedPane tabPanel;

    private JPanel effectControlsPage;
    private JPanel toolsPage;
    private JPanel layersPage;

    public ToolsPanel(ImageDisplay imageDisplay, ImageTimeline imageTimeline, EffectControls effectControls) {
        this.imageDisplay = imageDisplay;
        this.imageTimeline = imageTimeline;
        this.effectControls = effectControls;
        setupToolsFrame();
        setupTabs();
    }

    private void setupToolsFrame() {
        toolsFrame = new JFrame("Tools Panel");
        toolsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        toolsFrame.setSize(450, 800);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int x = screenWidth - toolsFrame.getWidth() - 10;
        int y = 0;

        toolsFrame.setLocation(x, y);
    }

    private void setupTabs() {
        tabPanel = new DraggableTabbedPane();

        //Effect controls page
        effectControlsPage = effectControls.getTabPanel();
        //Add to state based components
        StateBasedUIComponentGroup.getInstance().addUIComponent(effectControls);

        //Tools
        toolsPage = new JPanel();
        toolsPage.add(new JLabel("Tools"));


        //Layers - WORK IN PROGRESS
        layersPage = new JPanel();
        layersPage.add(new JLabel("Layers"));

        tabPanel.addTab("Effect Controls", effectControlsPage);
        tabPanel.addTab("Tools", toolsPage);
//        tabPanel.addTab("Layers", layersPage);

        toolsFrame.getContentPane().add(tabPanel); // Add tabPanel to the frame's content pane
    }

    public void show() {
        toolsFrame.setVisible(true);
    }
}
