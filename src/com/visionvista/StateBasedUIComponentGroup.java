package com.visionvista;

import java.util.ArrayList;
import java.util.List;

public class StateBasedUIComponentGroup {
    private List<StateBasedUIComponent> stateBasedUIComponents;

    public StateBasedUIComponentGroup(StateBasedUIComponent... stateBasedUIComponents) {
        this.stateBasedUIComponents = new ArrayList<>(List.of(stateBasedUIComponents));
    }

    public StateBasedUIComponent getUIComponent(Class<?> stateUIClass) {
        return stateBasedUIComponents.stream()
                .filter(component -> component
                        .getClass().equals(stateUIClass))
                .toList()
                .get(0);
    }

    public void addUIComponent(StateBasedUIComponent uiComponent) {
        stateBasedUIComponents.add(uiComponent);
    }

    public void updateAllUIFromState() {
        for (StateBasedUIComponent uiComponent: stateBasedUIComponents) {
            uiComponent.updateFromState();
        }
    }
}
