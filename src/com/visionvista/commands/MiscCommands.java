package com.visionvista.commands;

import com.visionvista.*;
import com.visionvista.components.EffectTextBox;
import com.visionvista.effects.Effect;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

//Misc commands is temporary to be sorted commands
public class MiscCommands {
    private final ImageDisplay imageDisplay;
    private final StateBasedUIComponentGroup stateBasedUIComponentGroup;

    private Effect effect;

    public MiscCommands(StateBasedUIComponentGroup stateBasedUIComponentGroup) {
        this.stateBasedUIComponentGroup = stateBasedUIComponentGroup;
        this.imageDisplay = (ImageDisplay) stateBasedUIComponentGroup.getUIComponent(ImageDisplay.class);
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public Command createUpdateEffectCommand() {
        return () -> {
            imageDisplay.updateImageByEffect(effect);
            ((ToolsPanel) stateBasedUIComponentGroup.getUIComponent(ToolsPanel.class)).setStateBasedUIComponentGroup(stateBasedUIComponentGroup);
            stateBasedUIComponentGroup.updateAllUIFromState();
        };
    }

    public Command createRandomEffectCommand() {
        return () -> {
            this.effect = new RandomEffect().getRandomEffect();
            imageDisplay.updateImageByEffect(effect);
            stateBasedUIComponentGroup.updateAllUIFromState();
            EffectTextBox randomEffectBox = new EffectTextBox(this.effect);
            randomEffectBox.show();
        };
    }
}
