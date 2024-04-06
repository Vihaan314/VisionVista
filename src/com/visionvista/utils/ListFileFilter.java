package com.visionvista.utils;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ListFileFilter extends FileFilter {
    private String[] extensions;
    private String description;

    public ListFileFilter(String[] extensions, String description) {
        this.extensions = extensions;
        this.description = description;
    }

    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        String name = file.getName().toLowerCase();
        //Allow filter to accept all files with any extension from given list
        for (String extension : extensions) {
            if (name.endsWith(extension.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public String getDescription() {
        return description + " (*" + String.join(", *", extensions) + ")";
    }
}