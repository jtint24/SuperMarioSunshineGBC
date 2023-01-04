import java.awt.*;

class Actor implements Renderable {

    Point location;
    Environment environment;
    int dz = 0;
    ImageFetcher imageFetcher;

    public Actor(Point location) {
        this.location = location;
    }

    // higher means draw this one last
    // lower means draw this one first
    int drawLayer() {
        return location.z-location.y;
    }

    void move() {

    }

    void updateOffsets() {
        int addToX = location.offsetX / 16;
        location.offsetX = location.offsetX % 16;
        int addToY = location.offsetY / 16;
        location.offsetY = location.offsetY % 16;
        int addToZ = location.offsetZ / 16;
        location.offsetZ = location.offsetZ % 16;

        location.x += addToX;
        location.y += addToY;
        location.z += addToZ;

        if (location.offsetZ < 0) {
            location.z -= 1;
            location.offsetZ += 16;
        }
    }

    public void applyGravity() {
        changeZBy(dz);

        if (!onSolidGround()) {
            dz--;
        } else {
            dz = 0;
        }
    }

    boolean onSolidGround() {
        // return (location.z == environment.highestZAt(location)+1 && location.offsetZ == 0);

        if ( location.offsetZ>0 ) {
            return false;
        }
        for (Tile t : environment.tilesAround(new Point(location.x, location.y, location.z-1, location.offsetX, location.offsetY, 0))) {
            if (t.type.isWalkable) {
                return true;
            }
        }
        return false;
    }

    void changeZBy(int zDiff) {
        if (zDiff == 0) {
            return;
        }
        int d = zDiff > 0 ? 1 : -1;
        zDiff *= d;
        for (int i = 0; i<zDiff; i++) {
            if (onSolidGround() && d == -1) {
                return;
            }
            location.offsetZ += d;
            updateOffsets();
        }
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
