import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class Canvas extends JPanel {

    Stack<RenderedImage> imagesToRender = new Stack<>();

    Canvas() {
        setPreferredSize(new Dimension(200, 200));
    }

    @Override
    public void paintComponent(Graphics g) {

        System.out.println("starting paint component");
        for (RenderedImage i : imagesToRender) {
            g.drawImage(i.image, i.x, i.y, null);
        }

    }
}
