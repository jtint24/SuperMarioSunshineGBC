import java.awt.*;
import java.util.ArrayList;

public class Actor implements Renderable {

    Point location;
    Environment environment;
    int dz = 0;
    ImageFetcher imageFetcher;

    public Actor(Point location, Environment environment) {

        this.location = location;
        this.environment = environment;
    }

    // higher means draw this one last
    // lower means draw this one first
    int drawLayer() {
        return (location.y*16+location.offsetY)+(location.z*16+location.offsetZ);
    }

    void move() {}

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

        if (location.x > 98) {
            location.x = 98;
            location.offsetX = 15;
        }
        if (location.x < 1) {
            location.x = 1;
            location.offsetX = -15;
        }

        if (location.y > 90) {
            location.y = 90;
            location.offsetY = 15;
        }
        if (location.y < 1) {
            location.y = 1;
            location.offsetY = -15;
        }
        if (location.z > 9) {
            location.z = 9;
            location.offsetZ = 15;
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
        for (Tile t : environment.tilesAroundXY(new Point(location.x, location.y, location.z-1, location.offsetX, location.offsetY, 0))) {
            if (t.type.isWalkable) {
                return true;
            }
        }
        return false;
    }

    boolean canMoveFront() {
        if ( location.offsetY<0 ) {
            return true;
        }
        ArrayList<Tile> arr = environment.tilesAroundXZ(new Point(location.x, location.y+1, location.z, location.offsetX, location.offsetY, 0));
        return arr.size() == 0;
    }
    boolean canMoveBack() {
        if ( location.offsetY>0 ) {
            return true;
        }
        ArrayList<Tile> arr = environment.tilesAroundXZ(new Point(location.x, location.y-1, location.z, location.offsetX, location.offsetY, 0));
        return arr.size() == 0;
    }
    boolean canMoveLeft() {
        if ( location.offsetX>0 ) {
            return true;
        }
        ArrayList<Tile> arr = environment.tilesAroundYZ(new Point(location.x-1, location.y, location.z, location.offsetX, location.offsetY, 0));
        return arr.size() == 0;
    }
    boolean canMoveRight() {
        if ( location.offsetX<0 ) {
            return true;
        }
        ArrayList<Tile> arr = environment.tilesAroundYZ(new Point(location.x+1, location.y, location.z, location.offsetX, location.offsetY, 0));
        return arr.size() == 0;
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
