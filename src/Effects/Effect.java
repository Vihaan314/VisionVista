package Effects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public abstract class Effect implements Serializable
{
    protected transient BufferedImage image;

    public Effect(BufferedImage image) {
        this.image = image;
    }

    public abstract BufferedImage run();

    public static BufferedImage getEmptyImage(BufferedImage image) {
        return new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    }

    public BufferedImage runWithImage(BufferedImage image) {
        this.image = image;
        return this.run();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();  // serialize other fields

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        out.writeInt(imageBytes.length);
        out.write(imageBytes);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();  // deserialize other fields

        int length = in.readInt();
        byte[] imageBytes = new byte[length];
        in.readFully(imageBytes);
        image = ImageIO.read(new ByteArrayInputStream(imageBytes));
    }

    public abstract String toString();

    public String filterNumbers(String[] effectData) {
        String arrayString = Arrays.toString(effectData);
        if (arrayString.contains(".0")) {
            arrayString = arrayString.replace(".0", "");
        }
        char[] charArray = arrayString.toCharArray();
        StringBuilder filteredNumbers = new StringBuilder();
        String negativeReference = "-";
        for (int i = 0; i < charArray.length; i++) {
            if (Character.isDigit(charArray[i])) {
                filteredNumbers.append(charArray[i]);
            }
            else if (charArray[i] == negativeReference.charAt(0) && Character.isDigit(charArray[i+1])) {
                filteredNumbers.append(charArray[i]);
            }
        }
        return filteredNumbers.toString();
    }

    public int locateFinish(String[] stringEffect) {
        int index = 0;
        for (int i = 0; i < stringEffect.length; i ++) {
            if (stringEffect[i].endsWith(".")) {
                index = i;
                break;
            }
        }
        return index;
    }

    public String getEffectInitials() {
        String[] splitString = this.toString().split(" ");
        System.out.println(Arrays.toString(splitString));
        StringBuilder effect = new StringBuilder();
        if (splitString[0].equals("Applied")) {
            if (!(splitString[1].contains("-")) && filterNumbers(splitString).equals("")) {
                effect = new StringBuilder(splitString[1].substring(0, 1) + splitString[1].charAt(splitString[1].length() - 1));
            }
            else if (!filterNumbers(splitString).equals("")) {
                effect = new StringBuilder(splitString[1].substring(0, 1).toLowerCase() + splitString[1].charAt(1));
                if  (!splitString[1].endsWith(".")) {
                    for (int i = 2; i < locateFinish(splitString)+1; i++) {
                        effect.append(Character.toLowerCase(splitString[i].charAt(0)));
                    }
                }
                effect.append("_").append(filterNumbers(splitString));
                System.out.println(filterNumbers(splitString));
            }
            else {
                for (int i = 0; i < splitString[1].split("-").length; i++) {
                    effect.append(splitString[1].split("-")[i].charAt(0));
                }
                effect.append(splitString[1].charAt(splitString[1].length()-1));
            }
            //checking later (counter)
        }
        else if (splitString[0].equals("Flipped")) {
            effect = new StringBuilder(splitString[0].substring(0, 1).toLowerCase() + splitString[1].substring(0, 2));
        }

        return effect.toString();
    }
}
