package com.visionvista;

import com.visionvista.effects.*;
import com.visionvista.effects.artistic.*;
import com.visionvista.effects.blur.BokehBlur;
import com.visionvista.effects.blur.BoxBlur;
import com.visionvista.effects.blur.GaussBlur;
import com.visionvista.effects.blur.TiltShift;
import com.visionvista.effects.distort.Anaglyph3D;
import com.visionvista.effects.distort.ChromaticAberration;
import com.visionvista.effects.distort.PixelSort;
import com.visionvista.effects.distort.Pixelate;
import com.visionvista.effects.enhance.EdgeEnhance;
import com.visionvista.effects.enhance.Sharpen;
import com.visionvista.effects.filters.*;
import com.visionvista.effects.transformation.Rotate;

import java.awt.image.BufferedImage;
import java.util.*;

public class RandomEffect {
    BufferedImage image;
    public RandomEffect() {
        this.image = EditorState.getInstance().getImage();
    }

    public Effect getRandomEffect() {
        Class<?>[] effectClasses = {
                Glow.class, Contrast.class, BoxBlur.class, Brightness.class, GaussBlur.class, Saturation.class, Sharpen.class,
                Vibrance.class, CrossProcess.class, Grayscale.class, Heatmap.class, Infrared.class, Lomography.class, Negative.class,
                PencilSketch.class, Pixelate.class, Posterize.class, Sepia.class, Solarize.class, SplitTone.class, Temperature.class,
                Vignette.class, Hue.class, ChromaticAberration.class, Halftone.class, Watercolor.class, EdgeEnhance.class, TiltShift.class,
                PixelSort.class, Anaglyph3D.class, OilPainting.class, BokehBlur.class, Cyberpunk.class, ColorSplash.class, Rotate.class,
        };
        int upper = effectClasses.length;
        Random rand = new Random();
        int randIndex = rand.nextInt(upper);

        Effect randomEffect = null;
        try {
            System.out.println(effectClasses[randIndex]);
            randomEffect = (Effect) effectClasses[randIndex].getDeclaredMethod("getRandomInstance").invoke(null);
        } catch (Exception e){
            e.printStackTrace();
        }
        return randomEffect;
    }
}
