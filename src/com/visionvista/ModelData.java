package com.visionvista;

public class ModelData {
    private String chosenModel;
    private String chosenQuality;

    public ModelData() {
        this.chosenModel = "dall-e-3";
        this.chosenQuality = "hd";
    }

    public String getChosenQuality() {
        return chosenQuality;
    }

    public String getChosenModel() {
        return chosenModel;
    }
}
