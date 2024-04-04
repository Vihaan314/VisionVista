package com.visionvista;

import com.visionvista.effects.transformation.Resize;
import io.github.namankhurpia.DAO.DAOImpl;
import io.github.namankhurpia.Pojo.Image.ImageRequest;
import io.github.namankhurpia.Pojo.Image.ImageResponse;
import io.github.namankhurpia.Service.EasyopenaiService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URL;

public class ImageGenerationAI {
    private static String[] testPrompts = new String[] {"A seal that is really friendly that is sitting with other seal friends at a beach under an umbrella and discussing serious business.",
            "A hyperrealistic futuristic tall mansion containing many crazy tech contraptions and traps for trespassers. This is set far into the future, and the mansion is situated at the top of a mountainous waterfall.",
    "Generate a high quality hyper realistic, detailed image of two supermassive black hole galaxies that are colliding in an epic fusion of elements and color bursting. These are warping space time and are showing a stunning visual of the catastrophic collision - there are massive time bending elements and debris flying around, the accretion disks fusing, and the relativistic jets beaming at each other. It looks like two universes are cracking and breaking into each other."
    };
//    private String userPrompt = testPrompts[2];

    private String userPrompt;

    public void setUserPrompt(String prompt) {
        this.userPrompt = prompt;
    }

    public void generateImage() throws Exception {
        System.out.println(userPrompt);
        ImageRequest imageRequest  = ImageRequest.builder()
                .prompt(this.userPrompt)
                .model("dall-e-3")
                .quality("hd")
                .build();
        ImageResponse response = new EasyopenaiService(new DAOImpl()).createImage(System.getenv("OPENAI-GPT4-KEY"),imageRequest);
        URL imageURL = new URI(response.getData().get(0).getUrl()).toURL();
        BufferedImage generatedImage = new Resize(983, 983).run(ImageIO.read(imageURL));
        EditorState.getInstance().setImage(generatedImage);
        EffectHistory effectHistory = new EffectHistory();
        effectHistory.add(null, generatedImage);
        EditorState.getInstance().setState(effectHistory);
        System.out.println(response);

        ImageEditor editor = new ImageEditor("Vision Vista", new String[] {userPrompt, "png"});
        editor.show();
    }
}
