package com.visionvista;

import com.visionvista.effects.Effect;
import com.visionvista.utils.Helper;
import com.visionvista.utils.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class EffectHistorySerializer implements Serializable {
    EffectHistory effectHistory;

    //work on serializing effect history
    public void extractEffectHistory() {
        effectHistory = EditorState.getInstance().getEffectHistory();
    }

    public EffectHistory getEffectHistory() {
        return this.effectHistory;
    }

    public void serializeImages(String filename) {
        extractEffectHistory();
        String directory = Helper.chooseDirectory();
        filename = directory + File.separator + Helper.getEditedFile(directory, filename, "dat", "_image-sequence").getName();
        System.out.println(filename);
        File effectSerialize = new File(filename);
        try {
            FileOutputStream effectOutputFile = new FileOutputStream(effectSerialize.getAbsoluteFile());
            ObjectOutputStream effectOut = new ObjectOutputStream(effectOutputFile);

            effectOut.writeObject(effectHistory);

            effectOut.close();
            effectOutputFile.close();
            System.out.println("Serialized");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readSerializedImages() {
        String filename = Helper.chooseFile();
        File imageSerialize = new File(filename);
        try {
            FileInputStream imageInFile = new FileInputStream(imageSerialize.getAbsoluteFile());
            ObjectInputStream imageIn = new ObjectInputStream(imageInFile);

            this.effectHistory = (EffectHistory) imageIn.readObject();

            imageInFile.close();
            imageIn.close();
            System.out.println("Read");
        } catch (OptionalDataException e) {
            e.printStackTrace();
            System.out.println("Optional Data Exception: " + e.eof + " " + e.length);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
