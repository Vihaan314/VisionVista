package com.visionvista.components;

import com.visionvista.effects.*;
import com.visionvista.ImageEditor;
import com.visionvista.EffectHistory;

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
    private BufferedImage image;
    private JPanel colorPanel = new JPanel();
    final Color[] chosenColor = {null};

    public JFrame setupColorFrame(EffectType effect) {
        JFrame colorFrame = new JFrame(effect.toString() + " color chooser");
        colorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        colorFrame.setSize(650, 400);
        colorFrame.setLocationRelativeTo(null);
        colorFrame.setVisible(true);

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

        colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                chosenColor[0] = colorChooser.getColor();
                colorLabel.setForeground(chosenColor[0]);
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

    public ColorEffectWindow(BufferedImage image, EffectType effect) {
        this.effect = effect;
        this.image = image;
        this.colorFrame = setupColorFrame(effect);
        this.colorChooser = setupColorChooser();
    }

    public void show() {
        colorPanel.add(submitButton);
        colorPanel.add(colorChooser);
        colorFrame.add(colorPanel);
        colorFrame.getContentPane().add(colorPanel, BorderLayout.CENTER);

        colorFrame.pack();
        colorFrame.setVisible(true);
    }

    public JFrame getSliderFrame() {
        return this.colorFrame;
    }


    public void colorPickerEffect(BufferedImage image, EffectType effect, EffectHistory effectHistory, ImageEditor editor) {
        JFrame.setDefaultLookAndFeelDecorated(true);

        ColorEffectWindow colorWindow = new ColorEffectWindow(image, effect);

        ActionListener submitEffectListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorWindow.getSliderFrame().dispose();
                Color chosenColor = colorWindow.getColor();
                Effect chosenEffect = effect.getEffect(image, chosenColor);
                BufferedImage editedImage = chosenEffect.run();
                effectHistory.updateEffectSequence(chosenEffect);
                editor.updateEditor(editedImage, "New " + effect.toString() + " image", effectHistory);

            }
        };
        colorWindow.setupSubmitButton(submitEffectListener);
        colorWindow.show();
    }
}
