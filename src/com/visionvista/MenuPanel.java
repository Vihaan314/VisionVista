package com.visionvista;

import com.visionvista.commands.*;
import com.visionvista.components.ColorEffectWindow;
import com.visionvista.components.InputEffectWindow;
import com.visionvista.components.SliderEffectWindow;
import com.visionvista.effects.EffectType;
import com.visionvista.effects.EffectUIType;
import com.visionvista.utils.MiscHelper;
import com.visionvista.utils.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuPanel {
    private JMenuBar menuBar;
    private Map<String, JMenu> categoryMenus = new HashMap<>();
    private JMenu effectsMenu;

    private ImageDisplay imageDisplay;
    private ImageTimeline imageTimeline;
    private EffectControls effectControls;
    private StateBasedUIComponentGroup stateBasedUIComponentGroup;

    public MenuPanel(StateBasedUIComponentGroup stateBasedUIComponentGroup) {
        menuBar = new JMenuBar();
        this.stateBasedUIComponentGroup = stateBasedUIComponentGroup;
        this.imageDisplay = (ImageDisplay) stateBasedUIComponentGroup.getUIComponent(ImageDisplay.class);
        this.imageTimeline = (ImageTimeline) stateBasedUIComponentGroup.getUIComponent(ImageTimeline.class);
        this.effectControls = (EffectControls) stateBasedUIComponentGroup.getUIComponent(EffectControls.class);
        setDefaultMenuItems();
    }

    public void addItemToMenu(String title, String menuItemTitle, Command command, boolean isEffectMenu) {
        JMenu menu = getMenu(title); //Gets the sub-menu
        //Create sub menu if it doesn't exist
        if (menu == null) {
            menu = new JMenu(title);
            menuBar.add(menu);
        }
        JMenuItem menuItem = new JMenuItem(menuItemTitle);
        menuItem.addActionListener(e -> {
            try {
                command.execute();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        if (isEffectMenu) {
            categoryMenus.put(title, menu);
        }
        menu.add(menuItem);
    }

    public void addItemToMenu(String title, String menuItemTitle, Command command, KeyStroke keyStroke, boolean isEffectMenu) {
        JMenu categoryMenu = getMenu(title); //Gets the sub-menu
        //Create sub menu if it doesn't exist
        if (categoryMenu == null) {
            categoryMenu = new JMenu(title);
            menuBar.add(categoryMenu);
        }
        JMenuItem menuItem = new JMenuItem(menuItemTitle);
        menuItem.setMnemonic(keyStroke.getKeyChar());
        //Setting the accelerator (for the keystroke):
        menuItem.setAccelerator(keyStroke);

        menuItem.addActionListener(e -> {
            try {
                command.execute();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        if (isEffectMenu) {
            categoryMenus.put(title, categoryMenu);
        }
        categoryMenu.add(menuItem);
    }

//    public JMenuItem getMenuItem(String menuItemTitle) {
//        return categoryMenus.get(menuItemTitle);
//    }

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

    private void moveCategoriesToEffectsCategory() {
        effectsMenu = new JMenu("Effects");
        menuBar.add(effectsMenu);
        for (Map.Entry<String, JMenu> entry : categoryMenus.entrySet()) {
            effectsMenu.add(entry.getValue());
        }
    }


    public void setDefaultMenuItems() {
        FileCommands fileCommands = new FileCommands(stateBasedUIComponentGroup);
        addItemToMenu("File", "Open Image", fileCommands.createOpenImageCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK), false);
        addItemToMenu("File", "Open URL", fileCommands.createOpenImageFromUrlCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK), false);
        addItemToMenu("File", "Open Project", fileCommands.createOpenProjectCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK), false);
        addItemToMenu("File", "New Blank Image", fileCommands.createNewBlankImageCommand(), false);
        addItemToMenu("File", "Save", fileCommands.createSaveImageCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), false);
        addItemToMenu("File", "Save with Text", fileCommands.createSaveImageWithTextCommand(), false);

        SerializingCommands serializingCommands = new SerializingCommands(stateBasedUIComponentGroup);
        addItemToMenu("Project", "Save Project", serializingCommands.createEffectHistorySerializeCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), false);
        addItemToMenu("Project", "Load Project", serializingCommands.createEffectHistoryLoadCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), false);
        addItemToMenu("Project", "Save Effect Sequence", serializingCommands.createEffectSerializeCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK), false);
        addItemToMenu("Project", "Load Effect Sequence", serializingCommands.createEffectLoadCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK), false);

        EffectHistoryCommands effectHistoryCommands = new EffectHistoryCommands(stateBasedUIComponentGroup);
        addItemToMenu("Edit", "Timeline", imageTimeline::show, KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK), false);
        addItemToMenu("Edit", "Undo", effectHistoryCommands.createUndoCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), false);
        addItemToMenu("Edit", "Redo", effectHistoryCommands.createRedoCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), false);
        addItemToMenu("Edit", "Reset", effectHistoryCommands.createResetCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), false);

        MiscCommands miscCommands = new MiscCommands(stateBasedUIComponentGroup);
        addItemToMenu("Apply", "Random effect", miscCommands.createRandomEffectCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_DOWN_MASK), false);
        addItemToMenu("Apply", "Random effect (multiple)", miscCommands.createMultipleRandomEffectsCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), false);

        //TODO
        addItemToMenu("Effects", "Search", miscCommands.createEffectSearchCommand(), true);

        AICommands aiCommands = new AICommands();
        aiCommands.setStateBasedUIComponentGroup(stateBasedUIComponentGroup);
        addItemToMenu("Generate", "Style", aiCommands.createImageStylizeCommand(), KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK), false);
        addItemToMenu("Generate", "Image", aiCommands.createImageGenerationCommand(null), false);
    }

    public void setupSliderMenuItems() {
        //Adding all effects that use slider UI to menu panel
        //Loop through all slider effects
        ArrayList<EffectType> sliderEffects = EffectType.getEffectTypeFromComponent(EffectUIType.SLIDER);
        for (EffectType effect : sliderEffects) {
            //Get bounds associated with each effect
            Pair<Integer, Integer> sliderEffectBounds = effect.getSliderBounds();
            int lower = sliderEffectBounds.left();
            int upper = sliderEffectBounds.right();
            SliderEffectWindow sliderEffectWindow = new SliderEffectWindow(effect, lower, upper, stateBasedUIComponentGroup); //Slider component with the extracted bounds
            sliderEffectWindow.setupSlider();
            //Get category label for effect
            String effectCategory = effect.getEffect((lower + upper) / 2.0).getClass().getSuperclass().getSimpleName(); //Get the name of the super class which will be the category for the effect
            Command effectActionListener = sliderEffectWindow.sliderValuesEffect();
            addItemToMenu(effectCategory, effect.toString(), effectActionListener, true); //Add effect to category in menu with corresponding action listener to apply the effect
        }
    }

    public void setupColorMenuItems() {
        ArrayList<EffectType> colorEffects = EffectType.getEffectTypeFromComponent(EffectUIType.COLOR_CHOOSER);
        for (EffectType effect : colorEffects) {
            ColorEffectWindow colorEffectWindow = new ColorEffectWindow(effect, stateBasedUIComponentGroup);
            String effectCategory = effect.getEffect(new Color(0, 0, 0)).getClass().getSuperclass().getSimpleName(); //Get the name of the super class which will be the category for the effect
            Command effectActionListener = colorEffectWindow.colorPickerEffect();
            addItemToMenu(effectCategory, effect.toString(), effectActionListener, true);
        }
    }

    public void setupTextFieldMenuItems() {
        ArrayList<EffectType> textFieldEffects = EffectType.getEffectTypeFromComponent(EffectUIType.TEXT_FIELD);
        for (EffectType effect : textFieldEffects) {
            String[] effectLabels = effect.getTextFieldParams();
            InputEffectWindow inputEffectWindow = new InputEffectWindow(effect, effectLabels, stateBasedUIComponentGroup);
            String effectCategory = effect.getEffect(MiscHelper.createZerosArray(effect.getTextFieldParams().length)).getClass().getSuperclass().getSimpleName(); //Get the name of the super class which will be the category for the effect
            Command effectActionListener = inputEffectWindow.inputValuesEffect();
            addItemToMenu(effectCategory, effect.toString(), effectActionListener, true);
        }
    }

    public void setupNoParamMenuItems() {
        ArrayList<EffectType> noParamEffects = EffectType.getEffectTypeFromComponent(EffectUIType.NONE);
        for (EffectType effect : noParamEffects) {
            String effectCategory = effect.getEffect().getClass().getSuperclass().getSimpleName(); //Get the name of the super class which will be the category for the effect
            MiscCommands miscCommands = new MiscCommands(stateBasedUIComponentGroup);
            miscCommands.setEffect(effect.getEffect());
            addItemToMenu(effectCategory, effect.toString(), miscCommands.createUpdateEffectCommand(), true);
        }
    }

    public void setupMenuPanel() {
        setupSliderMenuItems();
        setupColorMenuItems();
        setupTextFieldMenuItems();
        setupNoParamMenuItems();
        moveCategoriesToEffectsCategory();
    }
}
