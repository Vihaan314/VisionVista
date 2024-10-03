package com.visionvista.effects.blur;

import com.visionvista.effects.EffectType;
import com.visionvista.utils.ImageHelper;
import com.visionvista.utils.Pair;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class GaussianBlur2 extends Blur {
    private double intensity; //sigma

    public GaussianBlur2(double intensity) {
        super();
        this.intensity = intensity;
    }

    @Override public String toString() {
        return "Applied Gaussian Blur. Intensity: " + this.intensity;
    }

    public static float gauss2D(int x, int y, double sigma) {
        float multiple = (float) (1/(2*Math.PI*sigma*sigma));
        float exponential = (float) (1 / (Math.pow(Math.E, (double) (x * x + y * y) /(2*sigma*sigma))));
        return multiple*exponential;
    }

    public BufferedImage run(BufferedImage image) {
        System.out.println("Gauss Blurring image");
        if (intensity == 0) {
            return image;
        }
        else {
            BufferedImage blurredImg = getEmptyImage(image);
            int size = (int) (((6*intensity)%2 == 0) ? (6*intensity-1) : (6*intensity));

            float[][] gaussKernalDim = new float[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int centerPos = (size-1)/2;
                    int x = j - centerPos;
                    int y = i - centerPos;
                    gaussKernalDim[i][j] = gauss2D(x, y, intensity);
                }
            }

            float[] gaussKernal = ImageHelper.flatten2D(gaussKernalDim);
            Kernel kernel = new Kernel(size, size, gaussKernal);
            ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            op.filter(image, blurredImg);
            return blurredImg;
        }
    }

    public static GaussianBlur2 getRandomInstance() {
        Pair<Integer, Integer> bounds = EffectType.GAUSSIAN_BLUR.getSliderBounds();
        return new GaussianBlur2(ImageHelper.getRandomParameter(bounds));
    }
}