package com.visionvista.effects;


import com.visionvista.Pair;
import com.visionvista.effects.transformation.*;
import com.visionvista.effects.filters.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public enum EffectType {
    BLUR("Blur", 1, 10, EffectUIType.SLIDER, (img, param) -> new Blur(img, (Double) param)),
    BRIGHTNESS("Brightness", 1, 100, EffectUIType.SLIDER, (img, param) -> new Brightness(img, (Double) param)),
    CONTRAST("Contrast", 1, 100, EffectUIType.SLIDER, (img, param) -> new Contrast(img, (Double) param)),
    SATURATION("Saturation", 1, 100, EffectUIType.SLIDER, (img, param) -> new Saturation(img, (Double) param)),
    VIBRANCE("Vibrance", 1, 10, EffectUIType.SLIDER, (img, param) -> new Vibrance(img, (Double) param)),
    SHARPEN("Sharpen", 1, 10, EffectUIType.SLIDER, (img, param) -> new Sharpen(img, (Double) param)),
    GAUSSIAN_BLUR("Gaussian Blur", 1, 10, EffectUIType.SLIDER, (img, param) -> new GaussBlur(img, (Double) param)),
    TEMPERATURE("Temperature", 1, 100, EffectUIType.SLIDER, (img, param) -> new Temperature(img, (Double) param)),
    SEPIA("Sepia", 1, 100, EffectUIType.SLIDER, (img, param) -> new Sepia(img, (Double) param)),
    GLOW("Glow", 1, 10, EffectUIType.SLIDER, (img, param) -> new Glow(img, (Double) param)),
    VIGNETTE("Vignette", 1, 10, EffectUIType.SLIDER, (img, param) -> new Vignette(img, (Double) param)),
    PIXELATE("Pixelate", 1, 50, EffectUIType.SLIDER, (img, param) -> new Pixelate(img, (Integer) param)),

    HUE("Hue", EffectUIType.COLOR_CHOOSER, (img, param) -> new Hue(img, (Color) param)),

    FLIP_VERTICAL("Flip vertical", (img, param) -> new FlipVertical(img)),
    FLIP_HORIZONTAL("Flip horizontal", (img, param) -> new FlipHorizontal(img)),
    RESIZE("Resize", (img, params) -> new Resize(img, Integer.parseInt(((String[]) params)[0]), Integer.parseInt(((String[]) params)[1]))),

    GRAYSCALE("Grayscale", (img, param) -> new Grayscale(img)),
    NEGATIVE("Negative", (img, param) -> new Negative(img)),
    POSTERIZE("Posterize", (img, param) -> new Posterize(img)),
    CROSS_PROCESS("Cross Process", (img, param) -> new CrossProcess(img)),
    LOMOGRAPHY("Lomography", (img, param) -> new Lomography(img)),
    SOLARIZE("Solarize", (img, param) -> new Solarize(img)),
    SPLIT_TONE("Split tone", (img, param) -> new SplitTone(img)),
    HEAT_MAP("Heat map", (img, param) -> new Heatmap(img)),
    INFRARED("Infrared", (img, param) -> new Infrared(img)),
    TILT_SHIFT("Tilt Shift", (img, param) -> new TiltShift(img)),
    PENCIL_SKETCH("Pencil Sketch", (img, param) -> new PencilSketch(img)),
    ;


    private String effectLabel;
    private int lowerBound;
    private int upperBound;
    private EffectUIType uiType;
    private BiFunction<BufferedImage, Object, Effect> effectConstructor;

    EffectType(String effectLabel, BiFunction<BufferedImage, Object, Effect> effectConstructor) {
        this(effectLabel, 0, 0, EffectUIType.NONE, effectConstructor);
    }

    EffectType(String effectLabel, EffectUIType effectUIType, BiFunction<BufferedImage, Object, Effect> effectConstructor) {
        this(effectLabel, 0, 0, effectUIType, effectConstructor);
    }

    EffectType(String effectLabel, int lowerBound, int upperBound, EffectUIType uiType, BiFunction<BufferedImage, Object, Effect> effectConstructor) {
        this.effectLabel = effectLabel;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.uiType = uiType;
        this.effectConstructor = effectConstructor;
    }

    public EffectUIType getUIType() {
        return uiType;
    }

    public static EffectType fromLabel(String effectLabel) {
        String processedLabel = String.join("_", effectLabel.toUpperCase().split(" "));
        return EffectType.valueOf(processedLabel);
    }

//    public Effect getEffect(BufferedImage image, double effectParam) {
//        EffectType effect = fromLabel(this.effectLabel);
//        Effect newEffect;
//
//        switch (effect) {
//            case CONTRAST:
//                newEffect = new Contrast(image, effectParam);
//                break;
//            case BRIGHTNESS:
//                newEffect = new Brightness(image, effectParam);
//                break;
//            case BLUR:
//                newEffect = new Blur(image, (int) effectParam);
//                break;
//            case SATURATION:
//                newEffect = new Saturation(image, effectParam);
//                break;
//            case VIBRANCE:
//                newEffect = new Vibrance(image, effectParam);
//                break;
//            case SHARPEN:
//                newEffect = new Sharpen(image, effectParam);
//                break;
//            case TEMPERATURE:
//                newEffect = new Temperature(image, effectParam);
//                break;
//            case SEPIA:
//                newEffect = new Sepia(image, effectParam);
//                break;
//            case GAUSSIAN_BLUR:
//                newEffect = new GaussBlur(image, effectParam);
//                break;
//            case PIXELATE:
//                newEffect = new Pixelate(image, (int) effectParam);
//                break;
//            case VIGNETTE:
//                newEffect = new Vignette(image, (int) effectParam);
//                break;
//            case GLOW:
//                newEffect = new Glow(image, effectParam);
//                break;
//            default:
//                throw new IllegalArgumentException(effect + " is not currently recognized as an effect");
//        }
//
//        return newEffect;
//    }
//
//    public Effect getEffect(BufferedImage image, Color color) {
//        EffectType effect = fromLabel(this.effectLabel);
//        Effect newEffect;
//
//        switch(effect) {
//            case HUE:
//                newEffect = new Hue(image, color);
//                break;
//            default:
//                throw new IllegalArgumentException(effect + "is not currently recognized as an effect for colors");
//        }
//        return newEffect;
//    }
//
//    public Effect getEffect(BufferedImage image, String[] textInputs) {
//        EffectType effect = fromLabel(this.effectLabel);
//        Effect newEffect;
//        switch (effect) {
//            case RESIZE:
//                newEffect = new Resize(image, (int) Double.parseDouble(textInputs[0]), (int) Double.parseDouble(textInputs[1]));
//                break;
//            default:
//                throw new IllegalArgumentException(effect + " is not recognized as a text input effect");
//        }
//        return newEffect;
//    }
//
//    public Effect getEffect(BufferedImage image) {
//        EffectType effect = fromLabel(this.effectLabel);
//        Effect newEffect;
//
//        switch(effect) {
//            case FLIP_VERTICAL:
//                newEffect = new FlipVertical(image);
//                break;
//            case FLIP_HORIZONTAL:
//                newEffect = new FlipHorizontal(image);
//                break;
//            case NEGATIVE:
//                newEffect = new Negative(image);
//                break;
//            case GRAYSCALE:
//                newEffect = new Grayscale(image);
//                break;
//            case POSTERIZE:
//                newEffect = new Posterize(image);
//                break;
//            case CROSS_PROCESS:
//                newEffect = new CrossProcess(image);
//                break;
//            case LOMOGRAPHY:
//                newEffect = new Lomography(image);
//                break;
//            case SOLARIZE:
//                newEffect = new Solarize(image);
//                break;
//            case SPLIT_TONE:
//                newEffect = new SplitTone(image);
//                break;
//            case HEAT_MAP:
//                newEffect = new Heatmap(image);
//                break;
//            case INFRARED:
//                newEffect = new Infrared(image);
//                break;
//            case TILT_SHIFT:
//                newEffect = new TiltShift(image);
//                break;
//            case PENCIL_SKETCH:
//                newEffect = new PencilSketch(image);
//                break;
//            default:
//                throw new IllegalArgumentException(effect + " is not currently recognized as an effect for colors");
//        }
//        return newEffect;
//    }
    public Effect getEffect(BufferedImage image, Object param) {
        return effectConstructor.apply(image, param);
    }

    public Effect getEffect(BufferedImage image) {
        return effectConstructor.apply(image, null);
    }

    public Pair<Integer, Integer> getSliderBounds() {
        return new Pair<>(lowerBound, upperBound);
    }

    public static Map<EffectType, Pair<Integer, Integer>> getSliderEffects() {
        Map<EffectType, Pair<Integer, Integer>> sliderEntries = new HashMap<>();
        for (EffectType effect : EffectType.values()) {
            if (effect.getUIType() == EffectUIType.SLIDER) {
                sliderEntries.put(effect, effect.getSliderBounds());
            }
        }
        return sliderEntries;
    }


    public String toString() {
        return this.effectLabel;
    }
}
