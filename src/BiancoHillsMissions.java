public class BiancoHillsMissions {
    public static Mission[] makeBiancoHillsMissions() {
        Mission coinsOfTheLake = new Mission("Test", Mission.Objectives.collectShine,Main.createDelfinoPlaza(Main.gameEnvironment.player) );



        return new Mission[] {coinsOfTheLake};
    }
}
