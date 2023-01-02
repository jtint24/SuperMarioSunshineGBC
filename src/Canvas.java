import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(new ImageIcon("src/resources/img.png").getImage(), 16, 16, null);
    }
}
