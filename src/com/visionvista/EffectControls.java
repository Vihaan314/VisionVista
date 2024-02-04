package com.visionvista;

import javax.swing.*;
import java.awt.*;

public class EffectControls {
    private final EffectHistory effectHistory;
    private JFrame controlsFrame;

    public EffectControls() {
        setupControlsFrame();
        this.effectHistory = EditorState.getInstance().getEffectHistory();
    }

    public void setupControlsFrame() {
        controlsFrame = new JFrame("Effect Controls");

        controlsFrame.setSize(500, 300);
        controlsFrame.setLayout(new GridLayout(3, 1));
        controlsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public JPanel getTabPanel() {
        JPanel effectControlsPanel;
        effectControlsPanel = new JPanel();
        effectControlsPanel.setSize(500, 800);
        effectControlsPanel.add(new JLabel("Effect controls"));
        effectControlsPanel.setVisible(true);

        JPanel effectsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        for (int i = effectHistory.getSize() - 1; i >= 0; i--) {
//            SliderEffectWindow effectSlider = new SliderEffectWindow(effectHistory.getEffect(i));
//            effectsPanel.add(effectButton);
        }

        effectControlsPanel.add(effectsPanel);
        return effectControlsPanel;
    }

    public void show() {
        controlsFrame.pack();
        controlsFrame.setVisible(true);
    }
}
