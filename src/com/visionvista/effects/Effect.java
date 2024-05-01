package com.visionvista.effects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.visionvista.effects.artistic.*;
import com.visionvista.effects.blur.BokehBlur;
import com.visionvista.effects.blur.BoxBlur;
import com.visionvista.effects.blur.GaussianBlur;
import com.visionvista.effects.blur.TiltShift;
import com.visionvista.effects.distort.Anaglyph3D;
import com.visionvista.effects.distort.ChromaticAberration;
import com.visionvista.effects.distort.PixelSort;
import com.visionvista.effects.distort.Pixelate;
import com.visionvista.effects.enhance.EdgeEnhance;
import com.visionvista.effects.enhance.Sharpen;
import com.visionvista.effects.filters.*;
import com.visionvista.effects.transformation.FlipHorizontal;
import com.visionvista.effects.transformation.FlipVertical;
import com.visionvista.effects.transformation.Rotate;

import java.awt.image.BufferedImage;
import java.io.Serial;
import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "effect", visible = true)
@JsonSubTypes({
        //Effects
        @JsonSubTypes.Type(value = Brightness.class, name = "Brightness"),
        @JsonSubTypes.Type(value = Contrast.class, name = "Contrast"),
        @JsonSubTypes.Type(value = Saturation.class, name = "Saturation"),
        @JsonSubTypes.Type(value = Vibrance.class, name = "Vibrance"),
        @JsonSubTypes.Type(value = Hue.class, name = "Hue"),
        //Transforms
        @JsonSubTypes.Type(value = Rotate.class, name = "Rotate"),
        @JsonSubTypes.Type(value = FlipVertical.class, name = "Flip Vertical"),
        @JsonSubTypes.Type(value = FlipHorizontal.class, name = "Flip Horizontal"),
        //Filters
        @JsonSubTypes.Type(value = Grayscale.class, name = "Grayscale"),
        @JsonSubTypes.Type(value = Heatmap.class, name = "Heatmap"),
        @JsonSubTypes.Type(value = Infrared.class, name = "Infrared"),
        @JsonSubTypes.Type(value = Halftone.class, name = "Halftone"),
        @JsonSubTypes.Type(value = Negative.class, name = "Negative"),
        @JsonSubTypes.Type(value = Solarize.class, name = "Solarize"),
        @JsonSubTypes.Type(value = SplitTone.class, name = "Split Tone"),
        @JsonSubTypes.Type(value = Glow.class, name = "Glow"),
        @JsonSubTypes.Type(value = Temperature.class, name = "Temperature"),
        @JsonSubTypes.Type(value = Vignette.class, name = "Vignette"),
        @JsonSubTypes.Type(value = Sepia.class, name = "Sepia"),
        @JsonSubTypes.Type(value = Grain.class, name = "Grain"),
        //Enhance
        @JsonSubTypes.Type(value = EdgeEnhance.class, name = "Edge Enhance"),
        @JsonSubTypes.Type(value = Sharpen.class, name = "Sharpen"),
        //Distort
        @JsonSubTypes.Type(value = Anaglyph3D.class, name = "Anaglyph 3D"),
        @JsonSubTypes.Type(value = ChromaticAberration.class, name = "Chromatic Aberration"),
        @JsonSubTypes.Type(value = Pixelate.class, name = "Pixelate"),
        @JsonSubTypes.Type(value = PixelSort.class, name = "Pixel Sort"),
        //Blur
        @JsonSubTypes.Type(value = GaussianBlur.class, name = "Gaussian Blur"),
        @JsonSubTypes.Type(value = BoxBlur.class, name = "Box Blur"),
        @JsonSubTypes.Type(value = BokehBlur.class, name = "Bokeh Blur"),
        @JsonSubTypes.Type(value = TiltShift.class, name = "Tilt Shift"),
        //Artistic
        @JsonSubTypes.Type(value = ColorSplash.class, name = "Color Splash"),
        @JsonSubTypes.Type(value = CrossProcess.class, name = "Cross Process"),
        @JsonSubTypes.Type(value = Cyberpunk.class, name = "Cyberpunk"),
        @JsonSubTypes.Type(value = OilPainting.class, name = "Oil Painting"),
        @JsonSubTypes.Type(value = Lomography.class, name = "Lomography"),
        @JsonSubTypes.Type(value = PencilSketch.class, name = "Pencil Sketch"),
        @JsonSubTypes.Type(value = Posterize.class, name = "Posterize"),
        @JsonSubTypes.Type(value = Watercolor.class, name = "Watercolor"),
})

public abstract class Effect implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    public Effect() {
    }

    public abstract Object getParameter();

    public BufferedImage run(BufferedImage image) {
        BufferedImage result = getEmptyImage(image);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int newRGB = applyEffectAtPixel(image.getRGB(x, y));
                result.setRGB(x, y, newRGB);
            }
        }
        return result;
    }

    protected int applyEffectAtPixel(int rgb) {
        int alpha = (rgb >>> 24);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        int newRGB = applyEffect(red, green, blue);
        return (alpha << 24) | newRGB;
    }

    protected int applyEffect(int red, int green, int blue) {
        return (red << 16 | green << 8 | blue);
    }

    protected BufferedImage getEmptyImage(BufferedImage reference) {
        return new BufferedImage(reference.getWidth(), reference.getHeight(), BufferedImage.TYPE_INT_ARGB);
    }


    public String toString() {
        return "Effect";
    }
}
