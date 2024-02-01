package com.visionvista.commands;

import com.visionvista.EditorState;
import com.visionvista.EffectHistory;
import com.visionvista.ImageDisplay;
import com.visionvista.ImageTimeline;

public class EffectHistoryCommands {
    private final EffectHistory effectHistory;
    private final ImageDisplay imageDisplay;
    private final ImageTimeline imageTimeline;

    public EffectHistoryCommands(ImageDisplay imageDisplay, ImageTimeline imageTimeline) {
        this.effectHistory = EditorState.getInstance().getEffectHistory();
        this.imageDisplay = imageDisplay;
        this.imageTimeline = imageTimeline;
    }

    public Command createUndoCommand() {
        return () -> {
            if (effectHistory.getCurrentIndex() > 0) {
                effectHistory.updateCurrentImage(-1);
            }
            updateState();
        };
    }

    public Command createRedoCommand() {
        return () -> {
            if (effectHistory.getCurrentIndex() < effectHistory.getSize() - 1) {
                effectHistory.updateCurrentImage(1);
            }
            updateState();
        };
    }

    public Command createResetCommand() {
        return () -> {
            effectHistory.resetHistory();
            updateState();
        };
    }

    private void updateState() {
        EditorState.getInstance().setEffectHistory(effectHistory);
        imageDisplay.updateImageFromState();
        System.out.println("yo");
        imageTimeline.refreshTimeline();
    }
}
