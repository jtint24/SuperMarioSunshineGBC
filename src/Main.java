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
        frame.setContentPane(new JPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        ImagePanel myImagePanel = new ImagePanel("src/resources/img.png");

        Canvas myCanvas = new Canvas();

        frame.add(myCanvas);



    }
}
