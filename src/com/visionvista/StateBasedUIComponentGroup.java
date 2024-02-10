package com.visionvista;

import java.util.ArrayList;
import java.util.List;

public class StateBasedUIComponentGroup {
    private List<StateBasedUIComponent> stateBasedUIComponents;

    public StateBasedUIComponentGroup(StateBasedUIComponent... stateBasedUIComponents) {
        this.stateBasedUIComponents = new ArrayList<>(List.of(stateBasedUIComponents));
    }

    public StateBasedUIComponent getImageDisplay() {
        for (StateBasedUIComponent s : stateBasedUIComponents) {
            if (s instanceof ImageDisplay) {
                return s;
            }
        }
        return null;
    }

    public StateBasedUIComponent getImageTimeline() {
        for (StateBasedUIComponent s : stateBasedUIComponents) {
            if (s instanceof ImageTimeline) {
                return s;
            }
        }
        return null;
    }

    public void addUIComponent(StateBasedUIComponent uiComponent) {
        stateBasedUIComponents.add(uiComponent);
    }

    public void updateUIFromState() {
        for (StateBasedUIComponent uiComponent: stateBasedUIComponents) {
            uiComponent.updateFromState();
        }
    }
}
