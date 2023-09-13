import Effects.Effect;
import Effects.EffectType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class ImageTimeline {
    private Main mainReference;
    private ArrayList<Effect> effect_sequence;
    private int currentImage;
    private JFrame timelineFrame;
    private JPanel timelinePanel;
    private int effectLength;
    private ArrayList<JButton> effectButtons;
    private ArrayList<JLabel> effectLabels;

    public JFrame setupTimelineFrame() {
        JFrame frame = new JFrame("Effect Timeline");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(650, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    };

    public void refreshTimeline(Main mainRef) {
        if (timelineFrame.isVisible()) {
            timelineFrame.dispose();
            mainRef.showTimeline(mainRef.effect_sequence, mainRef.currentImage);
        }
    }

    public void setupEffectComponents() {
        for (int i = 0; i < effectLength; i++) {
            JButton effectButton = new JButton("Effect " + (i+1));
            String label = effect_sequence.get(i).toString() + " ";
            JLabel effectLabel = new JLabel((i == currentImage) ? label + "- CURRENT " : label);

            int finalI = i;
            effectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainReference.currentImage = finalI;
                    mainReference.editor.updateImage(effect_sequence.get(finalI).run(), "Effect");
                    refreshTimeline(mainReference);
                }
            });

            effectButtons.add(effectButton);
            effectLabels.add(effectLabel);
        }
        Collections.reverse(effectButtons);
        Collections.reverse(effectLabels);
        for (int i = 0; i < effectLength; i++) {
            timelinePanel.add(effectButtons.get(i));
            timelinePanel.add(effectLabels.get(i));
        }
    }

    public void dispose() {
        this.timelineFrame.dispose();
    }

    public boolean isWindowVisible() {
        return this.timelineFrame.isVisible();
    }

    public void setVis(boolean vis) {
        this.timelineFrame.setVisible(vis);
    }

    public ImageTimeline(ArrayList<Effect> effect_sequence, int currentImage, Main mainReference) {
        this.effect_sequence = effect_sequence;
        this.effectLength = effect_sequence.size();
        this.currentImage = currentImage;
        this.timelinePanel = new JPanel(new GridLayout(this.effectLength, this.effectLength));
        this.timelineFrame = setupTimelineFrame();
        this.mainReference = mainReference;
        this.effectButtons = new ArrayList<>(effectLength);
        this.effectLabels = new ArrayList<>(effectLength);
        setupEffectComponents();
    }

    public void show() {
        timelineFrame.add(timelinePanel);
        timelineFrame.pack();
        timelineFrame.setVisible(true);
    }
}
