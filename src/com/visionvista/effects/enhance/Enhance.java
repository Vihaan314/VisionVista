package com.visionvista.effects.enhance;

import com.visionvista.effects.Effect;

import java.io.Serial;

public abstract class Enhance extends Effect {
    @Serial
    private static final long serialVersionUID = -4660313523496985923L;

    public Enhance() {
        super();
    }

    @Override
    public Object getParameter() {
        return null;
    }
}
