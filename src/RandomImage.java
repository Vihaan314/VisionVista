import Effects.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
        List<EffectType> effects = Arrays.asList(EffectType.values());
        int upper = effects.size();
        Random rand = new Random();
        int randIndex = rand.nextInt(upper);

        List<String> doubleEffects = new ArrayList<>();


        if (doubleEffects.contains(effects.get(randIndex).toString())) {
//            return getEffect(effects.get(randIndex), randomParam);
        }
        return new Contrast(image, randomParam);
    }
}
