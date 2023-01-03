import javax.swing.*;
import java.util.HashMap;

public class Application extends JFrame {
    static KeyData keyData = new KeyData();

    Application(String name) {
        super(name);
    }

    static class KeyData {
        private HashMap<Integer, Boolean> data = new HashMap<>();

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
}
