package com.visionvista;

import com.visionvista.commands.AICommands;
import com.visionvista.effects.Effect;
import com.visionvista.effects.transformation.Resize;
import com.visionvista.utils.FileHelper;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.ListFileFilter;

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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class ImageHandler {
    public EffectHistory effectHistory;
    public ImageEditor editor;

    public String[] fileNameBroken;

    public ImageHandler() {
        this.effectHistory = new EffectHistory();
    }

    public void openImage() {
        String[] imageFormats = {"jpg", "jpeg", "png", "gif", "bmp"};
        JFileChooser fileChooser = FileHelper.addFileFilter(imageFormats, "Images");
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileNameRaw = selectedFile.getName();
            //Splits by necessary parts of file name - name and extension
            fileNameBroken = fileNameRaw.split("[.]");

            try {
                BufferedImage image = ImageHelper.fitImageToWindow(ImageIO.read(selectedFile));
                //Set the original image to be displayed
                EditorState.getInstance().setImage(image);
                //Add and keep to effect history
                effectHistory.add(null, image);
                EditorState.getInstance().setState(effectHistory);

                editor = new ImageEditor("Vision Vista", fileNameBroken);
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

        openImageButton.addActionListener(e -> {
            try {
                //Process URL to necessary file parts
                URL imageURL = new URI(urlField.getText()).toURL();
                fileNameBroken = urlField.getText().split("/");
                fileNameBroken = fileNameBroken[fileNameBroken.length-1].split("[.]");
                //Setup editor
                BufferedImage image = ImageHelper.fitImageToWindow(ImageIO.read(imageURL));
                EditorState.getInstance().setImage(image);
                effectHistory.add(null, image);
                EditorState.getInstance().setState(effectHistory);

                editor = new ImageEditor("Vision Vista", fileNameBroken);
                editor.show();
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        urlPanel.add(urlLabel);
        urlPanel.add(urlField);
        urlPanel.add(openImageButton);
        urlFrame.add(urlPanel);
        urlFrame.setVisible(true);
    }

    public void openRecentProject() {
        JFileChooser fileChooser = FileHelper.addFileFilter(new String[] {".dat"}, "Vision Vista Project");
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileNameRaw = selectedFile.getName();
            //Splits by necessary parts of file name - name and extension
            fileNameBroken = fileNameRaw.split("[.]");

            try {
                //Read serialized project
                EffectHistorySerializer deserialized = new EffectHistorySerializer();
                deserialized.readEffects(selectedFile);
                //Extract necessary components to generate history sequence and run editor
                ArrayList<Effect> effectsList = deserialized.getEffectsList();
                BufferedImage initialImage = deserialized.getInitialImage();
                effectHistory = new EffectHistory();
                effectHistory.setEffectSequence(effectsList, initialImage);
                EditorState.getInstance().setState(effectHistory);
                editor = new ImageEditor("Vision Vista", fileNameBroken);
                editor.show();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error loading project.");
                ex.printStackTrace();
            }
        }
    }

    public void generateImageFromPrompt(JFrame landingFrame) {
        AICommands aiCommands = new AICommands();
        try {
            aiCommands.createImageGenerationCommand(landingFrame).execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(landingFrame, "Error generating image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    public void createNewImage() {
        //Long term feature
        BufferedImage blankImg = ImageHelper.createBlankImage();
        EditorState.getInstance().setImage(blankImg);
        editor = new ImageEditor("Vision Vista - Draw", null);
        editor.show();
    }
}