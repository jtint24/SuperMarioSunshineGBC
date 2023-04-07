public class BiancoHillsMissions {
    public static Mission[] makeBiancoHillsMissions() {


        Mission coinsOfTheLake = new Mission("the Coins of the \nLake", Mission.Objectives.collectShine, Main.createBiancoHillsMission1(new Player(new Point(18, 57, 2), null)));

        Mission goopCleanup = new Mission("Goop cleanup on \nthe cliffs", Mission.Objectives.collectShine, Main.createBiancoHillsMission2(new Player(new Point(19, 10, 2), null)));
        Mission theHillsideSecret = new Mission("The Hillside Secret", Mission.Objectives.collectShine, Main.createBiancoHillsMission3(new Player(new Point(78, 9, 5), null)));

        Mission redCoinsOfTheVillage = new Mission("Red Coins of the \nVillage", Mission.Objectives.collectShine, Main.createBiancoHillsMission4(new Player(new Point(66, 40, 2), null)) );

        Mission theTrickyRuins = new Mission("The Tricky Ruins", Mission.Objectives.collectShine, Main.createNokiBayMission1(new Player(new Point(29,29,2), null)));


        return new Mission[] {theTrickyRuins, coinsOfTheLake, goopCleanup , theHillsideSecret, redCoinsOfTheVillage, /*coinsOfTheLake5*/};
    }
}
