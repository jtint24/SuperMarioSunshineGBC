import java.awt.event.KeyEvent;

public class Player extends Actor  {

    Direction direction = Direction.DOWN;

    public Player(Point location) {
        super(location);

        imageFetcher = () -> switch (direction) {
            case UP -> Images.getImage("marioU1");
            case DOWN -> Images.getImage("marioD1");
            case LEFT -> Images.getImage("marioL1");
            case RIGHT -> Images.getImage("marioR1");
        };
    }

    @Override
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
