package com.visionvista;

import com.visionvista.components.ColorEffectWindow;
import com.visionvista.components.SliderEffectWindow;
import com.visionvista.effects.Brightness;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.Pair;

import javax.swing.*;
import java.awt.*;

public class EffectControls implements StateBasedUIComponent {
    private EffectHistory effectHistory;
    private JFrame controlsFrame;
    private StateBasedUIComponentGroup stateBasedUIComponentGroup;

    private JPanel effectControlsPanel;

    public EffectControls(StateBasedUIComponentGroup stateBasedUIComponentGroup) {
        setupControlsFrame();
        this.effectHistory = EditorState.getInstance().getEffectHistory();
        this.stateBasedUIComponentGroup = stateBasedUIComponentGroup;
//        this.imageDisplay = (ImageDisplay) stateBasedUIComponentGroup.getUIComponent(ImageDisplay.class);
//        this.imageTimeline = (ImageTimeline) stateBasedUIComponentGroup.getUIComponent(ImageDisplay.class);
        setupTabPanel();
    }

    public void setupControlsFrame() {
        controlsFrame = new JFrame("Effect Controls");
        controlsFrame.setSize(450, 300);
        controlsFrame.setLayout(new GridLayout(3, 1));
        controlsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void addSliderEffectToTabPanel(EffectType effectType, JPanel panel) {
        SliderEffectWindow sliderEffectWindow = new SliderEffectWindow(effectType, effectType.getSliderBounds().getLeft(), effectType.getSliderBounds().getRight(), stateBasedUIComponentGroup);
//        sliderEffectWindow.setDefaultSliderValue((Double) this.effectHistory.getLastEffectInstance(effectType).getParameter());
        sliderEffectWindow.setupSlider();
        panel.add(sliderEffectWindow.getSliderPanel());
    }

    public void addColorEffectToTabPanel(EffectType effectType, JPanel panel) {
        ColorEffectWindow colorEffectWindow = new ColorEffectWindow(effectType, stateBasedUIComponentGroup);
        panel.add(colorEffectWindow.getColorPanel());
    }

    public void setupTabPanel() {
//        For testing
//        ArrayList<Effect> test = new ArrayList<>();
//        test.add(new Contrast(10));
//        test.add(new Brightness(15));
//        test.add(new Glow(5));
//        test.add(new Hue(new Color(25, 25, 25)));
//        test.add(new Brightness(20));
//        this.effectHistory = new EffectHistory();
//        this.effectHistory.setEffectSequence(test, EditorState.getInstance().getImage());
//        EditorState.getInstance().getEffectHistory().setEffectSequence(test, EditorState.getInstance().getImage());
        this.effectHistory = EditorState.getInstance().getEffectHistory();
//        this.effectHistory.getEffectSequence().add(new Pair<>(new Brightness(20), EditorState.getInstance().getImage()));

        //Setup effect controls panel
        effectControlsPanel = new JPanel();
        effectControlsPanel.setLayout(new BoxLayout(effectControlsPanel, BoxLayout.Y_AXIS));

        effectControlsPanel.add(new JLabel("Effect controls"));

        JPanel effectsListPanel = new JPanel();
        effectsListPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        if (effectHistory.getSize() == 1) {
            JLabel emptyLabel = new JLabel("No effects applied");
            effectsListPanel.add(emptyLabel);
        }
        for (int i = 0; i < effectHistory.getSize(); i++) {
            if (i != 0) {
//                String className = (effectHistory.getEffectFromIndex(i).getClass().getSimpleName().equals("GaussBlur")) ? "Gaussian blur" : effectHistory.getEffectFromIndex(i).getClass().getSimpleName();
//                EffectType effectType = EffectType.fromLabel(className);
                EffectType effectType = EffectType.BOX_BLUR;
                switch (effectType.getUIType()) {
                    case SLIDER:
                        System.out.println("SLIDER EFFECT");
                        addSliderEffectToTabPanel(effectType, effectsListPanel);
                    case COLOR_CHOOSER:
                        addColorEffectToTabPanel(effectType, effectsListPanel);
                }
            }
        }

        effectControlsPanel.add(effectsListPanel);
    }

    public JPanel getTabPanel() {
        return this.effectControlsPanel;
    }

    public void show() {
        controlsFrame.pack();
        controlsFrame.setVisible(true);
    }

    @Override
    public void updateFromState() {
        System.out.println("UPDATE EFFET CONTROLS");
        this.effectHistory = EditorState.getInstance().getEffectHistory();
        setupTabPanel();

        effectControlsPanel.revalidate();
        effectControlsPanel.repaint();

        SwingUtilities.invokeLater(() -> {
            controlsFrame.pack();
            controlsFrame.setSize(400, 800);
            controlsFrame.revalidate();
        });
    }
}
