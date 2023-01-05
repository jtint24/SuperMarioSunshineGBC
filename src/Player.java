import java.awt.event.KeyEvent;

public class Player extends Actor  {

    Direction direction = Direction.DOWN;
    boolean moving = false;

    public Player(Point location) {
        super(location);

        imageFetcher = () -> moving ? Images.getImage("mario"+direction.charCode()+Application.frameNumber()) : Images.getImage("mario"+direction.charCode()+"1");
    }

    @Override
    void move() {
        if (Application.keyData.getIsPressed(KeyEvent.VK_UP)) {
            direction = Direction.UP;
            location.offsetY -= 1;
            if (!canMoveBack()) {
                location.offsetY += 1;
            }
        }
        if (Application.keyData.getIsPressed(KeyEvent.VK_DOWN)) {
            direction = Direction.DOWN;
            location.offsetY += 1;
            if (!canMoveFront()) {
                location.offsetY -= 1;
            }
        }
        if (Application.keyData.getIsPressed(KeyEvent.VK_LEFT)) {
            direction = Direction.LEFT;
            location.offsetX -= 1;
            if (!canMoveLeft()) {
                location.offsetX += 1;
            }
        }
        if (Application.keyData.getIsPressed(KeyEvent.VK_RIGHT)) {
            direction = Direction.RIGHT;
            location.offsetX += 1;
            if (!canMoveRight()) {
                location.offsetX -= 1;
            }
        }
        moving = Application.keyData.getIsPressed(KeyEvent.VK_RIGHT) || Application.keyData.getIsPressed(KeyEvent.VK_LEFT) || Application.keyData.getIsPressed(KeyEvent.VK_DOWN) || Application.keyData.getIsPressed(KeyEvent.VK_UP);

        if (Application.keyData.getIsPressed(KeyEvent.VK_Z) && onSolidGround()) {
            dz = 10;
        }

        applyGravity();

        updateOffsets();
        // System.out.println(location.z+" "+ location.offsetZ+" +/-: "+dz);
    }
}
