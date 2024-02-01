package com.visionvista;

import com.visionvista.effects.Effect;
import com.visionvista.utils.Helper;

import javax.imageio.ImageIO;
import javax.swing.*;
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

    public void saveTextToFile(String directory, String file_name, String file_extension) {
        File textFile = Helper.getEditedFile(directory, file_name, "txt", "_log");
        List<String> imageLogs = new ArrayList<>();
        imageLogs.add("Original File name: " + file_name + "." + file_extension);
        imageLogs.add("Directory saving in: " + textFile.getParent());
        imageLogs.add("File type: " + file_extension);
        imageLogs.add("Edited file name: " + textFile.getName().replace("_log", ""));
        imageLogs.add("\nEdits log:");
        for (Pair<Effect, BufferedImage> edit : EditorState.getInstance().getEffectHistory().getEffectSequence()) {
            Effect effect = edit.getLeft();
            if (effect != null) imageLogs.add("(" + (EditorState.getInstance().getEffectHistory().getEffectSequence().indexOf(edit)) + ") " + effect.toString());
        }
        try {
            Files.write(textFile.toPath(), imageLogs, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveImgToFile(String directory, String file_name, String file_type) {
        try {
            File imageFile = Helper.getEditedFile(directory, file_name, file_type, "");
            ImageIO.write(image, "png", new File(imageFile.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveImage() {
//        JFileChooser f = new JFileChooser();
//        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        f.showSaveDialog(null);
        String file_name = fileNameBroken[0];
        String file_extension = fileNameBroken[1];
        String directory = Helper.chooseDirectory();
//        File directory_chosen = new File(String.valueOf(f.getSelectedFile()));
//        String directory = directory_chosen.getAbsolutePath();
        saveImgToFile(directory, file_name, file_extension);
        if (withText) saveTextToFile(directory, file_name, file_extension);
    }
}
