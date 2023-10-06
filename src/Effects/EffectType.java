package Effects;

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

    public String toString() {
        return this.effectLabel;
    }
}
