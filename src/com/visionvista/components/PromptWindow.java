package com.visionvista.components;

import javax.swing.*;

public class PromptWindow {
    private String description;
    private String buttonText;
    private JFrame parentFrame;

    private String prompt;
    private String selectedModel;
    private String selectedQuality;

    private String apiKey;

    private JDialog promptDialog;

    private static final String[] models = {"dall-e-3", "dall-e-2"};
    private static final String[] qualities = {"hd", "medium", "low"};

    private boolean modelDisplay = false;
    private boolean apiKeyDisplay = false;

    public PromptWindow(String description, String buttonText, JFrame parentFrame) {
        this.description = description;
        this.buttonText = buttonText;
        this.parentFrame = parentFrame;
    }

    public void setModelDisplay(boolean modelDisplay) {
        this.modelDisplay = modelDisplay;
    }

    public void setApiKeyDisplay(boolean apiKeyDisplay) {
        this.apiKeyDisplay = apiKeyDisplay;
    }

    public void createPromptWindow() {
        //Prompt window
        promptDialog = new JDialog(parentFrame, "Generate Image", true);
        promptDialog.setSize(400, 200);
        promptDialog.setLayout(new BoxLayout(promptDialog.getContentPane(), BoxLayout.Y_AXIS));

        //Components
        JLabel titleLabel = new JLabel(description);
        JTextField promptField = new JTextField();
        JComboBox<String> modelComboBox = new JComboBox<>(models);
        JComboBox<String> qualityComboBox = new JComboBox<>(qualities);

        JLabel apiFieldLabel = new JLabel("Enter your API key");
        JTextField apiKeyField = new JTextField();

        //Give model choosing options if model mode is true
        if (modelDisplay) {
            modelComboBox.setSelectedIndex(0);
            qualityComboBox.setSelectedIndex(0);
        }

        JButton generateButton = new JButton(buttonText);

        generateButton.addActionListener(e -> {
            prompt = promptField.getText();
            selectedModel = (String) modelComboBox.getSelectedItem();
            selectedQuality = (String) qualityComboBox.getSelectedItem();

            if (apiKeyDisplay) {
                apiKey = apiKeyField.getText();
                System.setProperty("OPENAI-GPT4-KEY", apiKey);
            }
            promptDialog.dispose();
        });

        if (modelDisplay) {
            promptDialog.add(modelComboBox);
            promptDialog.add(qualityComboBox);
        }
        if (apiKeyDisplay & System.getProperty("OPENAI-GPT4-KEY") == null) {
            promptDialog.add(apiFieldLabel);
            promptDialog.add(apiKeyField);
        }

        promptDialog.add(titleLabel);
        promptDialog.add(promptField);

        promptDialog.add(generateButton);

        promptDialog.setLocationRelativeTo(parentFrame);
    }

    public String getPrompt() {
        return prompt;
    }

    public String getSelectedModel() {
        return selectedModel;
    }

    public String getSelectedQuality() {
        return selectedQuality;
    }

    public void show() {
        promptDialog.setVisible(true);
    }
}
