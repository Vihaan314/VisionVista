package com.visionvista;

import com.visionvista.effects.Effect;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class ImageDisplay implements StateBasedUIComponent{
    private final JLabel imageLabel;

    private String[] fileNameBroken;

    public ImageDisplay() {
        this.imageLabel = new JLabel(new ImageIcon(EditorState.getInstance().getImage()));
    }

    @Override
    public void updateFromState() {
        BufferedImage currentImage = EditorState.getInstance().getImage();
        //Set currently displayed image to new image
        imageLabel.setIcon(new ImageIcon(currentImage));
    }

    public void displayTemporaryImage(BufferedImage tempImage) {
        //Temporarily display image without adding it to history (for effect previews)
        imageLabel.setIcon(new ImageIcon(tempImage));
    }

    public void updateImageByEffect(Effect effect) {
        BufferedImage currentImage = EditorState.getInstance().getImage();
        currentImage = effect.run(currentImage);
        EditorState.getInstance().getEffectHistory().add(effect, currentImage);
        EditorState.getInstance().setImage(currentImage);
        imageLabel.setIcon(new ImageIcon(currentImage));
    }

    public JLabel getImageLabel() {
        return this.imageLabel;
    }

    public void setFileDetails(String[] fileNameBroken) {
        this.fileNameBroken = fileNameBroken;
    }

    public String[] getFileNameDetails() {
        return this.fileNameBroken;
    }

    @Override
    public String toString() {
        return EditorState.getInstance().getImage().toString();
    }
}
