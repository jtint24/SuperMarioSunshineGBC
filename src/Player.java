import java.awt.event.KeyEvent;

public class Player extends Actor  {

    Direction direction = Direction.DOWN;
    boolean moving = false;
    private final int speed = 2;

    public Player(Point location) {
        super(location);

        imageFetcher = () -> moving ? Images.getImage("mario"+direction.charCode()+Application.frameNumber()) : Images.getImage("mario"+direction.charCode()+"1");
    }

    @Override
    void move() {
        if (Application.keyData.getIsPressed(KeyEvent.VK_UP)) {
            direction = Direction.UP;
            location.offsetY -= speed;
            if (!canMoveBack()) {
                location.offsetY += speed;
            }
        }
        if (Application.keyData.getIsPressed(KeyEvent.VK_DOWN)) {
            direction = Direction.DOWN;
            location.offsetY += speed;
            if (!canMoveFront()) {
                location.offsetY -= speed;
            }
        }
        if (Application.keyData.getIsPressed(KeyEvent.VK_LEFT)) {
            direction = Direction.LEFT;
            location.offsetX -= speed;
            if (!canMoveLeft()) {
                location.offsetX += speed;
            }
        }
        if (Application.keyData.getIsPressed(KeyEvent.VK_RIGHT)) {
            direction = Direction.RIGHT;
            location.offsetX += speed;
            if (!canMoveRight()) {
                location.offsetX -= speed;
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
