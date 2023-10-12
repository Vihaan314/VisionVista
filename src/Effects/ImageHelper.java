package Effects;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class ImageHelper {
    public static BufferedImage createBlankImage() {
        return new BufferedImage(1200, 800, BufferedImage.TYPE_INT_ARGB);
    }

    public static String getSerializeDirectory() {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);

        String directory = String.valueOf(f.getSelectedFile());
        return directory;
    }

    public static int getRandomParameter(int min, int max){
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

    public static boolean isBlankImage(BufferedImage image) {
        BufferedImage blankImage = createBlankImage();

        if (image.getWidth() == blankImage.getWidth() && image.getHeight() == blankImage.getHeight()) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    if (image.getRGB(x, y) != blankImage.getRGB(x, y))
                        return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
