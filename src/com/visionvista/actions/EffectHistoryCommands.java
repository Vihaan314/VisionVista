package com.visionvista.actions;

import com.visionvista.EditorState;
import com.visionvista.EffectHistory;
import com.visionvista.ImageDisplay;
import com.visionvista.ImageTimeline;
import com.visionvista.actions.Command;

public class EffectHistoryCommands {
    private final EffectHistory effectHistory;
    private final ImageDisplay imageDisplay;
    private final ImageTimeline imageTimeline;

    public EffectHistoryCommands(EffectHistory effectHistory, ImageDisplay imageDisplay, ImageTimeline imageTimeline) {
        this.effectHistory = effectHistory;
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

    public Command createTimelineCommand() {
        return () -> {
            ImageTimeline newImageTimeline = new ImageTimeline(imageDisplay);
            newImageTimeline.show();
        };
    }

    private void updateState() {
        EditorState.getInstance().setEffectHistory(effectHistory);
        imageDisplay.updateImageFromState();
        imageTimeline.refreshTimeline();
    }
}
