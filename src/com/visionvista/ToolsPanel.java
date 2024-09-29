package com.visionvista;

import com.visionvista.components.DraggableTabbedPane;
import com.visionvista.components.ImageTimeline;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ToolsPanel implements StateBasedUIComponent{
    private ImageDisplay imageDisplay;
    private ImageTimeline imageTimeline;
//    private EffectControls effectControls;
    private StateBasedUIComponentGroup stateBasedUIComponentGroup;

    private JFrame toolsFrame;
    private DraggableTabbedPane tabPanel;

    private EffectControls effectControls;

    private JPanel effectControlsPage;
    private JPanel toolsPage;
    private JPanel layersPage;

    private ArrayList<StateBasedUIComponent> toolUIComponents;
    private ArrayList<JPanel> toolPages;

    public ToolsPanel(StateBasedUIComponentGroup stateBasedUIComponentGroup)
    {
        this.stateBasedUIComponentGroup = stateBasedUIComponentGroup;
        this.effectControls = new EffectControls(stateBasedUIComponentGroup);
        this.imageDisplay = (ImageDisplay) stateBasedUIComponentGroup.getUIComponent(ImageDisplay.class);
        toolUIComponents = new ArrayList<>();
        toolPages = new ArrayList<>();
        setupToolsFrame();
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
//        System.out.println("TOOLS PANEL " + imageDisplay);
        effectControlsPage = effectControls.getTabPanel();
        //Tools
        toolsPage = new JPanel();
        toolsPage.add(new JLabel("Tools"));


        //Layers - WORK IN PROGRESS
        layersPage = new JPanel();
        layersPage.add(new JLabel("Layers"));

        tabPanel.addTab("Effect Controls", effectControlsPage);
        tabPanel.addTab("Tools", toolsPage);
//        tabPanel.addTab("Layers", layersPage);

        toolsFrame.getContentPane().add(tabPanel);
    }

    public void show() {
        toolsFrame.setVisible(true);
    }

    public void setStateBasedUIComponentGroup(StateBasedUIComponentGroup stateBasedUIComponentGroup) {
        this.stateBasedUIComponentGroup = stateBasedUIComponentGroup;
    }

    @Override
    public void updateFromState() {
//        System.out.println("YO TOOLS");
        effectControls.updateFromState();
//        effectControls = new EffectControls(stateBasedUIComponentGroup);
        this.imageDisplay = (ImageDisplay) stateBasedUIComponentGroup.getUIComponent(ImageDisplay.class);
        toolUIComponents = new ArrayList<>();
        toolPages = new ArrayList<>();
        this.setupToolsFrame();
        this.setupTabPanels();

        effectControlsPage.revalidate();
        effectControlsPage.repaint();

        tabPanel.revalidate();
        tabPanel.repaint();

        SwingUtilities.invokeLater(() -> {
            toolsFrame.pack();
            toolsFrame.setSize(450, 800);
            toolsFrame.revalidate();
        });
    }
}
