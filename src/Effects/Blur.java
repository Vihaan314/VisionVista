package Effects;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class Blur extends Effect{
    private int intensity;

    public Blur(BufferedImage image, int intensity) {
        super(image);
        this.intensity = intensity;
    }

    @Override public String toString() {
        return "Applied Blur. Intensity: " + this.intensity;
    }

    public BufferedImage run() {
        System.out.println("Blurring image");

        BufferedImage blurredImg;

        int size = intensity * 2 + 1;
        float weight = 1.0f / (size * size);
        float[] elements = new float[size * size];

        for (int i = 0; i < elements.length; i++) {
            elements[i] = weight;
        }

        Kernel kernel = new Kernel(size, size, elements);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        blurredImg = op.filter(image, null);

        return blurredImg;
    }

    public static Blur getRandomInstance(BufferedImage image) {
        return new Blur(image, ImageHelper.getRandomParameter(1, 10));
    }
}
