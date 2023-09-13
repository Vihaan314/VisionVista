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
}
