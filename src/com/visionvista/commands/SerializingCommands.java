package com.visionvista.commands;

import com.visionvista.*;
import com.visionvista.effects.Effect;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SerializingCommands {
    private final EffectSerializer effectSerializer;
    private final EffectHistorySerializer effectHistorySerializer;
    private final ImageDisplay imageDisplay;
    private final ImageTimeline imageTimeline;

    public SerializingCommands(StateBasedUIComponentGroup stateBasedUIComponentGroup) {
        this.effectSerializer = new EffectSerializer();
        this.effectHistorySerializer = new EffectHistorySerializer();
        this.imageDisplay = (ImageDisplay) stateBasedUIComponentGroup.getUIComponent(ImageDisplay.class);
        this.imageTimeline = (ImageTimeline) stateBasedUIComponentGroup.getUIComponent(ImageTimeline.class);
    }

    public Command createEffectSerializeCommand() {
        return () -> {
            effectSerializer.serializeEffects(imageDisplay.getFileNameDetails()[0]);
        };
    }

    public Command createEffectLoadCommand() {
        return () -> {
            effectSerializer.readSerializedEffects();
            ArrayList<Effect> serializedEffect = new ArrayList<>(effectSerializer.getEffectsList().subList(1, effectSerializer.getEffectsList().size()));
            BufferedImage currentImage = EditorState.getInstance().getImage();
            for (Effect effect : serializedEffect) {
                if (effect != null) {
                    currentImage = effect.run(currentImage);
                    EditorState.getInstance().setImage(currentImage);
                    EditorState.getInstance().getEffectHistory().add(effect, currentImage);
                    imageDisplay.updateFromState();
                    imageTimeline.updateFromState();
                }
            }
            imageDisplay.updateFromState();
            imageTimeline.updateFromState();
        };
    }

    public Command createEffectHistorySerializeCommand() {
        return () -> {
            effectHistorySerializer.serializeEffects(imageDisplay.getFileNameDetails()[0]);
        };
    }

    public Command createEffectHistoryLoadCommand() {
        return () -> {
            effectHistorySerializer.readSerializedEffects();
            BufferedImage initialImage = effectHistorySerializer.getInitialImage();
            ArrayList<Effect> effectsList = effectHistorySerializer.getEffectsList();
            EffectHistory serializedEffectHistory = new EffectHistory();
            serializedEffectHistory.setEffectSequence(effectsList, initialImage);
            EditorState.getInstance().setState(serializedEffectHistory);
            EditorState.getInstance().setImage(serializedEffectHistory.getCurrentImage());
            imageDisplay.updateFromState();
        };
    }
}
