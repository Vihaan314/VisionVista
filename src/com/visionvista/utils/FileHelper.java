package com.visionvista.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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

    public static File chooseSaveFile(String defaultFileName, String extension, String description) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");
        fileChooser.setSelectedFile(new File(defaultFileName + "." + extension));

        FileNameExtensionFilter filter = new FileNameExtensionFilter(description, extension);
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith("." + extension)) {
                fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + "." + extension);
            }
            return fileToSave;
        } else {
            //The user canceled the save
            return null;
        }
    }

    public static String chooseDirectory() {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = f.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return String.valueOf(f.getSelectedFile());
        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("Cancel was selected");
        }
        return null;
    }


    public static JFileChooser addFileFilter(String[] extensions, String description) {
        //Return JFileChooser with filter
        JFileChooser f = new JFileChooser();
        ListFileFilter listFileFilter = new ListFileFilter(extensions, description);

        f.setFileFilter(listFileFilter);
        f.setAcceptAllFileFilterUsed(false);

        return f;
    }

    public static String chooseFile(String[] extensions, String description, String dialogTitle) {
        JFileChooser f = new JFileChooser();
        f.setDialogTitle(dialogTitle);
        ListFileFilter listFileFilter = new ListFileFilter(extensions, description);

        f.setFileFilter(listFileFilter);
        f.setAcceptAllFileFilterUsed(false);

        int result = f.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = f.getSelectedFile();
            if (selectedFile == null || !selectedFile.exists()) {
                JOptionPane.showMessageDialog(null, "Invalid file selected. Please choose a valid file.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            return selectedFile.getAbsolutePath();
        } else if (result == JFileChooser.CANCEL_OPTION || result == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(null, "No file chosen.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    public static File getDefaultFileChooserDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        return fileChooser.getCurrentDirectory();
    }
}
