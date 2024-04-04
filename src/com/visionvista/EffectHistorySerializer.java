package com.visionvista;

import com.visionvista.effects.Effect;
import com.visionvista.utils.FileHelper;
import com.visionvista.utils.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class EffectHistorySerializer implements Serializable {
    private transient ArrayList<Effect> effectsList = new ArrayList<>();
    private transient BufferedImage initialImage;

    public EffectHistorySerializer() {
    }

    public void extractEffectHistory() {
        EffectHistory effectHistory = EditorState.getInstance().getEffectHistory();
        for (Pair<Effect, BufferedImage> entry : effectHistory.getEffectSequence()) {
            effectsList.add(entry.getLeft());
        }
        initialImage = effectHistory.getFirstImage();
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
        extractEffectHistory();
        String directory = FileHelper.chooseDirectory();
        filename = directory + File.separator + FileHelper.getEditedFile(directory, filename, "dat", "_image-sequence").getName();
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
        out.writeObject(effectsList);
        ImageIO.write(initialImage, "png", out);
    }

    public void readSerializedEffects() {
        String filename = FileHelper.chooseFile(new String[]{".DAT"}, "Vision Vista project");
        File serializeFile = new File(filename);
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(serializeFile))) {
            readObject(in);
            System.out.println(effectsList);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
