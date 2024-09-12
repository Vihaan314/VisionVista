package com.visionvista;

import com.visionvista.effects.Effect;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.Pair;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class EffectHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient ArrayList<Pair<Effect, BufferedImage>> effectSequence;
    int currentImageIndex = -1;

    public EffectHistory() {
        effectSequence = new ArrayList<>();
    }

    public void resetHistory() {
        this.currentImageIndex = 0;
        this.effectSequence = new ArrayList<>(effectSequence.subList(0, 1));
    }

    public void add(Effect effect, BufferedImage image) {
        Pair<Effect, BufferedImage> imageEntry = new Pair<>(effect, image);

        if (currentImageIndex < this.getSize() - 1) {
            effectSequence = new ArrayList<>(effectSequence.subList(0, currentImageIndex + 1));
        }
        effectSequence.add(imageEntry);
        this.setCurrentImageIndex(this.getSize() - 1);
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

    public ArrayList<Effect> extractEffectsList() {
        return effectSequence.stream().map(Pair::left).collect(Collectors.toCollection(ArrayList::new));
    }

    public void incrementCurrentImage(int change) {
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
        return this.effectSequence.get(0).right();
    }

    public ArrayList<Pair<Effect, BufferedImage>> getEffectSequence() {
        return this.effectSequence;
    }

    public int getCurrentIndex() {
        return this.currentImageIndex;
    }

    public BufferedImage getCurrentImage() {
        return this.effectSequence.get(currentImageIndex).right();
    }

    public void printSequence() {
        System.out.println(effectSequence.stream().map(Object::toString)
                .collect(Collectors.joining(", ")));
    }

    @Override public String toString() {
        printSequence();
        return "Current image index: " + this.currentImageIndex + ", Size: " + effectSequence.size();
    }

    public Effect getEffectFromIndex(int index) {
        return this.effectSequence.get(index).left();
    }

}
