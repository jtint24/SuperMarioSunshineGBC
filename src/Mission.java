public abstract class Mission {
    String name;
    boolean hasBeenCompleted = false;

    Mission(String name) {
        this.name = name;
    }

    boolean isComplete(Environment e) {
        boolean criteriaMet = completionCriteria(e);
        if (criteriaMet) {
            hasBeenCompleted = true;
        }
        return hasBeenCompleted;
    }

    abstract boolean completionCriteria(Environment e);
}
