import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main implements Runnable {
    static Environment gameEnvironment;
    static Canvas gameCanvas;
    static HUD hud = new HUD();

    public static void main(String[] args) {
        Images.initializeImages();

        gameCanvas = new Canvas();

        Player mario = new Player(new Point(10,33,3), gameEnvironment);

        gameEnvironment = createBiancoHills(mario);

        gameEnvironment.actors.add(new ActorLibrary.Shadow(mario, gameEnvironment));

        gameEnvironment.render();

        JFrame frame = createFrame();

        frame.setContentPane(gameCanvas);

        HUD.Meter coinMeter =  new HUD.Meter(Images.getImage("coinIcon"));
        HUD.Meter blueCoinMeter =  new HUD.Meter(Images.getImage("blueCoinIcon"));

        hud.addMeter("coin", coinMeter);
        hud.addMeter("blueCoin", blueCoinMeter);

        hud.meterToShow = blueCoinMeter;

        Main newMain = new Main();
        newMain.run();
    }

    public static Environment createBiancoHills(Player player) {
        Environment biancoHills = new Environment(player, new Tile[100][100][10], null, gameCanvas, hud);
        EnvironmentBuilder biancoBuilder = new EnvironmentBuilder(biancoHills);

        biancoBuilder.getFloor().makeGrass();

        biancoBuilder.getArea(0,100, 25,30).makeWater();
        biancoBuilder.getArea(0,100, 30, 100, 1,2).makeGrass();
        biancoBuilder.getArea(0,100,0,25, 0,2).makeGrass();
        biancoBuilder.getArea(0,100,30, 31, 1,2).fillType(biancoBuilder.grassEdge);
        biancoBuilder.getArea(0,100, 25,26,0,1).makeCliff();

        biancoBuilder.getArea(10,14, 26,31,1,2).fillType(biancoBuilder.bridge);
        biancoBuilder.getArea(18,22, 26,31,1,2).fillType(biancoBuilder.bridge);

        biancoBuilder.getArea(9,15, 31,40, 1,2).makePath();
        biancoBuilder.getArea(17,23, 31,40, 1,2).makePath();


        biancoBuilder.getArea(10,17, 40,45, 2,6).makeHouse();
        biancoBuilder.getArea(20,25, 40,45, 2,7).makeHouse();
        biancoBuilder.getArea(8, 27, 38, 47,1,2).makePath();



        biancoBuilder.getArea(40,42, 0,40, 2,6).fillType(biancoBuilder.wall);
        biancoBuilder.getArea(0, 100, 20,22,2, 6).fillType(biancoBuilder.wall);


        biancoBuilder.getArea(11,16, 39,40, 2,3).placeCoins();
        biancoBuilder.getArea(14, 19, 28,29, 2,4).placeShadowDelicateCoinArch();

        biancoBuilder.getArea().finalizeArea();

        //biancoHills.addActorWithShadow(new ActorLibrary.Pokey(new Point(17, 34,2), null, 15, 18));
        //gameEnvironment.addActor(new ActorLibrary.Goop(new Point(12, 34, 2), gameEnvironment));

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
            gameEnvironment.runFrame();
            gameEnvironment.render();

            hud.render(gameEnvironment.player.location, gameCanvas);

            Application.frameCount++;

            try {
                Thread.sleep(40);
            } catch (InterruptedException ignored) {}
        }
    }
}
