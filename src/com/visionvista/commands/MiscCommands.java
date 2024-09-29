package com.visionvista.commands;

import com.visionvista.*;
import com.visionvista.components.EffectTextBox;
import com.visionvista.components.FindEffectDialog;
import com.visionvista.components.NumberInputWindow;
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
            //Update editor with effect
            imageDisplay.updateImageByEffect(effect);
            ((ToolsPanel) stateBasedUIComponentGroup.getUIComponent(ToolsPanel.class)).setStateBasedUIComponentGroup(stateBasedUIComponentGroup);
            stateBasedUIComponentGroup.updateAllUIFromState();
        };
    }

    public Command createRandomEffectCommand() {
        return () -> {
            //Get random effect and update editor
            this.effect = new RandomEffect().getRandomEffect();
            imageDisplay.updateImageByEffect(effect);
            stateBasedUIComponentGroup.updateAllUIFromState();
            //Display random effect information
            EffectTextBox randomEffectBox = new EffectTextBox(this.effect);
            randomEffectBox.show();
        };
    }

    public Command createMultipleRandomEffectsCommand() {
        return () -> {
            //Apply random effects from specified amount
            NumberInputWindow numberInputWindow = new NumberInputWindow(number -> {
                ArrayList<Effect> effects = new ArrayList<>();
                RandomEffect randomEffectGenerator = new RandomEffect();
                for (int i = 0; i < number; i++) {
                    //Generate random effect, store in list and update
                    Effect effect = randomEffectGenerator.getRandomEffect();
                    effects.add(effect);
                    imageDisplay.updateImageByEffect(effect);
                }
                //Display all random effects
                stateBasedUIComponentGroup.updateAllUIFromState();
                EffectTextBox effectTextBox = new EffectTextBox(effects);
                effectTextBox.show();
            });
            numberInputWindow.initializeUI();
        };
    }

    public Command createEffectSearchCommand() {
        return () -> {
            FindEffectDialog findEffectDialog = new FindEffectDialog();
            findEffectDialog.initializeUI();
        };
    }
}
