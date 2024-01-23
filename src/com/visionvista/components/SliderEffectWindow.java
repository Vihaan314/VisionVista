package com.visionvista.components;

import com.visionvista.EditorState;
import com.visionvista.ImageDisplay;
import com.visionvista.effects.*;
import com.visionvista.EffectHistory;

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
    private JButton submitButton;
    private EffectType effect;
    public int lower;
    public int upper;
    private BufferedImage image;
    private JPanel sliderPanel = new JPanel();
    final int[] effect_amount = {0};

    private ImageDisplay imageDisplay;


    public JFrame setupSliderFrame(EffectType effect) {
        JFrame sliderFrame = new JFrame(effect.toString() + " slider");

        sliderFrame.setSize(500, 300);
        sliderFrame.setLayout(new GridLayout(3, 1));
        sliderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        return sliderFrame;
    }

    public SliderEffectWindow(EffectType effect, int lower, int upper, ImageDisplay imageDisplay) {
        this.effect = effect;
        this.lower = lower;
        this.upper = upper;
        this.imageDisplay = imageDisplay;
//        if (editor != null) {
//            this.sliderFrame = editor.getEditorFrame();
//        }
//        else {
            this.sliderFrame = setupSliderFrame(effect);
//        }
        this.slider = setupSlider(lower, upper);
    }


    public JSlider setupSlider(int lower, int upper) {
        JLabel status = new JLabel("Choose " + effect, JLabel.CENTER);
        JSlider slider = new JSlider(lower, upper, 0);
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
                effect_amount[0] = ((JSlider)e.getSource()).getValue();
                status.setText(effect + ": " + ((JSlider)e.getSource()).getValue());

                Effect chosenEffect = effect.getEffect((double) effect_amount[0]);
                BufferedImage currentImage = EditorState.getInstance().getImage();
                BufferedImage editedImage = chosenEffect.run(currentImage);
                EffectHistory effectHistory = EditorState.getInstance().getEffectHistory();
                effectHistory.add(chosenEffect, editedImage);
                EditorState.getInstance().setEffectHistory(effectHistory);
                EditorState.getInstance().setImage(editedImage);
                imageDisplay.updateEditorFromState();
            }
        });
        sliderPanel.add(status);
        return slider;
    }

    public double getEffectAmount() {
        return effect_amount[0];
    }

    public JFrame getSliderFrame() {
        return this.sliderFrame;
    }

    public void setupSubmitButton(ActionListener submitEffectListener) {
        JButton submitEffect = new JButton("Enter");
        submitEffect.addActionListener(submitEffectListener);
        this.submitButton = submitEffect;
    }

    public ActionListener createSubmitActionListener() {
        EffectHistory effectHistory = EditorState.getInstance().getEffectHistory();
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Close slider when submit pressed
                getSliderFrame().dispose();
                double effectAmount = getEffectAmount();
                //Get instance of effect with parameter
                Effect chosenEffect = effect.getEffect(effectAmount);
                //Get the current image in the editor and apply the effect to it
                BufferedImage currentImage = EditorState.getInstance().getImage();
                BufferedImage editedImage = chosenEffect.run(currentImage);
                //Update effect history and in state
                effectHistory.add(chosenEffect, editedImage);
                EditorState.getInstance().setEffectHistory(effectHistory);
                EditorState.getInstance().setImage(editedImage);
                System.out.println("EFFECT HISTORY IN SLIDER " + effectHistory);
                //Update editor with new image
                imageDisplay.updateEditorFromState();
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

    public ActionListener sliderValuesEffect() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame.setDefaultLookAndFeelDecorated(true);
                setupSubmitButton(createSubmitActionListener());
                show();
            }
        };
    }
}
