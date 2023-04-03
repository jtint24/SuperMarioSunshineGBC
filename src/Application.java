import javax.swing.*;
import java.util.HashMap;

public class Application extends JFrame {
    static KeyData keyData = new KeyData();
    static int frameCount = 0;

    Application(String name) {
        super(name);
        this.setIconImage(Images.getImage("shineIcon"));

    }


    static void advanceFrame() {
        if (Main.state != Main.GameState.PAUSE) {
            frameCount++;
        }
        keyData.runFrame();
    }

    static class KeyData {
        private final HashMap<Integer, Boolean> data = new HashMap<>();
        private final HashMap<Integer, Boolean> typeData = new HashMap<>();


        void setPressed(int key) {
            data.put(key, true);
            if (!typeData.containsKey(key)) {
                typeData.put(key, true);
            }
        }

        void setReleased(int key) {
            data.put(key, false);
            typeData.remove(key);
        }

        void runFrame() {
            typeData.replaceAll((k, v) -> false);
        }

        boolean getIsPressed(int key) {
            if (!data.containsKey(key)) {
                return false;
            }
            return data.get(key);
        }
        boolean getIsTyped(int key) {
            if (!typeData.containsKey(key)) {
                return false;
            }
            return typeData.get(key);
        }
    }

    static int frameNumber(int duration, int frameCount) {
        return ((int)(((float)Application.frameCount*40f)/((float)duration) % frameCount))+1;
    }

    static int frameNumber() {
        return frameNumber(160, 4);
    }
}
