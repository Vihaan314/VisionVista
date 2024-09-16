package com.visionvista.effects.filters;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.effects.EffectDescription;
import com.visionvista.effects.EffectParameter;
import com.visionvista.utils.ColorManipulator;
import com.visionvista.utils.ImageHelper;

import java.awt.*;
import java.io.Serial;

@EffectParameter(parameters = "RGB1, RGB2")
public class Duotone extends Filter {
    @Serial
    private static final long serialVersionUID = -25512245131277779L;

    private Color color1;
    private Color color2;

    public Duotone(Color color1, Color color2) {
        super();
        this.color1 = color1;
        this.color2 = color2;
    }

    public Duotone(@JsonProperty("red1") int red1, @JsonProperty("green1") int green1, @JsonProperty("blue1") int blue1, @JsonProperty("red2") int red2, @JsonProperty("green2") int green2, @JsonProperty("blue2") int blue2) {
        this.color1 = new Color((((red1 % 256) + 256) % 256), (((green1 % 256) + 256) % 256), (((blue1 % 256) + 256) % 256));
        this.color2 = new Color((((red2 % 256) + 256) % 256), (((green2 % 256) + 256) % 256), (((blue2 % 256) + 256) % 256));
    }

    @Override protected int applyEffect(int red, int green, int blue) {
        int gray = (int) ColorManipulator.toGray(red, green, blue);
        int newRed = (color1.getRed() * gray + color2.getRed() * (255 - gray)) / 255;
        int newGreen = (color1.getGreen() * gray + color2.getGreen() * (255 - gray)) / 255;
        int newBlue = (color1.getBlue() * gray + color2.getBlue() * (255 - gray)) / 255;

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

    @Override
    public String toString() {
        return "Applied Duotone";
    }


    public static Duotone getRandomInstance() {
        Color randomColor1 = new Color(ImageHelper.getRandomParameter(ImageHelper.getColorBounds()),ImageHelper.getRandomParameter(ImageHelper.getColorBounds()),ImageHelper.getRandomParameter(ImageHelper.getColorBounds()));
        Color randomColor2 = new Color(ImageHelper.getRandomParameter(ImageHelper.getColorBounds()),ImageHelper.getRandomParameter(ImageHelper.getColorBounds()),ImageHelper.getRandomParameter(ImageHelper.getColorBounds()));
        return new Duotone(randomColor1, randomColor2);
    }
}
