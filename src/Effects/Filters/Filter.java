package Effects.Filters;

import Effects.Effect;

import java.awt.image.BufferedImage;

public abstract class Filter extends Effect {
    public Filter(BufferedImage image) {
        super(image);
    }
}
