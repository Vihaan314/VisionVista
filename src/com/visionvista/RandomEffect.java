package com.visionvista;

import com.visionvista.effects.Effect;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

import java.awt.image.BufferedImage;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomEffect {
    private List<Class<? extends Effect>> effectClasses;

    public RandomEffect() {
        loadEffectClasses();
    }

    private void loadEffectClasses() {
        effectClasses = new ArrayList<>();
        //Extract all classes from effects package
        try (ScanResult scanResult = new ClassGraph()
                .enableClassInfo()
                .acceptPackages("com.visionvista.effects")
                .scan()) {
            for (ClassInfo classInfo : scanResult.getSubclasses(Effect.class.getName())) {
                Class<?> cls = classInfo.loadClass();
                //Exclude enums, interfaces, abstract classes, and annotations
                if (!Modifier.isAbstract(cls.getModifiers()) && !cls.isEnum() && !cls.isInterface() && !cls.isAnnotation()) {
                    effectClasses.add((Class<? extends Effect>) cls);
                }
            }
        }
    }

    public Effect getRandomEffect() {
        //Get random effect class object
        Random rand = new Random();
        int randIndex = rand.nextInt(effectClasses.size());
        Class<? extends Effect> effectClass = effectClasses.get(randIndex);
        Effect randomEffect = null;

        try {
            //Get random instance (randomized parameters, if any)
            System.out.println(effectClass.getSimpleName());
            do {
                randomEffect = (Effect) effectClass.getDeclaredMethod("getRandomInstance").invoke(null);
                Object param = randomEffect.getParameter();
                if (param instanceof Number number) {
                    if (number.doubleValue() == 0) continue;
                }
                break;
            } while (true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return randomEffect;
    }
}
