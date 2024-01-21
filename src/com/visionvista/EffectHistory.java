package com.visionvista;

import com.visionvista.effects.Effect;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class EffectHistory {
    ArrayList<Pair<Effect, BufferedImage>> effectSequence;

    public EffectHistory() {
        effectSequence = new ArrayList<>();
    }

    int currentImageIndex = 0;

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

    public void updateCurrentImage(int change) {
        this.currentImageIndex += change;
    }

    public int getSize() {
        return effectSequence.size();
    }

    public void setCurrentImageIndex(int amount) {
        this.currentImageIndex = amount;
    }

    public BufferedImage getFirstImage() {
        return this.effectSequence.get(currentImageIndex).getRight();
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
        return "Current Image: " + this.currentImageIndex + this.effectSequence;
    }
}
