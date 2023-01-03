import java.awt.*;

public class RenderedImage implements Cloneable {
    Image image;
    int x;
    int y;

    public RenderedImage(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public RenderedImage clone() {
        return new RenderedImage(image, x, y);
    }
}
