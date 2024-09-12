package com.visionvista;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visionvista.effects.Effect;
import com.visionvista.utils.MiscHelper;
import com.visionvista.utils.Pair;
import io.github.namankhurpia.Pojo.MyModels.EasyVisionRequest;
import io.github.namankhurpia.Pojo.Vision.VisionApiResponse;
import io.github.namankhurpia.Service.EasyVisionService;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ImageStylizeAI {
    private static List<Effect> effectsList;

    private String effectPromptList;

    private static String[] testPrompts = new String[] {"A look like inside of Willy Wonka's chocolate factory", "I want this to be like a 90s gangster movie", "A cinematic and lively look", "Like an Indian wedding", "From a Disney movie", "In the style of Vincent Van Gogh's The Starry Night painting, but it is futuristic and tech-like"};
    private String userPrompt = testPrompts[5];

    private String response;

    private BufferedImage userImage;

    ObjectMapper mapper = new ObjectMapper();

    public void setUserPrompt(String prompt) {
        this.userPrompt = prompt;
    }

    public String generatePrompt() {
        //Add json instructions when testing JSON
        effectPromptList = """
            - Contrast (-100, 100)
            - Brightness (-100, 100)
            - Saturation (0, 100)
            - Vibrance (0, 10)
            - Hue (RGB)
            - Temperature (0, 100)
            - Sepia (0, 10) - yellow-grayish look
            - Duotone (RGB1, RGB2)
            - Glow (0, 10)
            - Vignette (0, 50) - quite intense even at smaller values
            - Oil Painting (0, 50)
            - Color Splash (0, 50) - Adds random paint splashes
            - Pixel Sort (0, 255) - Creates epic glitched effect, most effect around 150
            - Pixelate (0, 50)
            - Chromatic Aberration (0, 10) - Creates color fringing to simulate a 3D effect
            - Anaglyph 3D (0, 30)
            - Box Blur (0, 10)
            - Gaussian Blur (0, 10)
            - Bokeh Blur (0, 20) - Adds soft, fuzzy, dream-like blur
            - Tilt Shift (0, 10) - Soft blur to edges of image
            - Sharpen (0, 10)
            - Rotate (degrees)
            - Grain (0, 100)
            - Watercolor (0, 20)
            - Halftone (0, 20) - Reproduces image with circular dots for a printed look
    
            Fixed effects:
            - Grayscale
            - Negative
            - Cross Process - Brighter blue tones
            - Solarize - Inverts tones in an image, creating surreal effects
            - Split tone - Applies an intense cool blue filter
            - Heat map - Visualizes image as thermal colors
            - Infrared - Simulates infrared photography
            - Cyberpunk - Accentuates futuristic blue tones to the image
            - Pencil Sketch
            - Posterize - Turns darker, black / red tones,
            - Lomography - Brighter green tone
            - Resize
            - Flip Vertical
            - Flip Horizontal
            - Edge Enhance
            """;
        //Include edge cases in example
        String respondJson = """
               Example response:
                [
                    {"effect": "Brightness", "value": 50},
                    {"effect": "Split Tone"},
                    {"effect": "Gaussian Blur", "value": 5},
                    {"effect": "Grayscale"},
                    {"effect": "Hue", "red": 50, "green": 50, "blue": 125}
                    {"effect": "Duotone", "red1": 50, "green1": 50, "blue1": 125, red2": 22, "green2": 70, "blue2": 12}
                ]
                """;

        return """
            Given the desired image style (e.g., "Nostalgic and cinematic look"), create a JSON with the effects and parameters. Provide 12 effects maximum (12 not required). For colors, refer to the example, for multi-word effect, write in capitalized case. Provide no other text but just the JSON, like the example. Use only the effects listed.
            """
                + respondJson +
            """
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
        System.out.println(System.getProperty("OPENAI-GPT4-KEY"));
        VisionApiResponse responseobj = new EasyVisionService().VisionAPI(System.getProperty("OPENAI-GPT4-KEY"), new EasyVisionRequest()
                .setModel("gpt-4o-2024-08-06")
                .setPrompt(prompt)
                .setMaxtokens(500)
                .setImageUrls(new ArrayList<>() {{
                    add(imageURL);
                }}));
        this.response = responseobj.getChoices().get(0).getSystemMessage().content;
    }

    private void parseResponse() {
        Pattern pattern = Pattern.compile("\\[(.*?)\\]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(response);
        if (matcher.find()) {
            response = matcher.group(0);
        }
    }

    private void extractEffects() throws JsonProcessingException {
        effectsList = new ArrayList<>();
        //Extract only from when the JSON part starts
        parseResponse();
        //Configure object mapper
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //Convert JSON to instances of effects
        try {
            effectsList = Arrays.asList(mapper.readerFor(Effect[].class).readValue(response));
            System.out.println((effectsList));
        }
        catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Unable to parse response. Trying again");
            generatePrompt();
            effectsList = Arrays.asList(mapper.readerFor(Effect[].class).readValue(response));
            System.out.println((effectsList));
        }
    }

    public List<Effect> getEffectsList() throws JsonProcessingException {
        extractEffects();
        return effectsList;
    }
}