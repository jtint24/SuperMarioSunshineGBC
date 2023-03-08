public class BiancoHillsMissions {
    public static Mission[] makeBiancoHillsMissions(Player player) {
        Mission coinsOfTheLake = new Mission("Test", Mission.Objectives.collectShine, Main.createDelfinoPlaza(player) );



        return new Mission[] {coinsOfTheLake};
    }
}
