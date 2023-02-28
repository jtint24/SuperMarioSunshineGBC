public class BiancoHillsMissions {
    public static Environment makeBiancoHillsMissions() {
        Mission coinsOfTheLake = new Mission("Coins of the Lake") {
            @Override
            boolean completionCriteria(Environment e) {
                return e.hasActorType(ActorLibrary.Coin.class);
            }
        };



        return null;
    }
}
