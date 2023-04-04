public class BiancoHillsMissions {
    public static Mission[] makeBiancoHillsMissions() {


        //Mission coinsOfTheLake = new Mission("Coins of the Lake", Mission.Objectives.collectShine, Main.createBiancoHillsMission1(new Player(new Point(18, 57, 2), null)));

        Mission coinsOfTheLake = new Mission("The Hillside Secret", Mission.Objectives.collectShine, Main.createBiancoHillsMission3(new Player(new Point(78, 9, 5), null)));
        Mission coinsOfTheLake2 = new Mission("Goop cleanup", Mission.Objectives.collectShine, Main.createBiancoHillsMission2(new Player(new Point(19, 10, 2), null)));

        Mission coinsOfTheLake3 = new Mission("Test two", Mission.Objectives.collectShine, Main.createNokiBay(new Player(new Point(30, 30, 1), null)) );
        Mission coinsOfTheLake4 = new Mission("Test three", Mission.Objectives.collectShine, Main.createGelatoBeach(new Player(new Point(30, 30, 1), null)) );
        //Mission coinsOfTheLake5 = new Mission("Test5", Mission.Objectives.collectShine, Main.createDelfinoPlaza(player) );



        return new Mission[] {coinsOfTheLake, coinsOfTheLake2 , coinsOfTheLake3, coinsOfTheLake4, /*coinsOfTheLake5*/};
    }
}
