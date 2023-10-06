import Effects.*;
import Effects.Filters.*;
import Effects.Negative;
import Effects.Transformations.FlipHorizontal;
import Effects.Transformations.FlipVertical;
import Effects.Transformations.Resize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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

    public ArrayList<Effect> effect_sequence = new ArrayList<Effect>();
    public int currentImage = 0;

    public EffectHistory effectHistory = new EffectHistory();

    ImageTimeline imageTimeline;

    public Main() {
        mainFrame = new JFrame("Image Editor");
        JPanel mainPanel = new JPanel();

        JButton newImageButton = new JButton("New Blank Image");
        JButton urlButton = new JButton("Load URL");
        JButton openButton = new JButton("Open Image");
        JButton templateButton = new JButton("Choose Template");

        Dimension buttonDimension = new Dimension(400, 400);

        newImageButton.setPreferredSize(buttonDimension);
        urlButton.setPreferredSize(buttonDimension);
        openButton.setPreferredSize(buttonDimension);
        templateButton.setPreferredSize(buttonDimension);

        mainPanel.add(newImageButton);
        mainPanel.add(openButton);
        mainPanel.add(urlButton);
        mainPanel.add(templateButton);

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                openImage();
                mainFrame.dispose();
            }
        });

        urlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                openImageFromUrl();
                mainFrame.dispose();
            }
        });

        newImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                createNewImage();
                mainFrame.dispose();
            }
        });

        mainFrame.add(mainPanel);

        mainFrame.setSize(1200, 800);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void openImage() {
        effect_sequence = new ArrayList<Effect>();
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(mainFrame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String file_name_raw = selectedFile.getName();
            file_name_broken = file_name_raw.split("[.]");

            try {
                BufferedImage image = ImageIO.read(selectedFile);
                Effect image_original = new Effect(image) {
                    @Override
                    public BufferedImage run() {
                        return this.image;
                    }
                    public String toString() { return "Original image"; }
                };
                effectHistory.add(image_original);

                editor = new ImageEditor("Image editor", effectHistory.getFirstImage(), setupMenuPanel(effectHistory.getFirstImage()));
                editor.show();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, "Error loading image.");
                ex.printStackTrace();
            }
        }
    }

    private void openImageFromUrl() {
        JFrame urlFrame = new JFrame();
        urlFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        urlFrame.setLocationRelativeTo(null);
        urlFrame.setSize(400, 180);

        JPanel urlPanel = new JPanel();

        JLabel urlLabel = new JLabel("Enter url:");
        urlLabel.setFont(new Font("Aria", Font.BOLD, 20));
        JTextField urlField = new JTextField();
        JButton openImageButton = new JButton("Open image");

        urlField.setPreferredSize(new Dimension(370, 30));

        openImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    URL img_url = new URL(urlField.getText());
                    file_name_broken = urlField.getText().split("[/]");
                    file_name_broken = file_name_broken[file_name_broken.length-1].split("[.]");
                    BufferedImage image = ImageIO.read(img_url);
                    Effect selectedImage = new Effect(image) {
                        @Override
                        public BufferedImage run() {
                            return image;
                        }
                        public String toString() { return "Original image"; }
                    };

                    effectHistory.add(selectedImage);
                    editor = new ImageEditor("Image editor", effectHistory.getFirstImage(), setupMenuPanel(effectHistory.getFirstImage()));
                    editor.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        urlPanel.add(urlLabel);
        urlPanel.add(urlField);
        urlPanel.add(openImageButton);

        urlFrame.add(urlPanel);
        urlFrame.setVisible(true);
    }

    private void createNewImage() {
        //TODO
        BufferedImage blankImg = ImageHelper.createBlankImage();
        editor = new ImageEditor("Image editor - Draw", blankImg, setupMenuPanel(blankImg));
        editor.show();//        showNewEditor("Default Blank Template", blank_img, true);
//        inputValuesEffect(new ImageEdit(blank_img), "Blank", new String[]{"Width", "Height"});
    }

    public Effect getEffect(BufferedImage image, EffectType effect, double effectParam) {
        Effect newEffect;

        switch (effect) {
            case CONTRAST:
                newEffect = new Contrast(image, effectParam);
                break;
            case BRIGHTNESS:
                newEffect = new Brightness(image, effectParam);
                break;
            case BLUR:
                newEffect = new Blur(image, (int) effectParam);
                break;
            case SATURATION:
                newEffect = new Saturation(image, effectParam);
                break;
            case VIBRANCE:
                newEffect = new Vibrance(image, effectParam);
                break;
            case SHARPEN:
                newEffect = new Sharpen(image, effectParam);
                break;
            case TEMPERATURE:
                newEffect = new Temperature(image, effectParam);
                break;
            case SEPIA:
                newEffect = new Sepia(image, effectParam);
                break;
            case GAUSSIAN_BLUR:
                newEffect = new GaussBlur(image, effectParam);
                break;
            case PIXELATE:
                newEffect = new Pixelate(image, (int) effectParam);
                break;
            case VIGNETTE:
                newEffect = new Vignette(image, (int) effectParam);
                break;
            case GLOW:
                newEffect = new Glow(image, effectParam);
                break;
            default:
                throw new IllegalArgumentException(effect + " is not currently recognized as an effect");
        }

        return newEffect;
    }

    public Effect getEffect(BufferedImage image, EffectType effect, Color color) {
        Effect newEffect;

        switch(effect) {
            case HUE:
                newEffect = new Hue(image, color);
                break;
            default:
                throw new IllegalArgumentException(effect + "is not currently recognized as an effect for colors");
        }
        return newEffect;
    }

    public Effect getEffect(BufferedImage image, EffectType effect, String[] textInputs) {
        Effect newEffect;
        switch (effect) {
            case RESIZE:
                newEffect = new Resize(image, (int) Double.parseDouble(textInputs[0]), (int) Double.parseDouble(textInputs[1]));
                break;
            default:
                throw new IllegalArgumentException(effect + " is not recognized as a text input effect");
        }
        return newEffect;
    }

    public Effect getEffect(BufferedImage image, EffectType effect) {
        Effect newEffect;

        switch(effect) {
            case FLIP_VERTICAL:
                newEffect = new FlipVertical(image);
                break;
            case FLIP_HORIZONTAL:
                newEffect = new FlipHorizontal(image);
                break;
            case NEGATIVE:
                newEffect = new Negative(image);
                break;
            case GRAYSCALE:
                newEffect = new Grayscale(image);
                break;
            case POSTERIZE:
                newEffect = new Posterize(image);
                break;
            case CROSS_PROCESS:
                newEffect = new CrossProcess(image);
                break;
            case LOMOGRAPHY:
                newEffect = new Lomography(image);
                break;
            case SOLARIZE:
                newEffect = new Solarize(image);
                break;
            case SPLIT_TONE:
                newEffect = new SplitTone(image);
                break;
            case HEAT_MAP:
                newEffect = new Heatmap(image);
                break;
            case INFRARED:
                newEffect = new Infrared(image);
                break;
            case TILT_SHIFT:
                newEffect = new TiltShift(image);
                break;
            case PENCIL_SKETCH:
                newEffect = new PencilSketch(image);
                break;
            default:
                throw new IllegalArgumentException(effect + " is not currently recognized as an effect for colors");
        }
        return newEffect;
    }

    public String getSerializeDirectory() {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);

        String directory = String.valueOf(f.getSelectedFile());
        return directory;
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

    public void updateEffectSequence(Effect effect) {
        effectHistory.add(effect);
        if (effectHistory.currentImage+1 != effectHistory.getSize()-1) {
            effectHistory.setCurrentImage(effectHistory.getSize()-1);
        }
        else {
            effectHistory.updateCurrentImage(1);
        }
    }

    public void updateEditor(BufferedImage newImg, String title) {
        editor.updateMenuPanel(setupMenuPanel(newImg));
        editor.updateImage(newImg, title);
        printEffectSequence();
    }

    public void sliderValuesEffect(BufferedImage image, EffectType effect, int lower, int upper) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SliderEffectWindow sliderWindow = new SliderEffectWindow(image, effect, lower, upper, null);

        ActionListener submitEffectListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sliderWindow.getSliderFrame().dispose();
                double effectAmount = sliderWindow.getEffectAmount();
                Effect chosenEffect = getEffect(image, effect, effectAmount);
                editedImage = chosenEffect.run();
                updateEffectSequence(chosenEffect);
                updateEditor(editedImage, "New " + effect.toString() + " image");

            }
        };
        sliderWindow.setupSubmitButton(submitEffectListener);
        sliderWindow.show();
    }

    public void colorPickerEffect(BufferedImage image, EffectType effect) {
        JFrame.setDefaultLookAndFeelDecorated(true);

        ColorEffectWindow colorWindow = new ColorEffectWindow(image, effect);

        ActionListener submitEffectListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorWindow.getSliderFrame().dispose();
                Color chosenColor = colorWindow.getColor();
                Effect chosenEffect = getEffect(image, effect, chosenColor);
                editedImage = chosenEffect.run();
                updateEffectSequence(chosenEffect);
                updateEditor(editedImage, "New " + effect.toString() + " image" );

            }
        };
        colorWindow.setupSubmitButton(submitEffectListener);
        colorWindow.show();
    }

    public void inputValuesEffect(BufferedImage image, EffectType effect, String[] labels, boolean newImage) {
        JFrame.setDefaultLookAndFeelDecorated(true);

        InputEffectWindow inputsWindow = new InputEffectWindow(image, effect, labels);
        ActionListener submitActionListener =  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Effect chosenEffect = getEffect(image, effect, inputsWindow.getValues());
                editedImage = chosenEffect.run();
                inputsWindow.getInputFrame().dispose();
                if (!newImage) {
                    updateEffectSequence(chosenEffect);
                    updateEditor(editedImage, "New " + effect.toString() + " image");
                } else {
                    updateEffectSequence(chosenEffect);
                    updateEditor(editedImage, "Image Editor - " + effect.toString() + " image");
                }
            }
        };

        inputsWindow.setupSubmitButton(submitActionListener);
        inputsWindow.show();
    }

    public void showTimeline(EffectHistory effectHistory) {
        imageTimeline = new ImageTimeline(effectHistory, this);
        imageTimeline.show();
    }

    public ImagePanel setupImagePanel(BufferedImage img) {
        ImagePanel imagePanel = new ImagePanel(img, editor, file_name_broken, effect_sequence);
        return imagePanel;
    }

    public MenuPanel setupMenuPanel(BufferedImage img) {
        MenuPanel menuPanel = new MenuPanel();

        menuPanel.addItemToMenu("File", "Open Image", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openImage();
            }
        });
        menuPanel.addItemToMenu("File", "Open URL", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openImageFromUrl();
            }
        });
        menuPanel.addItemToMenu("File", "Blank Image", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewImage();
            }
        });

        menuPanel.addItemToMenu("File", "Save", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.saveImage(img, file_name_broken, false, effectHistory.getEffectSequence());
            }
        });

        menuPanel.addItemToMenu("File", "Save with Text", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.saveImage(img, file_name_broken, true, effectHistory.getEffectSequence());
            }
        });

        menuPanel.addItemToMenu("File", "Save Effect Sequence", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String directory = getSerializeDirectory();
                try {
                    editor.saveImageSequence(effectHistory.getEffectSequence(), directory, file_name_broken[0]);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        menuPanel.addItemToMenu("Edit", "Undo", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printEffectSequence();
                if (effectHistory.getCurrentIndex() > 0) {
                    effectHistory.updateCurrentImage(-1);
                }
                updateEditor(effectHistory.getCurrentImage(), "Image Editor");
            }
        });
        menuPanel.addItemToMenu("Edit", "Redo", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printEffectSequence();
                if (effectHistory.getCurrentIndex() < effectHistory.getSize()-1) {
                    effectHistory.updateCurrentImage(1);
                }
                updateEditor(effectHistory.getCurrentImage(), "Image editor");
            }
        });
        menuPanel.addItemToMenu("Edit", "Timeline", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTimeline(effectHistory);
            }
        });
        menuPanel.addItemToMenu("Edit", "Reset", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                effectHistory.resetHistory();
                updateEditor(effectHistory.getFirstImage(), "Image editor");
            }
        });


        menuPanel.addItemToMenu("Apply", "Sequence", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.openSequenceInput(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        applyImageSequence();
                    }
                });
            }
        });

        menuPanel.addItemToMenu("Apply", "Random", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Effect randomEffect = new RandomImage(img).getRandomImage();
                updateEffectSequence(randomEffect);
                updateEditor(randomEffect.run(), "New image");
            }
        }); 


        menuPanel.addItemToMenu("Effects", ButtonPanelConstants.CONTRAST_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sliderValuesEffect(img, EffectType.CONTRAST, -100, 100);
            }
        });

        menuPanel.addItemToMenu("Effects", ButtonPanelConstants.BRIGHTNESS_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sliderValuesEffect(img, EffectType.BRIGHTNESS, -100, 100);
            }
        });

        menuPanel.addItemToMenu("Effects", ButtonPanelConstants.SATURATION_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sliderValuesEffect(img, EffectType.SATURATION, -100, 100);
            }
        });

        menuPanel.addItemToMenu("Effects", ButtonPanelConstants.VIBRANCE_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sliderValuesEffect(img, EffectType.VIBRANCE, -1, 1);
            }
        });

        menuPanel.addItemToMenu("Effects", ButtonPanelConstants.BLUR_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sliderValuesEffect(img, EffectType.BLUR, 0, 10);
            }
        });

        menuPanel.addItemToMenu("Effects", ButtonPanelConstants.SHARPEN_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sliderValuesEffect(img, EffectType.SHARPEN, 0, 1);
            }
        });

        menuPanel.addItemToMenu("Effects", ButtonPanelConstants.HUE_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorPickerEffect(img, EffectType.HUE);
            }
        });

        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.TEMPERATURE_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sliderValuesEffect(img, EffectType.TEMPERATURE, -1, 1);
            }
        });

        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.SEPIA_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sliderValuesEffect(img, EffectType.SEPIA, 0, 100);
            }
        });

        menuPanel.addItemToMenu("Effects", ButtonPanelConstants.GAUSSIAN_BLUR_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sliderValuesEffect(img, EffectType.GAUSSIAN_BLUR, 0, 10);
            }
        });

        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.GLOW, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sliderValuesEffect(img, EffectType.GLOW, 0, 10);
            }
        });

        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.GRAYSCALE_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Effect grayscaledImg = getEffect(img, EffectType.GRAYSCALE);
                editedImage = grayscaledImg.run();
                updateEffectSequence(grayscaledImg);
                updateEditor(editedImage, "New grayscale image");
            }
        });

        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.NEGATIVE_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Effect negativeImg = getEffect(img, EffectType.NEGATIVE);
                editedImage = negativeImg.run();
                updateEffectSequence(negativeImg);
                updateEditor(editedImage, "New negative image");
            }
        });

        menuPanel.addItemToMenu("Transformation", ButtonPanelConstants.FLIP_V_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Effect verticalImg = getEffect(img, EffectType.FLIP_VERTICAL);
                editedImage = verticalImg.run();
                updateEffectSequence(verticalImg);
                updateEditor(editedImage, "New flipped (vertical) image");
            }
        });

        menuPanel.addItemToMenu("Transformation", ButtonPanelConstants.FLIP_H_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Effect horizontalImg = getEffect(img, EffectType.FLIP_HORIZONTAL);
                editedImage = horizontalImg.run();
                updateEffectSequence(horizontalImg);
                updateEditor(editedImage, "New flipped (horizontally) image");
            }
        });

        menuPanel.addItemToMenu("Transformation", ButtonPanelConstants.RESIZE_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputValuesEffect(img, EffectType.RESIZE, new String[] {"Width", "Height"}, false);
            }
        });

        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.POSTERIZE_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Effect posterizeImg = getEffect(img, EffectType.POSTERIZE);
                editedImage = posterizeImg.run();
                updateEffectSequence(posterizeImg);
                updateEditor(editedImage, "New posterized image");
            }
        });

        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.CROSS_PROCESS_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Effect crossProcessImg = getEffect(img, EffectType.CROSS_PROCESS);
                editedImage = crossProcessImg.run();
                updateEffectSequence(crossProcessImg);
                updateEditor(editedImage, "New cross-processed image");
            }
        });

        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.LOMOGRAPHY_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Effect lomographyImg = getEffect(img, EffectType.LOMOGRAPHY);
                editedImage = lomographyImg.run();
                updateEffectSequence(lomographyImg);
                updateEditor(editedImage, "New lomography image");
            }
        });

        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.SOLARIZE_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Effect solarizeImg = getEffect(img, EffectType.SOLARIZE);
                editedImage = solarizeImg.run();
                updateEffectSequence(solarizeImg);
                updateEditor(editedImage, "New solarized image");
            }
        });

        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.SPLIT_TONE_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Effect splitToneImg = getEffect(img, EffectType.SPLIT_TONE);
                editedImage = splitToneImg.run();
                updateEffectSequence(splitToneImg);
                updateEditor(editedImage, "New split-tone image");
            }
        });

        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.HEAT_MAP_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Effect heatmapImg = getEffect(img, EffectType.HEAT_MAP);
                editedImage = heatmapImg.run();
                updateEffectSequence(heatmapImg);
                updateEditor(editedImage, "New heatmap image");
            }
        });

        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.INFRARED_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Effect infraredImg = getEffect(img, EffectType.INFRARED);
                editedImage = infraredImg.run();
                updateEffectSequence(infraredImg);
                updateEditor(editedImage, "New infrared image");
            }
        });

        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.PIXELATE_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sliderValuesEffect(img, EffectType.PIXELATE, 0, 50);
            }
        });

        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.VIGNETTE_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sliderValuesEffect(img, EffectType.VIGNETTE, 0, 1);
            }
        });

        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.TILT_SHIFT_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Effect tiltShiftImg = getEffect(img, EffectType.TILT_SHIFT);
                editedImage = tiltShiftImg.run();
                updateEffectSequence(tiltShiftImg);
                updateEditor(editedImage, "New tilt-shifted image");
            }
        });

        menuPanel.addItemToMenu("Filters", ButtonPanelConstants.PENCIL_SKETCH, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Effect pencilSketchImg = getEffect(img, EffectType.PENCIL_SKETCH);
                editedImage = pencilSketchImg.run();
                updateEffectSequence(pencilSketchImg);
                updateEditor(editedImage, "New pencil-sketched image");
            }
        });

        return menuPanel;
    }

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

        buttonPanel.addButtonToPanel(ButtonPanelConstants.RESIZE_TITLE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputValuesEffect(img, EffectType.RESIZE, new String[] {"Width", "Height"}, true);
            }
        });

        return buttonPanel;
    }


    public static void main(String[] args) {
        new Main();
    }
}
