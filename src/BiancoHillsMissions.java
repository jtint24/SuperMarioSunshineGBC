public class BiancoHillsMissions {
    public static Mission[] makeBiancoHillsMissions() {
        Mission coinsOfTheLake = new Mission("Test", Mission.Objectives.collectShine, Main.createDelfinoPlaza(new Player(new Point(30, 30, 1), null)));
        Mission coinsOfTheLake2 = new Mission("Test2", Mission.Objectives.collectShine, Main.createNokiBay(new Player(new Point(30, 30, 1), null)));
       //Mission coinsOfTheLake3 = new Mission("Test3", Mission.Objectives.collectShine, Main.createDelfinoPlaza(player) );
        //Mission coinsOfTheLake4 = new Mission("Test4", Mission.Objectives.collectShine, Main.createDelfinoPlaza(player) );
        //Mission coinsOfTheLake5 = new Mission("Test5", Mission.Objectives.collectShine, Main.createDelfinoPlaza(player) );



        return new Mission[] {coinsOfTheLake, coinsOfTheLake2 /*, coinsOfTheLake3, coinsOfTheLake4, coinsOfTheLake5*/};
    }
}
