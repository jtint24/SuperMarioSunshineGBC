import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Stack;

public class Canvas extends JPanel {

    Stack<RenderedImage> imagesToRender = new Stack<>();

    Canvas() {
        setPreferredSize(new Dimension(200, 200));
    }

    @Override
    public void paintComponent(Graphics g) {

        Stack<RenderedImage> imagesToRenderCopy = (Stack<RenderedImage>) imagesToRender.clone();

        for (RenderedImage i : imagesToRenderCopy) {
            g.drawImage(i.image, i.x, i.y, null);
        }

    }

    void clear() {
        imagesToRender = new Stack<>();
    }
}
