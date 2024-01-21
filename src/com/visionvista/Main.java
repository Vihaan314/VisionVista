package com.visionvista;

import com.visionvista.components.*;
import com.visionvista.effects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

//In first menu add option in addition to the existing "Open image" a "create new project" with dimension templates, where it
//is just a draw app, where you can also save - could be done with new buffered image, and set x, y, pixel to color (color picker)


//to work on:
//more filters
//adding text / shapes to image
//delete region by specifying start x, y, and end x, y
//Drawing app - new button panel
//JMenu


public class Main {
    private final JFrame mainFrame;
    public JFrame editorFrame;
    private JLabel imageLabel;
    public ImageEditor editor;

    public static String[] file_name_broken;

    public BufferedImage editedImage;

    public static Set<String> effects = new HashSet<String>();

    public int currentImage = 0;

    public EffectHistory effectHistory = new EffectHistory();

    ImageTimeline imageTimeline;

    public Main() {
        mainFrame = new JFrame("Image Editor");
        JPanel mainPanel = new JPanel();

//        com.visionvista.components.OutlineText openingText = new com.visionvista.components.OutlineText("Vision Vista", Color.BLUE, Color.YELLOW, new Font("Arial", Font.PLAIN, 24));
        JLabel openingText = new JLabel("Vision Vista");
        openingText.setFont(new Font("Aria", Font.BOLD, 20));

        JButton newImageButton = new JButton("New Blank Image");
        
        JLabel padding1 = new JLabel("");
        JLabel padding2 = new JLabel("");
        JLabel padding3 = new JLabel("");
        JLabel padding4 = new JLabel("");
        JLabel padding5 = new JLabel("");
        JLabel padding6 = new JLabel("");
        JLabel padding7 = new JLabel("");
        padding1.setBackground(new Color(238, 238, 238));
        padding2.setBackground(new Color(238, 238, 238));
        padding3.setBackground(new Color(238, 238, 238));
        padding4.setBackground(new Color(238, 238, 238));
        padding5.setBackground(new Color(238, 238, 238));
        padding6.setBackground(new Color(238, 238, 238));
        padding7.setBackground(new Color(238, 238, 238));

        JButton urlButton = new JButton("Load URL");
        JButton openButton = new JButton("Open Image");
        JButton templateButton = new JButton("Choose Template");

        Dimension buttonDimension = new Dimension(250, 250);

        newImageButton.setPreferredSize(buttonDimension);
        padding1.setPreferredSize(buttonDimension);
        padding2.setPreferredSize(buttonDimension);
        padding3.setPreferredSize(buttonDimension);
        padding4.setPreferredSize(buttonDimension);
        padding5.setPreferredSize(buttonDimension);
        padding6.setPreferredSize(buttonDimension);
        padding7.setPreferredSize(buttonDimension);

        urlButton.setPreferredSize(buttonDimension);
        openButton.setPreferredSize(buttonDimension);
        templateButton.setPreferredSize(buttonDimension);

        mainPanel.add(padding5);
        mainPanel.add(padding6);
        mainPanel.add(openingText);
        mainPanel.add(padding4);
        mainPanel.add(padding2);
        mainPanel.add(padding1);
        mainPanel.add(newImageButton);
        mainPanel.add(openButton);
        mainPanel.add(padding7);
        mainPanel.add(urlButton);
        mainPanel.add(templateButton);

        ImageHandler imageHandler = new ImageHandler(effectHistory, editor, mainFrame);
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                imageHandler.openImage();
                mainFrame.dispose();
            }
        });

        urlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                imageHandler.openImageFromUrl();
                mainFrame.dispose();
            }
        });

        newImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                imageHandler.createNewImage();
                mainFrame.dispose();
            }
        });

        mainFrame.add(mainPanel);

        mainFrame.setSize(1200, 800);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }




    public ArrayList<Effect> getEffectSequence() {
        ArrayList<Effect> effectSequence = new ArrayList<>();
        return effectSequence;
    }

    public void applyImageSequence() {
        //Iterate throuogh effectSequence, and create new instances of effect classes based on the elements in the given sequence - it will
        // convert the intitials to instances
        //Then will update the editor as well as the effect sequence by creating a "get effect_sequence from initialsList" function
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.FILES_ONLY);
        f.showSaveDialog(null);

        String directory = String.valueOf(f.getSelectedFile());
        System.out.println(directory);
    }

    public void printEffectSequence() {
        effectHistory.printSequence();
    }

//    public void updateEffectSequence(Effect effect) {
//        effectHistory.add(effect);
//        if (effectHistory.currentImage+1 != effectHistory.getSize()-1) {
//            effectHistory.setCurrentImage(effectHistory.getSize()-1);
//        }
//        else {
//            effectHistory.updateCurrentImage(1);
//        }
//    }

//    public void updateEditor(BufferedImage newImg, String title) {
//        editor.updateMenuPanel(setupMenuPanel(newImg));
//        editor.updateImage(newImg, title);
//        printEffectSequence();
//    }

    public void showTimeline(EffectHistory effectHistory) {
        imageTimeline = new ImageTimeline(effectHistory, this);
        imageTimeline.show();
    }

//    public com.visionvista.ImagePanel setupImagePanel(BufferedImage img) {
//        com.visionvista.ImagePanel imagePanel = new com.visionvista.ImagePanel(img, editor, file_name_broken, effect_sequence);
//        return imagePanel;
//    }

    public ButtonPanel setupButtonPanel(BufferedImage img) {
        ButtonPanel buttonPanel = new ButtonPanel();

//        buttonPanel.addButtonToPanel("Fill (BG)", new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                updateEditor(img, "Filled drawing");
//            }
//        });

        buttonPanel.updatePanelLayout(1, 3);

        return buttonPanel;
    }

//    public MenuPanel setupMenuPanel(BufferedImage img) {
//        MenuPanel menuPanel = new MenuPanel();
//
////        menuPanel.addItemToMenu("File", "Open Image", new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                ImageHandler imageHandler = new ImageHandler(effectHistory, editor, mainFrame);
////                imageHandler.openImage();
////            }
////        });
////        menuPanel.addItemToMenu("File", "Open URL", new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                ImageHandler imageHandler = new ImageHandler(effectHistory, editor, mainFrame);
////                imageHandler.openImageFromUrl();
////            }
////        });
////        menuPanel.addItemToMenu("File", "New Blank Image", new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                createNewImage();
////            }
////        });
////
////        menuPanel.addItemToMenu("File", "Save", new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                editor.saveImage(img, file_name_broken, false, effectHistory.getEffectSequence());
////            }
////        });
////
////        menuPanel.addItemToMenu("File", "Save with Text", new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                editor.saveImage(img, file_name_broken, true, effectHistory.getEffectSequence());
////            }
////        });
////
////        menuPanel.addItemToMenu("File", "Save Effect Sequence", new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                String directory = getSerializeDirectory();
////                try {
////                    editor.saveImageSequence(effectHistory.getEffectSequence(), directory, file_name_broken[0]);
////                } catch (IOException ex) {
////                    throw new RuntimeException(ex);
////                }
////            }
////        });
////
////        menuPanel.addItemToMenu("Edit", "Undo", new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                printEffectSequence();
////                if (effectHistory.getCurrentIndex() > 0) {
////                    effectHistory.updateCurrentImage(-1);
////                }
////                updateEditor(effectHistory.getCurrentImage(), "Image Editor");
////            }
////        });
////        menuPanel.addItemToMenu("Edit", "Redo", new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                printEffectSequence();
////                if (effectHistory.getCurrentIndex() < effectHistory.getSize()-1) {
////                    effectHistory.updateCurrentImage(1);
////                }
////                updateEditor(effectHistory.getCurrentImage(), "Image editor");
////            }
////        });
////        menuPanel.addItemToMenu("Edit", "Timeline", new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                showTimeline(effectHistory);
////            }
////        });
////        menuPanel.addItemToMenu("Edit", "Reset", new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                effectHistory.resetHistory();
////                updateEditor(effectHistory.getFirstImage(), "Image editor");
////            }
////        });
//
//
//        menuPanel.addItemToMenu("Apply", "Sequence", new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                editor.openSequenceInput(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        applyImageSequence();
//                    }
//                });
//            }
//        });
//
//        menuPanel.addItemToMenu("Apply", "Random", new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Effect randomEffect = new RandomImage(img).getRandomImage();
//                effectHistory.updateEffectSequence(randomEffect);
//                updateEditor(randomEffect.run(), "New image");
//                EffectTextBox randomEffectBox = new EffectTextBox(randomEffect);
//                randomEffectBox.show();
//            }
//        });
//
////        menuPanel.addItemToMenu("effects", EffectType.CONTRAST.toString(), new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                sliderValuesEffect(img, EffectType.CONTRAST, -100, 100);
////            }
////        });
////
////        menuPanel.addItemToMenu("effects", ButtonPanelConstants.BRIGHTNESS_TITLE, new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                sliderValuesEffect(img, EffectType.BRIGHTNESS, -100, 100);
////            }
////        });
////
////        menuPanel.addItemToMenu("effects", ButtonPanelConstants.SATURATION_TITLE, new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                sliderValuesEffect(img, EffectType.SATURATION, -100, 100);
////            }
////        });
////
////        menuPanel.addItemToMenu("effects", ButtonPanelConstants.VIBRANCE_TITLE, new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                sliderValuesEffect(img, EffectType.VIBRANCE, -1, 1);
////            }
////        });
////
////        menuPanel.addItemToMenu("effects", ButtonPanelConstants.BLUR_TITLE, new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                sliderValuesEffect(img, EffectType.BLUR, 0, 10);
////            }
////        });
////
////        menuPanel.addItemToMenu("effects", ButtonPanelConstants.SHARPEN_TITLE, new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                sliderValuesEffect(img, EffectType.SHARPEN, 0, 1);
////            }
////        });
////
////        menuPanel.addItemToMenu("effects", ButtonPanelConstants.HUE_TITLE, new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                colorPickerEffect(img, EffectType.HUE);
////            }
////        });
////
////        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.TEMPERATURE_TITLE, new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                sliderValuesEffect(img, EffectType.TEMPERATURE, -1, 1);
////            }
////        });
////
////        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.SEPIA_TITLE, new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                sliderValuesEffect(img, EffectType.SEPIA, 0, 100);
////            }
////        });
////
////        menuPanel.addItemToMenu("effects", ButtonPanelConstants.GAUSSIAN_BLUR_TITLE, new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                sliderValuesEffect(img, EffectType.GAUSSIAN_BLUR, 0, 10);
////            }
////        });
////
////        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.GLOW, new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                sliderValuesEffect(img, EffectType.GLOW, 0, 10);
////            }
////        });
//
//        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.GRAYSCALE_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Effect grayscaledImg = EffectType.GRAYSCALE.getEffect(img);
//                editedImage = grayscaledImg.run();
//                effectHistory.updateEffectSequence(grayscaledImg);
//                updateEditor(editedImage, "New grayscale image");
//            }
//        });
//
//        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.NEGATIVE_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Effect negativeImg = EffectType.NEGATIVE.getEffect(img);
//                editedImage = negativeImg.run();
//                effectHistory.updateEffectSequence(negativeImg);
//                updateEditor(editedImage, "New negative image");
//            }
//        });
//
//        menuPanel.addItemToMenu("Transformation", ButtonPanelConstants.FLIP_V_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Effect verticalImg = EffectType.FLIP_VERTICAL.getEffect(img);
//                editedImage = verticalImg.run();
//                effectHistory.updateEffectSequence(verticalImg);
//                updateEditor(editedImage, "New flipped (vertical) image");
//            }
//        });
//
//        menuPanel.addItemToMenu("Transformation", ButtonPanelConstants.FLIP_H_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Effect horizontalImg = EffectType.FLIP_HORIZONTAL.getEffect(img);
//                editedImage = horizontalImg.run();
//                effectHistory.updateEffectSequence(horizontalImg);
//                updateEditor(editedImage, "New flipped (horizontally) image");
//            }
//        });
//
////        menuPanel.addItemToMenu("Transformation", ButtonPanelConstants.RESIZE_TITLE, new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                inputValuesEffect(img, EffectType.RESIZE, new String[] {"Width", "Height"}, false);
////            }
////        });
//
//        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.POSTERIZE_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Effect posterizeImg = EffectType.POSTERIZE.getEffect(img);
//                editedImage = posterizeImg.run();
//                effectHistory.updateEffectSequence(posterizeImg);
//                updateEditor(editedImage, "New posterized image");
//            }
//        });
//
//        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.CROSS_PROCESS_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Effect crossProcessImg = EffectType.CROSS_PROCESS.getEffect(img);
//                editedImage = crossProcessImg.run();
//                effectHistory.updateEffectSequence(crossProcessImg);
//                updateEditor(editedImage, "New cross-processed image");
//            }
//        });
//
//        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.LOMOGRAPHY_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Effect lomographyImg = EffectType.LOMOGRAPHY.getEffect(img);
//                editedImage = lomographyImg.run();
//                effectHistory.updateEffectSequence(lomographyImg);
//                updateEditor(editedImage, "New lomography image");
//            }
//        });
//
//        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.SOLARIZE_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Effect solarizeImg = EffectType.SOLARIZE.getEffect(img);
//                editedImage = solarizeImg.run();
//                effectHistory.updateEffectSequence(solarizeImg);
//                updateEditor(editedImage, "New solarized image");
//            }
//        });
//
//        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.SPLIT_TONE_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Effect splitToneImg = EffectType.SPLIT_TONE.getEffect(img);
//                editedImage = splitToneImg.run();
//                effectHistory.updateEffectSequence(splitToneImg);
//                updateEditor(editedImage, "New split-tone image");
//            }
//        });
//
//        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.HEAT_MAP_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Effect heatmapImg = EffectType.HEAT_MAP.getEffect(img);
//                editedImage = heatmapImg.run();
//                effectHistory.updateEffectSequence(heatmapImg);
//                updateEditor(editedImage, "New heatmap image");
//            }
//        });
//
//        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.INFRARED_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Effect infraredImg = EffectType.INFRARED.getEffect(img);
//                editedImage = infraredImg.run();
//                effectHistory.updateEffectSequence(infraredImg);
//                updateEditor(editedImage, "New infrared image");
//            }
//        });
//
////        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.PIXELATE_TITLE, new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                sliderValuesEffect(img, EffectType.PIXELATE, 0, 50);
////            }
////        });
////
////        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.VIGNETTE_TITLE, new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                sliderValuesEffect(img, EffectType.VIGNETTE, 0, 1);
////            }
////        });
//
//        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.TILT_SHIFT_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Effect tiltShiftImg = EffectType.TILT_SHIFT.getEffect(img);
//                editedImage = tiltShiftImg.run();
//                effectHistory.updateEffectSequence(tiltShiftImg);
//                updateEditor(editedImage, "New tilt-shifted image");
//            }
//        });
//
//        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.PENCIL_SKETCH, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Effect pencilSketchImg = EffectType.PENCIL_SKETCH.getEffect(img);
//                editedImage = pencilSketchImg.run();
//                effectHistory.updateEffectSequence(pencilSketchImg);
//                updateEditor(editedImage, "New pencil-sketched image");
//            }
//        });
//
//        return menuPanel;
//    }

    public MenuPanel setupMenuPanelDraw(BufferedImage img) {
        MenuPanel menuPanel = new MenuPanel();

        return menuPanel;
    }

    public ButtonPanel setupButtonPanelNew(BufferedImage img) {
        ButtonPanel buttonPanel = new ButtonPanel();

        buttonPanel.addButtonToPanel(ButtonPanelConstants.PEN_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PenDraw penDraw = new PenDraw();
            }
        });

//        buttonPanel.addButtonToPanel(ButtonPanelConstants.RESIZE_TITLE, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                inputValuesEffect(img, EffectType.RESIZE, new String[] {"Width", "Height"}, true);
//            }
//        });

        return buttonPanel;
    }


    public static void main(String[] args) {
        new Main();
    }
}
