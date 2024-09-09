package com.visionvista.commands;

import com.visionvista.EditorState;
import com.visionvista.ImageGenerationAI;
import com.visionvista.ImageStylizeAI;
import com.visionvista.StateBasedUIComponentGroup;
import com.visionvista.components.PromptWindow;
import com.visionvista.effects.Effect;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;

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
            System.out.println(System.getProperty("OPENAI-GPT4-KEY"));
            String prompt = promptWindow.getPrompt();
            if (prompt != null) {
                //Create Stylize AI
                ImageStylizeAI imageStylizeAI = new ImageStylizeAI();
                imageStylizeAI.setUserPrompt(prompt);
                //Generate AI response and convert effects to a list
                imageStylizeAI.generateEffectsList();
                List<Effect> generatedEffectsList = imageStylizeAI.getEffectsList();
                //Keep track of the current image
                BufferedImage currentImage = EditorState.getInstance().getImage();
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
            }
        };
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
