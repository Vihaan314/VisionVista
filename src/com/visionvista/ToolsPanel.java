package com.visionvista;

import com.visionvista.components.DraggableTabbedPane;

import javax.swing.*;

public class ToolsPanel {
    private JFrame toolsFrame;
    private DraggableTabbedPane tabPanel;

    private JPanel effectControlsPage;
    private JPanel toolsPage;
    private JPanel layersPage;

    public ToolsPanel() {
        setupToolsFrame();
        setupTabs();
    }

    public void setupToolsFrame() {
        toolsFrame = new JFrame("Tools panel");
        toolsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        toolsFrame.setSize(500, 800);
        toolsFrame.setLocation(1225, 300);
        toolsFrame.pack();
    }

    public void setupTabs() {
        tabPanel = new DraggableTabbedPane();

        EffectControls effectControls = new EffectControls();
        effectControlsPage = effectControls.getTabPanel();

        toolsPage = new JPanel();
        toolsPage.add(new JLabel("Tools"));
        toolsPage.setVisible(true);

        layersPage = new JPanel();
        layersPage.add(new JLabel("Layers"));
        toolsPage.setVisible(true);

        tabPanel.addTab("Effect Controls", effectControlsPage);
        tabPanel.addTab("Tools", toolsPage);
        tabPanel.addTab("Layers", layersPage);
        toolsFrame.add(tabPanel);
    }

    public void show() {
        toolsFrame.pack();
        toolsFrame.setVisible(true);
    }
}
