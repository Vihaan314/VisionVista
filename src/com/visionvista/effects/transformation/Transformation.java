package com.visionvista.effects.transformation;

import com.visionvista.effects.Effect;

import java.io.Serial;

public abstract class Transformation extends Effect {
    @Serial
    private static final long serialVersionUID = 4715583490214351997L;

    public Transformation() {
        super();
    }

    @Override
    public Object getParameter() {
        return null;
    }
}
