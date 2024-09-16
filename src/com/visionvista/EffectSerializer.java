package com.visionvista;

import com.visionvista.effects.Effect;
import com.visionvista.utils.FileHelper;
import com.visionvista.utils.Pair;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class EffectSerializer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1705697157231620585L;

    transient ArrayList<Effect> effectsList = new ArrayList<>();

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
        effectsList = EditorState.getInstance().getEffectHistory().extractEffectsList();
        //Choose directory to save sequence in
        String directory = FileHelper.chooseDirectory();
        if (directory != null) {
            filename = directory + File.separator + FileHelper.getEditedFile(directory, filename, "dat", "_effects-sequence").getName();
            File effectSerialize = new File(filename);

            try {
                FileOutputStream effectOutputFile = new FileOutputStream(effectSerialize.getAbsoluteFile());
                ObjectOutputStream effectOut = new ObjectOutputStream(effectOutputFile);

                effectOut.writeObject(this);

                effectOut.close();
                effectOutputFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readSerializedEffects() {
        //Allow user to chooser serialized (.dat) files
        String filename = FileHelper.chooseFile(new String[]{".DAT"}, "Vision Vista Effect Sequence", "Select a Vision Vista effect sequence");
        File effectSerialize = new File(filename);
        try {
            FileInputStream effectInFile = new FileInputStream(effectSerialize.getAbsoluteFile());
            ObjectInputStream effectIn = new ObjectInputStream(effectInFile);

            EffectSerializer deserialized = (EffectSerializer) effectIn.readObject();
            this.effectsList = deserialized.getEffectsList();
            //Maybe update editor state
            effectInFile.close();
            effectIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
