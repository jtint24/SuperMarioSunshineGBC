import javax.swing.*;
import java.util.HashMap;

public class Application extends JFrame {
    static KeyData keyData = new KeyData();
    static int frameCount = 0;

    Application(String name) {
        super(name);
    }

    static class KeyData {
        private final HashMap<Integer, Boolean> data = new HashMap<>();

        void setPressed(int key) {
            data.put(key, true);
        }

        void setReleased(int key) {
            data.put(key, false);
        }

        boolean getIsPressed(int key) {
            if (!data.containsKey(key)) {
                return false;
            }
            return data.get(key);
        }
    }

    static int frameNumber(int duration, int frameCount) {
        return ((int)(System.currentTimeMillis()/duration % frameCount))+1;
    }

    static int frameNumber() {
        return frameNumber(160, 4);
    }
}
