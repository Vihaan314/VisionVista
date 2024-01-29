package com.visionvista.utils;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class SerializedFileFilter extends FileFilter {
    private String extension;
    private String description;

    public SerializedFileFilter(String extension, String description) {
        this.extension = extension;
        this.description = description;
    }

    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        return file.getName().endsWith(extension);
    }

    public String getDescription() {
        return description + String.format(" (*%s)", extension);
    }
}