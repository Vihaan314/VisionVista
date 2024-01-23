package com.visionvista;

import com.visionvista.effects.Effect;
import com.visionvista.utils.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class ImageTimeline {
    private ImageDisplay imageDisplay;
    private EffectHistory effectHistory;
    private ArrayList<Pair<Effect, BufferedImage>> effectSequence;
    private int currentImageIndex;
    private JFrame timelineFrame;
    private JPanel timelinePanel;
    private int effectLength;
    private ArrayList<JButton> effectButtons;
    private ArrayList<JLabel> effectLabels;

    public ImageTimeline(ImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
        this.effectHistory = EditorState.getInstance().getEffectHistory();
        this.effectSequence = effectHistory.getEffectSequence();
        this.effectLength = effectHistory.getSize();
        this.currentImageIndex = EditorState.getInstance().getEffectHistory().getCurrentIndex();
        this.timelinePanel = new JPanel(new GridLayout(this.effectLength, this.effectLength));
        this.timelineFrame = setupTimelineFrame();
//        this.mainReference = mainReference;
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

    public void setupEffectComponents() {
        //Loop through all effect history
        System.out.println(effectHistory);
        for (int i = 0; i < effectLength; i++) {
            //Add button for each effect along with label
            JButton effectButton = new JButton("Effect " + (i+1));
            String label = (i == 0) ? "Original image " : effectSequence.get(i).getLeft().toString() + " ";
            JLabel effectLabel = new JLabel((i == currentImageIndex) ? label + "- CURRENT " : label);

            int finalI = i;
            effectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    effectHistory.setCurrentImageIndex(finalI);
                    imageDisplay.updateEditorFromState();
                    refreshTimeline();
                }
            });

            effectButtons.add(effectButton);
            effectLabels.add(effectLabel);
        }
        //So that effects are displayed from top to bottom in last to first applied
        Collections.reverse(effectButtons);
        Collections.reverse(effectLabels);
        for (int i = 0; i < effectLength; i++) {
            timelinePanel.add(effectButtons.get(i));
            timelinePanel.add(effectLabels.get(i));
        }
    }

    public void refreshTimeline() {
        if (timelineFrame.isVisible()) {
            dispose();
            ImageTimeline imageTimeline = new ImageTimeline(imageDisplay);
            imageTimeline.show();
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
