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

    public static File getEditedFile(String directory, String file_name, String type, String customExtension) {
        int buffer = customExtension.length()-1 + 4;
        String directoryConstant = directory + File.separator;

        File editedFile = new File(directoryConstant +
                file_name.split("[.]")[0] + ("-VV" + customExtension + "." + type));
        int counter = 0;
        while (editedFile.exists()) {
            counter += 1;
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

        String directory = String.valueOf(f.getSelectedFile());
        return directory;
    }

    public static JFileChooser addFileFilter(String[] extensions, String description) {
        JFileChooser f = new JFileChooser();
        ListFileFilter listFileFilter = new ListFileFilter(extensions, description);

        f.setFileFilter(listFileFilter);
        f.setAcceptAllFileFilterUsed(false);

        return f;
    }

    public static String chooseFile(String[] extensions, String description) {
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
