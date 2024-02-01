package com.visionvista;

import com.visionvista.commands.*;
import com.visionvista.components.ColorEffectWindow;
import com.visionvista.components.EffectTextBox;
import com.visionvista.components.InputEffectWindow;
import com.visionvista.components.SliderEffectWindow;
import com.visionvista.effects.Effect;
import com.visionvista.effects.EffectType;
import com.visionvista.effects.EffectUIType;
import com.visionvista.utils.Helper;
import com.visionvista.utils.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuPanel {
    private JMenuBar menuBar;
    private Map<String, JMenuItem> menuItems = new HashMap<String, JMenuItem>();

    private ImageDisplay imageDisplay;
    private ImageTimeline imageTimeline;

    public MenuPanel(ImageDisplay imageDisplay, ImageTimeline imageTimeline) {
        menuBar = new JMenuBar();
        this.imageDisplay = imageDisplay;
        this.imageTimeline = imageTimeline;
    }

    public void addItemToMenu(String title, String menuItemTitle, Command command) {
        JMenu menu = getMenu(title); //Gets the sub-menu
        //Create sub menu if it doesn't exist
        if (menu == null) {
            menu = new JMenu(title);
            menuBar.add(menu);
        }
        JMenuItem menuItem = new JMenuItem(menuItemTitle);
        menuItem.addActionListener(e -> command.execute());
        menuItems.put(menuItemTitle, menuItem);
        menu.add(menuItem);
    }

    public JMenuItem getMenuItem(String menuItemTitle) {
        return menuItems.get(menuItemTitle);
    }

    //Returns the specific sub-menu of a JMenu
    private JMenu getMenu(String title) {
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            if (menuBar.getMenu(i).getText().equals(title)) {
                return menuBar.getMenu(i);
            }
        }
        return null;
    }

    public JMenuBar getMenuBar() {
        return this.menuBar;
    }

    public void setDefaultMenuItems() {
        FileCommands fileCommands = new FileCommands(imageDisplay);
        addItemToMenu("File", "Open Image", fileCommands.createOpenImageCommand());
        addItemToMenu("File", "Open URL", fileCommands.createOpenImageFromUrlCommand());
        addItemToMenu("File", "New Blank Image", fileCommands.createNewBlankImageCommand());
        addItemToMenu("File", "Save", fileCommands.createSaveImageCommand());
        addItemToMenu("File", "Save with Text", fileCommands.createSaveImageWithTextCommand());

        SerializingCommands serializingCommands = new SerializingCommands(imageDisplay, imageTimeline);

        addItemToMenu("Project", "Save Project", serializingCommands.createEffectHistorySerializeCommand());
        addItemToMenu("Project", "Load Project", serializingCommands.createEffectHistoryLoadCommand());
        addItemToMenu("Project", "Save Effect Sequence", serializingCommands.createEffectSerializeCommand());
        addItemToMenu("Project", "Load Effect Sequence", serializingCommands.createEffectLoadCommand());

        EffectHistoryCommands effectHistoryCommands = new EffectHistoryCommands(imageDisplay, imageTimeline);
        addItemToMenu("Edit", "Timeline", imageTimeline::show);
        addItemToMenu("Edit", "Undo", effectHistoryCommands.createUndoCommand());
        addItemToMenu("Edit", "Redo", effectHistoryCommands.createRedoCommand());
        addItemToMenu("Edit", "Reset", effectHistoryCommands.createResetCommand());

        MiscCommands miscCommands = new MiscCommands(imageDisplay, imageTimeline);
        EffectControls effectControls = new EffectControls();
        addItemToMenu("Image", "Effect Controls", effectControls::show);

        addItemToMenu("Apply", "Random effect", miscCommands.createRandomEffectCommand());
    }

    public void setupSliderMenuItems() {
        //Adding all effects that use slider UI to menu panel
        //Loop through all slider effects
        ArrayList<EffectType> sliderEffects = EffectType.getEffectTypeFromComponent(EffectUIType.SLIDER);
        for (EffectType effect : sliderEffects) {
            //Get bounds associated with each effect
            Pair<Integer, Integer> sliderEffectBounds = effect.getSliderBounds();
            int lower = sliderEffectBounds.getLeft();
            int upper = sliderEffectBounds.getRight();
            SliderEffectWindow sliderEffectWindow = new SliderEffectWindow(effect, lower, upper, imageDisplay, imageTimeline); //Slider component with the extracted bounds
            //Get category label for effect
            String effectCategory = effect.getEffect((lower + upper) / 2.0).getClass().getSuperclass().getSimpleName(); //Get the name of the super class which will be the category for the effect
            Command effectActionListener = sliderEffectWindow.sliderValuesEffect();
            addItemToMenu(effectCategory, effect.toString(), effectActionListener); //Add effect to category in menu with corresponding action listener to apply the effect
        }
    }

    public void setupColorMenuItems() {
        ArrayList<EffectType> colorEffects = EffectType.getEffectTypeFromComponent(EffectUIType.COLOR_CHOOSER);
        for (EffectType effect : colorEffects) {
            ColorEffectWindow colorEffectWindow = new ColorEffectWindow(effect, imageDisplay, imageTimeline);
            String effectCategory = effect.getEffect(new Color(0, 0, 0)).getClass().getSuperclass().getSimpleName(); //Get the name of the super class which will be the category for the effect
            Command effectActionListener = colorEffectWindow.colorPickerEffect();
            addItemToMenu(effectCategory, effect.toString(), effectActionListener);
        }
    }

    public void setupTextFieldMenuItems() {
        ArrayList<EffectType> textFieldEffects = EffectType.getEffectTypeFromComponent(EffectUIType.TEXT_FIELD);
        for (EffectType effect : textFieldEffects) {
            String[] effectLabels = effect.getTextFieldParams();
            InputEffectWindow inputEffectWindow = new InputEffectWindow(effect, effectLabels, imageDisplay);
            String effectCategory = effect.getEffect(Helper.createZerosArray(effect.getTextFieldParams().length)).getClass().getSuperclass().getSimpleName(); //Get the name of the super class which will be the category for the effect
            Command effectActionListener = inputEffectWindow.inputValuesEffect();
            addItemToMenu(effectCategory, effect.toString(), effectActionListener);
        }
    }

    public void setupNoParamMenuItems() {
        ArrayList<EffectType> noParamEffects = EffectType.getEffectTypeFromComponent(EffectUIType.NONE);
        for (EffectType effect : noParamEffects) {
            String effectCategory = effect.getEffect().getClass().getSuperclass().getSimpleName(); //Get the name of the super class which will be the category for the effect
            //effectActionLsitener is common put in class
            MiscCommands miscCommands = new MiscCommands(imageDisplay, imageTimeline);
            miscCommands.setEffect(effect.getEffect());
            addItemToMenu(effectCategory, effect.toString(), miscCommands.createUpdateEffectCommand());
        }
    }

    public void setupMenuPanel() {
        setDefaultMenuItems();
        setupSliderMenuItems();
        setupColorMenuItems();
        setupTextFieldMenuItems();
        setupNoParamMenuItems();
    }
}
