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

    public String file_name_broken[];

    private MenuPanel menuPanel = new MenuPanel();

    public ImageHandler(ImageEditor editor, JFrame mainFrame) {
        this.effectHistory = new EffectHistory();
        this.editor = editor;
        this.mainFrame = mainFrame;
    }

    public void openImage() {
        //TODO PROBLEM STILL IS that imageeditor and menupanel still depend on each other
        //TODO BUT EDITOR DOESN't NEED MENU PANEL ANYMORE

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(mainFrame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String file_name_raw = selectedFile.getName();
            //Splits by necessary parts of file name - name and extension
            file_name_broken = file_name_raw.split("[.]");

            try {
                BufferedImage image = ImageIO.read(selectedFile);
                //Set the original image to be displayed
                EditorState.getInstance().setImage(image);
                //Add and keep to effect history
                effectHistory.add(null, image);
                EditorState.getInstance().setEffectHistory(effectHistory);

                editor = new ImageEditor("Image editor", menuPanel.setupMenuPanel());
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
                    //Process URL to necessary file parts
                    URL img_url = new URL(urlField.getText());
                    file_name_broken = urlField.getText().split("[/]");
                    file_name_broken = file_name_broken[file_name_broken.length-1].split("[.]");
                    //Setup program
                    BufferedImage image = ImageIO.read(img_url);
                    EditorState.getInstance().setImage(image);
                    effectHistory.add(null, image);
                    EditorState.getInstance().setEffectHistory(effectHistory);

                    editor = new ImageEditor("Image editor", menuPanel.setupMenuPanel());
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
        editor = new ImageEditor("Image editor - Draw", menuPanel.setupMenuPanel());
        editor.addButtonPanel(buttonPanel.setupButtonPanel(blankImg));
        editor.show();
//        inputValuesEffect(new ImageEdit(blank_img), "Blank", new String[]{"Width", "Height"});
    }

}
