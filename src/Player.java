import java.awt.event.KeyEvent;

public class Player extends Actor  {

    Direction direction = Direction.DOWN;
    boolean moving = false;
    private int dy = 0;
    private int dx = 0;
    private final int maxSpeed = 3;

    private int flashingBeginFrame = -100;

    public Player(Point location, Environment e) {
        super(location, e);

        imageFetcher = () -> moving ? Images.getImage("mario"+direction.charCode()+Application.frameNumber()) : Images.getImage("mario"+direction.charCode()+"2");
    }

    int constrainedAdd(int a, int b) {
        return Math.min(Math.max(a+b, -maxSpeed), maxSpeed);
    }

    @Override
    void move() {
        if (Application.keyData.getIsPressed(KeyEvent.VK_UP)) {
            direction = Direction.UP;
            dy = constrainedAdd(dy, -2);
        } else if (Application.keyData.getIsPressed(KeyEvent.VK_DOWN)) {
            direction = Direction.DOWN;
            dy = constrainedAdd(dy, 2);
        } else {
            dy /= 2;
        }
        if (Application.keyData.getIsPressed(KeyEvent.VK_LEFT)) {
            direction = Direction.LEFT;
            dx = constrainedAdd(dx, -2);
        } else if (Application.keyData.getIsPressed(KeyEvent.VK_RIGHT)) {
            direction = Direction.RIGHT;
            dx = constrainedAdd(dx, 2);
        } else {
            dx /= 2;
        }


        location.offsetY += dy;
        location.offsetX += dx;

        if (dy < 0 && !canMoveBack()) {
            location.offsetY -= dy;
        }
        if (dy > 0 && !canMoveFront()) {
            location.offsetY -= dy;
        }
        if (dx > 0 && !canMoveRight()) {
            location.offsetX -= dx;
        }
        if (dx < 0 && !canMoveLeft()) {
            location.offsetX -= dx;
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
            dz = 8;
        }

        if (!Application.keyData.getIsPressed(KeyEvent.VK_UP) && !Application.keyData.getIsPressed(KeyEvent.VK_DOWN) &&!Application.keyData.getIsPressed(KeyEvent.VK_LEFT) && !Application.keyData.getIsPressed(KeyEvent.VK_RIGHT)) {
            dx = 0;
            dy = 0;
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
