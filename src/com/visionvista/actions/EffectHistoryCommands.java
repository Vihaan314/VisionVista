package com.visionvista.actions;

import com.visionvista.EditorState;
import com.visionvista.EffectHistory;

public class EffectHistoryCommands implements Command{
    private EffectHistory effectHistory;
    public EffectHistoryCommands() {
        this.effectHistory = EditorState.getInstance().getEffectHistory();
    }

    @Override
    public void execute() {
        
    }
}
