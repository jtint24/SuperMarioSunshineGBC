import java.awt.*;

public class Tile implements Renderable {
    Point location;
    TileType type;
    ImageFetcher imageFetcher;

    public Tile(Point location, TileType type, ImageFetcher imageFetcher) {
        this.location = location;
        this.type = type;
        this.imageFetcher = imageFetcher;
    }


    @Override
    public void render(Point p, Canvas c) {
        Image scaledImage = imageFetcher.getImage().getScaledInstance(80,80, Image.SCALE_DEFAULT);
        int dispX = (location.x-p.x)*5*16+(location.offsetX-p.offsetX)*5+80*5;
        int dispY = (location.y-p.y-location.z+p.z)*5*16+(location.offsetY-p.offsetY-location.offsetZ+p.offsetZ)*5+72*5;

        if (dispX > -80 && dispX < 160*5 && dispY > -80 && dispY < 144*5) {
            c.imagesToRender.push(new RenderedImage(scaledImage, dispX, dispY));
        }
    }
}
