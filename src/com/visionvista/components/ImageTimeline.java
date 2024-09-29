package com.visionvista.components;

import com.visionvista.EditorState;
import com.visionvista.EffectHistory;
import com.visionvista.ImageDisplay;
import com.visionvista.StateBasedUIComponent;
import com.visionvista.effects.Effect;
import com.visionvista.utils.KeyBinder;
import com.visionvista.utils.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageTimeline implements StateBasedUIComponent {
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
        KeyBinder.addCtrlWCloseKeyBinding(timelineFrame, null);

        this.effectButtons = new ArrayList<>();
        this.effectLabels = new ArrayList<>();
        setupEffectComponents();
    }

    private int calculatePanelHeight() {
        //Height for one row (button and label with vertical gap)
        int rowHeight = 35;
        int maxHeight = 7 * rowHeight;
        int currentHeight = effectLength * rowHeight;
        //Minimum height to display no effects message
        int minHeight = 2 * rowHeight;

        return Math.max(minHeight, Math.min(maxHeight, currentHeight));
    }

    private JFrame setupTimelineFrame() {
        JFrame frame = new JFrame("Effect Timeline");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, calculatePanelHeight() + 50);

        JScrollPane scrollPane = new JScrollPane(timelinePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(450, calculatePanelHeight()));
        frame.add(scrollPane);
        frame.setLocation(1225, 50);
        frame.pack();
        return frame;
    }


    private void setupEffectComponents() {
        timelinePanel.removeAll();
        timelinePanel.setLayout(new BorderLayout());

        //Panel for the effects and labels
        JPanel effectsPanel = new JPanel();
        effectsPanel.setLayout(new BoxLayout(effectsPanel, BoxLayout.Y_AXIS));
        int verticalGap = 2;

        if (effectLength == 1) {
            //No effects have been applied
            JLabel noEffectsLabel = new JLabel("No effects applied yet.");
            noEffectsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            noEffectsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            effectsPanel.add(Box.createVerticalGlue());
            effectsPanel.add(noEffectsLabel);
            effectsPanel.add(Box.createVerticalGlue());
        } else {
            //Get all effects from effect history
            for (int i = effectLength - 1; i >= 0; i--) {
                JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, verticalGap));
                //Label each effect
                JButton effectButton = new JButton("Effect " + (i));
                String label = (i == 0) ? "Original image" : effectSequence.get(i).left().toString();
                JLabel effectLabel = new JLabel(label);
                if (i == currentImageIndex) {
                    effectButton.setBackground(new Color(100, 149, 237));
                    effectLabel = new JLabel(label + " - CURRENT");
                }
                //Navigate to each effect by going back in the effect history
                int finalI = i;
                effectButton.addActionListener(e -> {
                    effectHistory.setCurrentImageIndex(finalI);
                    imageDisplay.updateFromState();
                    updateFromState();
                });

                rowPanel.add(effectButton);
                rowPanel.add(effectLabel);

                effectsPanel.add(rowPanel);
                effectButtons.add(effectButton);
                effectLabels.add(effectLabel);
            }
        }

        //Panel for timeline navigation buttons
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));

        //Up button
        JButton navigateUp = new JButton("↑");
        navigateUp.setFont(new Font("Arial", Font.PLAIN, 18));
        navigateUp.addActionListener(e -> {
            if (currentImageIndex < effectLength - 1) {
                effectHistory.setCurrentImageIndex(currentImageIndex + 1);
                imageDisplay.updateFromState();
                updateFromState();
            }
        });

        //Down button
        JButton navigateDown = new JButton("↓");
        navigateDown.setFont(new Font("Arial", Font.PLAIN, 18));
        navigateDown.addActionListener(e -> {
            if (currentImageIndex > 0) {
                effectHistory.setCurrentImageIndex(currentImageIndex - 1);
                imageDisplay.updateFromState();
                updateFromState();
            }
        });

        navigationPanel.add(navigateUp);
        navigationPanel.add(Box.createVerticalStrut(10));
        navigationPanel.add(navigateDown);

        timelinePanel.add(effectsPanel, BorderLayout.CENTER);
        timelinePanel.add(navigationPanel, BorderLayout.EAST);

        timelinePanel.revalidate();
        timelinePanel.repaint();
    }


    @Override
    public void updateFromState() {
        //Get all the latest values
        this.effectHistory = EditorState.getInstance().getEffectHistory();
        this.effectSequence = effectHistory.getEffectSequence();
        this.effectLength = effectHistory.getSize();
        this.currentImageIndex = effectHistory.getCurrentIndex();

        //Setup components with new values and update changes to component
        setupEffectComponents();

        timelinePanel.revalidate();
        timelinePanel.repaint();

        SwingUtilities.invokeLater(() -> {
            timelineFrame.pack();
            timelineFrame.setSize(450, calculatePanelHeight()+50);
            timelineFrame.revalidate();
        });
    }

    public void show() {
        updateFromState();
        timelineFrame.setVisible(true);
    }
}
