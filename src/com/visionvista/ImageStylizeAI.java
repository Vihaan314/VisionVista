package com.visionvista;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visionvista.effects.Effect;
import com.visionvista.effects.EffectDescription;
import com.visionvista.effects.EffectParameter;
import com.visionvista.utils.MiscHelper;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import io.github.namankhurpia.Pojo.MyModels.EasyVisionRequest;
import io.github.namankhurpia.Pojo.Vision.VisionApiResponse;
import io.github.namankhurpia.Service.EasyVisionService;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ImageStylizeAI {
    private static List<Effect> effectsList;

    private String userPrompt;
    private String response;

    private BufferedImage userImage;

    ObjectMapper mapper = new ObjectMapper();

    public void setUserPrompt(String prompt) {
        this.userPrompt = prompt;
    }

    public static String formatEffectName(String effectName) {
        //Insert a space before any uppercase letter or digit that follows a lowercase letter
        return effectName
                .replaceAll("([a-z])([A-Z])", "$1 $2")  //lowercase then uppercase
                .replaceAll("([a-zA-Z])([0-9])", "$1 $2")  //letter then digit
                .replaceAll("([0-9])([A-Z])", "$1 $2");   //digit then uppercase letter
    }

    public String generateEffectDescriptions() {
        StringBuilder effectsList = new StringBuilder();
        try (ScanResult scanResult = new ClassGraph()
                .enableClassInfo()
                .acceptPackages("com.visionvista.effects")
                .scan()) {
            for (ClassInfo classInfo : scanResult.getSubclasses(Effect.class.getName())) {
                Class<?> cls = classInfo.loadClass();
                if (!Modifier.isAbstract(cls.getModifiers()) && !cls.isEnum() && !cls.isInterface() && !cls.isAnnotation()) {
                    effectsList.append("- ").append(formatEffectName(cls.getSimpleName()));
                    if (cls.isAnnotationPresent(EffectParameter.class)) {
                        EffectParameter effectParameter = cls.getAnnotation(EffectParameter.class);
                        String parameters = effectParameter.parameters();
                        effectsList.append(" (" + parameters + ")");
                    }
                    if (cls.isAnnotationPresent(EffectDescription.class)) {
                        EffectDescription effectDescription = cls.getAnnotation(EffectDescription.class);
                        String description = effectDescription.description();
                        effectsList.append(" - ").append(description);
                    }
                    effectsList.append("\n");
                }
            }
        }
        return effectsList.toString();
    }

    public String generatePrompt() {
        //Add json instructions when testing JSON
        String effectPromptList = generateEffectDescriptions();
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
        System.out.println(prompt);
        userImage = EditorState.getInstance().getImage();
        String imageURL = "data:image/jpeg;base64," + MiscHelper.imageToBase64(userImage);
        System.out.println("API KEY: " + System.getProperty("OPENAI-GPT4-KEY"));
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
//            generatePrompt();
//            effectsList = Arrays.asList(mapper.readerFor(Effect[].class).readValue(response));
//            System.out.println((effectsList));
        }
    }

    public List<Effect> getEffectsList() throws JsonProcessingException {
        extractEffects();
        return effectsList;
    }
}