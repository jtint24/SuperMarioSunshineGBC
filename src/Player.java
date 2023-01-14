import java.awt.event.KeyEvent;

public class Player extends MovingActor  {

    Direction direction = Direction.DOWN;
    boolean moving = false;

    private int flashingBeginFrame = -100;

    public Player(Point location, Environment e) {
        super(location, e, 3);

        imageFetcher = () -> {
            String shadow = "";
            if (!environment.isUncovered(location)) {
                shadow = "s";
            }
            if (!onSolidGround()) {
                return Images.getImage(shadow+"mario"+direction.charCode()+"J");
            } else {
                return moving ? Images.getImage(shadow+"mario" + direction.charCode() + Application.frameNumber()) : Images.getImage(shadow+"mario" + direction.charCode() + "2");
            }
        };
    }

    @Override
    void move() {
        if (Application.keyData.getIsPressed(KeyEvent.VK_UP)) {
            direction = Direction.UP;
            accelBack();
        } else if (Application.keyData.getIsPressed(KeyEvent.VK_DOWN)) {
            direction = Direction.DOWN;
            accelFront();
        } else {
            deaccelY();
        }
        if (Application.keyData.getIsPressed(KeyEvent.VK_LEFT)) {
            direction = Direction.LEFT;
            accelLeft();
        } else if (Application.keyData.getIsPressed(KeyEvent.VK_RIGHT)) {
            direction = Direction.RIGHT;
            accelRight();
        } else {
            deaccelX();
        }

        super.move();

        if (Application.keyData.getIsPressed(KeyEvent.VK_X) && Application.frameCount%2==0 && environment.hud.waterLevel > 0) {
            Point loc = new Point(location.x, location.y, location.z, location.offsetX, location.offsetY, 16);
            int wdx = switch (direction) {
                case LEFT -> -5+dx;
                case RIGHT -> 5+dx;
                default -> 0;
            };
            int wdy = switch (direction) {
                case UP -> -5+dy;
                case DOWN -> 5+dy;
                default -> 0;
            };

            environment.addActor(new ActorLibrary.WaterDrop(loc, environment, wdx, wdy));

            environment.hud.waterLevel-=.5;
            environment.hud.waterLevel = Math.max(0, environment.hud.waterLevel);
        }

        moving = Application.keyData.getIsPressed(KeyEvent.VK_RIGHT) || Application.keyData.getIsPressed(KeyEvent.VK_LEFT) || Application.keyData.getIsPressed(KeyEvent.VK_DOWN) || Application.keyData.getIsPressed(KeyEvent.VK_UP);

        if (Application.keyData.getIsPressed(KeyEvent.VK_Z) && onSolidGround()) {
            dz = 9;
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
