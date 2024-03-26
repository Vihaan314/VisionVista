package com.visionvista;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.visionvista.effects.Effect;
import com.visionvista.utils.MiscHelper;
import okhttp3.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageStylizeAI {

    private static final String API_KEY = System.getenv("OPENAI_GPT4_KEY");
    private static final String API_URL = "https://api.openai.com/v1/completions";

    private static String userPrompt;

    public void setUserPrompt(String prompt) {
        this.userPrompt = prompt;
    }

    public static void main(String[] args) throws IOException {
        userPrompt = "Cinematic and lively look";
        BufferedImage image = ImageIO.read(new File("C:\\Users\\vihaa\\Documents\\ztest.jpg"));
        ArrayList<Effect> effectsList = getEffectsList(image);
    }

    public static ArrayList<Effect> getEffectsList(BufferedImage image) {
        try {
            String prompt = """
                    Specify the desired image style (e.g., "Nostalgic and cinematic look"). Based on the style, list the effects with parameters to apply. Limit to 10 effects. For colors, use RGB values. Only list effects and parameters, no additional text but this.
                                       
                    Effects with parameter bounds:
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
                    - Color Splash (0, 50)
                    - Pixel sort (0, 255)
                    - Pixelate (0, 50)
                    - Chromatic Aberration (0, 10)
                    - Anaglyph 3D (0, 30)
                    - Box Blur (0, 10)
                    - Gaussian Blur (0, 10)
                    - Bokeh Blur (0, 20)
                    - Tilt shift (0, 10)
                    - Sharpen (0, 10)
                    - Rotate (degrees)
                                       
                    Fixed effects:
                    - Grayscale
                    - Negative
                    - Cross process
                    - Solarize
                    - Split tone
                    - Heat map
                    - Infrared
                    - Halftone
                    - Duotone
                    - Watercolor
                    - Cyberpunk
                    - Pencil sketch
                    - Posterize
                    - Lomography
                    - Resize
                    - Flip vertical
                    - Flip horizontal
                    - Edge Enhance
                                       
                    Desired style:
                    "
                    """ + userPrompt + "\"";
            String response = createGPT4Prompt(prompt, image);
            return extractEffectsFromResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Effect> extractEffectsFromResponse(String response) {
        ArrayList<Effect> effectsList = new ArrayList<>();
        System.out.println(response);
        return effectsList;
    }

    private static String createGPT4Prompt(String promptText, BufferedImage image) throws Exception {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        String base64Image = MiscHelper.imageToBase64(image);

        JsonArray messages = new JsonArray();

        // Constructing the text message part
        JsonObject textMessage = new JsonObject();
        textMessage.addProperty("role", "user");
        JsonObject textContent = new JsonObject();
        textContent.addProperty("type", "text");
        textContent.addProperty("text", promptText);
        textMessage.add("content", textContent);

        // Constructing the image message part
        // If the API expects an image URL, make sure 'base64Image' is a URL to the image
        // If directly sending Base64-encoded data is supported, adjust the field name and content accordingly
        JsonObject imageMessage = new JsonObject();
        imageMessage.addProperty("role", "user");
        JsonObject imageContent = new JsonObject();
        imageContent.addProperty("type", "image_url"); // Or another type if sending image data directly
        imageContent.addProperty("image_url", base64Image); // Use the actual field name and content expected by the API
        imageMessage.add("content", imageContent);

        messages.add(textMessage);
        messages.add(imageMessage);

        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("model", "gpt-4-vision-preview");
        jsonBody.add("messages", messages);
        jsonBody.addProperty("max_tokens", 200);


        System.out.println(jsonBody.toString());

        RequestBody requestBody = RequestBody.create(jsonBody.toString(), mediaType);

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);

            if (response.body() != null) {
                return response.body().string();
            } else {
                return "No response body";
            }
        }
    }

    private static JsonObject createTextContent(String text) {
        JsonObject content = new JsonObject();
        content.addProperty("type", "text");
        content.addProperty("text", text);
        return content;
    }

    private static JsonObject createImageContent(String imageUrl) {
        JsonObject content = new JsonObject();
        content.addProperty("type", "image_url");
        content.addProperty("image_url", imageUrl);
        return content;
    }
}
