package com.visionvista;

import com.visionvista.commands.AICommands;
import com.visionvista.effects.Effect;
import com.visionvista.utils.FileHelper;
import com.visionvista.utils.ImageHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ImageHandler {
    public EffectHistory effectHistory;
    public ImageEditor editor;

    public String[] fileNameBroken;

    public ImageHandler() {
        this.effectHistory = new EffectHistory();
    }

    public void closeEditor() {
        editor.close();
    }

    public boolean openImage() {
        //Only allow image files to be opened
        String[] imageFormats = {"jpg", "jpeg", "png", "gif", "bmp"};
        JFileChooser fileChooser = FileHelper.addFileFilter(imageFormats, "Images");
        fileChooser.setDialogTitle("Select an image");
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            //If no selected file, return error
            if (selectedFile == null || !selectedFile.exists()) {
                JOptionPane.showMessageDialog(null, "Invalid file selected. Please choose a valid image file.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            String fileNameRaw = selectedFile.getName();
            //Splits by necessary parts of file name - name and extension
            fileNameBroken = fileNameRaw.split("[.]");
            try {
                //Fit image to window
                BufferedImage image = ImageHelper.fitImageToWindow(ImageIO.read(selectedFile));
                //Set the original image to be displayed
                EditorState.getInstance().setImage(image);
                //Add to effect history
                effectHistory.add(null, image);
                EditorState.getInstance().setState(effectHistory);

                editor = new ImageEditor("Vision Vista", fileNameBroken);
                editor.show();
                return true;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error loading image.");
                ex.printStackTrace();
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean openImageFromUrl() {
        //Modal dialog
        JDialog urlDialog = new JDialog();
        urlDialog.setTitle("Enter URL");
        urlDialog.setModal(true);
        urlDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        urlDialog.setLocationRelativeTo(null);
        urlDialog.setSize(400, 180);

        JPanel urlPanel = new JPanel();

        JLabel urlLabel = new JLabel("Enter URL:");
        urlLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JTextField urlField = new JTextField();
        JButton openImageButton = new JButton("Open Image");

        urlField.setPreferredSize(new Dimension(370, 30));

        //To keep track of whether the image was opened successfully
        //AtomicBoolean because logic happens in anonymous function
        final AtomicBoolean success = new AtomicBoolean(false);

        openImageButton.addActionListener(e -> {
            try {
                //Process error
                String urlText = urlField.getText().trim();
                if (urlText.isEmpty()) {
                    JOptionPane.showMessageDialog(urlDialog, "Please enter a valid URL.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //Convert text to URL
                URL imageURL = new URI(urlText).toURL();
                String[] urlParts = urlText.split("/");
                fileNameBroken = urlParts[urlParts.length - 1].split("\\.");
                //Extract image from URL and open in editor
                BufferedImage image = ImageHelper.fitImageToWindow(ImageIO.read(imageURL));
                EditorState.getInstance().setImage(image);
                effectHistory.add(null, image);
                EditorState.getInstance().setState(effectHistory);

                editor = new ImageEditor("Vision Vista", fileNameBroken);
                editor.show();
                //Successful
                success.set(true);
                urlDialog.dispose();
            } catch (IOException | URISyntaxException ex) {
                JOptionPane.showMessageDialog(urlDialog, "Error loading image from URL. Please check the URL and try again.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                success.set(false);
            }
        });

        urlPanel.add(urlLabel);
        urlPanel.add(urlField);
        urlPanel.add(openImageButton);
        urlDialog.add(urlPanel);
        urlDialog.setVisible(true);

        return success.get();
    }

    public boolean openRecentProject() {
        JFileChooser fileChooser = FileHelper.addFileFilter(new String[]{".dat"}, "Vision Vista Project");
        fileChooser.setDialogTitle("Select a Vision Vista Project file");
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            //Process error
            if (selectedFile == null || !selectedFile.exists()) {
                JOptionPane.showMessageDialog(null, "Invalid project file selected. Please choose a valid project file.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            String fileNameRaw = selectedFile.getName();
            //Splits by necessary parts of file name - name and extension
            fileNameBroken = fileNameRaw.split("[.]");

            try {
                //Read serialized project
                EffectHistorySerializer deserialized = new EffectHistorySerializer();
                deserialized.readEffects(selectedFile);
                //Extract components (image + effects list) to create the effect history and run the editor
                ArrayList<Effect> effectsList = deserialized.getEffectsList();
                BufferedImage initialImage = deserialized.getInitialImage();
                effectHistory = new EffectHistory();
                effectHistory.setEffectSequence(effectsList, initialImage);
                EditorState.getInstance().setState(effectHistory);

                editor = new ImageEditor("Vision Vista", fileNameBroken);
                editor.show();

                return true;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error loading project.");
                ex.printStackTrace();
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean generateImageFromPrompt(JFrame landingFrame) {
        AICommands aiCommands = new AICommands();
        try {
            aiCommands.createImageGenerationCommand(landingFrame).execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(landingFrame, "Error generating image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
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