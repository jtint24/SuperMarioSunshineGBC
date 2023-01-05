public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    char charCode() {
        return this.toString().charAt(0);
    }
}
