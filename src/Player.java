import java.awt.event.KeyEvent;

public class Player extends MovingActor  {

    Direction direction = Direction.DOWN;
    boolean moving = false;
    FluddType fluddType = FluddType.squirt;
    private int flashingBeginFrame = -100;

    public Player(Point location, Environment e) {
        super(location, e, 3);

        imageFetcher = () -> {
            if (Main.state.equals(Main.GameState.SHINE_COLLECTED)) {
                return Images.getImage("marioShineGet");
            }
            String shadow = "";
            String wetness = "";
            if (environment.tileBelowIs(getLocation().x, getLocation().y, getLocation().z, EnvironmentBuilder.water) || environment.tileBelowIs(getLocation().x, getLocation().y, getLocation().z, EnvironmentBuilder.lava) || environment.tileBelowIs(getLocation().x, getLocation().y, getLocation().z, EnvironmentBuilder.poison)) {
                wetness = "W";
            }
            if (!environment.isUncovered(getLocation())) {
                shadow = "s";
                wetness = "";
            }

            if (!onSolidGround()) {
                return Images.getImage(shadow+"mario"+wetness+direction.charCode()+"J");
            } else {
                return moving ? Images.getImage(shadow+"mario" + wetness + direction.charCode() + Application.frameNumber()) : Images.getImage(shadow+"mario" + wetness + direction.charCode() + "2");
            }
        };
    }

    private Point getLocation() {
        return location;
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

            if (fluddType == FluddType.squirt) {
                Point loc = new Point(location.x, location.y, location.z, location.offsetX, location.offsetY, 16);
                int wdx = switch (direction) {
                    case LEFT -> -4 + dx;
                    case RIGHT -> 4 + dx;
                    default -> 0;
                };
                int wdy = switch (direction) {
                    case UP -> -4 + dy;
                    case DOWN -> 4 + dy;
                    default -> 0;
                };

                environment.addActor(new ActorLibrary.WaterDrop(loc, environment, wdx, wdy, 4));
            } else {
                Point loc = new Point(location.x, location.y, location.z, location.offsetX, location.offsetY, location.offsetZ);
                environment.addActor(new ActorLibrary.WaterDrop(loc, environment, 0, 0, -2));
                dz = Math.min(dz+2,1);
            }

            environment.hud.waterLevel-=.5;
            environment.hud.waterLevel = Math.max(0, environment.hud.waterLevel);
        }

        moving = Application.keyData.getIsPressed(KeyEvent.VK_RIGHT) || Application.keyData.getIsPressed(KeyEvent.VK_LEFT) || Application.keyData.getIsPressed(KeyEvent.VK_DOWN) || Application.keyData.getIsPressed(KeyEvent.VK_UP);

        if (Application.keyData.getIsPressed(KeyEvent.VK_Z) && onSolidGround()) {
            dz = 9;
        }

        if (onSolidGround() && environment.tileBelowIs(location.x, location.y, location.z, EnvironmentBuilder.trampoline)) {
            dz = 9;
            if (Application.keyData.getIsPressed(KeyEvent.VK_Z)) {
                dz = 15;
            }
        }

        if (!Application.keyData.getIsPressed(KeyEvent.VK_UP) && !Application.keyData.getIsPressed(KeyEvent.VK_DOWN) &&!Application.keyData.getIsPressed(KeyEvent.VK_LEFT) && !Application.keyData.getIsPressed(KeyEvent.VK_RIGHT)) {
            dx = 0;
            dy = 0;
        }

        if (environment.tileBelowIs(location.x, location.y, location.z, EnvironmentBuilder.poison) || environment.tileBelowIs(location.x, location.y, location.z, EnvironmentBuilder.lava)) {
            damage();
        }

        checkFluddRecharge();

        applyGravity();


        updateOffsets();
        // System.out.println(location.z+" "+ location.offsetZ+" +/-: "+dz);
    }

    public void checkFluddRecharge() {
        if (environment.tileBelowIs(location.x, location.y, location.z, EnvironmentBuilder.water)) {
            environment.hud.waterLevel++;
            if (environment.hud.waterLevel > 100) {
                environment.hud.waterLevel = 100;
            }
        }
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

    void setFlashingBeginFrame(int fr) {
        flashingBeginFrame = fr;
    }

    enum FluddType {
        jet, squirt
    }
}
