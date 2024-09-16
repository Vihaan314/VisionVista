package com.visionvista.utils;

import com.visionvista.commands.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class KeyBinder {
    public static void addKeyBindingToWindow(Window window, KeyStroke keyStroke, Command command) {
        //Access the root pane to add the key binding
        JRootPane rootPane;
        if (window instanceof JFrame) {
            rootPane = ((JFrame) window).getRootPane();
        } else if (window instanceof JDialog) {
            rootPane = ((JDialog) window).getRootPane();
        } else {
            return;
        }

        //Create control + w keybind when window is active
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = rootPane.getActionMap();
        inputMap.put(keyStroke, "closeWindow");

        //Dispose of window and execute command (optional)
        actionMap.put("closeWindow", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                try {
                    command.execute();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void addCtrlWCloseKeyBinding(Window window, Command command) {
        addKeyBindingToWindow(window, KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK), command);
    }
}
