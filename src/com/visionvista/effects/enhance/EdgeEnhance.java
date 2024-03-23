package com.visionvista.effects.enhance;

import com.visionvista.effects.Effect;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class EdgeEnhance extends Enhance {

    public EdgeEnhance() {
        super();
    }

    @Override
    public Object getParameter() {
        return null;
    }

    @Override
    public String toString() {
        return "Applied Edge Enhance";
    }

    @Override
    public BufferedImage run(BufferedImage image) {
        float[] edgeKernel = {
                0f, -1f, 0f,
                -1f, 5f, -1f,
                0f, -1f, 0f
        };
        Kernel kernel = new Kernel(3, 3, edgeKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return op.filter(image, null);
    }

    public static EdgeEnhance getRandomInstance() {
        return new EdgeEnhance();
    }
}
