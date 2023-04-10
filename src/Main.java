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
    static GameState state = GameState.TITLE;
    static int missionIdx = 0;
    static int collectionFrame = 0;
    static int deathFrame = 0;

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

        frame.setIconImage(Images.preparedImage("shineIcon"));


        HUD.Meter coinMeter = new HUD.Meter(Images.getImage("coinIcon"));
        HUD.Meter blueCoinMeter = new HUD.Meter(Images.getImage("blueCoinIcon"));
        HUD.Meter redCoinMeter = new HUD.Meter(Images.getImage("redCoin2"));


        hud.addMeter("coin", coinMeter);
        hud.addMeter("blueCoin", blueCoinMeter);
        hud.addMeter("redCoin", redCoinMeter);


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
        delfinoPlaza.addActorWithShadow(new ActorLibrary.Coin(new Point(45, 45, 1), delfinoPlaza));
        delfinoPlaza.addActorWithShadow(new ActorLibrary.Shine(new Point(45, 43, 4), delfinoPlaza, Mission.Objectives.collectCoins));
        //delfinoPlaza.addActorWithShadow(new ActorLibrary.Shine(new Point(18, 7, 9), delfinoPlaza, Mission.Objectives.clearGoop));


        delfinoBuilder.getArea().finalizeArea();
        return delfinoPlaza;
    }

    public static Environment createBiancoHills(Player player) {
        Environment biancoHills = new Environment(player, new Tile[100][100][11], null, gameCanvas, hud);
        EnvironmentBuilder biancoBuilder = new EnvironmentBuilder(biancoHills);

        biancoBuilder.getFloor().makeGrass();

        // Boundary walls
        biancoBuilder.getArea(0,100,0,2,8,10).fillType(EnvironmentBuilder.wall);
        biancoBuilder.getArea(0,2,0,100,0,6).fillType(EnvironmentBuilder.wall);
        biancoBuilder.getArea(98,100,0,100,0,6).fillType(EnvironmentBuilder.wall);
        biancoBuilder.getArea(0,100,87,89,0,6).fillType(EnvironmentBuilder.wall);



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

        // biancoBuilder.getArea(21, 24, 42, 45, 8, 9).placeCoins();

        biancoBuilder.getArea(8, 27, 39, 48, 1, 2).makePath();


        // Big chunky wall
        biancoBuilder.getArea(30, 37, 28, 60, 2, 6).fillType(biancoBuilder.wall);

        // Northern wall

        biancoBuilder.getArea(0, 80, 20, 22, 2, 6).fillType(biancoBuilder.wall);



        // Path up to the houses

        biancoBuilder.getArea(16, 22, 47,87,1,2).fillType(EnvironmentBuilder.path);

        // First goop the player encounters

        // biancoBuilder.getArea(17, 21, 45,47, 2,3).placeGoop();

        // Coins on the first path

        // biancoBuilder.getArea(18,20, 50,53,2,3).placeCoins();

        // Coins leading up to the bridge

        // biancoBuilder.getArea(19, 21, 30, 33, 2, 3).placeCoins();

        // Wall that stops you from going from pokey area to shine area

        biancoBuilder.getArea(47, 49, 20,  27, 2, 6).fillType(EnvironmentBuilder.wall);

        // East 2 short building

        biancoBuilder.getArea(38, 45, 38, 44, 2, 6).makeHouse();


        // Bridge to shine

        biancoBuilder.getArea(56, 60, 26, 31, 1,2).fillType(EnvironmentBuilder.bridge);

        // Final shine for level 1
        // biancoHills.addActorWithShadow(new ActorLibrary.Shine(new Point(58, 23, 5), biancoHills, Mission.Objectives.clearedImmediately));

        // LEVEL 2
        // Northern cliffs
        biancoBuilder.getArea(0, 100, 0,10,2,5).fillType(EnvironmentBuilder.cliff);
        biancoBuilder.getArea(0, 100, 0,9,0,5).fillType(EnvironmentBuilder.grass);

        biancoBuilder.getArea(0, 100, 0,5,5,8).fillType(EnvironmentBuilder.cliff);
        biancoBuilder.getArea(0, 100, 0,4,0,8).fillType(EnvironmentBuilder.grass);


        // Northern house

        biancoBuilder.getArea(7,12,11,15, 2, 6).makeHouse();


        // Shine for Level 2

        biancoBuilder.getArea(35, 50, 40, 47, 1, 2).makePath();

        // biancoBuilder.getArea(30, 70, 22, 23, 2,6).makeCliff();
        biancoHills.addActorWithShadow(new ActorLibrary.Pianta("pink", new Point(55, 24, 2), biancoHills));


        // Level 4

        // hollow wall for red coin

        biancoBuilder.getArea(30, 50,60,62,2,6).fillType(EnvironmentBuilder.wall);
        biancoBuilder.getArea(52, 90,60,62,2,6).fillType(EnvironmentBuilder.wall);

        biancoBuilder.getArea(30, 90,64,66,2,6).fillType(EnvironmentBuilder.wall);
        biancoBuilder.getArea(30, 36,60,66,2,6).fillType(EnvironmentBuilder.wall);


        // Bridge between walls

        biancoBuilder.getArea(84, 88,50,53,2,6).fillType(EnvironmentBuilder.wall);
        biancoBuilder.getArea(84, 88,53,60,5,6).fillType(EnvironmentBuilder.bridge);



        // Windmill village plaza area

        biancoBuilder.getArea(52, 80, 38, 51, 1,2).fillType(EnvironmentBuilder.path);


        // plaza buildings

        biancoBuilder.getArea(71, 76, 41, 47, 2, 8).makeHouse();
        biancoBuilder.getArea(55, 62, 41, 47, 2, 7).makeHouse();

        // Plaza fountain

        biancoBuilder.getArea(65, 68,42, 45,1,2).makeWater();
        biancoBuilder.getArea(66,67,44,45,3,4).makeFountain();


        biancoBuilder.getArea().finalizeArea();


        // Northern pokeys

        biancoHills.addActorWithShadow(new ActorLibrary.Enemy("pokey", new Point(20, 24, 2), null, 18, 24, 1));
        biancoHills.addActorWithShadow(new ActorLibrary.Enemy("pokey", new Point(25, 23, 2), null, 23, 27, 1));
        biancoHills.addActorWithShadow(new ActorLibrary.Enemy("pokey", new Point(26, 24, 2), null, 24, 30, 1));


        // Short east 2 house piantas

        biancoHills.addActorWithShadow(new ActorLibrary.Pianta("blue", new Point(40,47, 2), null));
        biancoHills.addActorWithShadow(new ActorLibrary.Pianta("orange", new Point(42,47, 2), null));




        //gameEnvironment.addActor(new ActorLibrary.Goop(new Point(12, 34, 2), gameEnvironment));

        return biancoHills;
    }


    public static Environment createBiancoHillsMission1(Player player) {
        Environment biancoHills = createBiancoHills(player);

        EnvironmentBuilder biancoBuilder = new EnvironmentBuilder(biancoHills);

        biancoBuilder.getArea(17, 21, 45,47, 2,3).placeGoop();

        // Coins on the first path

        biancoBuilder.getArea(18,20, 50,53,2,3).placeCoins();

        // Coins leading up to the bridge

        biancoBuilder.getArea(19, 21, 30, 33, 2, 3).placeCoins();


        biancoBuilder.getArea(11, 16, 39, 40, 2, 3).placeCoins();


        // Bridge arch
        biancoBuilder.getArea(14, 19, 28, 29, 2, 4).placeShadowDelicateCoinArch();


        // Coin trail to shine

        biancoBuilder.getArea(44, 55, 33,34, 2,3).placeCoins();

        // Coins on top of the house

        biancoBuilder.getArea(21, 24, 42, 45, 8, 9).placeCoins();

        // Northern blue coin

        biancoHills.addActorWithShadow(new ActorLibrary.BlueCoin(new Point(6,24,6), null));

        // Short east 3 house blue coin

        biancoHills.addActorWithShadow(new ActorLibrary.BlueCoin(new Point(39, 45, 4), null));


        biancoHills.addActorWithShadow(new ActorLibrary.Shine(new Point(58, 23, 5), biancoHills, Mission.Objectives.clearedImmediately));



        return biancoHills;
    }


    public static Environment createBiancoHillsMission2(Player player) {
        Environment biancoHills = createBiancoHills(player);

        EnvironmentBuilder biancoBuilder = new EnvironmentBuilder(biancoHills);

        // Northern goop

        biancoBuilder.getArea(15,18,10, 14, 2,3).placeGoop();
        biancoBuilder.getArea(21,24,10, 14, 2,3).placeGoop();
        biancoBuilder.getArea(18,21,12, 14, 2,3).placeGoop();

        // Some Northwestern coins
        biancoBuilder.getArea(8, 9, 7,8, 6, 7).placeCoinArch();

        // Some northwestern coins after the cliff goop
        biancoBuilder.getArea(28, 29, 7,8, 6, 7).placeCoinArch();


        // House goop
        biancoBuilder.getArea(8, 11, 13, 15, 6,7).placeGoop();

        // Cliff Goop
        biancoBuilder.getArea(18,25,7,9,5,6).placeGoop();

        // Cliff Blue Coin
        biancoHills.addActorWithShadow(new ActorLibrary.BlueCoin(new Point(18,3,8),null));

        biancoHills.addActorWithShadow(new ActorLibrary.Shine(new Point(16, 7, 8), null, Mission.Objectives.clearGoop));

        return biancoHills;
    }

    public static Environment createBiancoHillsMission3(Player player) {
        Environment biancoHills = createBiancoHills(player);
        EnvironmentBuilder biancoBuilder = new EnvironmentBuilder(biancoHills);

        biancoHills.addActorWithShadow(new ActorLibrary.Enemy("pokey", new Point(80, 6, 5), null, 78, 82, 2));
        biancoHills.addActorWithShadow(new ActorLibrary.Enemy("pokey", new Point(79, 7, 5), null, 76, 80, 2));


        // Blue coin off to the right
        biancoHills.addActorWithShadow(new ActorLibrary.BlueCoin(new Point(90, 7, 10), null));

        // Goop protecting coin
        biancoBuilder.getArea(87,91, 5,10, 5,6).placeGoop();


        // Big wall that hides obstacles
        biancoBuilder.getArea(50, 74, 7,9,5, 9).fillType(EnvironmentBuilder.wall);
        biancoBuilder.getArea(50, 58, 7,9,5, 10).fillType(EnvironmentBuilder.wall);


        // First obstacle
        biancoBuilder.getArea(67,68, 5,7,5,6).fillType(EnvironmentBuilder.wall);


        // Second obstacle
        biancoBuilder.getArea(60,62, 5,7,5,8).fillType(EnvironmentBuilder.wall);

        // Platform that has blue coin
        biancoBuilder.getArea(55,57, 5,7,7,8).fillType(EnvironmentBuilder.wall);

        // Under-area coins
        biancoBuilder.getArea(55,56,5,7,5,6).placeCoins();

        biancoHills.addActorWithShadow(new ActorLibrary.BlueCoin(new Point(56,6,8), null));

        biancoBuilder.getArea().finalizeArea();

        biancoHills.addActorWithShadow(new ActorLibrary.Enemy("pokey", new Point(51,5,5), null, 47,49, 2));
        biancoHills.addActorWithShadow(new ActorLibrary.Enemy("pokey", new Point(42,5,5), null, 41,47, 2));


        // Shine
        biancoHills.addActorWithShadow(new ActorLibrary.Shine(new Point(37, 6, 8), null, Mission.Objectives.collectCoins));
        // Patch wall tile visual glitch

        biancoHills.tiles[57][8][8].imageFetcher = () -> Images.getImage("wallMR");

        return  biancoHills;
    }

    public static Environment createBiancoHillsMission4(Player player) {
        Environment biancoHills = createBiancoHills(player);
        EnvironmentBuilder biancoBuilder = new EnvironmentBuilder(biancoHills);

        // Behind the wall coins
        biancoBuilder.getArea(54, 85, 62,63,2,3).placeCoins();

        // Behind the wall red coin
        biancoHills.addActor(new ActorLibrary.RedCoin(new Point(38, 62, 2), null));


        // House coins
        biancoBuilder.getArea(38, 44, 41, 42, 6,7).placeCoins();

        // southwest corner goop

        biancoBuilder.getArea(39, 46, 52, 56, 2, 3).placeGoop();
        biancoHills.addActorWithShadow(new ActorLibrary.RedCoin(new Point(42, 54, 6), null));

        // Blue coin

        biancoHills.addActor(new ActorLibrary.BlueCoin(new Point(28,28,1), null));

        // On top of wall enemy
        biancoHills.addActor(new ActorLibrary.Enemy("pokey", new Point(31,37, 6), null, 31,35,2));

        // On top of wall red coin
        biancoHills.addActorWithShadow(new ActorLibrary.RedCoin(new Point(33,32,9), null));

        // fountain red coin

        biancoHills.addActor(new ActorLibrary.RedCoin(new Point(66,44,4), null));

        //coins under the bridge

        biancoBuilder.getArea(82,85,55,56,2,3).placeCoins();
        biancoBuilder.getArea(86,89,55,56,2,3).placeCoins();


        // red coin under the bridge

        // biancoHills.addActor(new ActorLibrary.RedCoin(new Point(85,86,55,56,2,3), null));

        // coin arch on the post

        biancoBuilder.getArea(84,85,52,53,7,8).placeCoinArch();

        // red coin on the post

        biancoHills.addActor(new ActorLibrary.RedCoin(new Point(85, 52,6,8,0,0), null));

        // Shine

        biancoHills.addActorWithShadow(new ActorLibrary.Shine(new Point(66,48,4), null, Mission.Objectives.clearRedCoins));


        return biancoHills;
    }

    public static Environment createNokiBay(Player player) {
        Environment nokiBay = new Environment(player, new Tile[100][100][13], null, gameCanvas, hud);

        EnvironmentBuilder nokiBuilder = new EnvironmentBuilder(nokiBay);


        // Northern cliff and grassy area

        nokiBuilder.getArea(0,100,0,4,0,12).fillType(EnvironmentBuilder.cliff);
        nokiBuilder.getArea(0,100,0,30,0,2).fillType(EnvironmentBuilder.grass);
        nokiBuilder.getArea(0,100,30,31,0,2).fillType(EnvironmentBuilder.cliff);


        nokiBuilder.getFloor().fillType(nokiBuilder.poison);

        // initial platforms

        nokiBuilder.getArea(30, 35, 32, 45, 1, 3).fillType(nokiBuilder.platform);

        nokiBuilder.getArea(23, 28, 32, 37, 1, 3).fillType(nokiBuilder.platform);



        // first trampoline

        nokiBuilder.getArea(15, 20, 45, 50, 1, 3).makeTrampoline();

        // rope to trampoline

        nokiBuilder.getArea(20, 32, 47,48, 2,3).fillType(EnvironmentBuilder.rope);


        // platform below trampoline 1

        nokiBuilder.getArea(15,20,53,65, 1,3).fillType(EnvironmentBuilder.platform);

        // West 1 house
        nokiBuilder.getArea(17,24, 14,19, 2,7).makeHouse();

        //West 2 house
        nokiBuilder.getArea(33,38, 14,19, 2,7).makeHouse();

        nokiBuilder.getArea(39,41,15,17,2,4).makeTrampoline();

        // Rope between houses

        nokiBuilder.getArea(23,34, 16,17,7,8).fillType(EnvironmentBuilder.rope);


        nokiBuilder.getArea(13,42, 12,21, 1,2).fillType(EnvironmentBuilder.path);

        // Rope from final platform to Blue Coin 3 platform

        nokiBuilder.getArea(19,30,57,58, 3,4).fillType(EnvironmentBuilder.rope);


        // Blue coin 3 platform

        nokiBuilder.getArea(28,34,55,60, 1,3).fillType(EnvironmentBuilder.platform);


        // dividing wall

        nokiBuilder.getArea(73,77,5,31,2,13).fillType(EnvironmentBuilder.wall);

        // First platform on the 2nd mission

        nokiBuilder.getArea(49,53,32,36,1,3).fillType(EnvironmentBuilder.platform);


        nokiBuilder.getArea(49,53,39,43,1,3).makeTrampoline();


        nokiBuilder.getArea(55,60,39,43,2,10).fillType(EnvironmentBuilder.wall);

        nokiBuilder.getArea(80,85,39,43,2,10).fillType(EnvironmentBuilder.wall);

        nokiBuilder.getArea(57,82,41,42,10,11).fillType(EnvironmentBuilder.rope);

        nokiBuilder.getArea(57,82,41,42,7,8).fillType(EnvironmentBuilder.rope);

        nokiBuilder.getArea(57,82,41,42,4,5).fillType(EnvironmentBuilder.rope);

        nokiBuilder.getArea(67,73,40,45,1,3).makeTrampoline();


        nokiBuilder.getArea().finalizeArea();

        return nokiBay;
    }

    public static Environment createNokiBayMission1(Player player) {
        Environment nokiBay = createNokiBay(player);

        EnvironmentBuilder nokiBuilder = new EnvironmentBuilder(nokiBay);


        // goop on initial platform 1

        nokiBuilder.getArea(24,27,34,37, 3,4).placeGoop();

        // coins on initial platform 1

        nokiBuilder.getArea(24,27,34,37,4,5).placeCoins();

        // Enemy on initial platform 2

        nokiBay.addActor(new ActorLibrary.Enemy("pokey", new Point(34,39,3), null, 31,33,1));

        nokiBuilder.getArea(17,18,48,49, 4,10).placeCoins();
        nokiBay.addActor(new ActorLibrary.BlueCoin(new Point(17,48,11), null));

        // Coins to south 1 platform

        nokiBuilder.getArea(17,18,54,55,4,6).placeCoins();

        // goop on West 1 house

        nokiBuilder.getArea(18,22, 16,19, 7,8).placeGoop();

        // Blue coin on house
        nokiBay.addActorWithShadow(new ActorLibrary.BlueCoin(new Point(20, 16, 11), null));


        // Coin arrow

        nokiBuilder.getArea(28,29,24,25,2,3).placeCoins();
        nokiBuilder.getArea(28,29,20,21,2,3).placeCoins();
        nokiBuilder.getArea(29,30,21,22,2,3).placeCoins();
        nokiBuilder.getArea(29,30,23,24,2,3).placeCoins();

        nokiBuilder.getArea(23,31,22,23,2,3).placeCoins();

        nokiBay.addActorWithShadow(new ActorLibrary.BlueCoin(new Point(31,59, 6), null));


        nokiBay.addActorWithShadow(new ActorLibrary.Enemy("pokey", new Point(21,57,4, 0,8,0), null, 21,26, 1));

        nokiBay.addActorWithShadow(new ActorLibrary.Shine(new Point(17,62,6), null, Mission.Objectives.clearedImmediately));

        return nokiBay;
    }

    public static Environment createNokiBayMission2(Player player) {
        Environment nokiBay = createNokiBay(player);
        EnvironmentBuilder nokiBuilder = new EnvironmentBuilder(nokiBay);


        // Trampoline Blue coin
        nokiBay.addActorWithShadow(new ActorLibrary.BlueCoin(new Point(42,15,12), null));

        nokiBuilder.getArea(42,52,19,20,2,3).placeCoins();
        nokiBuilder.getArea(52,53,19,29,2,3).placeCoins();

        // CoinFish

        // top row
        nokiBuilder.getArea(64,70,41,42,11,12).placeCoinsNoShadow();
        nokiBay.addActor(new ActorLibrary.RedCoin(new Point(77,41,11), null));

        // middle row
        nokiBuilder.getArea(63,65,41,42,8,9).placeCoinsNoShadow();
        nokiBay.addActor(new ActorLibrary.RedCoin(new Point(65, 41,8), null));
        nokiBuilder.getArea(66,78,41,42,8,9).placeCoinsNoShadow();

        nokiBuilder.getArea(63,78,41,42,9,10).placeCoinsNoShadow();
        nokiBuilder.getArea(63,78,41,42,6,7).placeCoinsNoShadow();


        //bottom row
        nokiBuilder.getArea(64,70,41,42,5,6).placeCoinsNoShadow();
        nokiBay.addActor(new ActorLibrary.RedCoin(new Point(77,41,5), null));


        nokiBay.addActorWithShadow(new ActorLibrary.Shine(new Point(83,41,12), null, Mission.Objectives.clearRedCoins));

        return nokiBay;
    }

    public static Environment createCoronaMountain(Player player) {
        Environment coronaMountain = new Environment(player, new Tile[100][100][10], null, gameCanvas, hud);

        EnvironmentBuilder coronaBuilder = new EnvironmentBuilder(coronaMountain);


        coronaBuilder.getFloor().fillType(EnvironmentBuilder.lava);

        coronaBuilder.getArea(19, 25, 10, 16, 1, 6).makePlatform();

        coronaBuilder.getArea(20, 26, 20, 24, 1, 6).makePlatform();

        coronaBuilder.getArea(22, 30, 29, 32, 1, 6).makePlatform();

        coronaBuilder.getArea(36, 42, 29, 32, 1, 6).makePlatform();

        // Hidden platform 3
        coronaBuilder.getArea(26, 36, 37, 42, 1, 6).makePlatform();


        coronaBuilder.getArea().finalizeArea();

        return coronaMountain;
    }

    public static Environment createCoronaMountainMission1(Player player) {
        Environment coronaMountain = createCoronaMountain(player);
        EnvironmentBuilder coronaBuilder = new EnvironmentBuilder(coronaMountain);

        coronaBuilder.getArea(27,35,38,40,6,7).placeGoop();

        coronaBuilder.getArea(27,30,40,42,6,7).placeGoop();
        coronaBuilder.getArea(32,35,40,42,6,7).placeGoop();

        coronaBuilder.getArea(27,35,42,43,6,7).placeGoop();


        // Goop on platform 2
        coronaBuilder.getArea(22,25,30,33,6,7).placeGoop();


        // Blue coin on secret platform
        coronaMountain.addActorWithShadow(new ActorLibrary.BlueCoin(new Point(38, 31, 8), coronaMountain));

        coronaMountain.addActorWithShadow(new ActorLibrary.Shine(new Point(24,31,9), null,Mission.Objectives.clearGoop));

        return coronaMountain;
    }

    public static Environment createCoronaMountainMission2(Player player) {
        Environment coronaMountain = createCoronaMountain(player);
        EnvironmentBuilder coronaBuilder = new EnvironmentBuilder(coronaMountain);


        // Coin loop on platform 1
        coronaBuilder.getArea(26,36,38,39,6,7).placeCoins();
        coronaBuilder.getArea(26,36,41,42,6,7).placeCoins();
        coronaBuilder.getArea(35,36,39,41,6,7).placeCoins();
        coronaBuilder.getArea(26,27,39,41,6,7).placeCoins();


        // Pokey on platform 2
        coronaMountain.addActorWithShadow(new ActorLibrary.Enemy("pokey", new Point(27,30,6),null, 23,28,2));

        // Goop on platform 3
        coronaBuilder.getArea(21,25,22, 24,6, 7).placeGoop();

        // Red coins on platform 4
        coronaMountain.addActorWithShadow(new ActorLibrary.RedCoin(new Point(26, 15,9), null));
        coronaMountain.addActorWithShadow(new ActorLibrary.RedCoin(new Point(26, 11,9), null));
        coronaMountain.addActorWithShadow(new ActorLibrary.RedCoin(new Point(17, 15,9), null));
        coronaMountain.addActorWithShadow(new ActorLibrary.RedCoin(new Point(17, 11,9), null));

        // Blue coin on platform 4
        coronaMountain.addActorWithShadow(new ActorLibrary.BlueCoin(new Point(30, 15,9), null));

        // Coin arch on platform 4

        coronaBuilder.getArea(20,21,13,14,7,10).placeCoinArch();

        // Shine on platform 4

        coronaMountain.addActorWithShadow(new ActorLibrary.Shine(new Point(22, 13,9), null, Mission.Objectives.clearRedCoins));



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
                        state = GameState.SHINE_COLLECTED;
                    }
                    gameEnvironment.render();

                    hud.render(gameEnvironment.player.location, gameCanvas);

                    System.out.println(gameEnvironment.player.location.toString());
                    if (Application.keyData.getIsTyped(KeyEvent.VK_ENTER)) {
                        state = GameState.PAUSE;
                    }
                    if (hud.lifeLevel == 0) {
                        state = GameState.DEATH;
                    }
                }
                case SHINE_COLLECTED -> {
                    gameEnvironment.render();
                    gameEnvironment.runLockedFrame();



                    if (Application.frameCount - collectionFrame > 100) {
                        state = GameState.MENU;
                    }

                    (new Text("Shine Get!", new Point(1,1, 0), Text.Size.DOUBLETIGHT)).render(null, gameCanvas);
                }
                case DEATH -> {
                    gameCanvas.clear();
                    renderDeath();
                    if (Application.keyData.getIsPressed(KeyEvent.VK_ENTER) || Application.keyData.getIsPressed(KeyEvent.VK_Z)) {
                        hud.lifeLevel = 8;
                        hud.waterLevel = 100;
                        state = GameState.MENU;
                    }

                    gameCanvas.setBackground(Color.DARK_GRAY);
                }
                case PAUSE -> {
                    gameEnvironment.render();
                    if (Application.keyData.getIsTyped(KeyEvent.VK_ENTER)) {
                        state = GameState.GAME;
                    }
                    if (Application.keyData.getIsPressed(KeyEvent.VK_LEFT)) {
                        gameEnvironment.player.fluddType = Player.FluddType.squirt;
                    }
                    if (Application.keyData.getIsPressed(KeyEvent.VK_RIGHT)) {
                        gameEnvironment.player.fluddType = Player.FluddType.jet;
                    }
                    renderPauseMenu();

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

    private void renderDeath() {
        Text deathHeader = new Text("game over", new Point(1,1,0,0,0,0), Text.Size.DOUBLETIGHT);
        deathHeader.render(null, gameCanvas);
        gameEnvironment.player.direction = Direction.DOWN;
        gameEnvironment.player.setFlashingBeginFrame(-100);
        gameEnvironment.player.render(gameEnvironment.player.location, gameCanvas);

    }

    private void renderPauseMenu() {
        Text pauseHeader = new Text("pause", new Point(2,1,0,8,0,0), Text.Size.DOUBLE);
        pauseHeader.render(null, gameCanvas);

        gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("pauseBG"), 3*5*16, 3*5*16));

        Text nozzleSubhead;
        if (gameEnvironment.player.fluddType == Player.FluddType.squirt) {
            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("squirtS"), 3 * 5 * 16 + 40, 3 * 5 * 16 + 40));
            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("hover"), 5 * 5 * 16, 3 * 5 * 16 + 40));
            nozzleSubhead = new Text("squirt", new Point(3,5,0,4,4,0));
        } else {
            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("squirt"), 3 * 5 * 16 + 40, 3 * 5 * 16 + 40));
            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("hoverS"), 5 * 5 * 16, 3 * 5 * 16 + 40));
            nozzleSubhead = new Text("hover", new Point(3,5,0,8,4,0));
        }

        nozzleSubhead.render(null, gameCanvas);


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

        Text t = new Text(selectedMission.environmentName, new Point(0,0,0, 0, 8, 0), Text.Size.DOUBLETIGHT);
        t.render(null, gameCanvas);
        Text t2 = new Text(selectedMission.name, new Point(0,6,0,4,4,0));
        t2.render(null, gameCanvas);


        int shineY = nearest5((int) (5 * 48 + Math.round(16 * -Math.sin(i / 10.0))));
        int sideShineY = nearest5((int) (5 * 48 + 8 + Math.round(8 * -Math.sin(i / 10.0))));


        gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("shineFlare"+Application.frameNumber(200,3)),72*5-16*5,   shineY-8*5));


        if (selectedMission.hasBeenCompleted) {
            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("bigShine"), 72 * 5 - 8 * 5, shineY));
        } else {
            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("greyBigShine"), 72 * 5 - 8 * 5, shineY));
        }

        if (missionIdx != 0) {
            if (missions[missionIdx-1].hasBeenCompleted) {
                gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("bigShine"), 72 * 2 - 8 * 5, sideShineY));
            } else {
                gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("greyBigShine"), 72 * 2 - 8 * 5, sideShineY));
            }

            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("leftArrow"),shineY-200,   260));

        }

        if (missionIdx != missions.length-1) {
            if (missions[missionIdx+1].hasBeenCompleted) {
                gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("bigShine"), 72 * 8 - 8 * 5, sideShineY));
            } else {
                gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("greyBigShine"), 72 * 8 - 8 * 5, sideShineY));
            }

            gameCanvas.imagesToRender.push(new RenderedImage(Images.preparedImage("rightArrow"),940-shineY,   260));
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
        if (Application.keyData.getIsTyped(KeyEvent.VK_Z)) {
            currentMission = selectedMission;
            gameEnvironment = selectedMission.environment;
            selectedMission.initialize();
            state = GameState.GAME;
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
        TITLE, GAME, MENU, PAUSE, SHINE_COLLECTED, DEATH;
    }

}
