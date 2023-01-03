import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main implements Runnable {

    static Environment gameEnvironment;
    static Canvas gameCanvas;

    public static void main(String[] args) {
        Images.initializeImages();

        gameCanvas = new Canvas();

        Player mario = new Player(new Point(0,29,0));

        gameEnvironment = createBiancoHills(mario);

        gameEnvironment.render();

        JFrame frame = createFrame();

        frame.setContentPane(gameCanvas);

        Main newMain = new Main();
        newMain.run();
    }

    public static Environment createBiancoHills(Player player) {
        Environment biancoHills = new Environment(player, new Tile[100][100][10], new Actor[0], gameCanvas);
        EnvironmentBuilder biancoBuilder = new EnvironmentBuilder(biancoHills);

        biancoBuilder.getFloor().makeGrass();
        biancoBuilder.getArea(0,1,25,26,1,6).makeCliff();
        biancoBuilder.getArea(1,2,25,26,1,5).makeCliff();
        biancoBuilder.getArea(2,3,25,26,1,4).makeCliff();
        biancoBuilder.getArea(3,4,25,26,1,3).makeCliff();
        biancoBuilder.getArea(4,5,25,26,1,2).makeCliff();

        biancoBuilder.getArea(5,20,30,35).makePath();
        biancoBuilder.getArea(10,15,25,40).makePath();

        biancoBuilder.getArea(5,10,15,20).makePath();

        biancoBuilder.getArea().setImages();
        return biancoHills;
    }

    public static JFrame createFrame() {
        Application frame = new Application("Super Mario Sunshine GBC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(new Dimension(160*5,144*5));
        frame.setResizable(false);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getExtendedKeyCode();
                Application.keyData.setPressed(key);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getExtendedKeyCode();
                Application.keyData.setReleased(key);

            }
        });
        return frame;
    }

    @Override
    public void run() {
        boolean running = true;
        while (running) {
            gameEnvironment.player.move();
            gameEnvironment.render();

            try {
                Thread.sleep(40);
            } catch (InterruptedException ignored) {}
        }
    }
}
