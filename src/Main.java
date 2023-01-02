import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Images.initializeImages();

        Application frame = new Application("Super Mario Sunshine GBC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(new Dimension(160*5,144*5));

        Canvas myCanvas = new Canvas();

        Player mario = new Player(new Point(0,24,0));


        Environment biancoHills = new Environment(mario, new Tile[100][100][10], new Actor[0], myCanvas);
        EnvironmentBuilder biancoBuilder = new EnvironmentBuilder(biancoHills);

        biancoBuilder.getFloor().makeWater();
        biancoBuilder.getArea(0,100,5,6,1,6).makeCliff();
        biancoBuilder.getArea().setImages();

        System.out.println(biancoHills.tiles[0][0][0].type.name);



        biancoHills.render();

        frame.setContentPane(myCanvas);

    }
}
