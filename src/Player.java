import java.awt.event.KeyEvent;

public class Player extends Actor  {

    Direction direction = Direction.DOWN;
    boolean moving = false;
    private final int speed = 2;
    private int flashingBeginFrame = -100;

    public Player(Point location, Environment e) {
        super(location, e);

        imageFetcher = () -> moving ? Images.getImage("mario"+direction.charCode()+Application.frameNumber()) : Images.getImage("mario"+direction.charCode()+"2");
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
        if (Application.keyData.getIsPressed(KeyEvent.VK_X) && Application.frameCount%2==0 && environment.hud.waterLevel > 0) {
            Point loc = new Point(location.x, location.y, location.z, location.offsetX, location.offsetY, 16);
            int dx = switch (direction) {
                case LEFT -> -5;
                case RIGHT -> 5;
                default -> 0;
            };
            int dy = switch (direction) {
                case UP -> -5;
                case DOWN -> 5;
                default -> 0;
            };

            environment.addActor(new ActorLibrary.WaterDrop(loc, environment, dx, dy));

            environment.hud.waterLevel-=.5;
            environment.hud.waterLevel = Math.max(0, environment.hud.waterLevel);
        }

        moving = Application.keyData.getIsPressed(KeyEvent.VK_RIGHT) || Application.keyData.getIsPressed(KeyEvent.VK_LEFT) || Application.keyData.getIsPressed(KeyEvent.VK_DOWN) || Application.keyData.getIsPressed(KeyEvent.VK_UP);

        if (Application.keyData.getIsPressed(KeyEvent.VK_Z) && onSolidGround()) {
            dz = 10;
        }

        applyGravity();

        updateOffsets();
        // System.out.println(location.z+" "+ location.offsetZ+" +/-: "+dz);
    }

    @Override
    public void render(Point p, Canvas c) {
        if (Application.frameCount-flashingBeginFrame > 50 || ((Application.frameCount/5)%2) == 0) {
            super.render(p,c);
        }
    }

    void damage() {
        if (Application.frameCount-flashingBeginFrame > 50) {
            environment.hud.lifeLevel--;
            flashingBeginFrame = Application.frameCount;
        }
    }
}
