package com.visionvista.effects.artistic;

import com.visionvista.effects.Effect;

import java.io.Serial;

public abstract class Artistic extends Effect {
    @Serial
    private static final long serialVersionUID = -7116696165198072824L;

    public Artistic() {
        super();
    }

    @Override
    public Object getParameter() {
        return null;
    }
}
