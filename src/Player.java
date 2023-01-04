import java.awt.event.KeyEvent;

public class Player extends Actor  {
    int dz = 0;
    Environment environment;

    public Player(Point location, Environment environment) {
        super(location);
        this.environment = environment;
    }

    void move() {
        if (Application.keyData.getIsPressed(KeyEvent.VK_UP)) {
            location.offsetY -= 1;
        }
        if (Application.keyData.getIsPressed(KeyEvent.VK_DOWN)) {
            location.offsetY += 1;
        }
        if (Application.keyData.getIsPressed(KeyEvent.VK_LEFT)) {
            location.offsetX -= 1;
        }
        if (Application.keyData.getIsPressed(KeyEvent.VK_RIGHT)) {
            location.offsetX += 1;
        }

        if (Application.keyData.getIsPressed(KeyEvent.VK_Z)) {
            dz = 10;
        }
        changeZBy(dz);

        if (!onSolidGround() || location.offsetZ>0) {
            dz--;
        } else {
            dz = 0;
        }


        updateOffsets();
        System.out.println(location.z+" "+ location.offsetZ);
    }

    boolean onSolidGround() {
       // return (location.z == environment.highestZAt(location)+1 && location.offsetZ == 0);

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
            location.offsetZ += d;
            updateOffsets();
            if (onSolidGround() && d == -1) {
                return;
            }
        }
    }
}
