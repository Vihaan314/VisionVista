package com.visionvista;

import com.visionvista.effects.Effect;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serializer implements Serializable {
    transient ArrayList<BufferedImage> imagesList;
    ArrayList<Effect> effectsList;




//
//    public void saveEffectPreset(String file_name) throws IOException {
//        String directory = getSerializeDirectory();
//        FileOutputStream fos = new FileOutputStream(directory+File.separator+file_name+".ser");
//        ObjectOutputStream oos = new ObjectOutputStream(fos);
//        extractEffectList();
//        oos.writeObject(effectsList);
//    }
//
//    public List<Effect> readEffectList(String directory) {
//
//    }
//
//    public void saveImageSequence(String filename) throws IOException {
//        String directory = getSerializeDirectory();
//        extractImageList();
//        ImageSerializer imageSerializer = new ImageSerializer(imagesList);
//        imageSerializer.serializeImage(directory, filename);
//    }
}
