package com.visionvista;

import com.visionvista.components.DraggableTabbedPane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ToolsPanel {
    private ImageDisplay imageDisplay;
    private ImageTimeline imageTimeline;
//    private EffectControls effectControls;

    private JFrame toolsFrame;
    private DraggableTabbedPane tabPanel;

    private JPanel effectControlsPage;
    private JPanel toolsPage;
    private JPanel layersPage;

    private ArrayList<StateBasedUIComponent> toolUIComponents;
    private ArrayList<JPanel> toolPages;

    public ToolsPanel(ImageDisplay imageDisplay, ImageTimeline imageTimeline)
    {
        this.imageDisplay = imageDisplay;
        this.imageTimeline = imageTimeline;
        toolUIComponents = new ArrayList<>();
        toolPages = new ArrayList<>();
        setupToolsFrame();
//        setupTabPanels();
    }

    public void addPage(StateBasedUIComponent stateBasedUIComponent) {
        toolUIComponents.add(stateBasedUIComponent);
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

    public void setupTabPanels() {
        tabPanel = new DraggableTabbedPane();

//        Effect controls page
//        effectControlsPage = toolUIComponents.getTab();
        effectControlsPage = new EffectControls(imageDisplay, imageTimeline).getTabPanel();
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
