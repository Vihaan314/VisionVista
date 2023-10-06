import Effects.*;
import Effects.Filters.*;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomImage {
    BufferedImage image;
    double randomParam;
    public RandomImage(BufferedImage image) {
        this.image = image;
        int max = 100;
        int min = 10;
        randomParam = Math.floor(Math.random() * (max - min + 1) + min);
    }

    public Effect getRandomImage() {
        List<EffectType> effectsList = Arrays.asList(EffectType.values());
        System.out.println();
        int upper = effectsList.size();
        Random rand = new Random();
        int randIndex = rand.nextInt(upper);
        System.out.println("Hi");
        System.out.println(effectsList.get(randIndex));

        List<EffectType> doubleEffects = effectsList.subList(0, 11);

        if (doubleEffects.contains(effectsList.get(randIndex))) {
            switch (effectsList.get(randIndex).toString()) {
                case "Glow" -> {
                    return new Glow(image, randomParam);
                }
                case "Blur" -> {
                    return new Blur(image, (int) randomParam);
                }
                case "Brightness" -> {
                    return new Brightness(image, randomParam);
                }
                case "Contrast" -> {
                    return new Contrast(image, randomParam);
                }
                case "Saturation" -> {
                    return new Saturation(image, randomParam);
                }
                case "Vibrance" -> {
                    return new Vibrance(image, randomParam);
                }
                case "Sharpen" -> {
                    return new Sharpen(image, randomParam);
                }
                case "Temperature" -> {
                    return new Temperature(image, randomParam);
                }
                case "Sepia" -> {
                    return new Sepia(image, randomParam);
                }
                case "Gaussian Blur" -> {
                    return new GaussBlur(image, randomParam);
                }
                case "Pixelate" -> {
                    return new Pixelate(image, (int) randomParam);
                }
                case "Vignette" -> {
                    return new Vignette(image, randomParam);
                }
            }
        }
        return new Contrast(image, randomParam);
    }
}
