import Effects.Contrast;
import Effects.Effect;
import Effects.EffectType;

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

public class ImagePanel extends MenuPanel {
    BufferedImage img;
    ImageEditor editor;
    MenuPanel menuPanel;
    String[] file_name_broken;
    ArrayList<Effect> effectSequence;

    public ImagePanel(BufferedImage img, ImageEditor editor, String[] file_name_broken, ArrayList<Effect> effectSequence) {
        this.img = img;
        this.editor = editor;
        this.menuPanel = new MenuPanel();
        this.file_name_broken = file_name_broken;
        this.effectSequence = effectSequence;
    }

    public MenuPanel setupMenuPanel() {
        menuPanel.addItemToMenu("File", "Save", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.saveImage(img, file_name_broken, false, effectSequence);
            }
        });

        menuPanel.addItemToMenu("File", "Save with Text", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.saveImage(img, file_name_broken, true, effectSequence);
            }
        });

        menuPanel.addItemToMenu("File", "Save Effect Sequence", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String directory = ImageHelper.getSerializeDirectory();
                try {
                    editor.saveImageSequence(effectSequence, directory, file_name_broken[0]);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        return menuPanel;
    }
}
