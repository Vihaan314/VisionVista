package Effects;

import Effects.Filters.*;
import Effects.Transformations.FlipHorizontal;
import Effects.Transformations.FlipVertical;
import Effects.Transformations.Resize;

import java.awt.*;
import java.awt.image.BufferedImage;

public enum EffectType {
    BLUR("Blur"),
    BRIGHTNESS("Brightness"),
    CONTRAST("Contrast"),
    SATURATION("Saturation"),
    VIBRANCE("Vibrance"),
    SHARPEN("Sharpen"),
    TEMPERATURE("Temperature"), //Filter
    SEPIA("Sepia"), //Filter
    GAUSSIAN_BLUR("Gaussian Blur"),
    GLOW("Glow"),
    PIXELATE("Pixelate"),
    VIGNETTE("Vignette"),

    HUE("Hue"),

    FLIP_VERTICAL("Flip vertical"),
    FLIP_HORIZONTAL("Flip horizontal"),
    RESIZE("Resize"),

    GRAYSCALE("Grayscale"),
    NEGATIVE("Negative"),
    POSTERIZE("Posterize"),
    CROSS_PROCESS("Cross Process"),
    LOMOGRAPHY("Lomography"),
    SOLARIZE("Solarize"),
    SPLIT_TONE("Split tone"),
    HEAT_MAP("Heat map"),
    INFRARED("Infrared"),
    TILT_SHIFT("Tilt Shift"),
    HALFTONE("Halftone"),
    PENCIL_SKETCH("Pencil Sketch"),
    ;

    private String effectLabel;

    EffectType(String effectLabel) {
        this.effectLabel = effectLabel;
    }

    public static EffectType fromLabel(String effectLabel) {
        switch (effectLabel) {
            case "Blur":
                return BLUR;
            case "Brightness":
                return BRIGHTNESS;
            case "Contrast":
                return CONTRAST;
            case "Saturation":
                return SATURATION;
            case "Vibrance":
                return VIBRANCE;
            case "Sharpen":
                return SHARPEN;
            case "Temperature":
                return TEMPERATURE;
            case "Sepia":
                return SEPIA;

            case "Hue":
                return HUE;
            default:
                throw new IllegalArgumentException("Unknown effect label: " + effectLabel);
        }
    }

    public Effect getEffect(BufferedImage image, double effectParam) {
        EffectType effect = fromLabel(this.effectLabel);
        Effect newEffect;

        switch (effect) {
            case CONTRAST:
                newEffect = new Contrast(image, effectParam);
                break;
            case BRIGHTNESS:
                newEffect = new Brightness(image, effectParam);
                break;
            case BLUR:
                newEffect = new Blur(image, (int) effectParam);
                break;
            case SATURATION:
                newEffect = new Saturation(image, effectParam);
                break;
            case VIBRANCE:
                newEffect = new Vibrance(image, effectParam);
                break;
            case SHARPEN:
                newEffect = new Sharpen(image, effectParam);
                break;
            case TEMPERATURE:
                newEffect = new Temperature(image, effectParam);
                break;
            case SEPIA:
                newEffect = new Sepia(image, effectParam);
                break;
            case GAUSSIAN_BLUR:
                newEffect = new GaussBlur(image, effectParam);
                break;
            case PIXELATE:
                newEffect = new Pixelate(image, (int) effectParam);
                break;
            case VIGNETTE:
                newEffect = new Vignette(image, (int) effectParam);
                break;
            case GLOW:
                newEffect = new Glow(image, effectParam);
                break;
            default:
                throw new IllegalArgumentException(effect + " is not currently recognized as an effect");
        }

        return newEffect;
    }

    public Effect getEffect(BufferedImage image, Color color) {
        EffectType effect = fromLabel(this.effectLabel);
        Effect newEffect;

        switch(effect) {
            case HUE:
                newEffect = new Hue(image, color);
                break;
            default:
                throw new IllegalArgumentException(effect + "is not currently recognized as an effect for colors");
        }
        return newEffect;
    }

    public Effect getEffect(BufferedImage image, String[] textInputs) {
        EffectType effect = fromLabel(this.effectLabel);
        Effect newEffect;
        switch (effect) {
            case RESIZE:
                newEffect = new Resize(image, (int) Double.parseDouble(textInputs[0]), (int) Double.parseDouble(textInputs[1]));
                break;
            default:
                throw new IllegalArgumentException(effect + " is not recognized as a text input effect");
        }
        return newEffect;
    }

    public Effect getEffect(BufferedImage image) {
        EffectType effect = fromLabel(this.effectLabel);
        Effect newEffect;

        switch(effect) {
            case FLIP_VERTICAL:
                newEffect = new FlipVertical(image);
                break;
            case FLIP_HORIZONTAL:
                newEffect = new FlipHorizontal(image);
                break;
            case NEGATIVE:
                newEffect = new Effects.Negative(image);
                break;
            case GRAYSCALE:
                newEffect = new Grayscale(image);
                break;
            case POSTERIZE:
                newEffect = new Posterize(image);
                break;
            case CROSS_PROCESS:
                newEffect = new CrossProcess(image);
                break;
            case LOMOGRAPHY:
                newEffect = new Lomography(image);
                break;
            case SOLARIZE:
                newEffect = new Solarize(image);
                break;
            case SPLIT_TONE:
                newEffect = new SplitTone(image);
                break;
            case HEAT_MAP:
                newEffect = new Heatmap(image);
                break;
            case INFRARED:
                newEffect = new Infrared(image);
                break;
            case TILT_SHIFT:
                newEffect = new TiltShift(image);
                break;
            case PENCIL_SKETCH:
                newEffect = new PencilSketch(image);
                break;
            default:
                throw new IllegalArgumentException(effect + " is not currently recognized as an effect for colors");
        }
        return newEffect;
    }

    public String toString() {
        return this.effectLabel;
    }
}
