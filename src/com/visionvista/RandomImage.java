package com.visionvista;

import com.visionvista.effects.*;
import com.visionvista.effects.filters.*;
import com.visionvista.effects.transformation.*;

import java.awt.image.BufferedImage;
import java.util.*;

public class RandomImage {
    BufferedImage image;
    public RandomImage(BufferedImage image) {
        this.image = image;
    }

    public Effect getRandomImage() {
        Class<?>[] effectClasses = {
                Glow.class, Contrast.class, Blur.class, Brightness.class, GaussBlur.class, Saturation.class, Sharpen.class,
                Vibrance.class, CrossProcess.class, Grayscale.class, Heatmap.class, Infrared.class, Lomography.class, Negative.class,
                PencilSketch.class, Pixelate.class, Posterize.class, Sepia.class, Solarize.class, SplitTone.class, Temperature.class,
                Vignette.class, Hue.class,
        };
        int upper = effectClasses.length;
        Random rand = new Random();
        int randIndex = rand.nextInt(upper);

        Effect randomEffect = null;
        try {
            randomEffect = (Effect) effectClasses[randIndex].getDeclaredMethod("getRandomInstance", BufferedImage.class).invoke(null, image);
        } catch (Exception e){
            e.printStackTrace();
        }
        return randomEffect;
    }
}
