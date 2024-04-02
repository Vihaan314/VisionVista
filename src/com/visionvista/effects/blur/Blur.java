package com.visionvista.effects.blur;

import com.visionvista.effects.Effect;

import java.io.Serial;

public abstract class Blur extends Effect {
    @Serial
    private static final long serialVersionUID = -6566740156403726117L;

    public Blur() {
        super();
    }

    @Override
    public Object getParameter() {
        return null;
    }
}
