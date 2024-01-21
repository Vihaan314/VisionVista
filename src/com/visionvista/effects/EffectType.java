package com.visionvista.effects;


import com.visionvista.Pair;
import com.visionvista.effects.transformation.*;
import com.visionvista.effects.filters.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

interface EffectConstructor {
    Effect construct(Object object);
}


public enum EffectType {
    BLUR("Blur", 1, 10, EffectUIType.SLIDER, (param) -> new Blur((Double) param)),
    BRIGHTNESS("Brightness", 1, 100, EffectUIType.SLIDER, (param) -> new Brightness((Double) param)),
    CONTRAST("Contrast", 1, 100, EffectUIType.SLIDER, (param) -> new Contrast((Double) param)),
    SATURATION("Saturation", 1, 100, EffectUIType.SLIDER, (param) -> new Saturation((Double) param)),
    VIBRANCE("Vibrance", 1, 10, EffectUIType.SLIDER, (param) -> new Vibrance((Double) param)),
    SHARPEN("Sharpen", 1, 10, EffectUIType.SLIDER, (param) -> new Sharpen((Double) param)),
    GAUSSIAN_BLUR("Gaussian Blur", 1, 10, EffectUIType.SLIDER, (param) -> new GaussBlur((Double) param)),
    TEMPERATURE("Temperature", 1, 100, EffectUIType.SLIDER, (param) -> new Temperature((Double) param)),
    SEPIA("Sepia", 1, 100, EffectUIType.SLIDER, (param) -> new Sepia((Double) param)),
    GLOW("Glow", 1, 10, EffectUIType.SLIDER, (param) -> new Glow((Double) param)),
    VIGNETTE("Vignette", 1, 10, EffectUIType.SLIDER, (param) -> new Vignette((Double) param)),
    PIXELATE("Pixelate", 1, 50, EffectUIType.SLIDER, (param) -> new Pixelate((Integer) param)),

    HUE("Hue", EffectUIType.COLOR_CHOOSER, (param) -> new Hue((Color) param)),

    FLIP_VERTICAL("Flip vertical", (param) -> new FlipVertical()),
    FLIP_HORIZONTAL("Flip horizontal", (param) -> new FlipHorizontal()),
    RESIZE("Resize", (params) -> new Resize(Integer.parseInt(((String[]) params)[0]), Integer.parseInt(((String[]) params)[1]))),

    GRAYSCALE("Grayscale", (param) -> new Grayscale()),
    NEGATIVE("Negative", (param) -> new Negative()),
    POSTERIZE("Posterize", (param) -> new Posterize()),
    CROSS_PROCESS("Cross Process", (param) -> new CrossProcess()),
    LOMOGRAPHY("Lomography", (param) -> new Lomography()),
    SOLARIZE("Solarize", (param) -> new Solarize()),
    SPLIT_TONE("Split tone", (param) -> new SplitTone()),
    HEAT_MAP("Heat map", (param) -> new Heatmap()),
    INFRARED("Infrared", (param) -> new Infrared()),
    TILT_SHIFT("Tilt Shift", (param) -> new TiltShift()),
    PENCIL_SKETCH("Pencil Sketch", (param) -> new PencilSketch()),
    ;

    private final String effectLabel;
    private final int lowerBound;
    private final int upperBound;
    private final EffectUIType uiType;
    private final EffectConstructor effectConstructor;

    EffectType(String effectLabel, EffectConstructor effectConstructor) {
        this(effectLabel, 0, 0, EffectUIType.NONE, effectConstructor);
    }

    EffectType(String effectLabel, EffectUIType effectUIType, EffectConstructor effectConstructor) {
        this(effectLabel, 0, 0, effectUIType, effectConstructor);
    }

    EffectType(String effectLabel, int lowerBound, int upperBound, EffectUIType uiType, EffectConstructor effectConstructor) {
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

    public Effect getEffect(Object param) {
        return effectConstructor.construct(param);
    }

    public Effect getEffect() {
        return effectConstructor.construct(null);
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