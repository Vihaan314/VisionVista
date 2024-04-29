package com.visionvista.commands;

import com.visionvista.*;
import com.visionvista.components.PromptWindow;
import com.visionvista.effects.Effect;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AICommands {
    private StateBasedUIComponentGroup stateBasedUIComponentGroup;

    public void setStateBasedUIComponentGroup(StateBasedUIComponentGroup stateBasedUIComponentGroup) {
        this.stateBasedUIComponentGroup = stateBasedUIComponentGroup;
    }

    public Command createImageStylizeCommand() {
        return () -> {
            //Get prompt from UI
            PromptWindow promptWindow = new PromptWindow("Enter style", "Stylize", null);
            promptWindow.setModelDisplay(false);
            promptWindow.createPromptWindow();
            promptWindow.show();
            String prompt = promptWindow.getPrompt();
            //Generate AI response
            BufferedImage currentImage = EditorState.getInstance().getImage();
            //Create Stylize AI
            ImageStylizeAI imageStylizeAI = new ImageStylizeAI();
            imageStylizeAI.setUserPrompt(prompt);
            imageStylizeAI.generateEffectsList();
            ArrayList<Effect> generatedEffectsList = imageStylizeAI.getEffectsList();
            //Apply generated effects
            for (Effect effect : generatedEffectsList) {
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

    public Command createImageGenerationCommand(JFrame parentFrame) {
        return () -> {
            PromptWindow promptWindow = new PromptWindow("Enter image description", "Generate", parentFrame);
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
