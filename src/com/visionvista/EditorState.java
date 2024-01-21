package com.visionvista;

import java.awt.image.BufferedImage;


public class EditorState {
    private static EditorState INSTANCE;
    private String info = "Initial info class";

    private BufferedImage image;
    private EffectHistory effectHistory;

    private EditorState() {

    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setEffectHistory(EffectHistory effectHistory) {
        this.effectHistory = effectHistory;
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
