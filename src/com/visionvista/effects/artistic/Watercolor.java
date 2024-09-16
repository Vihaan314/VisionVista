package com.visionvista.effects.artistic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.effects.EffectDescription;
import com.visionvista.effects.EffectType;
import com.visionvista.effects.EffectParameter;
import com.visionvista.effects.blur.GaussianBlur;
import com.visionvista.utils.ColorManipulator;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.util.Random;

import static com.visionvista.utils.ColorManipulator.clampCoordinate;

@EffectParameter(parameters = "0, 12")
public class Watercolor extends Artistic {
    @Serial
    private static final long serialVersionUID = -3395732790973255657L;

    private double intensity;

    private int textureIntensity;
    private int turbulence;
    private int blurIntensity;
    private int darkenAmount;
    private int noiseIntensity;

    public Watercolor(@JsonProperty("intensity") double intensity) {
        super();
        this.intensity = intensity;
        this.textureIntensity = (int) (intensity);
        this.turbulence = (int) (Math.ceil(intensity/4) + 1);
        this.blurIntensity = (int) (intensity * 2/5 - 1);
        this.darkenAmount = (int) (22+intensity);
        this.noiseIntensity = (int) (intensity+3);
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        if (intensity == 0) {
            return image;
        }
        //Apply paper texture to simulate watercolor paper grain
        BufferedImage texturedImage = addPaperTexture(image);
        //Simulate pigment flow using turbulent diffusion
        BufferedImage flowImage = simulateTurbulentFlow(texturedImage);
        BufferedImage edgeDarkenedImage = applyEdgeDarkening(flowImage, image);
        //Simulate wet-on-wet pigment dispersion
        BufferedImage pigmentImage = simulatePigmentDispersion(edgeDarkenedImage);
        return pigmentImage;
    }

    private BufferedImage addPaperTexture(BufferedImage image) {
        BufferedImage texturedImage = getEmptyImage(image);
        Random rand = new Random();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                //Noise to simulate paper texture
                int noise = rand.nextInt(textureIntensity) - (textureIntensity / 2);
                Color color = new Color(rgb);
                int r = ColorManipulator.truncate(color.getRed() + noise);
                int g = ColorManipulator.truncate(color.getGreen() + noise);
                int b = ColorManipulator.truncate(color.getBlue() + noise);
                texturedImage.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return texturedImage;
    }

    private BufferedImage simulateTurbulentFlow(BufferedImage image) {
        BufferedImage flowImage = getEmptyImage(image);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                //Add distortion
                int offsetX = (int) (Math.sin(y / 11.0) * turbulence);
                int offsetY = (int) (Math.cos(x / 11.0) * turbulence);
                int newX = clampCoordinate(x + offsetX, image.getWidth());
                int newY = clampCoordinate(y + offsetY, image.getHeight());

                flowImage.setRGB(newX, newY, new Color(color.getRed(), color.getGreen(), color.getBlue()).getRGB());
            }
        }

        //Gaussian blur to soften the flow and create smoother diffusion
        flowImage = new GaussianBlur(blurIntensity).run(flowImage);
        return flowImage;
    }

    private BufferedImage applyEdgeDarkening(BufferedImage image, BufferedImage original) {
        BufferedImage edgeDarkenedImage = getEmptyImage(image);

        for (int y = 1; y < original.getHeight() - 1; y++) {
            for (int x = 1; x < original.getWidth() - 1; x++) {
                int rgb = original.getRGB(x, y);
                int rgbRight = original.getRGB(x + 1, y);
                int rgbDown = original.getRGB(x, y + 1);

                int diffRight = Math.abs(rgb - rgbRight);
                int diffDown = Math.abs(rgb - rgbDown);

                int edgeVal = Math.max(diffRight, diffDown);
                Color edgeColor = new Color(image.getRGB(x, y));

                //Darken image
                if (edgeVal > 20) {
                    edgeDarkenedImage.setRGB(x, y, new Color(
                            ColorManipulator.truncate(edgeColor.getRed() - darkenAmount),
                            ColorManipulator.truncate(edgeColor.getGreen() - darkenAmount),
                            ColorManipulator.truncate(edgeColor.getBlue() - darkenAmount)).getRGB());
                } else {
                    edgeDarkenedImage.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }

        return edgeDarkenedImage;
    }

    private BufferedImage simulatePigmentDispersion(BufferedImage image) {
        BufferedImage dispersedImage = new GaussianBlur(blurIntensity).run(image);

        Random rand = new Random();
        for (int y = 0; y < dispersedImage.getHeight(); y++) {
            for (int x = 0; x < dispersedImage.getWidth(); x++) {
                int rgb = dispersedImage.getRGB(x, y);
                //More random noise to create the pigment dispersion
                int noise = rand.nextInt(noiseIntensity) - ((noiseIntensity-1)/2);
                Color color = new Color(rgb);

                int r = ColorManipulator.truncate(color.getRed() + noise);
                int g = ColorManipulator.truncate(color.getGreen() + noise);
                int b = ColorManipulator.truncate(color.getBlue() + noise);
                dispersedImage.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }

        return dispersedImage;
    }

    @Override
    public Object getParameter() {
        return intensity;
    }

    @Override
    public String toString() {
        return "Applied Watercolor";
    }

    public static Watercolor getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.WATERCOLOR.getSliderBounds();
        return new Watercolor(ImageHelper.getRandomParameter(bounds));
    }
}
