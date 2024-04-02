package com.visionvista.effects.filters;

import com.visionvista.effects.Effect;

import java.io.Serial;

public abstract class Filter extends Effect {
    @Serial
    private static final long serialVersionUID = -4292857528762740893L;

    public Filter() {
        super();
    }

    @Override
    public Object getParameter() {
        return null;
    }
}
