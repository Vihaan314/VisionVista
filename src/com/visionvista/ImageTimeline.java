package com.visionvista;

import com.visionvista.effects.Effect;
import com.visionvista.utils.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class ImageTimeline {
    private Dimension screenSize;
    private int screenWidth;
    private int screenHeight;

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
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();

        this.imageDisplay = imageDisplay;
        this.effectHistory = EditorState.getInstance().getEffectHistory();
        this.effectSequence = effectHistory.getEffectSequence();
        this.effectLength = effectHistory.getSize();
        this.currentImageIndex = effectHistory.getCurrentIndex();

        this.timelinePanel = new JPanel();
        this.timelinePanel.setLayout(new BoxLayout(this.timelinePanel, BoxLayout.Y_AXIS));

        this.timelineFrame = setupTimelineFrame();

        this.effectButtons = new ArrayList<>();
        this.effectLabels = new ArrayList<>();
        setupEffectComponents();
    }

    private int calculatePanelHeight() {
        // Height for one row (button and label with vertical gap)
        int rowHeight = 35; // Adjust as needed
        // Maximum height for 7 rows
        int maxHeight = 7 * rowHeight;
        // Actual height needed for current number of effects
        int currentHeight = effectLength * rowHeight;

        // If current height exceeds maxHeight, return maxHeight, else return current height
        return Math.min(maxHeight, currentHeight);
    }

    private JFrame setupTimelineFrame() {
        JFrame frame = new JFrame("Effect Timeline");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, calculatePanelHeight());

        JScrollPane scrollPane = new JScrollPane(timelinePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(450, calculatePanelHeight()));
        frame.add(scrollPane);
        System.out.println(screenWidth + " " + screenHeight);
        frame.setLocation(1225, 50);

        frame.pack(); // Adjust the frame size based on content

        return frame;
    }

    private void setupEffectComponents() {
        timelinePanel.removeAll();

        int verticalGap = 2; // Vertical gap between rows

        for (int i = effectLength - 1; i >= 0; i--) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, verticalGap));
            JButton effectButton = new JButton("Effect " + (i + 1));
            String label = (i == 0) ? "Original image" : effectSequence.get(i).getLeft().toString();
            JLabel effectLabel = new JLabel((i == currentImageIndex) ? label + " - CURRENT" : label);

            int finalI = i;
            effectButton.addActionListener(e -> {
                effectHistory.setCurrentImageIndex(finalI);
                imageDisplay.updateImageFromState();
                refreshTimeline();
            });

            rowPanel.add(effectButton);
            rowPanel.add(effectLabel);

            timelinePanel.add(rowPanel);
            effectButtons.add(effectButton);
            effectLabels.add(effectLabel);
        }
    }


    public void refreshTimeline() {
        this.effectHistory = EditorState.getInstance().getEffectHistory();
        this.effectSequence = effectHistory.getEffectSequence();
        this.effectLength = effectHistory.getSize();
        this.currentImageIndex = effectHistory.getCurrentIndex();
        setupEffectComponents();

        timelinePanel.revalidate();
        timelinePanel.repaint();


        // Defer frame resizing to ensure it occurs after UI updates
        SwingUtilities.invokeLater(() -> {
            timelineFrame.pack();
            timelineFrame.setSize(450, calculatePanelHeight());
            timelineFrame.revalidate();
        });
    }

    public void show() {
        timelineFrame.pack();
        timelineFrame.setVisible(true);
    }
}
