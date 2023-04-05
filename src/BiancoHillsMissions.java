public class BiancoHillsMissions {
    public static Mission[] makeBiancoHillsMissions() {


        Mission coinsOfTheLake = new Mission("the Coins of the Lake", Mission.Objectives.collectShine, Main.createBiancoHillsMission1(new Player(new Point(18, 57, 2), null)));

        Mission goopCleanup = new Mission("Goop cleanup on \nthe cliffs", Mission.Objectives.collectShine, Main.createBiancoHillsMission2(new Player(new Point(19, 10, 2), null)));
        Mission theHillsideSecret = new Mission("The Hillside Secret", Mission.Objectives.collectShine, Main.createBiancoHillsMission3(new Player(new Point(78, 9, 5), null)));

       // Mission coinsOfTheLake3 = new Mission("Test two", Mission.Objectives.collectShine, Main.createNokiBay(new Player(new Point(30, 30, 1), null)) );
        Mission redCoinsOfTheVillage = new Mission("Red Coins of the \nVillage", Mission.Objectives.collectShine, Main.createBiancoHillsMission4(new Player(new Point(66, 40, 2), null)) );
        //Mission coinsOfTheLake5 = new Mission("Test5", Mission.Objectives.collectShine, Main.createDelfinoPlaza(player) );



        return new Mission[] {redCoinsOfTheVillage, coinsOfTheLake, goopCleanup , theHillsideSecret, redCoinsOfTheVillage, /*coinsOfTheLake5*/};
    }
}
