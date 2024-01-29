package com.visionvista;

import com.visionvista.effects.Effect;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.SerializedFileFilter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;

public class ImageHandler {
    public EffectHistory effectHistory;
    public ImageEditor editor;

    public String file_name_broken[];

    public ImageHandler() {
        this.effectHistory = new EffectHistory();
    }

    public void openImage() {
        //TODO PROBLEM STILL IS that imageeditor and menupanel still depend on each other
        //TODO BUT EDITOR DOESN't NEED MENU PANEL ANYMORE
        //TODO DOENS"T IT AMKE SENSE TO HAVE AN IMAGE AS THE CONSTRUCTOR OF THE IMAGE EDITOR
        //TODO MENU PANEL STILL NEEDS EDITOR HOW TO DO

        //TODO MAYBE EVERYTIME I UPDATE EDITOR I INSTEAD UPDATE WITH IMAGE FROM EDITOR STATE AND ENSURE BEFORE THAT THAT EDITOSTATE IS SET WITH NEW IMAGE
        //TODO CURRENT PROBLEM LIES WITH ACTIONLISTENER NOT STORING LATEST VARIABLE
        //TODO TABS ABOVE IMAGE EDITOR SO EDITOR TABS
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
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

                editor = new ImageEditor("Image editor", file_name_broken);
                editor.show();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error loading image.");
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

                    editor = new ImageEditor("Image editor", file_name_broken);
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

    public void openRecentProject() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new SerializedFileFilter(".dat", "Serialized Vision Vista edits"));
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String file_name_raw = selectedFile.getName();
            //Splits by necessary parts of file name - name and extension
            file_name_broken = file_name_raw.split("[.]");

            try {
                FileInputStream imageInFile = new FileInputStream(selectedFile.getAbsoluteFile());
                ObjectInputStream imageIn = new ObjectInputStream(imageInFile);

                EffectHistorySerializer deserialized = (EffectHistorySerializer) imageIn.readObject();
                ArrayList<Effect> effectsList = deserialized.getEffectsList();
                BufferedImage initialImage = deserialized.getInitialImage();
                effectHistory = new EffectHistory();
                effectHistory.setEffectSequence(effectsList, initialImage);
                EditorState.getInstance().setEffectHistory(effectHistory);
                editor = new ImageEditor("Image editor", file_name_broken);
                editor.show();
//                BufferedImage image = ImageIO.read(selectedFile);
//                //Set the original image to be displayed
//                EditorState.getInstance().setImage(image);
//                //Add and keep to effect history
//                effectHistory.add(null, image);
//                EditorState.getInstance().setEffectHistory(effectHistory);
//
//                editor = new ImageEditor("Image editor", file_name_broken);
//                editor.show();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error loading image.");
                ex.printStackTrace();
            }
        }
    }

    public void createNewImage() {
        //TODO
        BufferedImage blankImg = ImageHelper.createBlankImage();
        ImageHelper.createBlankImage();
//        MenuPanel menuPanel = new MenuPanel();
        ButtonPanel buttonPanel = new ButtonPanel();
        editor = new ImageEditor("Image editor - Draw", null);
//        editor.addButtonPanel(buttonPanel.setupButtonPanel(blankImg));
        editor.show();
//        inputValuesEffect(new ImageEdit(blank_img), "Blank", new String[]{"Width", "Height"});
    }

}