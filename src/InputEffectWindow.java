import Effects.EffectType;
import Effects.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class InputEffectWindow {
    private JFrame inputFrame;
    private JButton submitButton;
    private EffectType effect;
    private BufferedImage image;
    private JPanel inputPanel;
    private String[] paramLabels;
    private ArrayList<PlaceholderTextField> textFields;
    private ArrayList<JLabel> fieldLabels;
    private int labelLength;

    public InputEffectWindow(BufferedImage image, EffectType effect, String[] paramLabels) {
        this.effect = effect;
        this.image = image;
        this.inputFrame = setupInputFrame(effect);
        this.paramLabels = paramLabels;
        this.labelLength = paramLabels.length;
        this.inputPanel = new JPanel(new GridLayout(this.labelLength+1, this.labelLength));
        this.textFields = new ArrayList<>(this.labelLength);
        this.fieldLabels = new ArrayList<>(this.labelLength);
        setupTextFields();
    }

    public String[] getValues() {
        String[] values = new String[labelLength];
        if (effect == EffectType.RESIZE) {
            String targetWidth = (textFields.get(0).getText());
            String targetHeight = (textFields.get(1).getText());
            values[0] = targetWidth;
            values[1] = targetHeight;
        }
        return values;
    }

    public JFrame getInputFrame() {
        return this.inputFrame;
    }

    public void setupTextFields() {
        for (int i = 0; i < labelLength; i++) {
            fieldLabels.add(new JLabel(paramLabels[i]));
            PlaceholderTextField tempField = new PlaceholderTextField("");
            tempField.setPreferredSize(new Dimension(370, 30));
            tempField.setPlaceholder(paramLabels[i]);

            textFields.add(tempField);
        }
        for (int i = 0; i < labelLength; i++ ) {
            inputPanel.add(fieldLabels.get(i));
            inputPanel.add(textFields.get(i));
        }
        textFields = addFieldListeners(textFields);
    }

    public ArrayList<PlaceholderTextField> addFieldListeners(ArrayList<PlaceholderTextField> inputFields) {
        Helper.addChangeListener(inputFields.get(0), e -> {
                if (!inputFields.get(0).equals("")) {
                    try {
                        double ratio = Double.parseDouble(inputFields.get(0).getText()) / image.getWidth();
                        inputFields.get(1).setText(String.valueOf(ratio * image.getHeight()));
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        System.out.println("Please enter a number");
                    }
                }
            });
        return inputFields;
    }

    private JFrame setupInputFrame(EffectType effect) {
        JFrame frame = new JFrame("Input for effects");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(650, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }

    public void setupSubmitButton(ActionListener actionListener) {
        JButton submitEffect = new JButton("Enter");
        submitEffect.addActionListener(actionListener);
        this.submitButton = submitEffect;
    }

    public void show() {
//        inputFrame.add(submitButton);
        inputPanel.add(submitButton);
        inputFrame.add(inputPanel);

        inputFrame.pack();
        inputFrame.setVisible(true);
    }
}
