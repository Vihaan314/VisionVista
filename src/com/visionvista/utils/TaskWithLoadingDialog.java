package com.visionvista.utils;

import com.visionvista.components.LoadingDialog;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class TaskWithLoadingDialog<T> {
    private Window parentWindow;
    private String loadingMessage;
    private Callable<T> task;
    private Consumer<T> onSuccess;
    private Consumer<Exception> onError;

    public TaskWithLoadingDialog(Window parentWindow, String loadingMessage, Callable<T> task, Consumer<T> onSuccess, Consumer<Exception> onError) {
        this.parentWindow = parentWindow;
        this.loadingMessage = loadingMessage;
        this.task = task;
        this.onSuccess = onSuccess;
        this.onError = onError;
    }

    public void execute() {
        LoadingDialog loadingDialog = new LoadingDialog(parentWindow, loadingMessage);
        //SwingWorker allows us to perform a background task without freezing the GUI, task is run in a separate thread
        SwingWorker<T, Void> worker = new SwingWorker<>() {
            @Override
            protected T doInBackground() throws Exception {
                //Executed in a background thread
                return task.call();
            }

            @Override
            protected void done() {
                //This is called on the EDT after the background task is completed
                loadingDialog.close();
                try {
                    //Retrieve the result of the task - doInBackground()
                    T result = get();
                    if (onSuccess != null) {
                        //Execute success callback with the result
                        onSuccess.accept(result);
                    }
                } catch (Exception e) {
                    //If an exception occurred during doInBackground()
                    if (onError != null) {
                        onError.accept(e);
                    } else {
                        //If no error callback is provided, display the error
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(parentWindow, "An error occurred: " + e.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
        worker.execute();
        loadingDialog.show();
    }
}
