package com.visionvista;

import com.visionvista.effects.Effect;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ImageSaver {
    private BufferedImage image;
    private final String[] fileNameBroken;
    private boolean withText;
    ArrayList<Effect> effectSequence;

    public ImageSaver(BufferedImage img, String[] file_name_broken, boolean withText) {
        image = img;
        fileNameBroken = file_name_broken;
        this.withText = withText;
        this.effectSequence = effectSequence;
    }

    private int getPower(int counter) {
        if (counter == 0 || counter == 1) {
            return 0;
        }
        return (int) Math.floor(Math.log10(counter))+1;
    }

    private File getEditedFile(String directory, String file_name, String type, int buffer, boolean log) {
        File editedFile = new File(directory + File.separator + file_name.split("[.]")[0] + ((log) ? "-VV_log.txt" : "-VV." + type));
        int counter = 0;
        while (editedFile.exists()) {
            counter += 1;
            editedFile = new File(directory + File.separator + editedFile.getName().split("[.]")[0].substring(0, editedFile.getName().split("[.]")[0].length()-(buffer+2*getPower(counter))) + "-" + counter + ((log) ? "-VV_log.txt" : "-VV." + type));
        }
        return editedFile;
    }

    public void saveTextToFile(String directory, String file_name, String file_extension) {
        File textFile = getEditedFile(directory, file_name, file_extension, 7, true);
        List<String> imageLogs = new ArrayList<>();
        imageLogs.add("Original File name: " + file_name + "." + file_extension);
        imageLogs.add("Directory saving in: " + textFile.getParent());
        imageLogs.add("File type: " + file_extension);
        imageLogs.add("Edited file name: " + textFile.getName().replace("_log", ""));

        List<String> effectVV = new ArrayList<>();

//        for (int i = 0; i < effectSequence.size(); i ++) {
//            effectVV.add(effectSequence.get(i).getEffectInitials());
//        }
        imageLogs.add("Copiable Effect Sequence: " + "[" + effectVV.toString().substring(3));
        imageLogs.add("");
        for (int i = 0; i <effectSequence.size(); i++) {
            String label = effectSequence.get(i).toString();
            imageLogs.add("Effect " + (i+1) + ((i == 0) ? ": " : " (" + effectVV.get(i) + "): ") + label);
        }
        try {
            Files.write(textFile.toPath(), imageLogs, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveImgToFile(String directory, String file_name, String type) {
        try {
            File imageFile = getEditedFile(directory, file_name, type, 3, false);
            ImageIO.write(image, "png", new File(imageFile.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveImage() {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);
        String file_name = fileNameBroken[0];
        System.out.println(file_name);
        String file_extension = fileNameBroken[1];
        File directory_chosen = new File(String.valueOf(f.getSelectedFile()));
        String directory = directory_chosen.getAbsolutePath();
        saveImgToFile(directory, file_name, file_extension);
        if (withText) {
            saveTextToFile(directory, file_name, file_extension);
        }
    }
}
