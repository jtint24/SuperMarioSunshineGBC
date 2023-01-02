public class Tile implements Renderable {
    Point location;

    public Tile(Point location, TileType type) {
        this.location = location;
        this.type = type;
    }

    TileType type;

    @Override
    public void render(Point p, Canvas c) {
        c.imagesToRender.push(new RenderedImage(type.imageFetcher.getImage(), location.x, location.y));
    }
}
