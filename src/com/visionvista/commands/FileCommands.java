package com.visionvista.commands;

import com.visionvista.EditorState;
import com.visionvista.ImageDisplay;
import com.visionvista.ImageHandler;
import com.visionvista.ImageSaver;

public class FileCommands {
    private final ImageDisplay imageDisplay;
    private final ImageHandler imageHandler;

    public FileCommands(ImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
        this.imageHandler = new ImageHandler();
    }

    public Command createOpenImageCommand() {
        return () -> {
            imageHandler.openImage();
            imageDisplay.updateImageFromState();
        };
    }

    public Command createOpenImageFromUrlCommand() {
        return () -> {
            imageHandler.openImageFromUrl();
            imageDisplay.updateImageFromState();
        };
    }

    public Command createNewBlankImageCommand() {
        return () -> {
            imageHandler.createNewImage();
            imageDisplay.updateImageFromState();
        };
    }

    public Command createSaveImageCommand() {
        return () -> {
            ImageSaver imageSaver = new ImageSaver(imageDisplay.getFileNameDetails(), false);
            imageSaver.saveImage();
        };
    }

    public Command createSaveImageWithTextCommand() {
        return () -> {
            ImageSaver imageSaver = new ImageSaver(imageDisplay.getFileNameDetails(), true);
            imageSaver.saveImage();
        };
    }
}
