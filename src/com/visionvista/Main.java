package com.visionvista;

import com.visionvista.components.LandingWindow;

//In first menu add option in addition to the existing "Open image" a "create new project" with dimension templates, where it
//is just a draw app, where you can also save - could be done with new buffered image, and set x, y, pixel to color (color picker)


//to work on:
//more filters
//adding text / shapes to image
//delete region by specifying start x, y, and end x, y
//Drawing app - new button panel
//JMenu


public class Main {
    public static String[] file_name_broken;

    public static void main(String[] args) {
        LandingWindow landingWindow = new LandingWindow();
        landingWindow.show();
    }
}
