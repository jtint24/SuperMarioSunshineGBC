public class MovingActor extends Actor {
    int dx = 0;
    int dy = 0;
    int maxSpeed;

    public MovingActor(Point location, Environment environment, int maxSpeed) {
        super(location, environment);

        this.maxSpeed = maxSpeed;
    }


    int constrainedAdd(int a, int b) {
        return Math.min(Math.max(a+b, -maxSpeed), maxSpeed);
    }

    void accelRight() {
        dx = constrainedAdd(dx, 2);

    }
    void accelLeft() {
        dx = constrainedAdd(dx, -2);

    }
    void accelFront() {
        dy = constrainedAdd(dy, 2);

    }
    void accelBack() {
        dy = constrainedAdd(dy, -2);
    }
    void deaccelX() {
        dx /= 2;
    }
    void deaccelY() {
        dy /= 2;
    }

    @Override
    void move() {
        location.offsetY += dy;
        location.offsetX += dx;

        updateOffsets();

        if (dy < 0 && !canMoveBack()) {
            location.offsetY -= dy;
            dy = 0;
        }
        if (dy > 0 && !canMoveFront()) {
            location.offsetY -= dy;
            dy = 0;
        }
        if (dx > 0 && !canMoveRight()) {
            location.offsetX -= dx;
            dx = 0;
        }
        if (dx < 0 && !canMoveLeft()) {
            location.offsetX -= dx;
            dx = 0;
        }
        updateOffsets();
    }
}
