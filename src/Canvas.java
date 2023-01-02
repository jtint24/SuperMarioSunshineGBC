import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class Canvas extends JPanel {

    Stack<Tile> tilesToRender = new Stack<>();

    Canvas() {
        setPreferredSize(new Dimension(200, 200));
    }

    @Override
    public void paintComponent(Graphics g) {

        for (Tile tile : tilesToRender) {

            g.drawImage(tile.type.imageFetcher.getImage(), 16 * tile.location.x, 16 * tile.location.y, null);
        }

    }
}
