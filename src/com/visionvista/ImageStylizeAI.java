package com.visionvista;

import com.visionvista.effects.Effect;
import com.visionvista.utils.MiscHelper;
import io.github.namankhurpia.Pojo.MyModels.EasyVisionRequest;
import io.github.namankhurpia.Pojo.Vision.VisionApiResponse;
import io.github.namankhurpia.Service.EasyVisionService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class ImageStylizeAI {
    private ArrayList<Effect> effectsList;

    private String effectPromptList;

    private String[] testPrompts = new String[] {"A look like inside of Willy Wonka's chocolate factory", "I want this to be like a 90s gangster movie", "A cinematic and lively look", "Like an Indian wedding", "From a Disney movie", "In the style of Vincent Van Gogh's The Starry Night painting, but it is futuristic and tech-like"};
    private String userPrompt = testPrompts[5];

    private BufferedImage userImage;

    public void setUserPrompt(String prompt) {
        this.userPrompt = prompt;
    }

    public void setCurrentImage(BufferedImage image) {
        this.userImage = image;
    }

    public String generatePrompt() {
        effectPromptList = """
            - Contrast (-100, 100)
            - Brightness (-100, 100)
            - Saturation (0, 100)
            - Vibrance (0, 10)
            - Hue (RGB)
            - Temperature (0, 100)
            - Sepia (0, 100)
            - Glow (0, 10)
            - Vignette (0, 50)
            - Oil Painting (0, 50)
            - Color Splash (0, 50) - Adds random paint splashes
            - Pixel sort (0, 255) - Creates a glitch effect by sorting pixels
            - Pixelate (0, 50)
            - Chromatic Aberration (0, 10) - Creates color fringing to simulate a 3D effect
            - Anaglyph 3D (0, 30)
            - Box Blur (0, 10)
            - Gaussian Blur (0, 10)
            - Bokeh Blur (0, 20) - Adds soft, fuzzy, dream-like blur
            - Tilt shift (0, 10) - Soft blur to edges of image
            - Sharpen (0, 10)
            - Rotate (degrees)
    
            Fixed effects:
            - Grayscale
            - Negative
            - Cross process - Brighter blue tones
            - Solarize - Inverts tones in an image, creating surreal effects
            - Split tone - Applies an intense blue-yellowish filter
            - Heat map - Visualizes image as thermal colors
            - Infrared - Simulates infrared photography
            - Halftone - Reproduces images with dots for a printed look
            - Duotone
            - Watercolor
            - Cyberpunk - Accentuates futuristic blue tones to the image
            - Pencil sketch
            - Posterize - Turns darker, black / red tones,
            - Lomography - Brighter green tone
            - Resize
            - Flip vertical
            - Flip horizontal
            - Edge Enhance
            """;

        return """
            Specify the desired image style (e.g., "Nostalgic and cinematic look"). Based on the style, list the effects with parameters to apply. Limit to 10 effects. For colors, use RGB value tuple. Only list effects and parameters, and no other text but that.
            Effects with parameter bounds:
            """
                + effectPromptList +
            """
            Desired style:
            """
                + userPrompt;
    }

    public void generateEffectsList() throws Exception {
        String prompt = generatePrompt();
        userImage = EditorState.getInstance().getImage();
        String imageURL = "data:image/jpeg;base64," + MiscHelper.imageToBase64(userImage);
        System.out.println(prompt);
        VisionApiResponse responseobj = new EasyVisionService().VisionAPI(System.getenv("OPENAI-GPT4-KEY"), new EasyVisionRequest()
                .setModel("gpt-4-vision-preview")
                .setPrompt(prompt)
                .setMaxtokens(500)
                .setImageUrls(new ArrayList<>() {{
                    add(imageURL);
                }}));
        System.out.println(responseobj.getChoices().get(0).getSystemMessage().content);
    }

    private void extractEffects() {
        //Parsing
    }

    public ArrayList<Effect> getEffectsList() {

        return effectsList;
    }

//    public static void main(String[] args) throws Exception {
//        generateEffectsList();
//    }
}