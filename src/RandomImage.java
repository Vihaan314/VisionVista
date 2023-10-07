import Effects.*;
import Effects.Filters.*;
import Effects.Negative;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class RandomImage {
    BufferedImage image;
    double randomParam;
    public RandomImage(BufferedImage image) {
        this.image = image;
    }

    public Effect getRandomImage() {
        Class[] effectClasses = {Glow.class, Contrast.class, Blur.class};
        int upper = effectClasses.length;
        Random rand = new Random();
        int randIndex = rand.nextInt(upper);

        Effect randomEffect = null;
        try {
            randomEffect = (Effect) effectClasses[randIndex].getDeclaredMethod("getRandomInstance", BufferedImage.class).invoke(null, image);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Random Effect: " + randomEffect.toString());
        return randomEffect;
    }
}
