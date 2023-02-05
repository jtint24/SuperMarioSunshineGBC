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
            "water2",
            "water3",
            "water4",
            "water5",
            "water6",
            "poison1",
            "poison2",
            "poison3",
            "poison4",
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
            "bridgeR",
            "roofMM",
            "wallMR",
            "wallML",
            "wallMM",
            "wallBR",
            "wallBL",
            "wallBM",
            "awningL",
            "awningM",
            "awningR",
            "door",
            "window",
            "roofMR",
            "roofML",
            "roofTM",
            "roofTR",
            "roofTL",
            "roofBM",
            "roofBR",
            "roofBL",
            "marioD1",
            "marioD2",
            "marioD3",
            "marioD4",
            "marioU1",
            "marioU2",
            "marioU3",
            "marioU4",
            "marioR1",
            "marioR2",
            "marioR3",
            "marioR4",
            "marioL1",
            "marioL2",
            "marioL3",
            "marioL4",
            "marioLJ",
            "marioRJ",
            "marioDJ",
            "marioUJ",
            "smarioD1",
            "smarioD2",
            "smarioD3",
            "smarioD4",
            "smarioU1",
            "smarioU2",
            "smarioU3",
            "smarioU4",
            "smarioR1",
            "smarioR2",
            "smarioR3",
            "smarioR4",
            "smarioL1",
            "smarioL2",
            "smarioL3",
            "smarioL4",
            "smarioLJ",
            "smarioRJ",
            "smarioDJ",
            "smarioUJ",
            "shadow",
            "waterDrop",
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "coinIcon",
            "xIcon",
            "blueCoinIcon",
            "shineIcon",
            "h2oIcon",
            "fludd10",
            "fludd9",
            "fludd8",
            "fludd7",
            "fludd6",
            "fludd5",
            "fludd4",
            "fludd3",
            "fludd2",
            "fludd1",
            "life8",
            "life7",
            "life6",
            "life5",
            "life4",
            "life3",
            "life2",
            "life1",
            "life0",
            "coin1",
            "coin2",
            "coin3",
            "coin4",
            "wallTopTM",
            "wallTopTR",
            "wallTopTL",
            "wallTopMM",
            "wallTopMR",
            "wallTopML",
            "wallTopBM",
            "wallTopBR",
            "wallTopBL",
            "goop",
            "pokey1",
            "pokey2",
            "pokey3",
            "pokey4",
            "goop1",
            "goop2",
            "goop3",
            "goop4",
            "sand",
            "sandDetail",
            "beach1",
            "beach2",
            "beach3",
            "beach4",
            "cataquack1",
            "cataquack2",
            "cataquack3",
            "cataquack4",
            "lava1",
            "lava2",
            "lava3",
            "lava4",
            "platformBL",
            "platformBM",
            "platformBR",
            "platformTBR",
            "platformTBL",
            "platformTBM",
            "platformTMR",
            "platformTML",
            "platformTMM",
            "platformTTR",
            "platformTTL",
            "platformTTM",
            "platformTBR",
            "platformTBL",
            "poleL",
            "poleR",
            "poleBL",
            "poleBR",
            "blueCoin1",
            "blueCoin2",
            "blueCoin3",
            "blueCoin4",
            "bluePianta",
            "orangePianta",
            "pinkPianta",
            "fountainT1",
            "fountainT2",
            "fountainB1",
            "fountainB2",
            "spoutL1",
            "spoutR1",
            "spoutL2",
            "spoutR2",
            "sparkle",
            "gaddBox",
            "sun1",
            "sun2",
            "smsLogo",
            "pressA",
            "flare1",
            "flare2",
            "flare3",
            "copyright",
            "cloudColumn",
            "a",
            "b",
            "c",
            "d",
            "e",
            "f",
            "g",
            "h",
            "i",
            "j",
            "k",
            "l",
            "m",
            "n",
            "o",
            "p",
            "q",
            "r",
            "s",
            "t",
            "u",
            "v",
            "w",
            "x",
            "y",
            "z"
    };

    static Image getImage(String s) {
        if (!images.containsKey(s)) {
            throw new IllegalArgumentException("There is no image called " + s);
        }
        return images.get(s);
    }

    static void initializeImages() {
        for (String name : imageNames) {
            try {
                images.put(name, ImageIO.read(new File("src/resources/"+name+".png")));
            } catch (IOException e) {
                System.out.println(name+".png not found");
                e.printStackTrace();
            }
        }
    }

    public static Image preparedImage(String name) {
        BufferedImage unscaledImage = (BufferedImage) getImage(name);
        return unscaledImage.getScaledInstance(unscaledImage.getWidth()*5, unscaledImage.getHeight()*5, Image.SCALE_DEFAULT);
    }
}
