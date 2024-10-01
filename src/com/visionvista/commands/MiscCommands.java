package com.visionvista.commands;

import com.visionvista.ImageDisplay;
import com.visionvista.RandomEffect;
import com.visionvista.StateBasedUIComponentGroup;
import com.visionvista.ToolsPanel;
import com.visionvista.components.EffectTextBox;
import com.visionvista.components.FindEffectDialog;
import com.visionvista.components.NumberInputWindow;
import com.visionvista.effects.Effect;
import com.visionvista.utils.MiscHelper;
import com.visionvista.utils.TaskWithLoadingDialog;
import okhttp3.internal.concurrent.Task;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

//Misc commands is temporary to be sorted commands
public class MiscCommands {
    private final ImageDisplay imageDisplay;
    private final StateBasedUIComponentGroup stateBasedUIComponentGroup;

    private Effect effect;

    public MiscCommands(StateBasedUIComponentGroup stateBasedUIComponentGroup) {
        this.stateBasedUIComponentGroup = stateBasedUIComponentGroup;
        this.imageDisplay = (ImageDisplay) stateBasedUIComponentGroup.getUIComponent(ImageDisplay.class);
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public Command createUpdateEffectCommand() {
        return () -> {
            //Update editor with effect
            Callable<Void> task = () -> {
                imageDisplay.updateImageByEffect(effect);
                ((ToolsPanel) stateBasedUIComponentGroup.getUIComponent(ToolsPanel.class)).setStateBasedUIComponentGroup(stateBasedUIComponentGroup);
                stateBasedUIComponentGroup.updateAllUIFromState();

                return null;
            };
            //Add loading dialog while effect applies to image
            TaskWithLoadingDialog<Void> taskWithLoadingDialog = new TaskWithLoadingDialog<>(null,
                    "Applying " + MiscHelper.formatEffectName(effect.getClass().getSimpleName()) + ". Please wait...", task, null, null);
            taskWithLoadingDialog.execute();
        };
    }

    public Command createRandomEffectCommand() {
        return () -> {
            Callable<EffectTextBox> task = () -> {
                //Get random effect and update editor
                this.effect = new RandomEffect().getRandomEffect();
                imageDisplay.updateImageByEffect(effect);
                stateBasedUIComponentGroup.updateAllUIFromState();
                //Display random effect information
                EffectTextBox randomEffectBox = new EffectTextBox(this.effect);
                return randomEffectBox;
            };
            //Show effects text box if random effect successful
            Consumer<EffectTextBox> onSuccess = EffectTextBox::show;

            TaskWithLoadingDialog<EffectTextBox> taskWithLoadingDialog = new TaskWithLoadingDialog<>(null,
                    "Applying random effect. Please wait...", task, onSuccess, null);
            taskWithLoadingDialog.execute();
        };
    }

    public Command createMultipleRandomEffectsCommand() {
        return () -> {
            //Apply random effects from specified amount
            NumberInputWindow numberInputWindow = new NumberInputWindow(number -> {
                Callable<EffectTextBox> task = () -> {
                    ArrayList<Effect> effects = new ArrayList<>();
                    RandomEffect randomEffectGenerator = new RandomEffect();
                    for (int i = 0; i < number; i++) {
                        //Generate random effect, store in list and update
                        Effect effect = randomEffectGenerator.getRandomEffect();
                        effects.add(effect);
                        imageDisplay.updateImageByEffect(effect);
                    }
                    //Display all random effects
                    stateBasedUIComponentGroup.updateAllUIFromState();
                    EffectTextBox effectTextBox = new EffectTextBox(effects);

                    return effectTextBox;
                };
                Consumer<EffectTextBox> onSuccess = EffectTextBox::show;
                TaskWithLoadingDialog<EffectTextBox> taskWithLoadingDialog = new TaskWithLoadingDialog<>(null,
                        "Applying random effects. Please wait...", task, onSuccess, null);
                taskWithLoadingDialog.execute();
            }, "Enter number of effects");
            numberInputWindow.initializeUI();
        };
    }

    public Command createEffectSearchCommand() {
        return () -> {
            FindEffectDialog findEffectDialog = new FindEffectDialog();
            findEffectDialog.initializeUI();
        };
    }
}
