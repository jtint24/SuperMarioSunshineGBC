import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Images {
    static BufferedImage water1;
    static BufferedImage cliffT;
    static BufferedImage cliffM;
    static BufferedImage cliffB;

    static void initializeImages() {
        try {
            water1 = ImageIO.read(new File("src/resources/water1.png"));
            cliffT = ImageIO.read(new File("src/resources/cliffT.png"));
            cliffM = ImageIO.read(new File("src/resources/cliffM.png"));
            cliffB = ImageIO.read(new File("src/resources/cliffB.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
