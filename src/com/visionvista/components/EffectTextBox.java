package com.visionvista.components;

import com.visionvista.effects.Effect;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EffectTextBox {
    private ArrayList<Effect> effects;

    private JFrame effectFrame;
    private JPanel effectPanel;
    private JComponent effectDisplay;
    private JButton closeButton;

    //Single effect
    public EffectTextBox(Effect effect) {
        this.effects = new ArrayList<>();
        this.effects.add(effect);
        initializeUI();
    }

    //Multiple effects
    public EffectTextBox(ArrayList<Effect> effects) {
        this.effects = effects;
        initializeUI();
    }

    private void initializeUI() {
        setupEffectBoxFrame();
        setupEffectBoxComponents();
        closeButton.addActionListener(e -> effectFrame.dispose());
    }

    private void setupEffectBoxFrame() {
        effectFrame = new JFrame("Effects");
        effectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void setupEffectBoxComponents() {
        effectPanel = new JPanel();
        effectPanel.setLayout(new BorderLayout());

        //Single effect was applied
        if (effects.size() == 1) {
            //Create JLabel to display
            effectDisplay = new JLabel(effects.get(0).toString(), SwingConstants.CENTER);
            effectDisplay.setPreferredSize(new Dimension(400, 100));
            effectDisplay.setBackground(Color.cyan);
        } else {
            //Multiple effects so JList
            effectDisplay = new JList<>(effects.toArray(new Effect[0]));
            effectDisplay.setBackground(Color.cyan);
        }
        effectDisplay.setFont(new Font("Arial", Font.BOLD, 16));
        effectDisplay.setOpaque(true);

        closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(100, 50));
        closeButton.setFont(new Font("Arial", Font.BOLD, 22));
        closeButton.setBackground(Color.green);

        effectPanel.add(effectDisplay, BorderLayout.CENTER);
        effectPanel.add(closeButton, BorderLayout.SOUTH);

        effectFrame.add(effectPanel);
        effectFrame.pack();
        effectFrame.setLocationRelativeTo(null);
    }

    public void show() {
        effectFrame.add(effectPanel);
        effectFrame.pack();
        effectFrame.setLocationRelativeTo(null);
        effectFrame.setVisible(true);
    }
}
