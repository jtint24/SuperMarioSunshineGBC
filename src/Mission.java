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

    boolean isComplete(Environment e) {
        boolean criteriaMet = objective.completionCriteria(e);
        if (criteriaMet) {
            hasBeenCompleted = true;
        }
        return hasBeenCompleted;
    }


    interface Objective {
        boolean completionCriteria(Environment e);
    }

    static class Objectives {
        static Objective collectShine = (Environment e) -> {
            for (Actor actor : e.actors) {
                if (actor instanceof ActorLibrary.Shine) {
                    return ((ActorLibrary.Shine) actor).collected;
                }
            }
            return false;
        };

    }
}
