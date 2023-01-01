import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Super Mario Sunshine GBC");
        frame.setContentPane(new JPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JPanel jp = new JPanel();

        BufferedImage myPicture = ImageIO.read(new File("src/resources/img.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        frame.add(picLabel);




    }
}
