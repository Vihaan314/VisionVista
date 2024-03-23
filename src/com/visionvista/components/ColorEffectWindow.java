package com.visionvista.components;

import com.visionvista.EditorState;
import com.visionvista.ImageDisplay;
import com.visionvista.ImageTimeline;
import com.visionvista.StateBasedUIComponentGroup;
import com.visionvista.commands.Command;
import com.visionvista.effects.*;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ColorEffectWindow {
    private JFrame colorFrame;
    private JColorChooser colorChooser;
    private JButton submitButton;
    private EffectType effect;
    private JPanel colorPanel = new JPanel();
    final Color[] chosenColor = {null};

    private final StateBasedUIComponentGroup stateBasedUIComponentGroup;

    public JFrame setupColorFrame(EffectType effect) {
        JFrame colorFrame = new JFrame(effect.toString() + " color chooser");
        colorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        colorFrame.setSize(650, 400);
        colorFrame.setLocationRelativeTo(null);
//        colorFrame.setVisible(false);
        return colorFrame;
    }

    public JPanel getColorPanel() {
        return this.colorPanel;
    }

    public Color getColor() {
        return this.chosenColor[0];
    }

    public JColorChooser setupColorChooser() {
        final JLabel colorLabel = new JLabel("Color chooser");
        final JColorChooser colorChooser = new JColorChooser();

        //no rpeviow panhel
        colorChooser.setPreviewPanel(new JPanel());

        colorChooser.setBorder(BorderFactory.createTitledBorder("Choose Label Color"));
        AbstractColorChooserPanel[] panels = colorChooser.getChooserPanels();

        //only keep last panl
        for(int i = 0; i < panels.length - 1; i++){
            colorChooser.removeChooserPanel(panels[i]);
        }
        colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                chosenColor[0] = colorChooser.getColor();
                colorLabel.setForeground(chosenColor[0]);
                Effect chosenEffect = effect.getEffect(chosenColor[0]);

                BufferedImage currentImage = EditorState.getInstance().getImage();
                BufferedImage editedImage = chosenEffect.run(currentImage);

                ImageDisplay imageDisplay = (ImageDisplay) stateBasedUIComponentGroup.getUIComponent(ImageDisplay.class);
                imageDisplay.displayTemporaryImage(editedImage);
            }
        });

        colorPanel.add(colorLabel);

        return colorChooser;
    }

    public void setupSubmitButton(ActionListener submitEffectListener) {
        JButton submitEffect = new JButton("Enter");
        submitEffect.addActionListener(submitEffectListener);
        this.submitButton = submitEffect;
    }

    public ColorEffectWindow(EffectType effect, StateBasedUIComponentGroup stateBasedUIComponentGroup) {
        this.effect = effect;
        this.colorFrame = setupColorFrame(effect);
        this.colorChooser = setupColorChooser();
        this.stateBasedUIComponentGroup = stateBasedUIComponentGroup;
    }

    public void show() {
        colorPanel.add(submitButton);
        colorPanel.add(colorChooser);
        colorFrame.add(colorPanel);
        colorFrame.getContentPane().add(colorPanel, BorderLayout.CENTER);

        colorFrame.pack();
        colorFrame.setVisible(true);
    }

    public JFrame getColorFrame() {
        return this.colorFrame;
    }

    public ActionListener createSubmitActionListener() {
        ActionListener submitEffectListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage currentImage = EditorState.getInstance().getImage();
                //Get final effect
                Color chosenColor = getColor();
                Effect chosenEffect = effect.getEffect(chosenColor);
                //Apply effect
                BufferedImage finalImage = chosenEffect.run(currentImage);
                //Set new states
                EditorState.getInstance().getEffectHistory().add(chosenEffect, finalImage);
                EditorState.getInstance().setImage(finalImage);
                // Update the display with the final image
                stateBasedUIComponentGroup.updateAllUIFromState();
                // Close slider window when submit pressed
                getColorFrame().dispose();
            }
        };
        return submitEffectListener;
    }

    public Command colorPickerEffect() {
        return () -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            setupSubmitButton(createSubmitActionListener());
            show();
        };
    }
}
