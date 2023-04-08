public class BiancoHillsMissions {
    public static Mission[] makeBiancoHillsMissions() {


        Mission coinsOfTheLake = new Mission("the Coins of the \nLake", "Bianco Hills", Mission.Objectives.collectShine, Main.createBiancoHillsMission1(new Player(new Point(18, 57, 2), null)));

        Mission goopCleanup = new Mission("Goop cleanup on \nthe cliffs","Bianco Hills", Mission.Objectives.collectShine, Main.createBiancoHillsMission2(new Player(new Point(19, 10, 2), null)));
        Mission theHillsideSecret = new Mission("The Hillside Secret", "Bianco Hills",Mission.Objectives.collectShine, Main.createBiancoHillsMission3(new Player(new Point(78, 9, 5), null)));

        Mission redCoinsOfTheVillage = new Mission("Red Coins of the \nVillage", "Bianco Hills",Mission.Objectives.collectShine, Main.createBiancoHillsMission4(new Player(new Point(66, 40, 2), null)) );

        Mission theTrickyRuins = new Mission("The Tricky Ruins", "   Noki Bay ", Mission.Objectives.collectShine, Main.createNokiBayMission1(new Player(new Point(29,29,2), null)));

        Mission redCoinFish = new Mission("The Red Coin Fish", "   Noki Bay ", Mission.Objectives.collectShine, Main.createNokiBayMission2(new Player(new Point(39,20,2), null)));


        Mission coronaCleanup = new Mission("Cleanup on\n Corona mountain", " Corona mtn ", Mission.Objectives.collectShine, Main.createCoronaMountainMission1(new Player(new Point(31,40,6,-8,0,0), null)));


        return new Mission[] {coronaCleanup, coinsOfTheLake, goopCleanup , theHillsideSecret, redCoinsOfTheVillage, theTrickyRuins, redCoinFish, coronaCleanup/*coinsOfTheLake5*/};
    }
}
