package com.visionvista;

import com.visionvista.effects.Effect;
import com.visionvista.utils.Helper;
import com.visionvista.utils.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class ImageSerializer implements Serializable {
    transient ArrayList<BufferedImage> imagesList = new ArrayList<>();

    public void extractImageList() {
        ArrayList<Pair<Effect, BufferedImage>> effectHistory = EditorState.getInstance().getEffectHistory().getEffectSequence();
        for (Pair<Effect, BufferedImage> entry : effectHistory) {
            imagesList.add(entry.getRight());
        }
    }


    public void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(imagesList.size()); // how many images are serialized?
        for (BufferedImage image : imagesList) {
            ImageIO.write(image, "png", out); // png is lossless
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        final int imageCount = in.readInt();
        imagesList = new ArrayList<BufferedImage>(imageCount);
        for (int i=0; i<imageCount; i++) {
            imagesList.add(ImageIO.read(in));
        }
    }

    public ArrayList<BufferedImage> getImagesList() {
        return this.imagesList;
    }

    public void serializeImages(String filename) {
        extractImageList();
        String directory = Helper.chooseDirectory();
        File effectSerialize = new File(directory + File.separator + filename.split("[.]")[0] + "-sequence.dat");
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

    public void readSerializedImages() {
        String filename = Helper.chooseFile();
        File imageSerialize = new File(filename);
        try {
            FileInputStream imageInFile = new FileInputStream(imageSerialize.getAbsoluteFile());
            ObjectInputStream imageIn = new ObjectInputStream(imageInFile);

            ImageSerializer deserialized = (ImageSerializer) imageIn.readObject();
            this.imagesList = deserialized.getImagesList();

            imageInFile.close();
            imageIn.close();
            System.out.println("Read");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
