package com.visionvista.utils;

import com.visionvista.EditorState;
import com.visionvista.effects.Effect;
import com.visionvista.utils.FileHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import com.visionvista.utils.Pair;

public class ImageSaver {
    private BufferedImage image;
    private final String[] fileNameBroken;
    private boolean withText;

    public ImageSaver(String[] file_name_broken, boolean withText) {
        this.image = EditorState.getInstance().getImage();
        this.fileNameBroken = file_name_broken;
        this.withText = withText;
    }

    public void saveTextToFile(String directory, String fileName, String fileExtension) {
        File textFile = FileHelper.getEditedFile(directory, fileName, "txt", "_log");
        List<String> imageLogs = new ArrayList<>();
        imageLogs.add("Original File name: " + fileName + "." + fileExtension);
        imageLogs.add("Directory saving in: " + textFile.getParent());
        imageLogs.add("File type: " + fileExtension);
        imageLogs.add("Edited file name: " + textFile.getName().replace("_log", "").replace("txt", fileExtension));
        imageLogs.add("\nEdits log:");
        //Add each applied effect as formatted string
        for (Pair<Effect, BufferedImage> edit : EditorState.getInstance().getEffectHistory().getEffectSequence()) {
            Effect effect = edit.left();
            if (effect != null) imageLogs.add("(" + (EditorState.getInstance().getEffectHistory().getEffectSequence().indexOf(edit)) + ") " + effect);
        }
        try {
            Files.write(textFile.toPath(), imageLogs, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveImgToFile(File imageFile) {
        try {
            ImageIO.write(image, "png", imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveImage() {
        String defaultFileName = fileNameBroken[0];
        String fileExtension = fileNameBroken[1];
        File editedFile = FileHelper.getEditedFile(FileHelper.getDefaultFileChooserDirectory().getAbsolutePath(), defaultFileName, fileExtension, "");
        File saveFile = FileHelper.chooseSaveFile(editedFile.getName().split("\\.")[0], "png", "Image Files (*jpg, *jpeg, *png, *gif, *bmp)");
        if (saveFile != null) {
            saveImgToFile(saveFile);
            if (withText) saveTextToFile(saveFile.getParent(), defaultFileName, fileExtension);
        }
    }
}
