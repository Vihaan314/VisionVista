package com.visionvista;

import com.visionvista.effects.Effect;
import com.visionvista.utils.Helper;
import com.visionvista.utils.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

//public class EffectHistorySerializer implements Serializable {
//    transient ArrayList<Effect> effectsList = new ArrayList<>();
//    transient BufferedImage initialImage;
//
//    //work on serializing effect history
//    public void extractEffectHistory() {
//        EffectHistory effectHistory = EditorState.getInstance().getEffectHistory();
//        for (Pair<Effect, BufferedImage> entry : effectHistory.getEffectSequence()) {
//            effectsList.add(entry.getLeft());
//        }
//        initialImage = effectHistory.getFirstImage();
//    }
//
//    public ArrayList<Effect> getEffectSequence() {
//        return this.effectsList;
//    }
//
//    public BufferedImage getInitialImage() {
//        return this.initialImage;
//    }
//
//    private void writeObject(ObjectOutputStream out) throws IOException {
//        out.defaultWriteObject();
//        out.writeInt(effectsList.size());
//        for (Effect effect : effectsList) {
//            out.writeObject(effect);
//        }
//        ImageIO.write(initialImage, "png", out);
//    }
//
//    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
//        in.defaultReadObject();
//        final int size = in.readInt();
//        effectsList = new ArrayList<>();
//        for (int i = 0; i < size; i++) {
//            Effect effect = (Effect) in.readObject();
//            effectsList.add(effect);
//        }
//        initialImage = ImageIO.read(in);
//    }
//
//    public void serializeImages(String filename) {
//        extractEffectHistory();
//        String directory = Helper.chooseDirectory();
//        filename = directory + File.separator + Helper.getEditedFile(directory, filename, "dat", "_image-sequence").getName();
//        System.out.println(filename);
//        File effectSerialize = new File(filename);
//        try {
//            FileOutputStream effectOutputFile = new FileOutputStream(effectSerialize.getAbsoluteFile());
//            ObjectOutputStream effectOut = new ObjectOutputStream(effectOutputFile);
//
//            effectOut.writeObject(this);
//
//            effectOut.close();
//            effectOutputFile.close();
//            System.out.println("Serialized");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void readSerializedImages() {
//        String filename = Helper.chooseFile(".dat");
//        File imageSerialize = new File(filename);
//        try {
//            FileInputStream imageInFile = new FileInputStream(imageSerialize.getAbsoluteFile());
//            ObjectInputStream imageIn = new ObjectInputStream(imageInFile);
//
//            EffectHistorySerializer deserialized = (EffectHistorySerializer) imageIn.readObject();
//            this.effectsList = deserialized.getEffectSequence();
//            this.initialImage = deserialized.getInitialImage();
//            System.out.println("EFFECTS LIST IN SERIALZIER " + effectsList);
//
//            imageInFile.close();
//            imageIn.close();
//            System.out.println("Read");
//        } catch (OptionalDataException e) {
//            e.printStackTrace();
//            System.out.println("Optional Data Exception: " + e.eof + " " + e.length);
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}

public class EffectHistorySerializer implements Serializable {
    private transient ArrayList<Effect> effectsList = new ArrayList<>();
    private transient BufferedImage initialImage;

    public EffectHistorySerializer() {
        // Default constructor
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
        String directory = Helper.chooseDirectory();
        filename = directory + File.separator + Helper.getEditedFile(directory, filename, "dat", "_image-sequence").getName();
        System.out.println(filename);
        File serializeFile = new File(filename);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(serializeFile))) {
            writeObject(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(effectsList);
        ImageIO.write(initialImage, "png", out); // Serialize BufferedImage as PNG
    }

    public void readSerializedEffects() {
        String filename = Helper.chooseFile(".dat");
        File serializeFile = new File(filename);
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(serializeFile))) {
            readObject(in);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        effectsList = (ArrayList<Effect>) in.readObject();
        initialImage = ImageIO.read(in); // Deserialize BufferedImage
    }
}
