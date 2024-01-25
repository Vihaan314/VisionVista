package com.visionvista.components;

import com.visionvista.EditorState;
import com.visionvista.ImageDisplay;
import com.visionvista.effects.*;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

    private ImageDisplay imageDisplay;

    public JFrame setupColorFrame(EffectType effect) {
        JFrame colorFrame = new JFrame(effect.toString() + " color chooser");
        colorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        colorFrame.setSize(650, 400);
        colorFrame.setLocationRelativeTo(null);
//        colorFrame.setVisible(false);
        return colorFrame;
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
        BufferedImage currentImage;
        colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                chosenColor[0] = colorChooser.getColor();
                colorLabel.setForeground(chosenColor[0]);

                Effect chosenEffect = effect.getEffect(chosenColor[0]);

                BufferedImage currentImage = EditorState.getInstance().getImage();
                BufferedImage editedImage = chosenEffect.run(currentImage);
//                EditorState.getInstance().getEffectHistory().add(effect, editedImage);
                EditorState.getInstance().setImage(editedImage);
                imageDisplay.updateImageFromState();
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

    public ColorEffectWindow(EffectType effect, ImageDisplay imageDisplay) {
        this.effect = effect;
        this.colorFrame = setupColorFrame(effect);
        this.colorChooser = setupColorChooser();
        this.imageDisplay = imageDisplay;
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
                //Dispose of color picker window
                getColorFrame().dispose();
                //Get chosenc color and create effect
                Color chosenColor = getColor();
                Effect chosenEffect = effect.getEffect(chosenColor);
//                BufferedImage currentImage = EditorState.getInstance().getImage();
//                BufferedImage editedImage = chosenEffect.run(currentImage);
                BufferedImage editedImage = EditorState.getInstance().getImage();
                EditorState.getInstance().getEffectHistory().add(chosenEffect, editedImage);
//                EditorState.getInstance().setImage(editedImage);
//                imageDisplay.updateImageFromState();

            }
        };
        return submitEffectListener;
    }

    public ActionListener colorPickerEffect() {
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
