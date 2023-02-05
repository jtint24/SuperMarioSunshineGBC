import java.awt.*;

public class Text implements Renderable{
    String body;
    Point location;

    public Text(String body, Point location) {
        this.body = body;
        this.location = location;
    }

    @Override
    public void render(Point p, Canvas c) {
        body = body.toLowerCase();
        int lineNumber = 0;
        int columnNumber = 0;
        for (char ch : body.toCharArray()) {
            if (ch == ' ') {
                columnNumber++;
                continue;
            }
            if (ch == '\n') {
                lineNumber++;
                columnNumber = 0;
                continue;
            }
            Image unscaledImage = Images.getImage("" + ch);
            Image scaledImage = unscaledImage.getScaledInstance(80,80, Image.SCALE_DEFAULT);
            c.imagesToRender.push(new RenderedImage(scaledImage,  location.x*80+location.offsetX*5+ columnNumber * 80, location.y*80+location.offsetY*5+lineNumber*85));
            columnNumber++;
        }
    }
}
