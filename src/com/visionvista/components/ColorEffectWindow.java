package com.visionvista.components;

import com.visionvista.EditorState;
import com.visionvista.ImageDisplay;
import com.visionvista.StateBasedUIComponentGroup;
import com.visionvista.commands.Command;
import com.visionvista.effects.*;
import com.visionvista.utils.KeyBinder;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class ColorEffectWindow {
    private JFrame colorFrame;
    private JColorChooser colorChooser;
    private JButton submitButton;
    private EffectType effect;
    private JPanel colorPanel = new JPanel();
    final Color[] chosenColor = {null};

    private StateBasedUIComponentGroup stateBasedUIComponentGroup;

    private Command onWindowClose = () -> stateBasedUIComponentGroup.updateAllUIFromState();

    public void setupColorFrame(EffectType effect) {
        colorFrame = new JFrame(effect.toString() + " color chooser");
        colorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        colorFrame.setSize(650, 400);
        colorFrame.setLocationRelativeTo(null);
        colorFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stateBasedUIComponentGroup.updateAllUIFromState();
            }
        });
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

        //No preview panel
        colorChooser.setPreviewPanel(new JPanel());

        colorChooser.setBorder(BorderFactory.createTitledBorder("Choose Label Color"));
        AbstractColorChooserPanel[] panels = colorChooser.getChooserPanels();

        //Only keep the last panel
        for(int i = 0; i < panels.length - 1; i++){
            colorChooser.removeChooserPanel(panels[i]);
        }
        colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //Get color
                chosenColor[0] = colorChooser.getColor();
                colorLabel.setForeground(chosenColor[0]);

                //Create effect
                Effect chosenEffect = effect.getEffect(chosenColor[0]);

                //Update preview
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
        setupColorFrame(effect);
        KeyBinder.addCtrlWCloseKeyBinding(colorFrame, onWindowClose);
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
        return e -> {
            BufferedImage currentImage = EditorState.getInstance().getImage();
            //Get final effect
            Color chosenColor1 = getColor();
            Effect chosenEffect = effect.getEffect(chosenColor1);
            //Apply effect
            BufferedImage finalImage = chosenEffect.run(currentImage);
            //Set new states
            EditorState.getInstance().getEffectHistory().add(chosenEffect, finalImage);
            EditorState.getInstance().setImage(finalImage);
            //Update the display with the final image
            stateBasedUIComponentGroup.updateAllUIFromState();
            //Close slider window when submit pressed
            getColorFrame().dispose();
        };
    }

    public Command colorPickerEffect() {
        return () -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            setupSubmitButton(createSubmitActionListener());
            show();
        };
    }
}
