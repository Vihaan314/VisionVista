package com.visionvista.commands;

import com.visionvista.ImageDisplay;
import com.visionvista.ImageTimeline;
import com.visionvista.RandomEffect;
import com.visionvista.components.EffectTextBox;
import com.visionvista.effects.Effect;

//Misc commands is temporary to be sorted commands
public class MiscCommands {
    private final ImageDisplay imageDisplay;
    private final ImageTimeline imageTimeline;

    private Effect effect;

    public MiscCommands(ImageDisplay imageDisplay, ImageTimeline imageTimeline) {
        this.imageDisplay = imageDisplay;
        this.imageTimeline = imageTimeline;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public Command createUpdateEffectCommand() {
        return () -> {
            imageDisplay.updateImageByEffect(effect);
            imageTimeline.refreshTimeline();
        };
    }

    public Command createRandomEffectCommand() {
        return () -> {
            this.effect = new RandomEffect().getRandomEffect();
            imageDisplay.updateImageByEffect(effect);
            imageTimeline.refreshTimeline();
            EffectTextBox randomEffectBox = new EffectTextBox(this.effect);
            randomEffectBox.show();
        };
    }
}
