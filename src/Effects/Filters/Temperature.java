package Effects.Filters;

import Effects.Contrast;
import Effects.Effect;
import Effects.Saturation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Temperature extends Filter {
    private double amount;

    public Temperature(BufferedImage image, double amount) {
        super(image);
        this.amount = amount;
    }

    @Override public String toString() {
        return "Applied Temperature. Amount: " + this.amount;
    }

    @Override public BufferedImage run() {
        System.out.println("Changing Temperature");
        BufferedImage sat_img = new Saturation(image, amount*0.5).run();
        BufferedImage con_img = new Contrast(sat_img, amount*0.5).run();

        return con_img;
    }
}
