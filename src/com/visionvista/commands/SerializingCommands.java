package com.visionvista.commands;

import com.visionvista.*;
import com.visionvista.effects.Effect;
import com.visionvista.utils.FileHelper;
import com.visionvista.utils.TaskWithLoadingDialog;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class SerializingCommands {
    private final EffectSerializer effectSerializer;
    private final EffectHistorySerializer effectHistorySerializer;

    private final StateBasedUIComponentGroup stateBasedUIComponentGroup;
    private final ImageDisplay imageDisplay;

    public SerializingCommands(StateBasedUIComponentGroup stateBasedUIComponentGroup) {
        this.effectSerializer = new EffectSerializer();
        this.effectHistorySerializer = new EffectHistorySerializer();
        this.stateBasedUIComponentGroup = stateBasedUIComponentGroup;
        this.imageDisplay = (ImageDisplay) stateBasedUIComponentGroup.getUIComponent(ImageDisplay.class);
    }

    public Command createEffectSerializeCommand() {
        return () -> {
            String defaultFileName = imageDisplay.getFileNameDetails()[0];
            String directory = FileHelper.chooseDirectory();
            if (directory != null) {
                File editedFile = FileHelper.getEditedFile(directory, defaultFileName, "dat", "_effects-sequence");
                String fullFilePath = editedFile.getAbsolutePath();
                effectSerializer.serializeEffects(fullFilePath);
            }
        };
    }

    public Command createEffectLoadCommand() {
        return () -> {
            //Get relevant serialized effects
            effectSerializer.readSerializedEffects();
            ArrayList<Effect> serializedEffects = new ArrayList<>(effectSerializer.getEffectsList().subList(1, effectSerializer.getEffectsList().size()));
            String effectSequenceName = effectSerializer.getEffectSequenceName();

            //The task - load and apply serialized effects
            Callable<Void> task = () -> {
                BufferedImage currentImage = EditorState.getInstance().getImage();
                //Apply to current image and update history
                for (Effect effect : serializedEffects) {
                    if (effect != null) {
                        currentImage = effect.run(currentImage);
                        EditorState.getInstance().setImage(currentImage);
                        EditorState.getInstance().getEffectHistory().add(effect, currentImage);
                    }
                }
                stateBasedUIComponentGroup.updateAllUIFromState();
                return null;
            };

            //If an error occurs with the serialized effects
            Consumer<Exception> onError = (ex) -> {
                JOptionPane.showMessageDialog(null, "An error occurred while loading and applying effects: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            };
            //Create the loading task
            TaskWithLoadingDialog<Void> taskWithLoadingDialog = new TaskWithLoadingDialog<>(null,
                    "Loading " + effectSequenceName + "Please wait...",
                    task,
                    null,
                    onError
            );
            taskWithLoadingDialog.execute();
        };
    }

    public Command createEffectHistorySerializeCommand() {
        return () -> {
            String defaultFileName = imageDisplay.getFileNameDetails()[0];
            String directory = FileHelper.chooseDirectory();
            if (directory != null) {
                File editedFile = FileHelper.getEditedFile(directory, defaultFileName, "dat", "_project-sequence");
                String fullFilePath = editedFile.getAbsolutePath();
                effectHistorySerializer.serializeEffects(fullFilePath);
            }
        };
    }


    public Command createEffectHistoryLoadCommand() {
        return () -> {
            //Extract initial image and effects list
            effectHistorySerializer.readSerializedEffects();
            BufferedImage initialImage = effectHistorySerializer.getInitialImage();
            ArrayList<Effect> effectsList = effectHistorySerializer.getEffectsList();
            String projectName = effectHistorySerializer.getProjectName();

            //The task - load and apply effect history
            Callable<Void> task = () -> {
                //Create Effect History
                EffectHistory serializedEffectHistory = new EffectHistory();
                serializedEffectHistory.setEffectSequence(effectsList, initialImage);
                //Update editor with created effect history
                EditorState.getInstance().setState(serializedEffectHistory);
                EditorState.getInstance().setImage(serializedEffectHistory.getCurrentImage());
                stateBasedUIComponentGroup.updateAllUIFromState();

                return null;
            };

            Consumer<Exception> onError = (ex) -> {
                JOptionPane.showMessageDialog(null, "An error occurred while loading the project: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            };

            TaskWithLoadingDialog<Void> taskWithLoadingDialog = new TaskWithLoadingDialog<>(null, "Loading " + projectName + ". Please wait...",
                    task,
                    null,
                    onError
            );
            taskWithLoadingDialog.execute();
        };
    }
}
