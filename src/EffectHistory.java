import Effects.Effect;

import java.util.ArrayList;

public class EffectHistory {
    ArrayList<Effect> effectSequence;
    int currentImage;

    public EffectHistory(ArrayList<Effect> effectSequence) {
        this.effectSequence = effectSequence;
        this.currentImage = 0;
    }

    public void add(Effect effect) {
        effectSequence.add(effect);
    }

    public void updateCurrent(int amount) {
        this.currentImage += amount;
    }

    public int getSize() {
        return effectSequence.size();
    }

    public ArrayList<Effect> getEffectSequence() {
        return this.effectSequence;
    }

    public int getCurrentImage() {
        return this.currentImage;
    }
}
