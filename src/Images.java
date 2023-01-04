import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Images {

    static HashMap<String, BufferedImage> images = new HashMap<>();
    static String[] imageNames = {
            "water1",
            "cliffT",
            "cliffM",
            "cliffB",
            "grass",
            "grassDetail",
            "pathTR",
            "pathTL",
            "pathTM",
            "pathMR",
            "pathML",
            "pathMM",
            "pathBR",
            "pathBL",
            "pathBM",
            "lpathTR",
            "lpathTL",
            "lpathBR",
            "lpathBL",
            "cliffS",
            "grassEdge",
            "bridgeL",
            "bridgeM",
            "bridgeR"
    };

    static Image getImage(String s) {
        if (!images.containsKey(s)) {
            throw new IllegalArgumentException("There is no image called " + s);
        }
        return images.get(s);
    }

    static void initializeImages() {
        try {
            for (String name : imageNames) {
                images.put(name, ImageIO.read(new File("src/resources/"+name+".png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
