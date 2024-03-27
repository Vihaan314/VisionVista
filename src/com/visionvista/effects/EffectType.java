package com.visionvista.effects;

import com.visionvista.effects.artistic.*;
import com.visionvista.effects.blur.*;
import com.visionvista.effects.enhance.*;
import com.visionvista.effects.transformation.*;
import com.visionvista.effects.filters.*;
import com.visionvista.effects.distort.*;
import com.visionvista.effects.*;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.util.ArrayList;

interface EffectConstructor {
    Effect construct(Object object);
}


public enum EffectType {
    //Slider
    BOX_BLUR("Box Blur", 0, 10, EffectUIType.SLIDER, (param) -> new BoxBlur((Double) param)),
    BRIGHTNESS("Brightness", -100, 100, EffectUIType.SLIDER, (param) -> new Brightness((Double) param)),
    CONTRAST("Contrast", -100, 100, EffectUIType.SLIDER, (param) -> new Contrast((Double) param)),
    SATURATION("Saturation", 0, 100, EffectUIType.SLIDER, (param) -> new Saturation((Double) param)),
    VIBRANCE("Vibrance", 0, 10, EffectUIType.SLIDER, (param) -> new Vibrance((Double) param)),
    SHARPEN("Sharpen", 0, 10, EffectUIType.SLIDER, (param) -> new Sharpen((Double) param)),
    GAUSSIAN_BLUR("Gaussian Blur", 0, 10, EffectUIType.SLIDER, (param) -> new GaussBlur((Double) param)),
    TEMPERATURE("Temperature", 0, 100, EffectUIType.SLIDER, (param) -> new Temperature((Double) param)),
    SEPIA("Sepia", 0, 100, EffectUIType.SLIDER, (param) -> new Sepia((Double) param)),
    GLOW("Glow", 0, 10, EffectUIType.SLIDER, (param) -> new Glow((Double) param)),
    VIGNETTE("Vignette", 0, 100, EffectUIType.SLIDER, (param) -> new Vignette((Double) param)),
    PIXELATE("Pixelate", 0, 50, EffectUIType.SLIDER, (param) -> new Pixelate((Double) param)),
    CHROMATIC_ABERRATION("Chromatic Aberration", 0, 10, EffectUIType.SLIDER, (param) -> new ChromaticAberration((Double) param)),
    TILT_SHIFT("Tilt shift", 0, 10, EffectUIType.SLIDER, (param) -> new TiltShift((Double) param)),
    PIXEL_SORT("Pixel sort", 0, 255, EffectUIType.SLIDER, (param) -> new PixelSort((Double) param)),
    ANAGLYPH("Anaglyph 3D", 0, 30, EffectUIType.SLIDER, (param) -> new Anaglyph3D((Double) param)),
    OIL_PAINTING("Oil painting", 0, 50, EffectUIType.SLIDER, (param) -> new OilPainting((Double) param)),
    BOKEH_BLUR("Bokeh blur", 0, 20, EffectUIType.SLIDER, (param) -> new BokehBlur((Double) param)),
    COLOR_SPLASH("Color splash", 0, 50, EffectUIType.SLIDER, (param) -> new ColorSplash((Double) param)),
    ROTATE("Rotate", 0, 360, EffectUIType.SLIDER, (param) -> new Rotate((Double) param)),

    //Color chooser
    HUE("Hue", EffectUIType.COLOR_CHOOSER, (param) -> new Hue((Color) param)),

    //Text field
    RESIZE("Resize", new String[]{"Width", "Height"}, EffectUIType.TEXT_FIELD, (params) -> new Resize((int) Double.parseDouble(((String[]) params)[0]), (int) Double.parseDouble(((String[]) params)[1]))),

    //// None

    //Transformations
    FLIP_VERTICAL("Flip vertical", (param) -> new FlipVertical()),
    FLIP_HORIZONTAL("Flip horizontal", (param) -> new FlipHorizontal()),

    //Filters
    GRAYSCALE("Grayscale", (param) -> new Grayscale()),
    NEGATIVE("Negative", (param) -> new Negative()),
    POSTERIZE("Posterize", (param) -> new Posterize()),
    CROSS_PROCESS("Cross Process", (param) -> new CrossProcess()),
    LOMOGRAPHY("Lomography", (param) -> new Lomography()),
    SOLARIZE("Solarize", (param) -> new Solarize()),
    SPLIT_TONE("Split tone", (param) -> new SplitTone()),
    HEAT_MAP("Heat map", (param) -> new Heatmap()),
    INFRARED("Infrared", (param) -> new Infrared()),
    PENCIL_SKETCH("Pencil Sketch", (param) -> new PencilSketch()),
    HALFTONE("Halftone", (param) -> new Halftone()),
    WATERCOLOR("Watercolor", (param) -> new Watercolor()),
    EDGE_ENHANCE("Edge Enhance", (param) -> new EdgeEnhance()),
    CYBERPUNK("Cyberpunk", (param) -> new Cyberpunk()),
    ;

    private final String effectLabel;
    private final int lowerBound;
    private final int upperBound;
    private final String[] textFieldLabels;
    private final EffectUIType uiType;
    private final EffectConstructor effectConstructor;

    EffectType(String effectLabel, EffectConstructor effectConstructor) {
        this(effectLabel, 0, 0, new String[]{}, EffectUIType.NONE, effectConstructor);
    }

    EffectType(String effectLabel, EffectUIType effectUIType, EffectConstructor effectConstructor) {
        this(effectLabel, 0, 0, new String[]{}, effectUIType, effectConstructor);
    }

    EffectType(String effectLabel, int lower, int upper, EffectUIType effectUIType, EffectConstructor effectConstructor) {
        this(effectLabel, lower, upper, new String[]{}, effectUIType, effectConstructor);
    }

    EffectType(String effectLabel, String[] paramLabels, EffectUIType effectUIType, EffectConstructor effectConstructor) {
        this(effectLabel, 0, 0, paramLabels, effectUIType, effectConstructor);
    }

    EffectType(String effectLabel, int lowerBound, int upperBound, String[] textFieldLabels, EffectUIType uiType, EffectConstructor effectConstructor) {
        this.effectLabel = effectLabel;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.textFieldLabels = textFieldLabels;
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
        //Return instance of effect with single parameter
        //Construct treats lambda as implementation of construct and therefore takes in param for instance of effect
        return effectConstructor.construct(param);
    }

    public Effect getEffect() {
        //Return instance of effect with no parameters
        return effectConstructor.construct(null);
    }

    public Pair<Integer, Integer> getSliderBounds() {
        return new Pair<>(lowerBound, upperBound);
    }

    public String[] getTextFieldParams() {
        return textFieldLabels;
    }

    //TODO MAKE MAP FROM UI ENUM TO EFFECTYTPE MANUALLY CONSTANTS FILE
    public static ArrayList<EffectType> getEffectTypeFromComponent(EffectUIType uiType) {
        ArrayList<EffectType> effectEntries = new ArrayList<>();

        //Process all effects that utilize the corresponding UI type
        for (EffectType effect : EffectType.values()) {
            if (effect.getUIType() == uiType) {
                effectEntries.add(effect);
            }
        }
        return effectEntries;
    }

    @Override
    public String toString() {
        return this.effectLabel;
    }
}