import java.awt.*;

public class Text implements Renderable{
    String body;
    Point location;
    Size size;

    public Text(String body, Point location, Size size) {
        this.body = body;
        this.location = location;
        this.size = size;
    }

    public Text(String body, Point location) {
        this.body = body;
        this.location = location;
        this.size = Size.SINGLE;
    }

    @Override
    public void render(Point p, Canvas c) {
        body = body.toLowerCase();
        int lineNumber = 0;
        int columnNumber = 0;
        int columnOffset = location.offsetX*5 + location.x*80;
        for (char ch : body.toCharArray()) {
            if (ch == ' ') {
                int width = 80;
                if (size == Size.DOUBLETIGHT) {
                    width = 40;
                }
                if (size == Size.SINGLE) {
                    width = 40;
                }
                columnOffset += width;
                columnNumber++;
                continue;
            }
            if (ch == '\n') {
                lineNumber++;
                columnNumber = 0;
                columnOffset = location.x*80+ location.offsetX*5;
                continue;
            }
            Image unscaledImage = Images.getImage("" + ch + (size == Size.SINGLE ? "s" : ""));
            int width = size == Size.SINGLE ? 40 : 80;
            Image scaledImage = unscaledImage.getScaledInstance(width, width, Image.SCALE_DEFAULT);
            if (ch == 'i' && size == Size.DOUBLETIGHT) {
                columnOffset -= 15;
            }
            if (ch == 'l' && size == Size.DOUBLETIGHT) {
                columnOffset -= 5;
            }
            if (size == Size.DOUBLETIGHT) {
                width = 75;
            }
            c.imagesToRender.push(new RenderedImage(scaledImage,  columnOffset, location.y*80+location.offsetY*5+lineNumber*(width+5)));
            columnNumber++;
            if (ch == 'i' && size == Size.DOUBLETIGHT) {
                width = 60;
            }
            columnOffset += width;
        }
    }

    enum Size {
        SINGLE, DOUBLE, DOUBLETIGHT;
    }
    enum Style {
        CENTER, LEFT;
    }
}
