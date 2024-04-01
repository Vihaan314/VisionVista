package com.visionvista.commands;

import com.visionvista.EditorState;
import com.visionvista.ImageGenerationAI;
import com.visionvista.ImageStylizeAI;
import com.visionvista.StateBasedUIComponentGroup;
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
            PromptWindow promptWindow = new PromptWindow("Enter style", "Stylize", null);
            promptWindow.show();
            String prompt = promptWindow.getPrompt();
            System.out.println(prompt);
            BufferedImage currentImage = EditorState.getInstance().getImage();
            ImageStylizeAI imageStylizeAI = new ImageStylizeAI();
            imageStylizeAI.setUserPrompt(prompt);
            imageStylizeAI.setCurrentImage(currentImage);
            imageStylizeAI.generateEffectsList();
            ArrayList<Effect> generatedEffectsList = imageStylizeAI.getEffectsList();
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
            PromptWindow promptWindow = new PromptWindow("Enter image description", "Generate", parentFrame);
            promptWindow.show();
            String prompt = promptWindow.getPrompt();
            ImageGenerationAI imageGenerationAI = new ImageGenerationAI();
            imageGenerationAI.setUserPrompt(prompt);
            imageGenerationAI.generateImage();
        };
    }
}
