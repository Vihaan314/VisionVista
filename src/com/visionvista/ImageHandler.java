package com.visionvista;

import com.visionvista.effects.Effect;
import com.visionvista.effects.ImageHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageHandler {
    public EffectHistory effectHistory;
    public ImageEditor editor;
    public JFrame mainFrame;

    public String file_name_raw;
    public String file_name_broken[];

    private MenuPanel menuPanel = new MenuPanel();

    public ImageHandler(EffectHistory effectHistory, ImageEditor editor, JFrame mainFrame) {
        this.effectHistory = effectHistory;
        this.editor = editor;
        this.mainFrame = mainFrame;
    }

    public void openImage() {
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
                menuPanel.setMenuParameters(effectHistory);
                editor = new ImageEditor("Image editor", effectHistory.getLastImage(), menuPanel.setupMenuPanel(effectHistory.getLastImage()));
                editor.show();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, "Error loading image.");
                ex.printStackTrace();
            }
        }
    }

    public void openImageFromUrl() {
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
                    editor = new ImageEditor("Image editor", effectHistory.getFirstImage(), menuPanel.setupMenuPanel(effectHistory.getFirstImage()));
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

    public void createNewImage() {
        //TODO
        BufferedImage blankImg = ImageHelper.createBlankImage();
        ImageHelper.createBlankImage();
        MenuPanel menuPanel = new MenuPanel();
        ButtonPanel buttonPanel = new ButtonPanel();
        editor = new ImageEditor("Image editor - Draw", blankImg, menuPanel.setupMenuPanel(blankImg));
        editor.addButtonPanel(buttonPanel.setupButtonPanel(blankImg));
        editor.show();
//        inputValuesEffect(new ImageEdit(blank_img), "Blank", new String[]{"Width", "Height"});
    }

}
