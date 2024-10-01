package com.visionvista.components;

import javax.swing.*;
import java.util.function.Consumer;

public class NumberInputWindow {
    private JFrame frame;
    private JTextField textField;
    private JButton enterButton;
    private String title;
    private Consumer<Integer> onNumberSubmitted;

    public NumberInputWindow(Consumer<Integer> onNumberSubmitted, String title) {
        this.onNumberSubmitted = onNumberSubmitted;
        this.title = title;
    }

    public void initializeUI() {
        frame = new JFrame(title);
        textField = new JTextField(10);
        enterButton = new JButton("Enter");

        enterButton.addActionListener(e -> {
            try {
                int number = Integer.parseInt(textField.getText());
                frame.dispose();
                onNumberSubmitted.accept(number);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
            }
        });

        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(enterButton);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
