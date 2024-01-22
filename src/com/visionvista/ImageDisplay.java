package com.visionvista;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class ImageDisplay {
    private JLabel imageLabel;
    private JPanel imagePanel;

    //TODO FIX
    public ImageDisplay(JPanel editorPanel) {
        this.imageLabel = new JLabel(new ImageIcon(EditorState.getInstance().getImage()));
        editorPanel.add(imageLabel);
    }

    public void updateEditorFromState() {
        BufferedImage currentImage = EditorState.getInstance().getImage();
        //Set currently displayed image to new image
        imageLabel.setIcon(new ImageIcon(currentImage));
    }

    public JLabel getImage() {
        return this.imageLabel;
    }
}
