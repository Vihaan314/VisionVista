package com.visionvista.commands;

import com.visionvista.EffectControls;
import com.visionvista.ImageDisplay;
import com.visionvista.ImageTimeline;
import com.visionvista.RandomEffect;
import com.visionvista.components.EffectTextBox;
import com.visionvista.effects.Effect;

//Misc commands is temporary to be sorted commands
public class MiscCommands {
    private final ImageDisplay imageDisplay;
    private final ImageTimeline imageTimeline;
    private final EffectControls effectControls;

    private Effect effect;

    public MiscCommands(ImageDisplay imageDisplay, ImageTimeline imageTimeline, EffectControls effectControls) {
        this.imageDisplay = imageDisplay;
        this.imageTimeline = imageTimeline;
        this.effectControls = effectControls;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public Command createUpdateEffectCommand() {
        return () -> {
            imageDisplay.updateImageByEffect(effect);
            imageTimeline.updateFromState();
//            effectControls.updateFromState();
        };
    }

    public Command createRandomEffectCommand() {
        return () -> {
            this.effect = new RandomEffect().getRandomEffect();
            imageDisplay.updateImageByEffect(effect);
            imageTimeline.updateFromState();
            effectControls.updateFromState();
            EffectTextBox randomEffectBox = new EffectTextBox(this.effect);
            randomEffectBox.show();
        };
    }
}
