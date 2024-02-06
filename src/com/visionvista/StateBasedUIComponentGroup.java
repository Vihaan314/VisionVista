package com.visionvista;

import java.util.ArrayList;
import java.util.List;

public class StateBasedUIComponentGroup {
    private static StateBasedUIComponentGroup INSTANCE;
    private List<StateBasedUIComponent> stateBasedUIComponents = new ArrayList<>();

    private StateBasedUIComponentGroup() {
    }

    public static StateBasedUIComponentGroup getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new StateBasedUIComponentGroup();
        }
        return INSTANCE;
    }

    public StateBasedUIComponent getUIComponent(StateBasedUIComponent stateBasedUIComponent) {
        for (StateBasedUIComponent s : stateBasedUIComponents) {
            if (s instanceof ImageDisplay) {
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
