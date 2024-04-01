package com.visionvista.commands;

import com.visionvista.ImageDisplay;
import com.visionvista.ImageHandler;
import com.visionvista.ImageSaver;
import com.visionvista.StateBasedUIComponentGroup;

public class FileCommands {
    private final ImageDisplay imageDisplay;
    private final ImageHandler imageHandler;

    public FileCommands(StateBasedUIComponentGroup stateBasedUIComponentGroup) {
        this.imageDisplay = (ImageDisplay) stateBasedUIComponentGroup.getUIComponent(ImageDisplay.class);
        this.imageHandler = new ImageHandler();
    }

    public Command createOpenImageCommand() {
        return () -> {
            imageHandler.openImage();
            imageDisplay.updateFromState();
        };
    }

    public Command createOpenImageFromUrlCommand() {
        return () -> {
            imageHandler.openImageFromUrl();
            imageDisplay.updateFromState();
        };
    }

    public Command createNewBlankImageCommand() {
        return () -> {
            imageHandler.createNewImage();
            imageDisplay.updateFromState();
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
