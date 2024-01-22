package com.visionvista;

import com.visionvista.components.SliderEffectWindow;
import com.visionvista.effects.EffectType;
import com.visionvista.effects.EffectUIType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MenuPanel {
    private JMenuBar menuBar;
    private Map<String, JMenuItem> menuItems = new HashMap<String, JMenuItem>();

    String[] file_name_broken;

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
                ImageSaver imageSaver = new ImageSaver(image, file_name_broken, false);
                imageSaver.saveImage();
//                editor.saveImage(img, file_name_broken, false, effectHistory.getEffectSequence());
            }
        });

        addItemToMenu("File", "Save with Text", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageSaver imageSaver = new ImageSaver(image, file_name_broken, true);
                imageSaver.saveImage();
//                editor.saveImage(img, file_name_broken, true, effectHistory.getEffectSequence());
            }
        });

        addItemToMenu("File", "Save Effect Sequence", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Serializer serializer = new Serializer();
                String directory = serializer.getSerializeDirectory();
                try {
                    serializer.saveImageSequence(effectHistory.getEffectSequence(), directory, file_name_broken[0]);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        addItemToMenu("Edit", "Undo", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (effectHistory.getCurrentIndex() > 0) {
                    effectHistory.updateCurrentImage(-1);
                    //ask about automatic effectype getter
                }
                imageDisplay.updateEditorFromState();
            }
        });
        addItemToMenu("Edit", "Redo", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (effectHistory.getCurrentIndex() < effectHistory.getSize()-1) {
                    effectHistory.updateCurrentImage(1);
                }
                imageDisplay.updateEditorFromState();
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
                imageDisplay.updateEditorFromState();
            }
        });
    }

    public void setupSliderMenuItems() {
        //Adding all effects that use slider UI to menu panel
        //Loop through all slider effects
        Map<EffectType, Pair<Integer, Integer>> sliderEffects = EffectType.getEffectTypeFromComponent(EffectUIType.SLIDER);
        for (EffectType effect : sliderEffects.keySet()) {
            //Get bounds associated with each effect
            int lower = sliderEffects.get(effect).getLeft();
            int upper = sliderEffects.get(effect).getRight();
            SliderEffectWindow sliderEffectWindow = new SliderEffectWindow(effect, lower, upper, imageDisplay); //Slider component with the extracted bounds
            //Get category label for effect
            String effectCategory = effect.getEffect((lower + upper) / 2.0).getClass().getSuperclass().getSimpleName(); //Get the name of the super class which will be the category for the effect
            ActionListener effectActionListener = sliderEffectWindow.sliderValuesEffect();
            addItemToMenu(effectCategory, effect.toString(), effectActionListener); //Add effect to category in menu with corresponding action listener to apply the effect
        }
    }

    public void setupMenuPanel() {
        setDefaultMenuItems();
        setupSliderMenuItems();

//        return this;
    }
}
