import java.awt.event.KeyEvent;

public class Player extends Actor  {

    public Player(Point location) {
        super(location);
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

        if (Application.keyData.getIsPressed(KeyEvent.VK_Z) && onSolidGround()) {
            dz = 10;
        }

        applyGravity();

        updateOffsets();
        // System.out.println(location.z+" "+ location.offsetZ+" +/-: "+dz);
    }
}
