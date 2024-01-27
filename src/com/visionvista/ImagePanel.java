package com.visionvista;

import com.visionvista.effects.Effect;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImagePanel extends MenuPanel {
    BufferedImage img;
    ImageEditor editor;
    MenuPanel menuPanel;
    String[] file_name_broken;
    ArrayList<Effect> effectSequence;

    public ImagePanel(ImageDisplay imageDisplay, ImageTimeline imageTimeline) {
        super(imageDisplay, imageTimeline);
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
