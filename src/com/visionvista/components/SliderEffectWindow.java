package com.visionvista.components;

import com.visionvista.EditorState;
import com.visionvista.ImageDisplay;
import com.visionvista.StateBasedUIComponentGroup;
import com.visionvista.ToolsPanel;
import com.visionvista.commands.Command;
import com.visionvista.effects.Effect;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.KeyBinder;
import com.visionvista.utils.TaskWithLoadingDialog;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class SliderEffectWindow {
    private JFrame sliderFrame;
    private JSlider slider;
    private final JButton submitButton= new JButton("Enter");
    private EffectType effect;
    public int lower;
    public int upper;
    private JPanel sliderPanel = new JPanel();
    final int[] effect_amount = {0};

    private int defaultSliderValue = 0;

    private StateBasedUIComponentGroup stateBasedUIComponentGroup;

    private Command onWindowClose = () -> stateBasedUIComponentGroup.updateAllUIFromState();

    public void setupSliderFrame(EffectType effect) {
        this.sliderFrame = new JFrame(effect.toString() + " Slider");

        sliderFrame.setSize(400, 200);

        sliderFrame.setLayout(new BorderLayout());
        sliderFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stateBasedUIComponentGroup.updateAllUIFromState();
            }
        });
    }

    public SliderEffectWindow(EffectType effect, int lower, int upper, StateBasedUIComponentGroup stateBasedUIComponentGroup) {
        this.effect = effect;
        this.lower = lower;
        this.upper = upper;
        setupSliderFrame(effect);
        KeyBinder.addCtrlWCloseKeyBinding(sliderFrame, onWindowClose);
        this.stateBasedUIComponentGroup = stateBasedUIComponentGroup;
    }

    public JPanel getSliderPanel() {
        return this.sliderPanel;
    }

    public void setDefaultSliderValue(Double sliderValue) {
        System.out.println(sliderValue.intValue());
        this.defaultSliderValue = sliderValue.intValue();
    }

    public void setupSlider() {
        JLabel status = new JLabel("Choose " + effect + ": " + defaultSliderValue, JLabel.LEFT);

        slider = new JSlider(lower, upper, defaultSliderValue);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        //Set the tick markings
        int majorTickSpacing = findOptimalSpacing(upper);
        slider.setMajorTickSpacing(majorTickSpacing);

        //Add the numerical values in the tick markings Calculate where to label the slider
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        for (int i = lower; i <= upper; i += majorTickSpacing) {
            labels.put(i, new JLabel(String.valueOf(i)));
        }
        labels.put(upper, new JLabel(String.valueOf(upper)));
        slider.setLabelTable(labels);

        slider.addChangeListener(e -> {
            BufferedImage currentImage = EditorState.getInstance().getImage();
            effect_amount[0] = ((JSlider) e.getSource()).getValue();
            status.setText(effect + ": " + effect_amount[0]);

            //Apply the effect to the original image, not the current state image
            Effect chosenEffect = effect.getEffect((double) effect_amount[0]);
            BufferedImage editedImage = chosenEffect.run(currentImage);

            //Temporarily display the edited image without updating the EditorState
            ImageDisplay imageDisplay = (ImageDisplay) stateBasedUIComponentGroup.getUIComponent(ImageDisplay.class);
            imageDisplay.displayTemporaryImage(editedImage);
        });

        //Plus and minus buttons panel
        JPanel buttonPanel = createParameterAdjustButtons();

        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.addActionListener(e -> {
            sliderFrame.dispose();
            stateBasedUIComponentGroup.updateAllUIFromState();
        });

        //Panel to hold Enter and Cancel buttons
        JPanel enterCancelPanel = new JPanel();
        enterCancelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        enterCancelPanel.add(submitButton);
        enterCancelPanel.add(cancelButton);

        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
        sliderPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        //Add all components to panel in order
        sliderPanel.add(status);
        sliderPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        sliderPanel.add(slider);

        sliderPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sliderPanel.add(buttonPanel);

        sliderPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sliderPanel.add(enterCancelPanel);

        sliderFrame.add(sliderPanel, BorderLayout.CENTER);
    }

    private JPanel createParameterAdjustButtons() {
        JButton minusButton = new JButton("-");
        minusButton.setFont(new Font("Arial", Font.PLAIN, 20));
        minusButton.addActionListener(e -> {
            int currentValue = slider.getValue();
            if (currentValue > lower) {
                slider.setValue(currentValue - 1);
            }
        });

        //Plus button
        JButton plusButton = new JButton("+");
        plusButton.setFont(new Font("Arial", Font.PLAIN, 20));
        plusButton.addActionListener(e -> {
            int currentValue = slider.getValue();
            if (currentValue < upper) {
                slider.setValue(currentValue + 1);
            }
        });

        //Arrange plus and minus buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(minusButton);
        buttonPanel.add(plusButton);
        return buttonPanel;
    }

    //Create the optimal tick markings given the upper bound of the parameter
    private int findOptimalSpacing(int upper) {
        int idealNumberOfTicks = 5 + (int) Math.log10(upper);
        for (int numTicks = idealNumberOfTicks; numTicks > 3; numTicks--) {
            int possibleSpacing = upper / numTicks;
            if (upper % possibleSpacing == 0 || numTicks * possibleSpacing >= upper) {
                return possibleSpacing;
            }
        }
        return upper;
    }

    public double getEffectAmount() {
        return effect_amount[0];
    }

    public JFrame getSliderFrame() {
        return this.sliderFrame;
    }

    public void setupSubmitButton(ActionListener submitEffectListener) {
        submitButton.addActionListener(submitEffectListener);
    }

    @NotNull
    private TaskWithLoadingDialog<BufferedImage> getTasksForLoadingSliderEffect() {
        BufferedImage currentImage = EditorState.getInstance().getImage();
        //Get final effect amount and create effect
        double effectAmount = getEffectAmount();
        Effect chosenEffect = effect.getEffect(effectAmount);

        //The task - apply the effect to the current image
        //Use a callable to run in the background and have its output accessible when it is done with get()
        Callable<BufferedImage> task = () -> chosenEffect.run(currentImage);

        //What to do after the task is completed successfully (the effect is applied) - update the UI and history
        //We can define consumers for custom actions that are executed after a background task is completed
        Consumer<BufferedImage> onSuccess = (finalImage) -> {
            //Set new states
            EditorState.getInstance().getEffectHistory().add(chosenEffect, finalImage);
            EditorState.getInstance().setImage(finalImage);
            //Update the display with the final image
            ((ToolsPanel) stateBasedUIComponentGroup.getUIComponent(ToolsPanel.class)).setStateBasedUIComponentGroup(stateBasedUIComponentGroup);
            stateBasedUIComponentGroup.updateAllUIFromState();
            //Close slider window when submit pressed
            getSliderFrame().dispose();
        };

        //What to do if an error occurs - display it
        Consumer<Exception> onError = (ex) -> {
            JOptionPane.showMessageDialog(getSliderFrame(), "An error occurred while applying the effect: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        };

        //Create and return the task with loading dialog
        TaskWithLoadingDialog<BufferedImage> taskWithLoadingDialog = new TaskWithLoadingDialog<>(
                getSliderFrame(), "Applying effect. Please wait...", task, onSuccess, onError);
        return taskWithLoadingDialog;
    }

    public ActionListener createSubmitActionListener() {
        return e -> {
            double effectAmount = getEffectAmount();
            if (effectAmount != 0) {
                //Execute tasks
                TaskWithLoadingDialog<BufferedImage> taskWithLoadingDialog = getTasksForLoadingSliderEffect();
                taskWithLoadingDialog.execute();
            } else {
                //If effect amount is 0, just close slider window when submit pressed
                getSliderFrame().dispose();
            }
        };
    }


    public Command sliderValuesEffect() {
        //Command to create slider and show
        return () -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            setupSubmitButton(createSubmitActionListener());
            show();
        };
    }

    public void show() {
        sliderFrame.add(sliderPanel);
        sliderFrame.pack();
        sliderFrame.setVisible(true);
    }
}
