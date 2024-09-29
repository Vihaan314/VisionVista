package com.visionvista.commands;

import com.visionvista.EditorState;
import com.visionvista.ImageGenerationAI;
import com.visionvista.ImageStylizeAI;
import com.visionvista.StateBasedUIComponentGroup;
import com.visionvista.components.PromptWindow;
import com.visionvista.effects.Effect;
import com.visionvista.utils.TaskWithLoadingDialog;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class AICommands {
    private StateBasedUIComponentGroup stateBasedUIComponentGroup;

    public void setStateBasedUIComponentGroup(StateBasedUIComponentGroup stateBasedUIComponentGroup) {
        this.stateBasedUIComponentGroup = stateBasedUIComponentGroup;
    }

    public Command createImageStylizeCommand() {
        return () -> {
            //Get prompt from UI
            PromptWindow promptWindow = new PromptWindow("Enter style", "Stylize", null);
            promptWindow.setApiKeyDisplay(true);
            promptWindow.createPromptWindow();
            promptWindow.show();
            String prompt = promptWindow.getPrompt();
            if (prompt != null) {
                TaskWithLoadingDialog<List<Effect>> taskWithLoadingDialog = getTasksForAIStylizeLoading(prompt);
                taskWithLoadingDialog.execute();
            }
        };
    }

    @NotNull
    private TaskWithLoadingDialog<List<Effect>> getTasksForAIStylizeLoading(String prompt) {
        //Create the task to perform in the background - the API call, which consumes time
        Callable<List<Effect>> task = () -> {
            ImageStylizeAI imageStylizeAI = new ImageStylizeAI();
            imageStylizeAI.setUserPrompt(prompt);
            //Generate AI response and convert effects to a list
            imageStylizeAI.generateEffectsList();
            return imageStylizeAI.getEffectsList();
        };
        //If the generated effects list is successfully created
        Consumer<List<Effect>> onSuccess = (generatedEffectsList) -> {
            if (generatedEffectsList != null && !generatedEffectsList.isEmpty()) {
                //Keep track of the current image
                BufferedImage currentImage = EditorState.getInstance().getImage();
                //Apply generated effects
                for (Effect effect : generatedEffectsList) {
                    if (effect != null) {
                        currentImage = effect.run(currentImage);
                        EditorState.getInstance().setImage(currentImage);
                        EditorState.getInstance().getEffectHistory().add(effect, currentImage);
                    }
                }
                stateBasedUIComponentGroup.updateAllUIFromState();
            } else {
                JOptionPane.showMessageDialog(null, "No effects were generated.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        };
        //If an error with the background task occured
        Consumer<Exception> onError = (e) -> {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        };

        //Create the task with the loading dialog
        TaskWithLoadingDialog<List<Effect>> taskWithLoadingDialog = new TaskWithLoadingDialog<>(
                null, "Applying AI Stylization. Please wait...", task, onSuccess, onError);
        return taskWithLoadingDialog;
    }

    public Command createImageGenerationCommand(JFrame parentFrame) {
        return () -> {
            PromptWindow promptWindow = new PromptWindow("Enter image description", "Generate", parentFrame);
            promptWindow.setModelDisplay(true);
            promptWindow.createPromptWindow();
            promptWindow.show();

            String prompt = promptWindow.getPrompt();
            String model = promptWindow.getSelectedModel();
            String quality = promptWindow.getSelectedQuality();

            ImageGenerationAI.builder()
                    .prompt(prompt)
                    .model(model)
                    .quality(quality)
                    .buildGeneration();
        };
    }
}
