package com.visionvista;

import com.visionvista.components.SliderEffectWindow;
import com.visionvista.effects.EffectType;

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

    JFrame mainFrame;
    ImageEditor editor;
    String[] file_name_broken;
    Main main;

    public MenuPanel() {
        menuBar = new JMenuBar();
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

    public String getItemName(JMenuItem menuItem) {
        return "hi";
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


    public void setEditor(ImageEditor editor) {
        this.editor = editor;
    }

    public void setDefaultMenuItems() {
        EffectHistory effectHistory = EditorState.getInstance().getEffectHistory();
        BufferedImage image = EditorState.getInstance().getImage();
        addItemToMenu("File", "Open Image", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageHandler imageHandler = new ImageHandler(editor, mainFrame);
                imageHandler.openImage();
            }
        });
        addItemToMenu("File", "Open URL", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageHandler imageHandler = new ImageHandler(editor, mainFrame);
                imageHandler.openImageFromUrl();
            }
        });
        addItemToMenu("File", "New Blank Image", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageHandler imageHandler = new ImageHandler(editor, mainFrame);
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
//                printEffectSequence();
                if (effectHistory.getCurrentIndex() > 0) {
                    effectHistory.updateCurrentImage(-1);
                }
                editor.updateEditor(effectHistory.getCurrentImage(), "Image Editor");
            }
        });
        addItemToMenu("Edit", "Redo", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                printEffectSequence();
                if (effectHistory.getCurrentIndex() < effectHistory.getSize()-1) {
                    effectHistory.updateCurrentImage(1);
                }
                editor.updateEditor(effectHistory.getCurrentImage(), "Image editor");
            }
        });
//        addItemToMenu("Edit", "Timeline", new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ImageTimeline imageTimeline = new ImageTimeline(effectHistory, main);
//                imageTimeline.show();
//            }
//        });
        addItemToMenu("Edit", "Reset", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                effectHistory.resetHistory();
                editor.updateEditor(effectHistory.getFirstImage(), "Image editor");
            }
        });
    }

    public void setupSliderMenuItems() {
        EffectHistory effectHistory = EditorState.getInstance().getEffectHistory();
        //Loop through all slider effects
        for (EffectType effect : EffectType.getSliderEffects().keySet()) {
            //Get bounds associated with each effect
            int lower = EffectType.getSliderEffects().get(effect).getLeft();
            int upper = EffectType.getSliderEffects().get(effect).getRight();
            System.out.println("EDITOR " + editor);
            SliderEffectWindow sliderEffectWindow = new SliderEffectWindow(effect, lower, upper, editor); //Slider component with the extracted bounds
            //Get category label for effect
            String effectCategory = effect.getClass().getSuperclass().getName(); //Get the name of the super class which will be the category for the effect
            System.out.println(effectHistory);
            ActionListener effectActionListener = sliderEffectWindow.sliderValuesEffect();
            System.out.println("yo");
            addItemToMenu(effectCategory, effect.toString(), effectActionListener); //Add effect to category in menu with corresponding action listener to apply the effect
        }
    }

    public MenuPanel setupMenuPanel() {
        setDefaultMenuItems();
        setupSliderMenuItems();

        return this;
    }
}
