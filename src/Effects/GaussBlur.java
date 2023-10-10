package Effects;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GaussBlur extends Effect{
    private double intensity; //sigma

    public GaussBlur (BufferedImage image, double intensity) {
        super(image);
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

    public BufferedImage run() {
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

            float[] gaussKernal = Helper.flatten2D(gaussKernalDim);
            Kernel kernel = new Kernel(size, size, gaussKernal);
            ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            op.filter(image, blurredImg);

            return blurredImg;
        }
    }

    public static GaussBlur getRandomInstance(BufferedImage image) {
        return new GaussBlur(image, ImageHelper.getRandomParameter(1, 10));
    }
}
