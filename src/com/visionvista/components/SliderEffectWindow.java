package com.visionvista.components;

import com.visionvista.*;
import com.visionvista.commands.Command;
import com.visionvista.effects.Effect;
import com.visionvista.effects.EffectType;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

public class SliderEffectWindow {
    private JFrame sliderFrame;
    private JSlider slider;
    private final JButton submitButton= new JButton("Enter");
    private EffectType effect;
    public int lower;
    public int upper;
    private JPanel sliderPanel = new JPanel();
    final int[] effect_amount = {0};

    private int defaultSliderValue = 0;

    private StateBasedUIComponentGroup stateBasedUIComponentGroup;

    public void setupSliderFrame(EffectType effect) {
        this.sliderFrame = new JFrame(effect.toString() + " slider");

        sliderFrame.setSize(500, 300);
        sliderFrame.setLayout(new GridLayout(3, 1));
        sliderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public SliderEffectWindow(EffectType effect, int lower, int upper, StateBasedUIComponentGroup stateBasedUIComponentGroup) {
        this.effect = effect;
        this.lower = lower;
        this.upper = upper;
        setupSliderFrame(effect);
        this.stateBasedUIComponentGroup = stateBasedUIComponentGroup;
    }

    public JPanel getSliderPanel() {
        return this.sliderPanel;
    }

    public void setDefaultSliderValue(Double sliderValue) {
        System.out.println(sliderValue.intValue());
        this.defaultSliderValue = sliderValue.intValue();
    }

    public void setupSlider() {
        JLabel status = new JLabel("Choose " + effect, JLabel.CENTER);
        slider = new JSlider(lower, upper, defaultSliderValue);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);

        slider.setPaintLabels(true);

        //Setup slider markings
        Hashtable<Integer, JLabel> position = new Hashtable<Integer, JLabel>();
        if (upper % 10 == 0) {
            for (int i = lower; i < (3*upper/2); i+= upper/2) {
                position.put(i, new JLabel(String.valueOf(i)));
            }
        }
        else {
            for (int i = lower; i < upper+1; i++) {
                position.put(i, new JLabel(String.valueOf(i)));
            }
        }

        slider.setLabelTable(position);

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                BufferedImage currentImage = EditorState.getInstance().getImage();

                effect_amount[0] = ((JSlider)e.getSource()).getValue();
                status.setText(effect + ": " + ((JSlider)e.getSource()).getValue());

                //Apply the effect to the original image, not the current state image
                Effect chosenEffect = effect.getEffect((double) effect_amount[0]);
                BufferedImage editedImage = chosenEffect.run(currentImage);

                //Temporarily display the edited image without updating the EditorState
                ImageDisplay imageDisplay = (ImageDisplay) stateBasedUIComponentGroup.getUIComponent(ImageDisplay.class);
                imageDisplay.displayTemporaryImage(editedImage);
            }
        });
        sliderPanel.add(status);
        sliderPanel.add(submitButton);
        sliderPanel.add(slider);
        sliderFrame.add(sliderPanel);
    }

    public double getEffectAmount() {
        return effect_amount[0];
    }

    public JFrame getSliderFrame() {
        return this.sliderFrame;
    }

    public void setupSubmitButton(ActionListener submitEffectListener) {
        submitButton.addActionListener(submitEffectListener);
    }

    public ActionListener createSubmitActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage currentImage = EditorState.getInstance().getImage();
                //Get final effect
                double effectAmount = getEffectAmount();
                Effect chosenEffect = effect.getEffect(effectAmount);
                //Apply effect
                BufferedImage finalImage = chosenEffect.run(currentImage);
                //Set new states
                EditorState.getInstance().getEffectHistory().add(chosenEffect, finalImage);
                EditorState.getInstance().setImage(finalImage);
                //Update the display with the final image
                ((ToolsPanel) stateBasedUIComponentGroup.getUIComponent(ToolsPanel.class)).setStateBasedUIComponentGroup(stateBasedUIComponentGroup);
                stateBasedUIComponentGroup.updateAllUIFromState();

                //Close slider window when submit pressed
                getSliderFrame().dispose();
            }
        };
    }

    public void show() {
        sliderPanel.add(submitButton);
        sliderPanel.add(slider);
        sliderFrame.add(sliderPanel);

        sliderFrame.pack();
        sliderFrame.setVisible(true);
    }

    public Command sliderValuesEffect() {
        //Command to create slider and show
        return () -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            setupSubmitButton(createSubmitActionListener());
            show();
        };
    }
}
