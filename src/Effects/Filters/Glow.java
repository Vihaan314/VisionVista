package Effects.Filters;

import Blending.ColorDodge;
import Effects.Brightness;
import Effects.GaussBlur;

import java.awt.image.BufferedImage;

public class Glow extends Filter {
    private double intensity;

    public Glow(BufferedImage image, double intensity) {
        super(image);
        this.intensity = intensity;
    }

    @Override
    public String toString() {
        return "Applied Glow. Intensity: " + this.intensity;
    }

    @Override
    public BufferedImage run() {
        BufferedImage blurImg = new GaussBlur(image, intensity).run();
        BufferedImage blendImg = new ColorDodge(image, blurImg).blend();
        BufferedImage correctedBlend = new Brightness(blendImg, -10*intensity).run();
        return correctedBlend;
    }
}
