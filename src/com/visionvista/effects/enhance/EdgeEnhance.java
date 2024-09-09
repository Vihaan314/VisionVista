package com.visionvista.effects.enhance;

import com.visionvista.effects.Effect;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.Serial;

public class EdgeEnhance extends Enhance {

    @Serial
    private static final long serialVersionUID = -7949423668169675062L;

    public EdgeEnhance() {
        super();
    }

    @Override
    public String toString() {
        return "Applied Edge Enhance";
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        float[] edgeKernel = {
                0f, -0.5f, 0f,
                -0.5f, 3f, -0.5f,
                0f, -0.5f, 0f
        };
        Kernel kernel = new Kernel(3, 3, edgeKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return op.filter(image, null);
    }

    public static EdgeEnhance getRandomInstance() {
        return new EdgeEnhance();
    }
}

