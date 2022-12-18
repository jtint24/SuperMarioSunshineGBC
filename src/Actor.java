public class Actor {
    int x;
    int y;
    int z;

    // higher means draw this one last
    // lower means draw this one first
    int drawLayer() {
        return z-y;
    }
}
