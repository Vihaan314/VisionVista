package com.visionvista.effects.blur;

import com.visionvista.effects.Effect;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.MathHelper;
import com.visionvista.utils.Pair;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class GaussBlur extends Blur {
    private double intensity; //sigma

    public GaussBlur (double intensity) {
        super();
        this.intensity = intensity;
    }

    @Override
    public String toString() {
        return "Applied Gaussian Blur. Intensity: " + this.intensity;
    }

    public BufferedImage run(BufferedImage image) {
        System.out.println("Gauss Blurring image");
        if (intensity == 0) {
            return image;
        } else {
            return applySeparableGaussianBlur(image, intensity);
        }
    }

    private BufferedImage applySeparableGaussianBlur(BufferedImage image, double sigma) {
        int radius = (int) Math.ceil(sigma * 3);
        float[] kernel = create1DGaussianKernel(sigma, radius);

        BufferedImage horizontalBlurred = convolveHorizontally(image, kernel);
        return convolveVertically(horizontalBlurred, kernel);
    }

    private float[] create1DGaussianKernel(double sigma, int radius) {
        int size = radius * 2 + 1;
        float[] kernel = new float[size];
        double sigmaSquare = sigma * sigma;
        double piSigma = 2.0 * Math.PI * sigmaSquare;
        double term = 1.0 / Math.sqrt(piSigma);

        for (int i = -radius; i <= radius; i++) {
            kernel[i + radius] = (float) (term * Math.exp(-i * i / (2 * sigmaSquare)));
        }

        return kernel;
    }

    private BufferedImage convolveHorizontally(BufferedImage image, float[] kernel) {
        Kernel kernelHor = new Kernel(kernel.length, 1, kernel);
        java.awt.image.ConvolveOp op = new java.awt.image.ConvolveOp(kernelHor, java.awt.image.ConvolveOp.EDGE_NO_OP, null);
        return op.filter(image, null);
    }

    private BufferedImage convolveVertically(BufferedImage image, float[] kernel) {
        Kernel kernelVer = new Kernel(1, kernel.length, kernel);
        java.awt.image.ConvolveOp op = new java.awt.image.ConvolveOp(kernelVer, java.awt.image.ConvolveOp.EDGE_NO_OP, null);
        return op.filter(image, null);
    }

    public static GaussBlur getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.GAUSSIAN_BLUR.getSliderBounds();
        return new GaussBlur(ImageHelper.getRandomParameter(bounds));
    }
}
