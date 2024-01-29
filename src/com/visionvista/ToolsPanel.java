package com.visionvista;

import javax.swing.*;

public class ToolsPanel {
    private JFrame toolsFrame;
    private JTabbedPane tabPanel;

    private JPanel effectControlsPage;
    private JPanel timelinePage;
    private JPanel page3;

    public ToolsPanel() {
        setupToolsFrame();
        setupTabs();
    }

    public void setupToolsFrame() {
        toolsFrame = new JFrame("Tools panel");
        toolsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        toolsFrame.setSize(400, 800);
        toolsFrame.pack();
    }

    public void setupTabs() {
        tabPanel = new JTabbedPane();
        effectControlsPage = new JPanel();
        effectControlsPage.setSize(400, 800);
        effectControlsPage.add(new JLabel("Effect controls"));
        timelinePage = new JPanel();
        timelinePage.add(new JLabel("Timeline"));
        page3 = new JPanel();
        page3.add(new JLabel("This is Tab 3"));

        tabPanel.addTab("Tab 1", effectControlsPage);
        tabPanel.addTab("Tab 2", timelinePage);
        tabPanel.addTab("Tab 3", page3);
        toolsFrame.add(tabPanel);
    }

    public void show() {
        toolsFrame.pack();
        toolsFrame.setVisible(true);
    }
}
