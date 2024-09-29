package com.visionvista.components;

import com.visionvista.effects.Effect;
import com.visionvista.utils.MiscHelper;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class FindEffectDialog {
    private JFrame frame;

    List<String> effectClassNames;

    private void loadEffectClasses() throws InstantiationException, IllegalAccessException {
        effectClassNames = new ArrayList<>();
        //Extract all classes from effects package
        try (ScanResult scanResult = new ClassGraph()
                .enableClassInfo()
                .acceptPackages("com.visionvista.effects")
                .scan()) {
            for (ClassInfo classInfo : scanResult.getSubclasses(Effect.class.getName())) {
                Class<?> cls = classInfo.loadClass();
                //Exclude enums, interfaces, abstract classes, and annotations
                if (!Modifier.isAbstract(cls.getModifiers()) && !cls.isEnum() && !cls.isInterface() && !cls.isAnnotation()) {
                    effectClassNames.add(MiscHelper.formatEffectName(cls.getSimpleName()));
                }
            }
        }
    }

    public void initializeUI() throws InstantiationException, IllegalAccessException {
        frame = new JFrame("Search effect");
        JPanel panel = new JPanel();

        JLabel searchLabel = new JLabel("Search effect");
        JTextField searchArea = new JTextField();
        panel.add(searchLabel);
        panel.add(searchArea);

        loadEffectClasses();

        JPanel buttons = new JPanel( new GridLayout(effectClassNames.size(), 0) );

        for (String effect : effectClassNames) {
            JButton effectButton = new JButton(effect);
            buttons.add(effectButton);
        }
        panel.add(buttons);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(175, 400));

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(scrollPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}