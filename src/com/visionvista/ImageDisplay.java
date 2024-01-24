package com.visionvista;

import com.visionvista.effects.Effect;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class ImageDisplay {
    private JLabel imageLabel;
    private JPanel imagePanel;

    private String[] fileNameBroken;

    public ImageDisplay(JPanel editorPanel) {
        this.imageLabel = new JLabel(new ImageIcon(EditorState.getInstance().getImage()));
        editorPanel.add(imageLabel);
    }

    public void updateImageFromState() {
        BufferedImage currentImage = EditorState.getInstance().getImage();
        //Set currently displayed image to new image
        imageLabel.setIcon(new ImageIcon(currentImage));
    }

    public void updateImageByEffect(Effect effect) {
        BufferedImage currentImage = EditorState.getInstance().getImage();
        currentImage = effect.run(currentImage);
        EditorState.getInstance().getEffectHistory().add(effect, currentImage);
        EditorState.getInstance().setImage(currentImage);
        imageLabel.setIcon(new ImageIcon(currentImage));
    }

    public JLabel getImage() {
        return this.imageLabel;
    }

    public void setFileDetails(String[] fileNameBroken) {
        this.fileNameBroken = fileNameBroken;
    }

    public String[] getFileNameDetails() {
        return this.fileNameBroken;
    }
}
