public class Tile implements Renderable {
    Point location;

    TileType type;

    @Override
    public void render(Point p, Canvas c) {
        c.tilesToRender.push(this);
    }
}
