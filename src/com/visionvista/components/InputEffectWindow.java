package com.visionvista.components;

import com.visionvista.EditorState;
import com.visionvista.ImageDisplay;
import com.visionvista.commands.Command;
import com.visionvista.effects.Effect;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.MiscHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class InputEffectWindow {
    private JFrame inputFrame;
    private JButton submitButton;
    private EffectType effect;
    private JPanel inputPanel;
    private String[] paramLabels;
    private ArrayList<PlaceholderTextField> textFields;
    private ArrayList<JLabel> fieldLabels;
    private int labelLength;

    private ImageDisplay imageDisplay;

    public InputEffectWindow(EffectType effect, String[] paramLabels, ImageDisplay imageDisplay) {
        this.effect = effect;
        this.inputFrame = setupInputFrame();
        this.paramLabels = paramLabels;
        this.labelLength = paramLabels.length;
        this.inputPanel = new JPanel(new GridLayout(this.labelLength+1, this.labelLength));
        this.textFields = new ArrayList<>(this.labelLength);
        this.fieldLabels = new ArrayList<>(this.labelLength);
        this.imageDisplay = imageDisplay;
        this.submitButton = new JButton("Enter");
        setupTextFields();
    }

    public String[] getValues() {
        String[] values = new String[labelLength];
        if (effect == EffectType.RESIZE) {
            String targetWidth = (textFields.get(0).getText());
            String targetHeight = (textFields.get(1).getText());
            values[0] = targetWidth;
            values[1] = targetHeight;
        }
        return values;
    }

    public JFrame getInputFrame() {
        return this.inputFrame;
    }

    public void setupTextFields() {
        //Create text field labels
        for (int i = 0; i < labelLength; i++) {
            fieldLabels.add(new JLabel(paramLabels[i]));
            PlaceholderTextField tempField = new PlaceholderTextField("");
            tempField.setPreferredSize(new Dimension(370, 30));
            tempField.setPlaceholder(paramLabels[i]);

            textFields.add(tempField);
        }
        for (int i = 0; i < labelLength; i++ ) {
            inputPanel.add(fieldLabels.get(i));
            inputPanel.add(textFields.get(i));
        }
        textFields = addFieldListeners(textFields);
    }
    public ArrayList<PlaceholderTextField> addFieldListeners(ArrayList<PlaceholderTextField> inputFields) {
        BufferedImage image = EditorState.getInstance().getImage();
        PlaceholderTextField widthField = inputFields.get(0);
        PlaceholderTextField heightField = inputFields.get(1);

        MiscHelper.addProportionalChangeListener(image, widthField, heightField, true);
        MiscHelper.addProportionalChangeListener(image, heightField, widthField, false);

        return inputFields;
    }

    private JFrame setupInputFrame() {
        JFrame frame = new JFrame("Input for effects");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(650, 400);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    public void setupSubmitButton(ActionListener actionListener) {
        submitButton.addActionListener(actionListener);
    }

    public ActionListener createSubmitActionListener() {
        return e -> {
            //Extract input values
            Effect chosenEffect = effect.getEffect(getValues());
            //Run effect
            BufferedImage currentImage = EditorState.getInstance().getImage();
            BufferedImage editedImage = chosenEffect.run(currentImage);
            getInputFrame().dispose();
            //Update editor state
            EditorState.getInstance().getEffectHistory().add(chosenEffect, editedImage);
            EditorState.getInstance().setImage(editedImage);
            imageDisplay.updateFromState();
        };
    }

    public Command inputValuesEffect() {
        return () -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            setupSubmitButton(createSubmitActionListener());
            show();
        };
    }

    public void show() {
        inputPanel.add(submitButton);
        inputFrame.add(inputPanel);

        inputFrame.pack();
        inputFrame.setVisible(true);
    }
}
