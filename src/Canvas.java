import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class Canvas extends JPanel {

    public Stack<RenderedImage> imagesToRender = new Stack<>();

    Canvas() {

        setPreferredSize(new Dimension(200, 200));
        this.setBackground(new Color(109, 166, 237));
    }

    @Override
    public void paintComponent(Graphics g) {

        Stack<RenderedImage> imagesToRenderCopy = (Stack<RenderedImage>) imagesToRender.clone();
        // System.out.println(imagesToRenderCopy.size());
        for (RenderedImage i : imagesToRenderCopy) {
            g.drawImage(i.image, i.x, i.y, null);
        }
    }



    void clear() {
        imagesToRender = new Stack<>();
    }
}
