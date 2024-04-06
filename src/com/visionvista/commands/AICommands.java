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
//
//    public void setPrompt(String userPrompt) {
//        this.prompt = userPrompt;
//    }

    public Command createImageStylizeCommand() {
        return () -> {
//            prompt = "Cinematic and lively look";
            //Get prompt from UI
            PromptWindow promptWindow = new PromptWindow("Enter style", "Stylize", null, null);
            promptWindow.show();
            String prompt = promptWindow.getPrompt();
            System.out.println(prompt);
            //Generate AI response
            BufferedImage currentImage = EditorState.getInstance().getImage();
            ImageStylizeAI imageStylizeAI = new ImageStylizeAI();
            imageStylizeAI.setUserPrompt(prompt);
            imageStylizeAI.setCurrentImage(currentImage);
            imageStylizeAI.generateEffectsList();
            ArrayList<Effect> generatedEffectsList = imageStylizeAI.getEffectsList();
            //Apply generated effects
            for (Effect effect : generatedEffectsList) {
                currentImage = effect.run(currentImage);
                EditorState.getInstance().setImage(currentImage);
                EditorState.getInstance().getEffectHistory().add(effect, currentImage);
                stateBasedUIComponentGroup.updateAllUIFromState();
            }
            stateBasedUIComponentGroup.updateAllUIFromState();
        };
    }

    public Command createImageGenerationCommand(JFrame parentFrame) {
        return () -> {
            ModelData modelData = new ModelData();
            PromptWindow promptWindow = new PromptWindow("Enter image description", "Generate", parentFrame, modelData);
            promptWindow.show();
            String prompt = promptWindow.getPrompt();
            ImageGenerationAI.builder()
                    .prompt(prompt)
                    .model(modelData.getChosenModel())
                    .quality(modelData.getChosenQuality())
                    .buildGeneration();
        };
    }
}
