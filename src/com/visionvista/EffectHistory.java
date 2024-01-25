package com.visionvista;

import com.visionvista.effects.Effect;
import com.visionvista.utils.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class EffectHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient ArrayList<Pair<Effect, BufferedImage>> effectSequence;
    int currentImageIndex = 0;

    public EffectHistory() {
        effectSequence = new ArrayList<>();
    }

    public void resetHistory() {
        this.currentImageIndex = 0;
        this.effectSequence = new ArrayList<>(effectSequence.subList(0, 1));
    }

    public void add(Effect effect, BufferedImage image) {
        Pair<Effect, BufferedImage> imageEntry = new Pair<>(effect, image);
        effectSequence.add(imageEntry);
        if (this.currentImageIndex +1 != this.getSize()-1) {
            this.setCurrentImageIndex(this.getSize()-1);
        }
        else {
            this.updateCurrentImage(1);
        }
    }

    public void setEffectSequence(ArrayList<Effect> effectSequence, BufferedImage initialImage) {
        this.effectSequence = new ArrayList<>();
        this.add(null, initialImage);
        for (Effect effect : effectSequence) {
            if (effect != null) {
                initialImage = effect.run(initialImage);
                this.add(effect, initialImage);
            }
        }
        this.currentImageIndex = effectSequence.size()-1;
    }

    public void updateCurrentImage(int change) {
        this.currentImageIndex += change;
        EditorState.getInstance().setImage(getCurrentImage());
    }

    public int getSize() {
        return effectSequence.size();
    }

    public void setCurrentImageIndex(int amount) {
        this.currentImageIndex = amount;
        EditorState.getInstance().setImage(getCurrentImage());
    }

    public BufferedImage getFirstImage() {
        return this.effectSequence.get(0).getRight();
    }

    public BufferedImage getLastImage() {
        return this.effectSequence.get(effectSequence.size()-1).getRight();
    }

    public ArrayList<Pair<Effect, BufferedImage>> getEffectSequence() {
        return this.effectSequence;
    }

    public int getCurrentIndex() {
        return this.currentImageIndex;
    }

    public BufferedImage getCurrentImage() {
        return this.effectSequence.get(currentImageIndex).getRight();
    }

    public void printSequence() {
        effectSequence.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    @Override public String toString() {
        printSequence();
        return "Current image index: " + this.currentImageIndex + ", Size: " + effectSequence.size();
    }

//    private void writeObject(ObjectOutputStream out) throws IOException {
//        out.defaultWriteObject(); // Writes currentImageIndex
//        out.writeInt(effectSequence.size());
//        for (Pair<Effect, BufferedImage> pair : effectSequence) {
//            out.writeObject(pair.getLeft()); // Serialize Effect
//            ImageIO.write(pair.getRight(), "png", out); // Serialize BufferedImage
//        }
//    }
//
//    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
//        in.defaultReadObject(); // Reads currentImageIndex
//        int size = in.readInt();
//        effectSequence = new ArrayList<>();
//        for (int i = 0; i < size; i++) {
//            Effect effect = (Effect) in.readObject(); // Deserialize Effect
//            BufferedImage image = ImageIO.read(in); // Deserialize BufferedImage
//            System.out.println(image);
//            effectSequence.add(new Pair<>(effect, image));
//        }
//    }
}
