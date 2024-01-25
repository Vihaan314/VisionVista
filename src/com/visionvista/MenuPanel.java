package com.visionvista;

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

    public MenuPanel(ImageDisplay imageDisplay) {
        menuBar = new JMenuBar();
        this.imageDisplay = imageDisplay;
    }

    public void addItemToMenu(String title, String menuItemTitle, ActionListener actionListener) {
        JMenu menu = getMenu(title); //Gets the sub-menu
        //If the sub-menu doesn't exist yet, it is created
        if (menu == null) {
            menu = new JMenu(title);
            menuBar.add(menu);
        }
        JMenuItem menuItem = new JMenuItem(menuItemTitle);
        menuItem.addActionListener(actionListener);
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
        EffectHistory effectHistory = EditorState.getInstance().getEffectHistory();
        BufferedImage image = EditorState.getInstance().getImage();
        addItemToMenu("File", "Open Image", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageHandler imageHandler = new ImageHandler();
                imageHandler.openImage();
            }
        });
        addItemToMenu("File", "Open URL", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageHandler imageHandler = new ImageHandler();
                imageHandler.openImageFromUrl();
            }
        });
        addItemToMenu("File", "New Blank Image", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageHandler imageHandler = new ImageHandler();
                imageHandler.createNewImage();
            }
        });

        addItemToMenu("File", "Save", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageSaver imageSaver = new ImageSaver(image, imageDisplay.getFileNameDetails(), false);
                imageSaver.saveImage();
            }
        });

        addItemToMenu("File", "Save with Text", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageSaver imageSaver = new ImageSaver(image, imageDisplay.getFileNameDetails(), true);
                imageSaver.saveImage();
            }
        });

        addItemToMenu("Edit", "Undo", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (effectHistory.getCurrentIndex() > 0) {
                    effectHistory.updateCurrentImage(-1);
                    //ask about automatic effectype getter
                }
                EditorState.getInstance().setEffectHistory(effectHistory);
                imageDisplay.updateImageFromState();
            }
        });
        addItemToMenu("Edit", "Redo", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (effectHistory.getCurrentIndex() < effectHistory.getSize()-1) {
                    effectHistory.updateCurrentImage(1);
                }
                EditorState.getInstance().setEffectHistory(effectHistory);
                imageDisplay.updateImageFromState();
            }
        });
        addItemToMenu("Edit", "Timeline", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageTimeline imageTimeline = new ImageTimeline(imageDisplay);
                imageTimeline.show();
            }
        });
        addItemToMenu("Edit", "Reset", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                effectHistory.resetHistory();
                EditorState.getInstance().setEffectHistory(effectHistory);
                imageDisplay.updateImageFromState();
            }
        });


        addItemToMenu("Image", "Save Effect Sequence", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EffectSerializer effectSerializer = new EffectSerializer();
                effectSerializer.serializeEffects(imageDisplay.getFileNameDetails()[0]);
            }
        });

        addItemToMenu("Image", "Load Effect Sequence", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EffectSerializer effectSerializer = new EffectSerializer();
                effectSerializer.readSerializedEffects();
                ArrayList<Effect> serializedEffect = new ArrayList<>(effectSerializer.getEffectsList().subList(1, effectSerializer.getEffectsList().size()));
                BufferedImage currentImage = EditorState.getInstance().getImage();
                for (Effect effect : serializedEffect) {
                    currentImage = effect.run(currentImage);
                    EditorState.getInstance().setImage(image);
                    EditorState.getInstance().getEffectHistory().add(effect, currentImage);
                    imageDisplay.updateImageFromState();
                }
                imageDisplay.updateImageFromState();
            }
        });

        addItemToMenu("Image", "Save Image Sequence", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EffectHistorySerializer effectHistorySerializer = new EffectHistorySerializer();
                effectHistorySerializer.serializeEffects(imageDisplay.getFileNameDetails()[0]);
            }
        });

        addItemToMenu("Image", "Load Image Sequence", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EffectHistorySerializer effectHistorySerializer = new EffectHistorySerializer();
                effectHistorySerializer.readSerializedEffects();
                BufferedImage initialImage = effectHistorySerializer.getInitialImage();
                ArrayList<Effect> effectsList = effectHistorySerializer.getEffectsList();
                System.out.println("INTIIAL IAMGE " + initialImage);
                System.out.println(effectsList);
                EffectHistory serializedEffectHistory = new EffectHistory();
                serializedEffectHistory.setEffectSequence(effectsList, initialImage);
                EditorState.getInstance().setEffectHistory(serializedEffectHistory);
                EditorState.getInstance().setImage(serializedEffectHistory.getCurrentImage());
                imageDisplay.updateImageFromState();
                System.out.println(EditorState.getInstance().getEffectHistory());
            }
        });

        addItemToMenu("Apply", "Random effect", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage currentImage = EditorState.getInstance().getImage();
                Effect randomEffect = new RandomEffect().getRandomEffect();
                currentImage = randomEffect.run(currentImage);
                effectHistory.add(randomEffect, currentImage);
                EditorState.getInstance().setImage(currentImage);
                imageDisplay.updateImageFromState();
                EffectTextBox randomEffectBox = new EffectTextBox(randomEffect);
                randomEffectBox.show();
            }
        });
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
            SliderEffectWindow sliderEffectWindow = new SliderEffectWindow(effect, lower, upper, imageDisplay); //Slider component with the extracted bounds
            //Get category label for effect
            String effectCategory = effect.getEffect((lower + upper) / 2.0).getClass().getSuperclass().getSimpleName(); //Get the name of the super class which will be the category for the effect
            ActionListener effectActionListener = sliderEffectWindow.sliderValuesEffect();
            addItemToMenu(effectCategory, effect.toString(), effectActionListener); //Add effect to category in menu with corresponding action listener to apply the effect
        }
    }

    public void setupColorMenuItems() {
        ArrayList<EffectType> colorEffects = EffectType.getEffectTypeFromComponent(EffectUIType.COLOR_CHOOSER);
        for (EffectType effect : colorEffects) {
            ColorEffectWindow colorEffectWindow = new ColorEffectWindow(effect, imageDisplay);
            String effectCategory = effect.getEffect(new Color(0, 0, 0)).getClass().getSuperclass().getSimpleName(); //Get the name of the super class which will be the category for the effect
            ActionListener effectActionListener = colorEffectWindow.colorPickerEffect();
            addItemToMenu(effectCategory, effect.toString(), effectActionListener);
        }
    }

    public void setupTextFieldMenuItems() {
        ArrayList<EffectType> textFieldEffects = EffectType.getEffectTypeFromComponent(EffectUIType.TEXT_FIELD);
        for (EffectType effect : textFieldEffects) {
            String[] effectLabels = effect.getTextFieldParams();
            InputEffectWindow inputEffectWindow = new InputEffectWindow(effect, effectLabels, imageDisplay);
            String effectCategory = effect.getEffect(Helper.createZerosArray(effect.getTextFieldParams().length)).getClass().getSuperclass().getSimpleName(); //Get the name of the super class which will be the category for the effect
            ActionListener effectActionListener = inputEffectWindow.inputValuesEffect();
            addItemToMenu(effectCategory, effect.toString(), effectActionListener);
        }
    }

    public void setupNoParamMenuItems() {
        ArrayList<EffectType> noParamEffects = EffectType.getEffectTypeFromComponent(EffectUIType.NONE);
        for (EffectType effect : noParamEffects) {
            String effectCategory = effect.getEffect().getClass().getSuperclass().getSimpleName(); //Get the name of the super class which will be the category for the effect
            ActionListener effectActionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Effect chosenEffect = effect.getEffect();
                    BufferedImage currentImage = EditorState.getInstance().getImage();
                    currentImage = chosenEffect.run(currentImage);
                    EditorState.getInstance().getEffectHistory().add(chosenEffect, currentImage);
                    EditorState.getInstance().setImage(currentImage);
                    imageDisplay.updateImageFromState();
                }
            };
            addItemToMenu(effectCategory, effect.toString(), effectActionListener);
        }
    }

    //NOTE: Procedure for applying effect:
    /*
    Effect effect = new Effect();
    BufferedImage currentImage = EditorState.getInstance().getImage();
    currentImage = effect.run(currentImage);
    EditorState.getInstance().add((effect, currentImage);
    EditorState.getInstance().setImage(currentImage);
    imageDisplay.updateImageFromState();
     */
    public void setupMenuPanel() {
        setDefaultMenuItems();
        setupSliderMenuItems();
        setupColorMenuItems();
        setupTextFieldMenuItems();
        setupNoParamMenuItems();
    }
}
