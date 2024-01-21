package com.visionvista;

import com.visionvista.effects.Effect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class ImageTimeline {
    private Main mainReference;
    private ArrayList<Pair<Effect, BufferedImage>> effect_sequence;
    private int currentImage;
    private JFrame timelineFrame;
    private JPanel timelinePanel;
    private int effectLength;
    private ArrayList<JButton> effectButtons;
    private ArrayList<JLabel> effectLabels;

    public ImageTimeline(EffectHistory effectHistory, Main mainReference) {
        this.effect_sequence = effectHistory.getEffectSequence();
        this.effectLength = effectHistory.getSize();
        this.currentImage = effectHistory.getCurrentIndex();
        this.timelinePanel = new JPanel(new GridLayout(this.effectLength, this.effectLength));
        this.timelineFrame = setupTimelineFrame();
        this.mainReference = mainReference;
        this.effectButtons = new ArrayList<>(effectLength);
        this.effectLabels = new ArrayList<>(effectLength);
        setupEffectComponents();
    }

    public JFrame setupTimelineFrame() {
        JFrame frame = new JFrame("Effect Timeline");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(650, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    };

    public void refreshTimeline() {
        if (timelineFrame.isVisible()) {
            timelineFrame.dispose();
            mainReference.showTimeline(mainReference.effectHistory);
        }
    }

    public void setupEffectComponents() {
        //Loop through all effect history
        for (int i = 0; i < effectLength; i++) {
            //Add button for each effect along with label
            JButton effectButton = new JButton("Effect " + (i+1));
            String label = effect_sequence.get(i).toString() + " ";
            JLabel effectLabel = new JLabel((i == currentImage) ? label + "- CURRENT " : label);

            int finalI = i;
            effectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainReference.effectHistory.setCurrentImageIndex(finalI);
                    mainReference.editor.updateImage(effect_sequence.get(finalI).getRight(), "Effect");
                    refreshTimeline();
                }
            });

            effectButtons.add(effectButton);
            effectLabels.add(effectLabel);
        }
        //So that effects are displayed from top to bottom in first to last applied
        Collections.reverse(effectButtons);
        Collections.reverse(effectLabels);
        for (int i = 0; i < effectLength; i++) {
            timelinePanel.add(effectButtons.get(i));
            timelinePanel.add(effectLabels.get(i));
        }
    }

    public void dispose() {
        timelineFrame.dispose();
    }

    public boolean isWindowVisible() {
        return timelineFrame.isVisible();
    }

    public void setVis(boolean vis) {
        timelineFrame.setVisible(vis);
    }

    public void show() {
        timelineFrame.add(timelinePanel);
        timelineFrame.pack();
        timelineFrame.setVisible(true);
    }
}
