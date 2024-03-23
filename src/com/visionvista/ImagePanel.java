//package com.visionvista;
//
//public class ImagePanel extends MenuPanel {
//    ImageDisplay imageDisplay;
//    ImageTimeline imageTimeline;
//    EffectControls effectControls;

//    public ImagePanel(ImageDisplay imageDisplay, ImageTimeline imageTimeline, EffectControls effectControls) {
//        super(imageDisplay, imageTimeline, effectControls);
//    }

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
//}
