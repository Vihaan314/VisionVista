package com.visionvista.components;

import com.visionvista.ModelData;

import javax.swing.*;

public class PromptWindow {
    private String description;
    private String buttonText;
    private JFrame parentFrame;
    private ModelData modelData;

    private String prompt;

    private JDialog promptDialog;

    public PromptWindow(String description, String buttonText, JFrame parentFrame, ModelData modelData) {
        this.description = description;
        this.buttonText = buttonText;
        this.parentFrame = parentFrame;
        this.modelData = modelData;
        createPromptWindow();
    }

    public void createPromptWindow() {
        if (modelData != null) {
            //TODO Model choosing
        }
        promptDialog = new JDialog(parentFrame, "Generate Image", true);
        promptDialog.setSize(400, 200);
        promptDialog.setLayout(new BoxLayout(promptDialog.getContentPane(), BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(description);
        JTextField promptField = new JTextField();
        JButton generateButton = new JButton(buttonText);

        generateButton.addActionListener(e -> {
            prompt = promptField.getText();
            promptDialog.dispose();
        });

        promptDialog.add(titleLabel);
        promptDialog.add(promptField);
        promptDialog.add(generateButton);

        promptDialog.setLocationRelativeTo(parentFrame);
    }

    public String getPrompt() {
        return this.prompt;
    }

    public void show() {
        promptDialog.setVisible(true);
    }
}
