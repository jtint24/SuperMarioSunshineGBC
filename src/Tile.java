import java.awt.*;

public class Tile implements Renderable {
    Point location;
    TileType type;
    TileImageFetcher imageFetcher;

    public Tile(Point location, TileType type, TileImageFetcher imageFetcher) {
        this.location = location;
        this.type = type;
        this.imageFetcher = imageFetcher;
    }


    @Override
    public void render(Point p, Canvas c) {
        c.imagesToRender.push(new RenderedImage(imageFetcher.getImage().getScaledInstance(80,80, Image.SCALE_DEFAULT), location.x*5, location.y*5-100));
    }
}
