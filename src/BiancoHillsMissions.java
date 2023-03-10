public class BiancoHillsMissions {
    public static Mission[] makeBiancoHillsMissions() {
        Mission coinsOfTheLake = new Mission("Test", Mission.Objectives.collectShine, Main.createDelfinoPlaza(new Player(new Point(30, 30, 1), null)));
        Mission coinsOfTheLake2 = new Mission("Test one", Mission.Objectives.collectShine, Main.createDelfinoPlaza(new Player(new Point(30, 30, 1), null)));
        Mission coinsOfTheLake3 = new Mission("Test two", Mission.Objectives.collectShine, Main.createDelfinoPlaza(new Player(new Point(30, 30, 1), null)) );
        Mission coinsOfTheLake4 = new Mission("Test three", Mission.Objectives.collectShine, Main.createDelfinoPlaza(new Player(new Point(30, 30, 1), null)) );
        //Mission coinsOfTheLake5 = new Mission("Test5", Mission.Objectives.collectShine, Main.createDelfinoPlaza(player) );



        return new Mission[] {coinsOfTheLake, coinsOfTheLake2 , coinsOfTheLake3, coinsOfTheLake4, /*coinsOfTheLake5*/};
    }
}
