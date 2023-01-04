import java.awt.event.KeyEvent;

public class Player extends Actor  {
    float dz = 0;
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

        if (!onSolidGround() || location.offsetZ>0) {
            location.offsetZ--;
        }
        location.offsetZ += dz;

        updateOffsets();
        System.out.println(location.z+" "+ location.offsetZ);

    }

    boolean onSolidGround() {
        for (Tile t : environment.tilesAround(new Point(location.x, location.y, location.z-1, location.offsetX, location.offsetY, 0))) {
            System.out.println(t.type.name);
            if (t.type.isWalkable) {
                return true;
            }
        }
        return false;
    }
}
