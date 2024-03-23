package com.visionvista.commands;

import com.visionvista.*;

public class EffectHistoryCommands {
    private final EffectHistory effectHistory;
    private final StateBasedUIComponentGroup stateBasedUIComponentGroup;

    public EffectHistoryCommands(StateBasedUIComponentGroup stateBasedUIComponentGroup) {
        this.stateBasedUIComponentGroup = stateBasedUIComponentGroup;
        this.effectHistory = EditorState.getInstance().getEffectHistory();
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
        EditorState.getInstance().setState(effectHistory);
        stateBasedUIComponentGroup.updateAllUIFromState();
    }
}
