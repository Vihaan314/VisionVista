package com.visionvista;

import com.visionvista.effects.Effect;
import com.visionvista.utils.FileHelper;
import com.visionvista.utils.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class EffectHistorySerializer implements Serializable {
    @Serial
    private static final long serialVersionUID = -1993788387174167380L;

    private transient ArrayList<Effect> effectsList = new ArrayList<>();
    private transient BufferedImage initialImage;

    public EffectHistorySerializer() {
    }

    public void setEffectsList(ArrayList<Effect> effectsList) {
        this.effectsList = effectsList;
    }

    public void setInitialImage(BufferedImage initialImage) {
        this.initialImage = initialImage;
    }

    public ArrayList<Effect> getEffectsList() {
        return effectsList;
    }

    public BufferedImage getInitialImage() {
        return initialImage;
    }

    public void serializeEffects(String filename) {
        effectsList = EditorState.getInstance().getEffectHistory().extractEffectsList();
        initialImage = EditorState.getInstance().getEffectHistory().getFirstImage();
        String directory = FileHelper.chooseDirectory();
        filename = directory + File.separator + FileHelper.getEditedFile(directory, filename, "dat", "_project-sequence").getName();
        System.out.println(filename);
        File serializeFile = new File(filename);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(serializeFile))) {
            writeObject(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        effectsList = (ArrayList<Effect>) in.readObject();
        initialImage = ImageIO.read(in);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        //Serialize effects list and initial image to reduce size
        out.writeObject(effectsList);
        ImageIO.write(initialImage, "png", out);
    }

    public void readEffects(File serializedFile) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(serializedFile))) {
            readObject(in);
            System.out.println(effectsList);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readSerializedEffects() {
        String filename = FileHelper.chooseFile(new String[]{".DAT"}, "Vision Vista project");
        File serializedFile = new File(filename);
        readEffects(serializedFile);
    }

}
