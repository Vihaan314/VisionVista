package com.visionvista.components;

import com.visionvista.commands.AICommands;
import com.visionvista.commands.Command;

import javax.swing.*;

public class PromptWindow {
    String description;
    String buttonText;
    JFrame parentFrame;

    private String prompt;

    private JDialog promptDialog;

    public PromptWindow(String description, String buttonText, JFrame parentFrame) {
        this.description = description;
        this.buttonText = buttonText;
        this.parentFrame = parentFrame;
        createPromptWindow();
    }

    public void createPromptWindow() {
        //TODO choose model
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
