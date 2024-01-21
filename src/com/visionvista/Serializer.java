package com.visionvista;

import com.visionvista.effects.Effect;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Serializer {
    public String getSerializeDirectory() {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);

        String directory = String.valueOf(f.getSelectedFile());
        return directory;
    }

    public void saveImageSequence(ArrayList<Pair<Effect, BufferedImage>> effectSequence, String directory, String file_name) throws IOException {

        File effectSerialize = new File(directory + File.separator + file_name.split("[.]")[0] + "-sequence.dat");
        try {
            FileOutputStream effectOutputFile = new FileOutputStream(effectSerialize.getAbsoluteFile());
            ObjectOutputStream effectOut = new ObjectOutputStream(effectOutputFile);
//            effectOut.writeInt(effectSequence.size()); // Write the size of the list
//            for (Effect effect : effectSequence) {
//                effectOut.writeObject(effect); // This will invoke the custom writeObject method in the Effect class
//            }

            effectOut.close();
            effectOutputFile.close();
            System.out.println("Serialized");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
