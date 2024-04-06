package com.visionvista.components;

import com.visionvista.effects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EffectTextBox {
    Effect effect;

    private JFrame effectFrame;
    private JPanel effectPanel;
    private JLabel effectMessage;
    private JButton closeButton;

    public EffectTextBox(Effect effect) {
        this.effect = effect;
        setupEffectBoxFrame();
        setupEffectBoxComponents();
        closeButton.addActionListener(e -> effectFrame.dispose());
    }

    private void setupEffectBoxFrame() {
        effectFrame = new JFrame("Random effect");
        effectFrame.setSize(500, 500);
        effectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void setupEffectBoxComponents() {
        effectPanel = new JPanel(new GridLayout(2, 1));
        effectMessage = new JLabel(effect.toString(), SwingConstants.CENTER);
        effectMessage.setFont(new Font("Aria", Font.BOLD, 17));
        effectPanel.setBackground(Color.cyan);
        effectPanel.setPreferredSize(new Dimension(300, 170));
        closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(100, 50));
        closeButton.setFont(new Font("Aria", Font.BOLD, 22));
        closeButton.setBackground(Color.green);
    }

    public void show() {
        effectPanel.add(effectMessage);
        effectPanel.add(closeButton);
        effectFrame.add(effectPanel);
        effectFrame.pack();
        effectFrame.setVisible(true);
    }
}
