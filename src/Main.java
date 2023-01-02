import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Application frame = new Application("Super Mario Sunshine GBC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(new Dimension(160*5,144*5));

        Canvas myCanvas = new Canvas();

        Player mario = new Player();


        Environment biancoHills = new Environment(mario, null, new Actor[0], myCanvas);

        biancoHills.tiles = new Tile[100][100][10];

        BufferedImage water1 = ImageIO.read(new File("src/resources/water1.png"));

        TileType water = new TileType(true, false);

        for (int i = 0; i<100; i++) {
            for (int j = 0; j<100; j++) {
                biancoHills.tiles[i][j][0] = new Tile(new Point(i,j,0), water, ()-> water1);
            }
        }

        biancoHills.render();

        frame.setContentPane(myCanvas);




    }
}
