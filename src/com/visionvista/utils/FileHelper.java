package com.visionvista.utils;

import javax.swing.*;
import java.io.File;

public class FileHelper {
    private static int getPower(int counter) {
        if (counter == 0 || counter == 1) {
            return 0;
        }
        return (int) Math.floor(Math.log10(counter))+1;
    }

    public static File getEditedFile(String directory, String fileName, String type, String customExtension) {
        //The constant letter padding of Vision Vista brand
        int buffer = customExtension.length()-1 + 4;
        String directoryConstant = directory + File.separator;
        //Create initial iteration of file
        File editedFile = new File(directoryConstant +
                fileName.split("[.]")[0] + ("-VV" + customExtension + "." + type));
        int counter = 0;
        //If the generated file name already exists, insert a number until it is unique
        while (editedFile.exists()) {
            counter += 1;
            //Automatically calculating where to insert the number in relation to all the components of the file
            editedFile = new File(directoryConstant + editedFile.getName().split("[.]")[0].substring(
                    0, editedFile.getName().split("[.]")[0].length()-(buffer+2*getPower(counter))) + "-" + counter + "-VV" + customExtension + "." + type);
        }
        if (type.isEmpty()) {
            editedFile = new File(directoryConstant + editedFile.getName().split("[.]")[0]);
        }
        return editedFile;
    }

    public static String chooseDirectory() {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);

        return String.valueOf(f.getSelectedFile());
    }

    public static JFileChooser addFileFilter(String[] extensions, String description) {
        //Return JFileChooser with filter
        JFileChooser f = new JFileChooser();
        ListFileFilter listFileFilter = new ListFileFilter(extensions, description);

        f.setFileFilter(listFileFilter);
        f.setAcceptAllFileFilterUsed(false);

        return f;
    }

    public static String chooseFile(String[] extensions, String description) {
        //Get path to chosen file
        JFileChooser f = new JFileChooser();
        ListFileFilter listFileFilter = new ListFileFilter(extensions, description);

        f.setFileFilter(listFileFilter);
        f.setAcceptAllFileFilterUsed(false);

        int result = f.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return f.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
}
