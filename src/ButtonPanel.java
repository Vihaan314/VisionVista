import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ButtonPanel {
    private JPanel buttonPanel;
    private ArrayList<JButton> panelButtons;

    public ButtonPanel() {
        buttonPanel = new JPanel(new GridLayout(4, 9));
        panelButtons = new ArrayList<>();
    }

    public void addButtonToPanel(String title, ActionListener actionListener) {
        JButton newButton = new JButton(title);
        newButton.addActionListener(actionListener);
        this.buttonPanel.add(newButton);
        this.panelButtons.add(newButton);
    }

    public JPanel getButtonPanel() {
        return this.buttonPanel;
    }
}
