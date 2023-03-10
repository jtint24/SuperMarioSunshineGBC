import javax.print.attribute.standard.MediaSize;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main implements Runnable {
    static Mission currentMission;
    static Environment gameEnvironment;
    static Mission[] missions;
    static Canvas gameCanvas;
    static HUD hud = new HUD();
    static GameState state = GameState.GAME;
    static int missionIdx = 0;
    static int collectionFrame = 0;

    public static void main(String[] args) {
        Images.initializeImages();

        gameCanvas = new Canvas();

        missions = BiancoHillsMissions.makeBiancoHillsMissions();
        currentMission = missions[0];

        gameEnvironment = currentMission.environment;


                // createDelfinoPlaza(mario); //createCoronaMountain(mario); //createDelfinoPlaza(mario); //createBiancoHills(mario); //createGelatoBeach(mario); //createBiancoHills(mario);

        // gameEnvironment.actors.add(new ActorLibrary.Shadow(mario, gameEnvironment));

        gameEnvironment.render();

        JFrame frame = createFrame();

        frame.setContentPane(gameCanvas);

        HUD.Meter coinMeter = new HUD.Meter(Images.getImage("coinIcon"));
        HUD.Meter blueCoinMeter = new HUD.Meter(Images.getImage("blueCoinIcon"));

        hud.addMeter("coin", coinMeter);
        hud.addMeter("blueCoin", blueCoinMeter);

        hud.meters.get("blueCoin").incrementBy(3);

        hud.meterToShow = blueCoinMeter;

        Main newMain = new Main();
        newMain.run();
    }

    public static Environment createDelfinoPlaza(Player player) {
        Environment delfinoPlaza = new Environment(player, new Tile[100][100][10], null, gameCanvas, hud);
        EnvironmentBuilder delfinoBuilder = new EnvironmentBuilder(delfinoPlaza);

        delfinoBuilder.getFloor().makeGrass();

        delfinoBuilder.getArea(30, 45, 30, 40).makePath();
        delfinoBuilder.getArea(34, 41, 30, 55).makePath();

        delfinoBuilder.getArea(35, 41, 29, 32).makeWater();
        delfinoBuilder.getArea(35, 41, 32, 33, 1, 2).fillType(delfinoBuilder.grassEdge);
        delfinoBuilder.getArea(36, 37, 31, 32, 0, 3).makeFountain();
        delfinoBuilder.getArea(39, 40, 31, 32, 0, 3).makeFountain();

        delfinoBuilder.getArea(37, 38, 37, 38, 1, 2).fillType(delfinoBuilder.gaddBox);


        delfinoBuilder.getArea(33, 34, 33, 34, 1, 2).fillType(delfinoBuilder.gaddBox);


        delfinoBuilder.getArea(32, 43, 24, 29, 0, 6).fillType(delfinoBuilder.wall);
        delfinoBuilder.getArea(37, 39, 29, 30, 0, 1).makeSpout();

        delfinoBuilder.getArea(46, 51, 33, 41, 0, 6).makeHouse();

        delfinoBuilder.getArea(23, 28, 33, 41, 0, 6).makeHouse();


        delfinoPlaza.addActor(new ActorLibrary.Pianta("blue", new Point(33, 31, 1), delfinoPlaza));
        delfinoPlaza.addActor(new ActorLibrary.Pianta("pink", new Point(38, 35, 1), delfinoPlaza));
        delfinoPlaza.addActor(new ActorLibrary.Pianta("orange", new Point(43, 40, 1), delfinoPlaza));
        delfinoPlaza.addActorWithShadow(new ActorLibrary.Shine(new Point(45, 43, 4), delfinoPlaza));


        delfinoBuilder.getArea().finalizeArea();
        return delfinoPlaza;
    }

    public static Environment createBiancoHills(Player player) {
        Environment biancoHills = new Environment(player, new Tile[100][100][10], null, gameCanvas, hud);
        EnvironmentBuilder biancoBuilder = new EnvironmentBuilder(biancoHills);

        biancoBuilder.getFloor().makeGrass();

        biancoBuilder.getArea(0, 100, 25, 30).makeWater();
        biancoBuilder.getArea(0, 100, 30, 100, 1, 2).makeGrass();
        biancoBuilder.getArea(0, 100, 0, 25, 0, 2).makeGrass();
        biancoBuilder.getArea(0, 100, 30, 31, 1, 2).fillType(biancoBuilder.grassEdge);
        biancoBuilder.getArea(0, 100, 25, 26, 0, 1).makeCliff();

        biancoBuilder.getArea(10, 14, 26, 31, 1, 2).fillType(biancoBuilder.bridge);
        biancoBuilder.getArea(18, 22, 26, 31, 1, 2).fillType(biancoBuilder.bridge);

        biancoBuilder.getArea(9, 15, 31, 40, 1, 2).makePath();
        biancoBuilder.getArea(17, 23, 31, 40, 1, 2).makePath();


        biancoBuilder.getArea(10, 17, 40, 45, 2, 6).makeHouse();
        biancoBuilder.getArea(20, 25, 40, 45, 2, 7).makeHouse();

        biancoBuilder.getArea(21, 24, 42, 45, 8, 9).placeCoins();

        biancoBuilder.getArea(8, 27, 39, 48, 1, 2).makePath();


        biancoBuilder.getArea(30, 37, 0, 40, 2, 6).fillType(biancoBuilder.wall);
        biancoBuilder.getArea(0, 100, 20, 22, 2, 6).fillType(biancoBuilder.wall);


        biancoBuilder.getArea(11, 16, 39, 40, 2, 3).placeCoins();
        biancoBuilder.getArea(14, 19, 28, 29, 2, 4).placeShadowDelicateCoinArch();

        biancoBuilder.getArea(11, 20, 51, 61, 2, 3).placeGoop();
        biancoBuilder.getArea(10, 21, 49, 61, 1, 2).makePath();


        biancoBuilder.getArea(35, 50, 40, 47, 1, 2).makePath();

        // biancoBuilder.getArea(30, 70, 22, 23, 2,6).makeCliff();
        biancoHills.addActorWithShadow(new ActorLibrary.Pianta("pink", new Point(55, 24, 2), biancoHills));
        biancoHills.addActorWithShadow(new ActorLibrary.BlueCoin(new Point(58, 23, 3), biancoHills));


        biancoBuilder.getArea().finalizeArea();

        biancoHills.addActorWithShadow(new ActorLibrary.Enemy("pokey", new Point(25 + 2, 44, 2), null, 25 + 2, 28 + 2, 2));
        biancoHills.addActorWithShadow(new ActorLibrary.Enemy("pokey", new Point(29 + 2, 44, 2), null, 28 + 2, 31 + 2, 2));
        biancoHills.addActorWithShadow(new ActorLibrary.Enemy("pokey", new Point(33 + 2, 44, 2), null, 31 + 2, 34 + 2, 2));


        //gameEnvironment.addActor(new ActorLibrary.Goop(new Point(12, 34, 2), gameEnvironment));

        return biancoHills;
    }

    public static Environment createNokiBay(Player player) {
        Environment nokiBay = new Environment(player, new Tile[100][100][10], null, gameCanvas, hud);

        EnvironmentBuilder nokiBuilder = new EnvironmentBuilder(nokiBay);

        nokiBuilder.getFloor().fillType(nokiBuilder.poison);

        nokiBuilder.getArea(30, 35, 30, 35, 1, 3).fillType(nokiBuilder.platform);

        nokiBuilder.getArea(20, 25, 30, 35, 1, 3).fillType(nokiBuilder.platform);

        nokiBuilder.getArea(40, 45, 30, 35, 1, 3).fillType(nokiBuilder.platform);


        nokiBuilder.getArea(31, 34, 32, 35, 3, 4).placeGoop();

        nokiBuilder.getArea().finalizeArea();

        return nokiBay;
    }

    public static Environment createCoronaMountain(Player player) {
        Environment coronaMountain = new Environment(player, new Tile[100][100][10], null, gameCanvas, hud);

        EnvironmentBuilder coronaBuilder = new EnvironmentBuilder(coronaMountain);


        coronaBuilder.getFloor().fillType(coronaBuilder.lava);

        coronaBuilder.getArea(19, 25, 10, 16, 1, 6).makePlatform();

        coronaBuilder.getArea(20, 26, 20, 24, 1, 6).makePlatform();

        coronaBuilder.getArea(22, 30, 29, 32, 1, 6).makePlatform();

        coronaMountain.addActorWithShadow(new ActorLibrary.BlueCoin(new Point(27, 31, 8), coronaMountain));

        coronaBuilder.getArea(26, 32, 37, 41, 1, 6).makePlatform();


        coronaBuilder.getArea().finalizeArea();

        return coronaMountain;
    }

    public static Environment createGelatoBeach(Player player) {
        Environment gelatoBeach = new Environment(player, new Tile[100][100][10], null, gameCanvas, hud);
        EnvironmentBuilder gelatoBuilder = new EnvironmentBuilder(gelatoBeach);

        gelatoBuilder.getArea(0, 100, 0, 30, 0, 1).fillType(gelatoBuilder.sand);
        gelatoBuilder.getArea(0, 100, 30, 31, 0, 1).fillType(gelatoBuilder.beach);
        gelatoBuilder.getArea(0, 100, 31, 100, 0, 1).fillType(gelatoBuilder.water);


        gelatoBeach.addActorWithShadow(new ActorLibrary.Enemy("cataquack", new Point(30, 27, 1), gelatoBeach, 10, 40, 1));

        gelatoBeach.addActorWithShadow(new ActorLibrary.Enemy("cataquack", new Point(35, 23, 1), gelatoBeach, 10, 40, 1));


        gelatoBuilder.getArea().finalizeArea();

        return gelatoBeach;
    }

    public static JFrame createFrame() {
        Application frame = new Application("Super Mario Sunshine GBC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(new Dimension(160 * 5, 144 * 5));
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

            switch (state) {
                case GAME -> {
                    gameEnvironment.runFrame();
                    if (currentMission.isComplete()) {
                        collectionFrame = Application.frameCount;
                        state = GameState.SHINECOLLECTED;
                    }
                    gameEnvironment.render();

                    hud.render(gameEnvironment.player.location, gameCanvas);

                    // System.out.println(gameEnvironment.player.location.toString());
                    if (Application.keyData.getIsTyped(KeyEvent.VK_ENTER)) {
                        state = GameState.PAUSE;
                    }
                }
                case SHINECOLLECTED -> {
                    gameEnvironment.render();
                    gameEnvironment.runLockedFrame();



                    if (Application.frameCount - collectionFrame > 100) {
                        state = GameState.MENU;
                    }

                    (new Text("Shine Get!", new Point(1,1, 0), Text.Size.DOUBLETIGHT)).render(null, gameCanvas);
                }
                case PAUSE -> {
                    gameEnvironment.render();
                    if (Application.keyData.getIsTyped(KeyEvent.VK_ENTER)) {
                        state = GameState.GAME;
                    }

                }
                case TITLE -> {
                    gameCanvas.clear();
                    showTitleScreen();
                    if (Application.keyData.getIsPressed(KeyEvent.VK_Z)) {
                        state = GameState.MENU;
                    }
                }
                case MENU -> {
                    gameCanvas.clear();
                    showMenu();
                }
            }
            Application.advanceFrame();
            try {
                    Thread.sleep(40);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public void showMenu() {
        float i = Application.frameCount;
        Mission selectedMission = missions[missionIdx];

        gameCanvas.setBackground(new Color(33,144,14));

        for (int j = 2; j<6; j++) {
            for (int k = 0; k<10; k++) {
                gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("greenBand"), k*16*5,j*5*16));
            }
        }

        Text t = new Text("bianco hills", new Point(0,0,0, 0, 8, 0), Text.Size.DOUBLETIGHT);
        t.render(null, gameCanvas);
        Text t2 = new Text(selectedMission.name, new Point(0,6,0,4,4,0));
        t2.render(null, gameCanvas);


        int shineY = nearest5((int) (5 * 48 + Math.round(16 * -Math.sin(i / 10.0))));


        gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("shineFlare"+Application.frameNumber(200,4)),72*5-8*5,   shineY));


        if (selectedMission.hasBeenCompleted) {
            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("bigShine"), 72 * 5 - 8 * 5, shineY));
        } else {
            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("greyBigShine"), 72 * 5 - 8 * 5, shineY));
        }

        if (missionIdx != 0) {
            if (missions[missionIdx-1].hasBeenCompleted) {
                gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("bigShine"), 72 * 2 - 8 * 5, shineY));
            } else {
                gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("greyBigShine"), 72 * 2 - 8 * 5, shineY));
            }

            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("leftArrow"),shineY-180,   260));

        }

        if (missionIdx != missions.length-1) {
            if (missions[missionIdx+1].hasBeenCompleted) {
                gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("bigShine"), 72 * 8 - 8 * 5, shineY));
            } else {
                gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("greyBigShine"), 72 * 8 - 8 * 5, shineY));
            }

            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("rightArrow"),920-shineY,   260));
        }

        gameCanvas.repaint();

        if (Application.keyData.getIsTyped(KeyEvent.VK_LEFT)) {
            missionIdx = Math.max(0, missionIdx-1);
            System.out.println(missionIdx+" left");
        }
        if (Application.keyData.getIsTyped(KeyEvent.VK_RIGHT)) {
            missionIdx = Math.min(missions.length - 1, missionIdx + 1);
            System.out.println(missionIdx+" right");
        }

    }

    public void showTitleScreen() {
        float i = Application.frameCount;


        if (i % 10 < 5) {
            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("sun1"), 7 * 16 * 5, (int) (Math.min(5 * i - 240, 0))));
        } else {
            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("sun2"), 7 * 16 * 5, (int) (Math.min(5 * i - 240, 0))));
        }


        for (int j = -1; j < 4; j++) {
            int offsetX = nearest5((int) ((i / 50) % 5 * 3 * 16));
            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("cloudColumn"), 5 * 16 * 3 * j + offsetX, (int) (5 * 16 * 3 - Math.max(0, 200 - 8 * i))));
        }

        if (-500 * 2 + i * 20 < 0) {
            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("smsLogo"), 5 * 32, nearest5((int) (5 * 16 * 1 + -500 * 2 + i * 20))));
        } else {
            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("smsLogo"), 5 * 32, nearest5((int) (5 * 16 * 1 + Math.round(16 * -Math.sin((i - 225) / 10.0))))));
        }


        if (-500 * 2 + i * 20 > 0) {
            if (i % 20 < 10) {
                gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("pressA"), 5 * 32, nearest5((int) (5 * 16 * 5.5))));
            }
            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("copyright"), 5 * 32, 5 * 16 * 7));
        }


        gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("flare1"), 3 * 5 * 16, nearest5((int) (6 * 5 * 16 - 15 * i))));
        gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("flare2"), 5 * 5 * 16, nearest5((int) (4 * 5 * 16 - 10 * i))));
        gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("flare3"), 7 * 5 * 16, nearest5((int) (2 * 5 * 16 - 5 * i))));

        gameCanvas.repaint();
    }

    public static int nearest5(int a) {
        return 5*(a/5);
    }

    enum GameState {
        TITLE, GAME, MENU, PAUSE, SHINECOLLECTED;
    }

}
