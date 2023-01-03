import java.awt.event.KeyEvent;

public class Player extends Actor  {
    public Player(Point location) {
        super(location);
    }

    void move() {
        if (Application.keyData.getIsPressed(KeyEvent.VK_UP)) {
            location.y -= 1;
        }
        if (Application.keyData.getIsPressed(KeyEvent.VK_DOWN)) {
            location.y += 1;
        }
    }
}
