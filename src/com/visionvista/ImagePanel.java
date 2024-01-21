package com.visionvista;

import com.visionvista.effects.Effect;
import com.visionvista.effects.ImageHelper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class ImagePanel extends MenuPanel {
    BufferedImage img;
    ImageEditor editor;
    MenuPanel menuPanel;
    String[] file_name_broken;
    ArrayList<Effect> effectSequence;

    public ImagePanel(BufferedImage img, ImageEditor editor, String[] file_name_broken, ArrayList<Effect> effectSequence) {
        this.img = img;
        this.editor = editor;
        this.menuPanel = new MenuPanel();
        this.file_name_broken = file_name_broken;
        this.effectSequence = effectSequence;
    }

//    public MenuPanel setupMenuPanel() {
//        menuPanel.addItemToMenu("File", "Save", new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ImageSaver imageSaver = new ImageSaver(img, file_name_broken, false, effectHistory.getEffectSequence());
//                imageSaver.saveImage();
//            }
//        });
//
//        menuPanel.addItemToMenu("File", "Save with Text", new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ImageSaver imageSaver = new ImageSaver(img, file_name_broken, true, effectHistory.getEffectSequence());
//                imageSaver.saveImage();
//            }
//        });
//
//        menuPanel.addItemToMenu("File", "Save Effect Sequence", new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Serializer serializer = new Serializer();
//                String directory = serializer.getSerializeDirectory();
//                try {
//                    serializer.saveImageSequence(effectSequence, directory, file_name_broken[0]);
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        });
//
//        return menuPanel;
//    }
}
