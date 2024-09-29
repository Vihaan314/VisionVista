package com.visionvista.commands;

import com.visionvista.*;
import com.visionvista.components.ImageTimeline;
import com.visionvista.effects.Effect;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SerializingCommands {
    private final EffectSerializer effectSerializer;
    private final EffectHistorySerializer effectHistorySerializer;

    private final StateBasedUIComponentGroup stateBasedUIComponentGroup;
    private final ImageDisplay imageDisplay;
    private final ImageTimeline imageTimeline;

    public SerializingCommands(StateBasedUIComponentGroup stateBasedUIComponentGroup) {
        this.effectSerializer = new EffectSerializer();
        this.effectHistorySerializer = new EffectHistorySerializer();
        this.stateBasedUIComponentGroup = stateBasedUIComponentGroup;
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
            //Get relevant serialized effects
            effectSerializer.readSerializedEffects();
            ArrayList<Effect> serializedEffects = new ArrayList<>(effectSerializer.getEffectsList().subList(1, effectSerializer.getEffectsList().size()));
            BufferedImage currentImage = EditorState.getInstance().getImage();
            //Apply to current image
            for (Effect effect : serializedEffects) {
                if (effect != null) {
                    currentImage = effect.run(currentImage);
                    EditorState.getInstance().setImage(currentImage);
                    EditorState.getInstance().getEffectHistory().add(effect, currentImage);
                    stateBasedUIComponentGroup.updateAllUIFromState();
                }
            }
            stateBasedUIComponentGroup.updateAllUIFromState();
        };
    }

    public Command createEffectHistorySerializeCommand() {
        return () -> {
            effectHistorySerializer.serializeEffects(imageDisplay.getFileNameDetails()[0]);
        };
    }

    public Command createEffectHistoryLoadCommand() {
        return () -> {
            //Extract initial image and effects list
            effectHistorySerializer.readSerializedEffects();
            BufferedImage initialImage = effectHistorySerializer.getInitialImage();
            ArrayList<Effect> effectsList = effectHistorySerializer.getEffectsList();
            //Create Effect History
            EffectHistory serializedEffectHistory = new EffectHistory();
            serializedEffectHistory.setEffectSequence(effectsList, initialImage);
            //Update editor with created effect history
            EditorState.getInstance().setState(serializedEffectHistory);
            EditorState.getInstance().setImage(serializedEffectHistory.getCurrentImage());
            imageDisplay.updateFromState();
        };
    }
}
