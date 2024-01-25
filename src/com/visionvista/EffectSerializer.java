package com.visionvista;

import com.visionvista.effects.Effect;
import com.visionvista.utils.Helper;
import com.visionvista.utils.Pair;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class EffectSerializer implements Serializable {
    transient ArrayList<Effect> effectsList = new ArrayList<>();

    public void extractEffectList() {
        ArrayList<Pair<Effect, BufferedImage>> effectHistory = EditorState.getInstance().getEffectHistory().getEffectSequence();
        for (Pair<Effect, BufferedImage> entry : effectHistory) {
            effectsList.add(entry.getLeft());
        }
    }

    public ArrayList<Effect> getEffectsList() {
        return this.effectsList;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(effectsList.size());
        for (Effect effect : effectsList) {
            out.writeObject(effect);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        final int size = in.readInt();
        effectsList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Effect effect = (Effect) in.readObject();
            effectsList.add(effect);
        }
    }

    public void serializeEffects(String filename) {
        extractEffectList();
        String directory = Helper.chooseDirectory();
        filename = directory + File.separator + Helper.getEditedFile(directory, filename, "dat", "_effects-sequence").getName();
        System.out.println(filename);
        File effectSerialize = new File(filename);
        try {
            FileOutputStream effectOutputFile = new FileOutputStream(effectSerialize.getAbsoluteFile());
            ObjectOutputStream effectOut = new ObjectOutputStream(effectOutputFile);

            effectOut.writeObject(this);

            effectOut.close();
            effectOutputFile.close();
            System.out.println("Serialized");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readSerializedEffects() {
        String filename = Helper.chooseFile(".dat");
        File effectSerialize = new File(filename);
        try {
            FileInputStream effectInFile = new FileInputStream(effectSerialize.getAbsoluteFile());
            ObjectInputStream effectIn = new ObjectInputStream(effectInFile);

            EffectSerializer deserialized = (EffectSerializer) effectIn.readObject();
            this.effectsList = deserialized.getEffectsList();
            //maybe update editor state
            effectInFile.close();
            effectIn.close();
            System.out.println("Read");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
