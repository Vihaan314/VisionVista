package com.visionvista;

import java.awt.image.BufferedImage;


public class EditorState {
    private static EditorState INSTANCE;

    private BufferedImage image;
    private EffectHistory effectHistory;
    private StateBasedUIComponentGroup stateBasedUIComponentGroup;

    private EditorState() {
    }

    public void resetHistory() {

    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setState(EffectHistory effectHistory) {
        this.effectHistory = effectHistory;
        this.image = effectHistory.getCurrentImage();
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public EffectHistory getEffectHistory() {
        return this.effectHistory;
    }

    public static EditorState getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new EditorState();
        }
        return INSTANCE;
    }

}
