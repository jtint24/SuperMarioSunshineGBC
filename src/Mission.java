public class Mission {
    String name;
    Environment environment;
    Objective objective;
    boolean hasBeenCompleted = false;


    Mission(String name, Objective objective, Environment environment) {
        this.name = name;
        this.objective = objective;
        this.environment = environment;
    }

    boolean isComplete() {
        boolean criteriaMet = objective.completionCriteria(environment);
        if (criteriaMet) {
            hasBeenCompleted = true;
        }
        return hasBeenCompleted;
    }

    void initialize() {
        // environment.player.location = new Point(20,20,10);
        Main.hud.waterLevel = 100;
        // environment.getShine().location = new Point(30,30, 7);
    }


    interface Objective {
        boolean completionCriteria(Environment e);
    }

    static class Objectives {
        public static Objective collectCoins = (Environment e) -> {
            return !e.hasActorType(ActorLibrary.Coin.class);
        };
        public static Objective clearGoop = (Environment e) -> {
            return !e.hasActorType(ActorLibrary.Goop.class);
        };
        public static Objective clearRedCoins = (Environment e) -> {
            return !e.hasActorType(ActorLibrary.RedCoin.class);
        };
        static Objective collectShine = (Environment e) -> {
            for (Actor actor : e.actors) {
                if (actor instanceof ActorLibrary.Shine) {
                    return ((ActorLibrary.Shine) actor).collected;
                }
            }
            return false;
        };
        public static Objective clearedImmediately = (Environment e) -> true;

    }
}
