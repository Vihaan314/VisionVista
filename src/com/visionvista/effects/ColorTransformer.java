package com.visionvista.effects;

@FunctionalInterface
public interface ColorTransformer {
    int apply(int x, int y, int rgbColor);
}
