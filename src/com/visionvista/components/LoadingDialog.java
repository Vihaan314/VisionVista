package com.visionvista.components;

import javax.swing.*;
import java.awt.*;

public class LoadingDialog {
    private JDialog dialog;
    private JProgressBar progressBar;
    private JLabel messageLabel;

    private Window parentWindow;
    private String message;

    public LoadingDialog(Window parentWindow, String message) {
        this.parentWindow = parentWindow;
        this.message = message;
        initializeUI();
    }

    public void initializeUI() {
        dialog = new JDialog(parentWindow, "Processing", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setSize(300, 100);
        dialog.setLocationRelativeTo(parentWindow);

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        //Looping progress bar
        messageLabel = new JLabel(message, SwingConstants.CENTER);
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        panel.add(messageLabel, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);

        dialog.getContentPane().add(panel);
    }

    public void show() {
        dialog.setVisible(true);
    }

    public void close() {
        dialog.dispose();
    }
}
