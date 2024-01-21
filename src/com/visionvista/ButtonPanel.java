package com.visionvista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ButtonPanel {
    private JPanel buttonPanel;
    private ArrayList<JButton> panelButtons;

    public ButtonPanel() {
        buttonPanel = new JPanel(); //new GridLayout(4, 9)
        panelButtons = new ArrayList<>();
    }

    public void addButtonToPanel(String title, ActionListener actionListener) {
        JButton newButton = new JButton(title);
        newButton.addActionListener(actionListener);
        this.buttonPanel.add(newButton, BorderLayout.SOUTH);
        this.panelButtons.add(newButton);
    }

    public void updatePanelLayout(int rows, int cols) {
        this.buttonPanel.setLayout(new GridLayout(rows, cols));
    }

    public JPanel getButtonPanel() {
        return this.buttonPanel;
    }

    public ButtonPanel setupButtonPanel(BufferedImage blankImg) {
        return this;
    }
}
