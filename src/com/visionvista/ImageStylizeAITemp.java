package com.visionvista;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.visionvista.effects.Effect;
import com.visionvista.effects.EffectType;
import com.visionvista.utils.MiscHelper;
import io.github.namankhurpia.Pojo.MyModels.EasyVisionRequest;
import io.github.namankhurpia.Pojo.Vision.VisionApiResponse;
import io.github.namankhurpia.Service.EasyVisionService;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ImageStylizeAITemp {
    private ArrayList<Effect> effectsList;

    private String effectPromptList;

    private String[] testPrompts = new String[] {"A look like inside of Willy Wonka's chocolate factory", "I want this to be like a 90s gangster movie", "A cinematic and lively look", "Like an Indian wedding", "From a Disney movie", "In the style of Vincent Van Gogh's The Starry Night painting, but it is futuristic and tech-like"};
    private String userPrompt = testPrompts[5];

    private String response;

    private BufferedImage userImage;

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
            - Sepia (0, 10)
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
        this.response = responseobj.getChoices().get(0).getSystemMessage().content;
        System.out.println(response);
    }

    private void extractEffects() throws JsonProcessingException {
        effectsList = new ArrayList<>();
        //TEMPORARY SOLUTION
        String responseJSON = """
                {
                    "effects": [
                        {
                            "name": "Pixelate",
                            "parameters": {
                                "intensity": 8
                            }
                        },
                        {
                            "name": "Hue",
                            "parameters": {
                                "RGB": (255, 0, 0)
                            }
                        },
                        {
                            "name": "Contrast",
                            "parameters": {
                                "intensity": -20
                            }
                        },
                        {
                            "name": "Brightness",
                            "parameters": {
                                "intensity": -10
                            }
                        },
                        {
                            "name": "Saturation",
                            "parameters": {
                                "intensity": 60
                            }
                        },
                        {
                            "name": "Sepia",
                            "parameters": {
                                "intensity": 3
                            }
                        },
                        {
                            "name": "Temperature",
                            "parameters": {
                                "intensity": 60
                            }
                        },
                        {
                            "name": "Glow",
                            "parameters": {
                                "intensity": 5
                            }
                        },
                        {
                            "name": "Vignette",
                            "parameters": {
                                "intensity": 20
                            }
                        },
                        {
                            "name": "Oil Painting",
                            "parameters": {
                                "intensity": 10
                            }
                        }
                    ]
                }
                """;
//        response = """
//                {
//                  "Saturation": 50,
//                  "Hue": {"Red": 255, "Green": 0, "Blue": 0},
//                  "Contrast": 20,
//                  "Brightness": -10,
//                  "Temperature": 60,
//                  "Sepia": 3,
//                  "Vignette": 30,
//                  "Glow": 5,
//                  "Pixelate": 5,
//                  "Oil Painting": 10
//                }
//                """;
        String[] responseLines = response.split("\\r?\\n");
        for (String effectResponse : responseLines) {
            String[] effectResponseSplit = effectResponse.split("[\\p{Punct}\\s]+");
            List<String> list = new ArrayList<>(Arrays.asList(effectResponseSplit));
            list.removeAll(Arrays.asList("", null));
            String[] effectSplit = new String[list.size()];
            effectSplit = list.toArray(effectSplit);
            System.out.println(Arrays.toString(effectSplit));
            Effect generatedEffect = null;
            if (effectSplit.length == 1) {
                generatedEffect = EffectType.fromLabel(effectSplit[0]).getEffect();
                System.out.println("Length 1: " + generatedEffect);
            }
            else if (effectSplit.length == 2) {
                generatedEffect = EffectType.fromLabel(effectSplit[0]).getEffect(Double.parseDouble(effectSplit[1]));
                System.out.println("Length 2: " + generatedEffect);
            }
            else {
                StringBuilder effectName = new StringBuilder();
                ArrayList<Object> params = new ArrayList<>();
                for (String s : effectSplit) {
                    if (MiscHelper.isString(s)) {
                        effectName.append((effectName.isEmpty()) ? s : ("_"+s) );
                    } else {
                        params.add(s);
                    }
                }
                System.out.println("Not l2 Effect name: " + effectName + ", Params: " + params);
            }
            effectsList.add(generatedEffect);
        }
//        Gson gson = new Gson();
////        from JSON to object
//        Effect effect = gson.fromJson(response, Effect.class);
//        List<String> responseLines = Arrays.asList(response.split("\\r?\\n"));
        //        response = """
//            [
//                {"effect": "Contrast", "value": 50},
//                {"effect": "Brightness", "value": 50},
//                {"effect": "Saturation", "value": 50},
//                {"effect": "Vibrance", "value": 5},
//                {"effect": "Temperature", "value": 50},
//                {"effect": "Sepia", "value": 5},
//                {"effect": "Glow", "value": 5},
//                {"effect": "Vignette", "value": 25},
//                {"effect": "Oil Painting", "value": 25},
//                {"effect": "Color Splash", "value": 25},
//                {"effect": "Pixel Sort", "value": 128},
//                {"effect": "Pixelate", "value": 25},
//                {"effect": "Chromatic Aberration", "value": 5},
//                {"effect": "Anaglyph 3D", "value": 15},
//                {"effect": "Box Blur", "value": 5},
//                {"effect": "Gaussian Blur", "value": 5},
//                {"effect": "Bokeh Blur", "value": 10},
//                {"effect": "Tilt Shift", "value": 5},
//                {"effect": "Sharpen", "value": 5},
//                {"effect": "Rotate", "value": 90},
//                {"effect": "Hue", "red": 90, "green": 50, "blue": 50},
//                {"effect": "Grayscale"},
//                {"effect": "Negative"},
//                {"effect": "Cross Process"},
//                {"effect": "Solarize"},
//                {"effect": "Split Tone"},
//                {"effect": "Heatmap"},
//                {"effect": "Infrared"},
//                {"effect": "Halftone"},
//                {"effect": "Watercolor"},
//                {"effect": "Cyberpunk"},
//                {"effect": "Pencil Sketch"},
//                {"effect": "Posterize"},
//                {"effect": "Lomography"},
//                {"effect": "Flip Vertical"},
//                {"effect": "Flip Horizontal"},
//                {"effect": "Edge Enhance"}
//            ]
//        """;
    }

    public ArrayList<Effect> getEffectsList() throws JsonProcessingException {
        extractEffects();
        return effectsList;
    }

//    public static void main(String[] args) throws JsonProcessingException {
//        extractEffects();
//    }
}