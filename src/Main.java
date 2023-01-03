import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

public class Main  {

    static Environment gameEnvironment;
    static Canvas gameCanvas;
    static Player player;


    public static void main(String[] args) {
        Images.initializeImages();

        gameCanvas = new Canvas();

        player = new Player(new Point(0,29,0));

        gameEnvironment = createBiancoHills();

        gameEnvironment.render();

        JFrame frame = createFrame();

        frame.setContentPane(gameCanvas);
    }

    public static Environment createBiancoHills() {
        Environment biancoHills = new Environment(player, new Tile[100][100][10], new Actor[0], gameCanvas);
        EnvironmentBuilder biancoBuilder = new EnvironmentBuilder(biancoHills);

        biancoBuilder.getFloor().makeGrass();
        biancoBuilder.getArea(0,100,25,26,1,6).makeCliff();
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
                gameEnvironment.keyEvents.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        return frame;
    }
}
