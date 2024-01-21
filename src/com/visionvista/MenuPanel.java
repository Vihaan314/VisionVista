package com.visionvista;

import com.visionvista.components.SliderEffectWindow;
import com.visionvista.effects.Effect;
import com.visionvista.effects.EffectType;

import javax.swing.*;
import java.awt.*;
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
    EffectHistory effectHistory;
    BufferedImage img;
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

    public void setMenuParameters(JFrame mainFrame, ImageEditor editor, EffectHistory effectHistory, BufferedImage img, String[] file_name_broken, Main main) {
        this.mainFrame = mainFrame;
        this.editor = editor;
        this.effectHistory = effectHistory;
        this.img = img;
        this.file_name_broken = file_name_broken;
        this.main = main;
    }

    public void setMenuParameters(ImageEditor editor, EffectHistory effectHistory) {
        this.editor = editor;
        this.effectHistory = effectHistory;
    }

    public void setMenuParameters(EffectHistory effectHistory) {
        this.effectHistory = effectHistory;
    }

    public void setDefaultMenuItems(BufferedImage img) {
        addItemToMenu("File", "Open Image", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageHandler imageHandler = new ImageHandler(effectHistory, editor, mainFrame);
                imageHandler.openImage();
            }
        });
        addItemToMenu("File", "Open URL", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageHandler imageHandler = new ImageHandler(effectHistory, editor, mainFrame);
                imageHandler.openImageFromUrl();
            }
        });
        addItemToMenu("File", "New Blank Image", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageHandler imageHandler = new ImageHandler(effectHistory, editor, mainFrame);
                imageHandler.createNewImage();
            }
        });

        addItemToMenu("File", "Save", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageSaver imageSaver = new ImageSaver(img, file_name_broken, false, effectHistory.getEffectSequence());
                imageSaver.saveImage();
//                editor.saveImage(img, file_name_broken, false, effectHistory.getEffectSequence());
            }
        });

        addItemToMenu("File", "Save with Text", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageSaver imageSaver = new ImageSaver(img, file_name_broken, true, effectHistory.getEffectSequence());
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
                editor.updateEditor(effectHistory.getCurrentImage(), "Image Editor", effectHistory);
            }
        });
        addItemToMenu("Edit", "Redo", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                printEffectSequence();
                if (effectHistory.getCurrentIndex() < effectHistory.getSize()-1) {
                    effectHistory.updateCurrentImage(1);
                }
                editor.updateEditor(effectHistory.getCurrentImage(), "Image editor", effectHistory);
            }
        });
        addItemToMenu("Edit", "Timeline", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageTimeline imageTimeline = new ImageTimeline(effectHistory, main);
                imageTimeline.show();
            }
        });
        addItemToMenu("Edit", "Reset", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                effectHistory.resetHistory();
                editor.updateEditor(effectHistory.getFirstImage(), "Image editor", effectHistory);
            }
        });
    }

    public void setupSliderMenuItems() {
        for (EffectType effect : EffectType.getSliderEffects().keySet()) {
            int lower = EffectType.getSliderEffects().get(effect).getLeft();
            int upper = EffectType.getSliderEffects().get(effect).getRight();
            System.out.println("EDITOR " + editor);
            SliderEffectWindow sliderEffectWindow = new SliderEffectWindow(img, effect, lower, upper, editor); //Slider component with the extracted bounds
            String effectCategory = effect.getClass().getSuperclass().getName(); //Get the name of the super class which will be the category for the effect
            System.out.println(effectHistory);
            ActionListener effectActionListener = sliderEffectWindow.sliderValuesEffect(effectHistory);
            System.out.println("yo");
            addItemToMenu(effectCategory, effect.toString(), effectActionListener); //Add effect to category in menu with corresponding action listener to apply the effect
        }




//        SliderEffectWindow contrastSliderWindow = new SliderEffectWindow(img, EffectType.CONTRAST, lower, upper, editor);
//        addItemToMenu("effects", EffectType.CONTRAST.toString(),
//                contrastSliderWindow.createSliderActionListener(EffectType.CONTRAST, editor, effectHistory));
//
//
//        addItemToMenu("effects", EffectType.CONTRAST.toString(), new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sliderEffectWindow.sliderValuesEffect(editor, effectHistory);
//            }
//        });
//
//        addItemToMenu("effects", ButtonPanelConstants.BRIGHTNESS_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sliderEffectWindow.sliderValuesEffect(editor, effectHistory);
//            }
//        });
//
//        addItemToMenu("effects", ButtonPanelConstants.SATURATION_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sliderValuesEffect(img, EffectType.SATURATION, -100, 100);
//            }
//        });
//
//        addItemToMenu("effects", ButtonPanelConstants.VIBRANCE_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sliderValuesEffect(img, EffectType.VIBRANCE, -1, 1);
//            }
//        });
//
//        addItemToMenu("effects", ButtonPanelConstants.BLUR_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sliderValuesEffect(img, EffectType.BLUR, 0, 10);
//            }
//        });
//
//        addItemToMenu("effects", ButtonPanelConstants.SHARPEN_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sliderValuesEffect(img, EffectType.SHARPEN, 0, 1);
//            }
//        });
//
//        addItemToMenu("Filters", ButtonPanelConstants.TEMPERATURE_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sliderValuesEffect(img, EffectType.TEMPERATURE, -1, 1);
//            }
//        });
//
//        addItemToMenu("Filters", ButtonPanelConstants.SEPIA_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sliderValuesEffect(img, EffectType.SEPIA, 0, 100);
//            }
//        });
//
//        addItemToMenu("effects", ButtonPanelConstants.GAUSSIAN_BLUR_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sliderValuesEffect(img, EffectType.GAUSSIAN_BLUR, 0, 10);
//            }
//        });
//
//        addItemToMenu("Filters", ButtonPanelConstants.GLOW, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sliderValuesEffect(img, EffectType.GLOW, 0, 10);
//            }
//        });
//
//        addItemToMenu("Filters", ButtonPanelConstants.PIXELATE_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sliderValuesEffect(img, EffectType.PIXELATE, 0, 50);
//            }
//        });
//
//        addItemToMenu("Filters", ButtonPanelConstants.VIGNETTE_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sliderValuesEffect(img, EffectType.VIGNETTE, 0, 1);
//            }
//        });
    }

    public MenuPanel setupMenuPanel() {
        setDefaultMenuItems();
        setupSliderMenuItems();

        return this;
    }
}
