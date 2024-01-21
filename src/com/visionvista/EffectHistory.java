package com.visionvista;

import com.visionvista.effects.Effect;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class EffectHistory {
    ArrayList<Effect> effectSequence = new ArrayList<>();
    int currentImage = 0;

    public void resetHistory() {
        this.currentImage = 0;
        this.effectSequence = new ArrayList<>(effectSequence.subList(0, 1));
    }

    public void add(Effect effect) {
        effectSequence.add(effect);
    }


    public void updateEffectSequence(Effect effect) {
        effectSequence.add(effect);
        if (this.currentImage+1 != this.getSize()-1) {
            this.setCurrentImage(this.getSize()-1);
        }
        else {
            this.updateCurrentImage(1);
        }
    }


    public void updateCurrentImage(int change) {
        this.currentImage += change;
    }

    public int getSize() {
        return effectSequence.size();
    }

    public void setCurrentImage(int amount) {
        this.currentImage = amount;
    }

    public BufferedImage getFirstImage() {
        return this.effectSequence.get(currentImage).run();
    }

    public BufferedImage getLastImage() {
        return this.effectSequence.get(effectSequence.size()-1).run();
    }

    public ArrayList<Effect> getEffectSequence() {
        return this.effectSequence;
    }

    public int getCurrentIndex() {
        return this.currentImage;
    }

    public BufferedImage getCurrentImage() {
        return this.effectSequence.get(currentImage).run();
    }

    public void printSequence() {
//        this.effectSequence.stream().forEach(System.out::print);
        effectSequence.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    @Override public String toString() {
        printSequence();
        return "Current Image: " + this.currentImage + this.effectSequence;
    }
}
