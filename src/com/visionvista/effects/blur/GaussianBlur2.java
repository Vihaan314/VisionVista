package com.visionvista.effects.blur;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.visionvista.effects.EffectParameter;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.Serial;

//Add annotation to say to not inlcude in automations
@EffectParameter(parameters = "0, 10")
public class GaussianBlur2 extends Blur {
    @Serial
    private static final long serialVersionUID = 3829537176814306082L;

    private double intensity;

    public GaussianBlur2(@JsonProperty("intensity") double intensity) {
        super();
        this.intensity = intensity;
    }

    @Override
    public String toString() {
        return "Applied Gaussian Blur 2. Intensity: " + this.intensity;
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        int radius = (int) Math.ceil(this.intensity);
        //Create Gaussian kernel
        float[] kernelData = createGaussianKernel(radius);
        Kernel kernel = new Kernel(radius * 2 + 1, radius * 2 + 1, kernelData);

        ConvolveOp convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        return convolveOp.filter(image, null);
    }

    private float[] createGaussianKernel(int radius) {
        //Gaussian function creation
        int size = radius * 2 + 1;
        float[] kernel = new float[size * size];
        double sigma = radius / 3.0;
        double mean = radius;
        double sum = 0.0;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                double dx = x - mean;
                double dy = y - mean;
                double exponent = -(dx * dx + dy * dy) / (2 * sigma * sigma);
                double value = Math.exp(exponent);
                kernel[y * size + x] = (float) value;
                sum += value;
            }
        }
        //Normalize kernal
        for (int i = 0; i < kernel.length; i++) {
            kernel[i] /= (float) sum;
        }
        return kernel;
    }

    public static GaussianBlur2 getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.GAUSSIAN_BLUR.getSliderBounds();
        return new GaussianBlur2(ImageHelper.getRandomParameter(bounds));
    }
}
