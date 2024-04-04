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

    public String fileNameBroken[];

    public ImageHandler() {
        this.effectHistory = new EffectHistory();
    }

    public void openImage() {
        //TODO TABS ABOVE IMAGE EDITOR SO EDITOR TABS
        //TODO MAKE ONLY SHOW IMAGE FILES
        String[] imageFormats = {"jpg", "jpeg", "png", "gif", "bmp"};
        JFileChooser fileChooser = FileHelper.addFileFilter(imageFormats, "Images");
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileNameRaw = selectedFile.getName();
            //Splits by necessary parts of file name - name and extension
            fileNameBroken = fileNameRaw.split("[.]");

            try {
                BufferedImage image = ImageIO.read(selectedFile);
                //Try to resize image if too large
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int screenWidth = (int) screenSize.getWidth();
                int screenHeight = (int) screenSize.getHeight();
                if (image.getWidth() > screenWidth) {
                    int convertRatio = image.getWidth() / screenWidth;
                    System.out.println("width too");
                    image = new Resize(screenWidth, image.getHeight() / convertRatio).run(image);
                }
                else if (image.getHeight() > screenHeight) {
                    System.out.println("Height too");
                    int convertRatio = image.getHeight() / screenHeight;
                    image = new Resize(image.getWidth() / convertRatio, screenHeight).run(image);
                }

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

        openImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //Process URL to necessary file parts
                    URL img_url = new URI(urlField.getText()).toURL();
                    fileNameBroken = urlField.getText().split("[/]");
                    fileNameBroken = fileNameBroken[fileNameBroken.length-1].split("[.]");
                    //Setup program
                    BufferedImage image = ImageIO.read(img_url);
                    EditorState.getInstance().setImage(image);
                    effectHistory.add(null, image);
                    EditorState.getInstance().setState(effectHistory);

                    editor = new ImageEditor("Vision Vista", fileNameBroken);
                    editor.show();
                } catch (IOException | URISyntaxException ex) {
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
        JFileChooser fileChooser = FileHelper.addFileFilter(new String[] {".dat"}, "Vision Vista Project");
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String file_name_raw = selectedFile.getName();
            //Splits by necessary parts of file name - name and extension
            fileNameBroken = file_name_raw.split("[.]");

            try {
                FileInputStream imageInFile = new FileInputStream(selectedFile.getAbsoluteFile());
                ObjectInputStream imageIn = new ObjectInputStream(imageInFile);

                EffectHistorySerializer deserialized = (EffectHistorySerializer) imageIn.readObject();
                ArrayList<Effect> effectsList = deserialized.getEffectsList();
                BufferedImage initialImage = deserialized.getInitialImage();
                effectHistory = new EffectHistory();
                effectHistory.setEffectSequence(effectsList, initialImage);
                EditorState.getInstance().setState(effectHistory);
                editor = new ImageEditor("Vision Vista", fileNameBroken);
                editor.show();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error loading image.");
                ex.printStackTrace();
            }
        }
    }

    public void generateImageFromPrompt(JFrame landingFrame) throws Exception {
        AICommands aiCommands = new AICommands();
        aiCommands.createImageGenerationCommand(landingFrame).execute();
    }

    public void createNewImage() {
        //TODO
        BufferedImage blankImg = ImageHelper.createBlankImage();
        EditorState.getInstance().setImage(blankImg);
        editor = new ImageEditor("Vision Vista - Draw", null);
        editor.show();
//        inputValuesEffect(new ImageEdit(blank_img), "Blank", new String[]{"Width", "Height"});
    }

}