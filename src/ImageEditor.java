import Effects.Contrast;
import Effects.Effect;
import Effects.EffectType;
import Effects.ImageHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.*;
import java.util.List;

public class ImageEditor {
    private JFrame editorFrame;
    private JPanel editorPanel;
    private JLabel imageLabel;
    private BufferedImage currentImg;
    private String title;

    private JLabel effectSequenceLabel;
    private PlaceholderTextField effectSequenceField;
    private JButton submitSequence;

    private boolean isBlankImage = false;

    private ButtonPanel buttonPanel;

    public ImageEditor(String title, BufferedImage img, MenuPanel menuPanel) {
        this.currentImg = img;
        editorFrame = new JFrame();
        editorFrame.setTitle(title);
        editorPanel = new JPanel(); //do gridlayout to add compoemnets onto editor
        this.title = title;
        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        editorFrame.setJMenuBar(menuPanel.getMenuBar());
        imageLabel = new JLabel(new ImageIcon(img));
        if (ImageHelper.isBlankImage(img)) {
            this.isBlankImage = true;
        }
        System.out.println(isBlankImage );
    }

    public String getSequenceInput() {
        return effectSequenceField.getText();
    }

    public void openSequenceInput(ActionListener sequenceListener) {
        effectSequenceLabel = new JLabel("Enter sequence:");
        effectSequenceLabel.setFont(new Font("Aria", Font.BOLD, 20));
        effectSequenceField = new PlaceholderTextField();
        effectSequenceField.setPlaceholder("Effect sequence");
        effectSequenceField.setPreferredSize(new Dimension(370, 30));
        submitSequence = new JButton("Apply sequence");
        System.out.println("hi");
        submitSequence.addActionListener(sequenceListener);


        editorPanel.add(effectSequenceField);
        editorPanel.add(effectSequenceLabel);
        editorPanel.add(submitSequence);
    }

    public void addButtonPanel(ButtonPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
        JPanel panelButton = buttonPanel.getButtonPanel();
        editorPanel.add(panelButton);
    }
    public void updateMenuPanel(MenuPanel menuPanel) {
        editorFrame.setJMenuBar(menuPanel.getMenuBar());
    }

    public void updateButtonPanel(ButtonPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    public BufferedImage getCurrentImage() {
        return currentImg;
    }

    public String getTitle() {
        return this.title;
    }

    public void updateImage(BufferedImage img, String title) {
        this.title = title;
        editorFrame.setTitle(title);
        imageLabel.setIcon(new ImageIcon(img));
        editorFrame.pack();
    }

    public void show() {

        editorPanel.add(imageLabel);
        editorFrame.add(editorPanel);
        editorFrame.pack();


        editorFrame.setVisible(true);
    }

    public JFrame getEditorFrame() {
        return this.editorFrame;
    }

    private int getPower(int counter) {
        if (counter == 0 || counter == 1) {
            return 0;
        }
        return (int) Math.floor(Math.log10(counter))+1;
    }

    public File getEditedFile(String directory, String file_name, String type, int buffer, boolean log) {
        File editedFile = new File(directory + File.separator + file_name.split("[.]")[0] + ((log) ? "-VV_log.txt" : "-VV." + type));
        int counter = 0;
        while (editedFile.exists()) {
            counter += 1;
            editedFile = new File(directory + File.separator + editedFile.getName().split("[.]")[0].substring(0, editedFile.getName().split("[.]")[0].length()-(buffer+2*getPower(counter))) + "-" + counter + ((log) ? "-VV_log.txt" : "-VV." + type));
        }
        return editedFile;
    }

    public void saveImageSequence(ArrayList<Effect> effectSequence, String directory, String file_name) throws IOException {

        File effectSerialize = new File(directory + File.separator + file_name.split("[.]")[0] + "-sequence.dat");
        try {
            FileOutputStream effectOutputFile = new FileOutputStream(effectSerialize.getAbsoluteFile());
            ObjectOutputStream effectOut = new ObjectOutputStream(effectOutputFile);
//            effectOut.writeInt(effectSequence.size()); // Write the size of the list
//            for (Effect effect : effectSequence) {
//                effectOut.writeObject(effect); // This will invoke the custom writeObject method in the Effect class
//            }

            effectOut.close();
            effectOutputFile.close();
            System.out.println("Serialized");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveTextToFile(String directory, String file_name, String file_extension, ArrayList<Effect> effectSequence) {
        File textFile = getEditedFile(directory, file_name, file_extension, 7, true);
        List<String> imageLogs = new ArrayList<>();
        imageLogs.add("Original File name: " + file_name + "." + file_extension);
        imageLogs.add("Directory saving in: " + textFile.getParent());
        imageLogs.add("File type: " + file_extension);
        imageLogs.add("Edited file name: " + textFile.getName().replace("_log", ""));

        List<String> effectVV = new ArrayList<>();

        for (int i = 0; i < effectSequence.size(); i ++) {
            effectVV.add(effectSequence.get(i).getEffectInitials());
        }
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

    private void saveImgToFile(BufferedImage img, String directory, String file_name, String type) {
        try {
            File imageFile = getEditedFile(directory, file_name, type, 3, false);
            ImageIO.write(img, "png", new File(imageFile.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveImage(BufferedImage img, String[] file_name_broken, boolean withText, ArrayList<Effect> effectSequence) {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);
        String file_name = file_name_broken[0];
        System.out.println(file_name);
        String file_extension = file_name_broken[1];
        File directory_chosen = new File(String.valueOf(f.getSelectedFile()));
        String directory = directory_chosen.getAbsolutePath();
        saveImgToFile(img, directory, file_name, file_extension);
        if (withText) {
            saveTextToFile(directory, file_name, file_extension, effectSequence);
        }
    }
}
